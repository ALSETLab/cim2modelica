package cim2model;

import java.util.ArrayList;
import java.util.Map;

import cim2model.cim.*;
import cim2model.cim.map.*;
import cim2model.cim.map.ipsl.base.*;
import cim2model.cim.map.ipsl.branches.*;
import cim2model.cim.map.ipsl.buses.*;
import cim2model.cim.map.ipsl.connectors.*;
import cim2model.cim.map.ipsl.loads.*;
import cim2model.cim.map.ipsl.machines.*;
import cim2model.cim.map.ipsl.transformers.*;
import cim2model.modelica.*;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

public class AlfaFactoryCIMOD 
{
	public static void main(String[] args) 
	{
		Map<Resource, RDFNode> components;
		String [] cimClassResource;
		String _cimSource= args[0];
		ModelDesigner cartografo;
		ModelBuilder constructor;
		
		cartografo= new ModelDesigner(_cimSource);
		constructor= new ModelBuilder(args[1]);
		components= cartografo.load_CIMModel();
		for (Resource key : components.keySet())
		{	
			cimClassResource= cartografo.get_CIMComponentName(key);
			/* subjectResource[0] is the rfd_id, subjectResource[1] is the CIM name */
			if (cimClassResource[1].equals("Terminal"))
			{
				String [] equipmentResource, topologyResource;
				PwPinMap mapTerminal= 
						cartografo.create_TerminalModelicaMap(key, "./res/map/cim_iteslalibrary_pwpin.xml", cimClassResource);
				MOConnector mopin= constructor.create_PinConnector(mapTerminal);
				/* after loading terminal, load the resource connected to it, aka, ConductingEquipment */
				equipmentResource= cartografo.get_CIMComponentName(
						cartografo.get_CurrentConnectionMap().getConductingEquipment());
				topologyResource= cartografo.get_CIMComponentName(
						cartografo.get_CurrentConnectionMap().getTopologicalNode());
				/* According to CIM Composer, SynchMachine has one terminal */
				if (equipmentResource[1].equals("SynchronousMachine"))
				{
					MOClass momachine= null;
					String machineType= cartografo.typeOfSynchronousMachine(
							cartografo.get_CurrentConnectionMap().getConductingEquipment());
					if (machineType.equals("GENCLS")) {
						GENCLSMap mapSyncMach= cartografo.create_GENCLSModelicaMap(
								cartografo.get_CurrentConnectionMap().getConductingEquipment(), 
								"./res/map/cim_iteslalibrary_gencls.xml", equipmentResource);
						momachine= constructor.create_GENCLSComponent(mapSyncMach);
					}
					if (machineType.equals("GENROU")) {
						GENROUMap mapSyncMach= cartografo.create_GENROUModelicaMap(
								cartografo.get_CurrentConnectionMap().getConductingEquipment(), 
								"./res/map/cim_iteslalibrary_genrou.xml", equipmentResource);
						momachine= constructor.create_GENROUComponent(mapSyncMach);
					}
					if (machineType.equals("GENSAL")){
						GENSALMap mapSyncMach= cartografo.create_GENSALModelicaMap(
								cartografo.get_CurrentConnectionMap().getConductingEquipment(), 
								"./res/map/cim_iteslalibrary_gensal.xml", equipmentResource);
						momachine= constructor.create_GENSALComponent(mapSyncMach);
					}
					if (machineType.equals("GENROE")){
						GENROEMap mapSyncMach= cartografo.create_GENROEModelicaMap(
								cartografo.get_CurrentConnectionMap().getConductingEquipment(), 
								"./res/map/cim_iteslalibrary_genroe.xml", equipmentResource);
						momachine= constructor.create_GENROEComponent(mapSyncMach);
					}
					momachine.add_Terminal(mopin);
					momachine.update_powerFlow(mopin);
					constructor.add_deviceNetwork(momachine);
				}
				/* According to CIM Composer, EnergyConsumer has one terminal */
				if (equipmentResource[1].equals("EnergyConsumer"))
				{
					LoadMap mapEnergyC= cartografo.create_LoadModelicaMap(
							cartografo.get_CurrentConnectionMap().getConductingEquipment(), 
							"./res/map/cim_iteslalibrary_load.xml", equipmentResource);
					MOClass moload= constructor.create_LoadComponent(mapEnergyC);
					moload.add_Terminal(mopin);
					moload.update_powerFlow(mopin);
					constructor.add_deviceNetwork(moload);
				}
				/* According to CIM Composer, ACLineSegment has two terminals */
				if (equipmentResource[1].equals("ACLineSegment"))
				{
					MOClass moline= constructor.get_equipmentNetwork(equipmentResource[0]);
					if (moline!= null)
					{/* condition to check if the line already exist in the model, true, add the second terminal */
						moline.add_Terminal(mopin);
					}
					else 
					{/* false, create map of the line and add the first terminal */
						PwLineMap mapACLine= cartografo.create_LineModelicaMap(
								cartografo.get_CurrentConnectionMap().getConductingEquipment(), 
								"./res/map/cim_iteslalibrary_pwline.xml", equipmentResource);
						moline= constructor.create_LineComponent(mapACLine);
						moline.add_Terminal(mopin);
						constructor.add_deviceNetwork(moline);
					}
				}
				/* According to CIM Composer, TN has 1..N terminals */
				if (topologyResource[1].equals("TopologicalNode"))
				{
					MOClass mobus= constructor.get_equipmentNetwork(topologyResource[0]);
					if (mobus!= null)
					{/* condition to check if the line already exist in the model, true, add the second terminal */
						mobus.add_Terminal(mopin);
					}
					else
					{/* false, create map of the line and add the first terminal */
						PwBusMap mapTopoNode= cartografo.create_BusModelicaMap(
								cartografo.get_CurrentConnectionMap().getTopologicalNode(), 
										"./res/map/cim_iteslalibrary_pwbus.xml", topologyResource);
						mobus= constructor.create_BusComponent(mapTopoNode);
						mobus.add_Terminal(mopin);
						constructor.add_deviceNetwork(mobus);
					}
				}
			}
			if (cimClassResource[1].equals("PowerTransformerEnd"))
			{
				String [] transformerResource, terminalResource;
				CIMTransformerEnd transformerEnd= cartografo.create_TransformerModelicaMap(key, 
								"./res/map/cim_iteslalibrary_twowindingtransformer.xml", 
								cimClassResource);
				TwoWindingTransformerMap mapPowerTrans= transformerEnd.get_TransformerMap();
				transformerResource= cartografo.get_CIMComponentName(transformerEnd.get_PowerTransformerMap());
				terminalResource= cartografo.get_CIMComponentName(transformerEnd.get_TerminalMap());
				PwPinMap mapTerminal= cartografo.create_TerminalModelicaMap(transformerEnd.get_TerminalMap(), 
								"./res/map/cim_iteslalibrary_pwpin.xml", terminalResource);
				/*Create the terminal object associated with this PowerTransformerEnd*/
				MOConnector mopin= constructor.create_PinConnector(mapTerminal);
				/*Create additional attribute for the ratio tap changer associated with the PowerTransformerEnd*/
				ArrayList<MOAttribute> moPowTransEnd= constructor.create_AttTransformerEnd(mapPowerTrans);
				/*check if the PowerTransformer is already in the network*/
				MOClass moTransformer= constructor.get_equipmentNetwork(transformerResource[0]);
				if (moTransformer!= null) {
					moTransformer.add_Terminal(mopin);
					for(MOAttribute moparam: moPowTransEnd)
						moTransformer.add_Attribute(moparam);
				}
				else {
					moTransformer= constructor.create_TransformerComponent(mapPowerTrans);
					moTransformer.add_Terminal(mopin);
					for(MOAttribute moparam: moPowTransEnd)
						moTransformer.add_Attribute(moparam);
					constructor.add_deviceNetwork(moTransformer);
				}
				moPowTransEnd.clear(); moPowTransEnd= null;
			}
//			if (cimClassResource[1].equals("Fault"))
//			{
//				System.out.println("rfd_id: "+ cimClassResource[0] + " cim name: "+ cimClassResource[1]);
//				PwFaultMap mapFault= cartografo.create_FaultModelicaMap(key, "./res/map/cim_iteslalibrary_pwfault.xml", cimClassResource);
//				MOClass mofault= constructor.create_FaultComponent(mapFault);
//				
//				constructor.add_deviceNetwork(mofault);
//				System.out.println(mofault.to_ModelicaClass());
//				System.out.println(mofault.to_ModelicaInstance());
//			}

		}
		constructor.connect_Components(cartografo.get_ConnectionMap());
		constructor.save_ModelicaFile(constructor.get_Network().to_ModelicaClass());
	}
}
