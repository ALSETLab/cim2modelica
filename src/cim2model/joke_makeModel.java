package cim2model;

import java.util.Map;

import cim2model.cim.*;
import cim2model.mapping.modelica.*;
import cim2model.modelica.*;
import cim2model.modelica.ipsl.buses.Bus;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

public class joke_makeModel {
	
	private static final String RDF_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private static final String CIM16_NS = "http://iec.ch/TC57/2013/CIM-schema-cim16#";
    
    private static final String MD_NS= "http://iec.ch/TC57/61970-552/ModelDescription/1#";
    
    private static final String ENTSOE_NS= "http://entsoe.eu/Secretariat/2013/ProfileExtension/3#";
	
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
						cartografo.create_TerminalModelicaMap(key, "./res/cim_iteslalibrary_pwpin.xml", cimClassResource);
				PwPinMap mapTerminal= conector.get_TerminalMap();
				MOConnector mopin= constructor.create_PinConnector(mapTerminal);
				/* after loading terminal, load the resource connected to it, aka, ConductingEquipment */
				equipmentResource= cartografo.get_CIMComponentName(conector.get_ConductingEquipmentMap());
				topologyResource= cartografo.get_CIMComponentName(conector.get_TopologicalNodeMap());
				//According to CIM Composer, EC has one terminal
				if (equipmentResource[1].equals("SynchronousMachine"))
				{
//					System.out.println("rfd_id: "+ equipmentResource[0] + " cim name: "+ equipmentResource[1]);
					GENSALMap mapSyncMach= cartografo.create_MachineModelicaMap(conector.get_ConductingEquipmentMap(), 
							"./res/cim_iteslalibrary_gensal.xml", equipmentResource);
					MOClass momachine= constructor.create_MachineComponent(mapSyncMach);
					momachine.add_Terminal(mopin);
					//TODO: save this to a file
					constructor.add_deviceNetwork(momachine);
				}
				//According to CIM Composer, EC has one terminal
				if (equipmentResource[1].equals("EnergyConsumer"))
				{
//					System.out.println("rfd_id: "+ equipmentResource[0] + " cim name: "+ equipmentResource[1]);
					PwLoadPQMap mapEnergyC= cartografo.create_LoadModelicaMap(conector.get_ConductingEquipmentMap(), 
							"./res/cim_iteslalibrary_pwloadpq.xml", equipmentResource);
					MOClass moload= constructor.create_LoadComponent(mapEnergyC);
					moload.add_Terminal(mopin);
					//TODO: save this to a file
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
								"./res/cim_iteslalibrary_pwline.xml", equipmentResource);
						moline= constructor.create_LineComponent(mapACLine);
						moline.add_Terminal(mopin);
						constructor.add_deviceNetwork(moline);
					}
				}
				//According to CIM Composer, TN has 1..N terminals
				if (topologyResource[1].equals("TopologicalNode"))
				{//TODO change type of bus for mapping, use BusExt2 
					MOClass mobus= constructor.get_equipmentNetwork(topologyResource[0]);
					if (mobus!= null){
						/* condition to check if the line already exist in the model, true, add the second terminal */
						mobus.add_Terminal(mopin);
					}
					else
					{
						/* false, create map of the line and add the first terminal */
//						System.out.println("rfd_id: "+ topologyResource[0] + " cim name: "+ topologyResource[1]);
						PwBusMap mapTopoNode= cartografo.create_BusModelicaMap(conector.get_TopologicalNodeMap(), 
										"./res/cim_iteslalibrary_pwbus.xml", topologyResource);
						mobus= constructor.create_BusComponent(mapTopoNode);
						mobus.add_Terminal(mopin);
						//TODO: save this to a file
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
								"./res/cim_iteslalibrary_twowindingtransformer.xml", 
								cimClassResource);
				TwoWindingTransformerMap mapPowerTrans= transformerEnd.get_TransformerMap();
				transformerResource= cartografo.get_CIMComponentName(transformerEnd.get_PowerTransformerMap());
				//processing info from terminalResource, here is to create the MOConnector
				terminalResource= cartografo.get_CIMComponentName(transformerEnd.get_TerminalMap());
				CIMTerminal conector= cartografo.create_TerminalModelicaMap(transformerEnd.get_TerminalMap(), 
								"./res/cim_iteslalibrary_pwpin.xml", terminalResource);
				//Create the terminal object associated with this PowerTransformerEnd
				PwPinMap mapTerminal= conector.get_TerminalMap();
				MOConnector mopin= constructor.create_PinConnector(mapTerminal);
				//Create additional attribute for the ratio tap changer associated with the PowerTransformerEnd
				//TODO TapChanger.ltcFlag parameter, get the value from endNumber= 1
				//TODO moRation must be MOAttribute [] type
				MOAttribute moRatio= constructor.create_TransformerEndAttribute(mapPowerTrans);
				//check if the PowerTransformer is already in the network
				MOClass moTransformer= constructor.get_equipmentNetwork(transformerResource[0]);
				if (moTransformer!= null) {
					//TODO update the value of PowerTransformer Attributes with new attributes from a new PowerTransformerEnd
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
