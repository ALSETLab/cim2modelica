package cim2model;

import java.util.Map;

import cim2model.cim.*;
import cim2model.ipsl.cimmap.*;
import cim2model.modelica.*;
import cim2model.ipsl.buses.Bus;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

public class joke_makeModel 
{
	public static void main(String[] args) 
	{
		Map<Resource, RDFNode> components;
		String [] cimClassResource;
		String _cimSource= args[0];
		ModelDesigner cartografo;
		ModelBuilder constructor;
		
		cartografo= new ModelDesigner(_cimSource);
		constructor= new ModelBuilder("IEEE_9Bus");
		components= cartografo.load_CIMModel();
		for (Resource key : components.keySet())
		{	
			cimClassResource= cartografo.get_CIMComponentName(key);
			/* subjectResource[0] is the rfd_id, subjectResource[1] is the CIM name */
			if (cimClassResource[1].equals("Terminal"))
			{
				String [] equipmentResource, topologyResource;
//				System.out.println("rfd_id: "+ cimClassResource[0] + " cim name: "+ cimClassResource[1]);
				CIMTerminal conector= 
						cartografo.create_TerminalModelicaMap(key, "./res/map/cim_iteslalibrary_pwpin.xml", cimClassResource);
				PwPinMap mapTerminal= conector.get_TerminalMap();
				MOConnector mopin= constructor.create_PinConnector(mapTerminal);
				/* after loading terminal, load the resource connected to it, aka, ConductingEquipment */
				equipmentResource= cartografo.get_CIMComponentName(conector.get_ConductingEquipmentMap());
				topologyResource= cartografo.get_CIMComponentName(conector.get_TopologicalNodeMap());
				//According to CIM Composer, EC has one terminal
				if (equipmentResource[1].equals("SynchronousMachine"))
				{
//					System.out.println("rfd_id: "+ equipmentResource[0] + " cim name: "+ equipmentResource[1]);
					SynchronousMachineMap mapSyncMach= cartografo.create_MachineModelicaMap(conector.get_ConductingEquipmentMap(), 
							"./res/map/cim_iteslalibrary_syncmach.xml", equipmentResource);
					MOClass momachine= constructor.create_MachineComponent(mapSyncMach);
					momachine.add_Terminal(mopin);
					constructor.add_deviceNetwork(momachine);
				}
				//According to CIM Composer, EC has one terminal
				if (equipmentResource[1].equals("EnergyConsumer"))
				{
//					System.out.println("rfd_id: "+ equipmentResource[0] + " cim name: "+ equipmentResource[1]);
					PwLoadPQMap mapEnergyC= cartografo.create_LoadModelicaMap(conector.get_ConductingEquipmentMap(), 
							"./res/map/cim_iteslalibrary_pwloadpq.xml", equipmentResource);
					MOClass moload= constructor.create_LoadComponent(mapEnergyC);
					moload.add_Terminal(mopin);
					constructor.add_deviceNetwork(moload);
				}
				//According to CIM Composer, ACLineSegment has two terminals
				if (equipmentResource[1].equals("ACLineSegment"))
				{
					MOClass moline= constructor.get_equipmentNetwork(equipmentResource[0]);
					if (moline!= null){
						/* condition to check if the line already exist in the model, true, add the second terminal */
						moline.add_Terminal(mopin);
					}
					else 
					{/* false, create map of the line and add the first terminal */
//						System.out.println("rfd_id: "+ equipmentResource[0] + " cim name: "+ equipmentResource[1]);
						PwLineMap mapACLine= cartografo.create_LineModelicaMap(conector.get_ConductingEquipmentMap(), 
								"./res/map/cim_iteslalibrary_pwline.xml", equipmentResource);
						moline= constructor.create_LineComponent(mapACLine);
						moline.add_Terminal(mopin);
						constructor.add_deviceNetwork(moline);
					}
				}
				//According to CIM Composer, TN has 1..N terminals
				if (topologyResource[1].equals("TopologicalNode"))
				{
					MOClass mobus= constructor.get_equipmentNetwork(topologyResource[0]);
					if (mobus!= null){
						/* condition to check if the line already exist in the model, true, add the second terminal */
						mobus.add_Terminal(mopin);
					}
					else
					{
						/* false, create map of the line and add the first terminal */
//						System.out.println("rfd_id: "+ topologyResource[0] + " cim name: "+ topologyResource[1]);
						BusExt2Map mapTopoNode= cartografo.create_BusModelicaMap(conector.get_TopologicalNodeMap(), 
										"./res/map/cim_iteslalibrary_busext2.xml", topologyResource);
						mobus= constructor.create_BusComponent(mapTopoNode);
						mobus.add_Terminal(mopin);
						constructor.add_deviceNetwork(mobus);
					}
				}
			}
			//PowerTransformerEnd at the same level as Terminal? The first class contains useful information about the transformer
			if (cimClassResource[1].equals("PowerTransformerEnd"))
			{
				String [] transformerResource, terminalResource;
//				System.out.println("rfd_id: "+ cimClassResource[0] + " cim name: "+ cimClassResource[1]);
				CIMTransformerEnd transformerEnd= 
						cartografo.create_TransformerModelicaMap(key, 
								"./res/map/cim_iteslalibrary_twowindingtransformer.xml", 
								cimClassResource);
				TwoWindingTransformerMap mapPowerTrans= transformerEnd.get_TransformerMap();
				transformerResource= cartografo.get_CIMComponentName(transformerEnd.get_PowerTransformerMap());
				//processing info from terminalResource, here is to create the MOConnector
				terminalResource= cartografo.get_CIMComponentName(transformerEnd.get_TerminalMap());
				CIMTerminal conector= cartografo.create_TerminalModelicaMap(transformerEnd.get_TerminalMap(), 
								"./res/map/cim_iteslalibrary_pwpin.xml", terminalResource);
				//Create the terminal object associated with this PowerTransformerEnd
				PwPinMap mapTerminal= conector.get_TerminalMap();
				MOConnector mopin= constructor.create_PinConnector(mapTerminal);
				//Create additional attribute for the ratio tap changer associated with the PowerTransformerEnd
				MOAttribute moRatio= constructor.create_TransformerEndAttribute(mapPowerTrans);
				//check if the PowerTransformer is already in the network
				MOClass moTransformer= constructor.get_equipmentNetwork(transformerResource[0]);
				if (moTransformer!= null) {
					moTransformer.add_Terminal(mopin);
					moTransformer.add_Attribute(moRatio);
				}
				else {
					moTransformer= constructor.create_TransformerComponent(mapPowerTrans);
					moTransformer.add_Terminal(mopin);
					moTransformer.add_Attribute(moRatio);
					constructor.add_deviceNetwork(moTransformer);
				}
			}
		}
		constructor.connect_Components(cartografo.get_ConnectionMap());
		constructor.save_ModelicaFile(constructor.get_Network().to_ModelicaClass());
	}
}
