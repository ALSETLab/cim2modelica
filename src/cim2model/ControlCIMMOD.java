package cim2model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import cim2model.ModelBuilder;
import cim2model.ModelDesigner;
import cim2model.cim.*;
import cim2model.cim.map.ipsl.branches.*;
import cim2model.cim.map.ipsl.buses.*;
import cim2model.cim.map.ipsl.connectors.*;
import cim2model.cim.map.ipsl.controls.es.ESDC1AMap;
import cim2model.cim.map.ipsl.loads.*;
import cim2model.cim.map.ipsl.machines.*;
import cim2model.cim.map.ipsl.transformers.*;
import cim2model.modelica.*;
import cim2model.modelica.ipsl.controls.es.IPSLExcitationSystem;
import cim2model.modelica.ipsl.machines.IPSLMachine;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

public class ControlCIMMOD 
{
	public static void main(String[] args) 
	{
		Map<Resource, RDFNode> profile_EQ;
		Map<Resource, RDFNode> profile_TP;
		Map<Resource, RDFNode> profile_SV;
		String [] cimClassResource;
		String _source_EQ_profile= args[0];
		String _source_TP_profile= args[1];
		String _source_SV_profile= args[2];
		ModelDesigner cartografo;
		ModelBuilder constructor;
		String [] equipmentResource, topologyResource;
		
		cartografo= new ModelDesigner(_source_EQ_profile, _source_TP_profile, _source_SV_profile);
		constructor= new ModelBuilder(args[3]);
		String xmlns_cim= "http://iec.ch/TC57/2009/CIM-schema-cim14#";
		profile_EQ= cartografo.load_EQ_profile(xmlns_cim);
		profile_TP= cartografo.load_TP_profile(xmlns_cim);
		profile_SV= cartografo.load_SV_profile(xmlns_cim);
		// verify 
		for (Resource key : profile_EQ.keySet())
		{	
			cimClassResource= cartografo.get_CIMClassName(key);
			/* subjectResource[0] is the rfd_id, subjectResource[1] is the CIM name */
			if (cimClassResource[1].equals("Terminal"))
			{
				System.out.println(cimClassResource[0]+ " is the rfd_id; "+ cimClassResource[1]+ " is the CIM name");
				PwPinMap mapTerminal= 
						cartografo.create_TerminalModelicaMap(key, "./res/map/cim_iteslalibrary_pwpin.xml", cimClassResource);
				MOConnector mopin= constructor.create_PinConnector(mapTerminal);
				/* after loading terminal, load the resource connected to it, 
				 * a.k.a., ConductingEquipment 
				 * a.k.a. TopologicalNode */
				equipmentResource= cartografo.get_CIMClassName(
						cartografo.get_CurrentConnectionMap().get_ConductingEquipment());
				//TODO get tag name from TP profile object
				topologyResource= cartografo.get_CIMClassName(
						cartografo.get_CurrentConnectionMap().get_TopologicalNode());
//				/* According to CIM Composer, SynchMachine has one terminal */
//				if (equipmentResource[1].equals("SynchronousMachine"))
//				{
//					IPSLMachine momachine= null;
//					IPSLExcitationSystem moexcsys= null;
//					String machineType= cartografo.typeOfSynchronousMachine(
//							cartografo.get_CurrentConnectionMap().getConductingEquipment());
//					if (machineType.equals("GENCLS")) {
//						GENCLSMap mapSyncMach= cartografo.create_GENCLSModelicaMap(
//								cartografo.get_CurrentConnectionMap().getConductingEquipment(), 
//								"./res/map/cim_iteslalibrary_gencls.xml", equipmentResource);
//						momachine= constructor.create_MachineComponent(mapSyncMach);
//					}
//					if (machineType.equals("GENROU")) {
//						GENROUMap mapSyncMach= cartografo.create_GENROUModelicaMap(
//								cartografo.get_CurrentConnectionMap().getConductingEquipment(), 
//								"./res/map/cim_iteslalibrary_genrou.xml", equipmentResource);
//						momachine= constructor.create_MachineComponent(mapSyncMach);
//					}
//					if (machineType.equals("GENSAL")){
//						GENSALMap mapSyncMach= cartografo.create_GENSALModelicaMap(
//								cartografo.get_CurrentConnectionMap().getConductingEquipment(), 
//								"./res/map/cim_iteslalibrary_gensal.xml", equipmentResource);
//						momachine= constructor.create_MachineComponent(mapSyncMach);
//					}
//					if (machineType.equals("GENROE")){
//						GENROEMap mapSyncMach= cartografo.create_GENROEModelicaMap(
//								cartografo.get_CurrentConnectionMap().getConductingEquipment(), 
//								"./res/map/cim_iteslalibrary_genroe.xml", equipmentResource);
//						momachine= constructor.create_MachineComponent(mapSyncMach);
//					}
//					momachine.add_Terminal(mopin);
//					momachine.update_powerFlow(mopin);
//					//TODO with the object map, look and create object map for ES, TG and Stab objects
//					MOPlant moplanta;
//					Entry<String, Resource> excsData= cartografo.typeOfExcitationSystem(
//							cartografo.get_CurrentConnectionMap().getConductingEquipment());
//					if (excsData!= null & !machineType.equals("GENCLS")){
//						ESDC1AMap mapExcSys= cartografo.create_ESDC1ModelicaMap(
//							excsData.getValue(),
//							"./res/map/cim_iteslalibrary_esdc1a.xml", excsData.getKey());
//						moexcsys= constructor.create_ExcSysComponent(mapExcSys);
//						moplanta= new PlantBuilder().machine(momachine).excitationSystem(moexcsys).buildPlant();
//						moplanta.add_Terminal(mopin);
//						constructor.add_plantNetwork(moplanta);
//					}
//					else
//						if (!machineType.equals("GENCLS")){
//							moplanta= new PlantBuilder().machine(momachine).buildPlant();
//							moplanta.add_Terminal(mopin);
//							constructor.add_plantNetwork(moplanta);
//						}
//						else
//							constructor.add_equipmentNetwork(momachine);
//							
//					//TODO create plant object, name of generator instance name, with genmap, esmap, tgmap and stabmap
//					//genmap can contain ES[0..1], TG[0..1], PSS[0..1]
//				}
				/* EnergyConsumer has one terminal */
				if (equipmentResource[1].equals("EnergyConsumer"))
				{
					LoadMap mapEnergyC= cartografo.create_LoadModelicaMap(
							cartografo.get_CurrentConnectionMap().get_ConductingEquipment(), 
							"./res/map/cim_iteslalibrary_load.xml", equipmentResource);
					MOClass moload= constructor.create_LoadComponent(mapEnergyC);
					moload.add_Terminal(mopin);
					moload.update_powerFlow(mopin);
					constructor.add_equipmentNetwork(moload);
				}
				/* ACLineSegment has two terminals */
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
								cartografo.get_CurrentConnectionMap().get_ConductingEquipment(), 
								"./res/map/cim_iteslalibrary_pwline.xml", equipmentResource);
						moline= constructor.create_LineComponent(mapACLine);
						moline.add_Terminal(mopin);
						constructor.add_equipmentNetwork(moline);
					}
				}
				/* TN has 1..N terminals */
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
								cartografo.get_CurrentConnectionMap().get_TopologicalNode(), 
										"./res/map/cim_iteslalibrary_pwbus.xml", topologyResource);
						mobus= constructor.create_BusComponent(mapTopoNode);
						mobus.add_Terminal(mopin);
						constructor.add_equipmentNetwork(mobus);
					}
				}
//			}
//			if (cimClassResource[1].equals("PowerTransformerEnd"))
//			{
//				String [] transformerResource, terminalResource;
//				CIMTransformerEnd transformerEnd= cartografo.create_TransformerModelicaMap(key, 
//								"./res/map/cim_iteslalibrary_twowindingtransformer.xml", 
//								cimClassResource);
//				TwoWindingTransformerMap mapPowerTrans= transformerEnd.get_TransformerMap();
//				transformerResource= cartografo.get_CIMComponentName(transformerEnd.get_PowerTransformerMap());
//				terminalResource= cartografo.get_CIMComponentName(transformerEnd.get_TerminalMap());
//				PwPinMap mapTerminal= cartografo.create_TerminalModelicaMap(transformerEnd.get_TerminalMap(), 
//								"./res/map/cim_iteslalibrary_pwpin.xml", terminalResource);
//				/*Create the terminal object associated with this PowerTransformerEnd*/
//				MOConnector mopin= constructor.create_PinConnector(mapTerminal);
//				/*Create additional attribute for the ratio tap changer associated with the PowerTransformerEnd*/
//				ArrayList<MOAttribute> moPowTransEnd= constructor.create_AttTransformerEnd(mapPowerTrans);
//				/*check if the PowerTransformer is already in the network*/
//				MOClass moTransformer= constructor.get_equipmentNetwork(transformerResource[0]);
//				if (moTransformer!= null) {
//					moTransformer.add_Terminal(mopin);
//					for(MOAttribute moparam: moPowTransEnd)
//						moTransformer.add_Attribute(moparam);
//				}
//				else {
//					moTransformer= constructor.create_TransformerComponent(mapPowerTrans);
//					moTransformer.add_Terminal(mopin);
//					for(MOAttribute moparam: moPowTransEnd)
//						moTransformer.add_Attribute(moparam);
//					constructor.add_equipmentNetwork(moTransformer);
//				}
//				moPowTransEnd.clear(); moPowTransEnd= null;
			}
//
		}
//		constructor.connect_Components(cartografo.get_ConnectionMap());
//		constructor.save_ModelicaFile(constructor.get_Network().to_ModelicaClass());
	}
}
