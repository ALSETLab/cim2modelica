package cim2model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import cim2model.cim.*;
import cim2model.cim.map.ComponentMap;
import cim2model.cim.map.ModelDesigner;
import cim2model.cim.map.ipsl.branches.*;
import cim2model.cim.map.ipsl.buses.*;
import cim2model.cim.map.ipsl.connectors.*;
import cim2model.cim.map.ipsl.controls.es.ESDC1AMap;
import cim2model.cim.map.ipsl.loads.*;
import cim2model.cim.map.ipsl.machines.*;
import cim2model.cim.map.ipsl.transformers.*;
import cim2model.modelica.*;
import cim2model.modelica.ipsl.controls.es.IPSLExcitationSystem;
import cim2model.modelica.ipsl.controls.tg.IPSLTurbineGovernor;
import cim2model.modelica.ipsl.machines.IPSLMachine;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

public class ControlCIMMOD 
{
	private static String xmlns_cim;
	private static ModelDesigner cartografo;
	private static ModelBuilder constructor;
	
	public static void setUp (String[] args)
	{
		String _source_EQ_profile= args[0];
		String _source_TP_profile= args[1];
		String _source_SV_profile= args[2];
		String _source_DY_profile= args[3];
		cartografo= new ModelDesigner(_source_EQ_profile, _source_TP_profile, 
				_source_SV_profile, _source_DY_profile);
		constructor= new ModelBuilder(args[4]);
		xmlns_cim= args[5];
	}
	
	/**
	 * Creates plant object given MachineMap, adds esmap, tgmap and stabmap
	 * MachinMap can contain ES[0..1], TG[0..1], PSS[0..1]
	 * @param _momachine
	 * @param _machineType
	 * @param _mopin
	 */
	public static void factory_Plant(IPSLMachine _momachine, String _machineType, MOConnector _mopin)
	{
		MOPlant moplanta= null;
		IPSLExcitationSystem moexcsys= null;
		IPSLTurbineGovernor motgov= null;
		MOClass mopss= null;
		ComponentMap mapExcSys= null;
		ComponentMap mapPSS= null;
		ComponentMap mapTGov= null;
		
		if (!_machineType.equals("GENCLS"))
		{
			Entry<String, Resource> excSysData= cartografo.typeOf_ExcitationSystem(
				cartografo.get_CurrentConnectionMap().get_ConductingEquipment());
			if (excSysData!= null)
			{
				switch (excSysData.getKey())
				{
				case "ESCD1A":
					mapExcSys= cartografo.create_ESDC1AModelicaMap(
							excSysData.getValue(),
						"./res/map/ipsl/controls/es/cim_iteslalibrary_esdc1a.xml", excSysData.getKey());
					break;
				case "ExcSEXS": 
					mapExcSys= cartografo.create_ExcSEXSModelicaMap(
							excSysData.getValue(),
						"./res/map/ipsl/controls/es/cim_iteslalibrary_excsexs.xml", excSysData.getKey());
					break;
				}
				moexcsys= constructor.create_ExcSysComponent(mapExcSys);
			}
			Entry<String, Resource> tGovData= cartografo.typeOf_TurbineGovernor(
					cartografo.get_CurrentConnectionMap().get_ConductingEquipment());
			if (tGovData!= null)
			{
				switch (tGovData.getKey())
				{
				case "GovHydro1":
					mapTGov= cartografo.create_HyGOVModelicaMap(
							tGovData.getValue(),
						"./res/map/ipsl/controls/tg/cim_iteslalibrary_hygov.xml", tGovData.getKey());
					break;
				case "GovSteamSGO": 
					mapTGov= cartografo.create_IEESGOModelicaMap(
							tGovData.getValue(),
						"./res/map/ipsl/controls/tg/cim_iteslalibrary_ieesgo.xml", tGovData.getKey());
					break;
				}
				motgov= constructor.create_TGovComponent(mapTGov);
			}
//			Entry<String, Resource> pssData= cartografo.typeOf_ExcitationSystem(
//					cartografo.get_CurrentConnectionMap().get_ConductingEquipment());
//			if (pssData!= null)
//			{
//				HYGOVMap mapExcSys= cartografo.create_HYGOVModelicaMap(
//						excSysData.getValue(),
//					"./res/map/ipsl/controls/es/cim_iteslalibrary_esdc1a.xml", excSysData.getKey());
//				moexcsys= constructor.create_ExcSysComponent(mapExcSys);
//			}
			moplanta= new PlantBuilder().machine(_momachine).excitationSystem(moexcsys).
					turbineGovernor(motgov).stabilizer(mopss).buildPlant();
			moplanta.add_Terminal(_mopin);
			constructor.add_plantNetwork(moplanta);
			// Add machine to network equipment to connect with bus
			//TODO build a proper plant component
			constructor.add_equipmentNetwork(_momachine);
			
		}
		else
			constructor.add_equipmentNetwork(_momachine);	
	}
	
	public static void main(String[] args) 
	{
		setUp(args);
		
		Map<Resource, RDFNode> profile_EQ;
//		Map<Resource, RDFNode> profile_TP;
//		Map<Resource, RDFNode> profile_SV;
		String [] cimClassResource;
		String [] equipmentResource, topologyResource;
		
		profile_EQ= cartografo.load_EQ_profile(xmlns_cim);
		cartografo.load_TP_profile(xmlns_cim);
		cartografo.load_SV_profile(xmlns_cim);
		cartografo.load_DY_profile(xmlns_cim);
		for (Resource key : profile_EQ.keySet())
		{	
			cimClassResource= cartografo.get_EquipmentClassName(key);
			/* subjectResource[0] is the rdf_id, subjectResource[1] is the CIM name */
			if (cimClassResource[1].equals("Terminal"))
			{
//				System.out.println(cimClassResource[0]+ " is the rfd_id; "+ cimClassResource[1]+ " is the CIM name");
				PwPinMap mapTerminal= 
						cartografo.create_TerminalModelicaMap(key, 
								"./res/map/ipsl/connectors/cim_iteslalibrary_pwpin.xml", 
								cimClassResource);
				MOConnector mopin= constructor.create_PinConnector(mapTerminal);
				/* after loading terminal, load the resource connected to it, 
				 * a.k.a., ConductingEquipment 
				 * a.k.a. TopologicalNode */
				equipmentResource= cartografo.get_EquipmentClassName(
						cartografo.get_CurrentConnectionMap().get_ConductingEquipment());
				topologyResource= cartografo.get_TopoNodeClassName(
						cartografo.get_CurrentConnectionMap().get_TopologicalNode());
//				System.out.println(equipmentResource[0]+ " is the rfd_id; "+ equipmentResource[1]+ " is the CIM name");
//				System.out.println(topologyResource[0]+ " is the rfd_id; "+ topologyResource[1]+ " is the CIM name");
				/* According to CIM Composer, SynchMachine has one terminal */
				if (equipmentResource[1].equals("SynchronousMachine"))
				{
					IPSLMachine momachine= null;
					String machineType= cartografo.typeOf_SynchronousMachine(
							cartografo.get_CurrentConnectionMap().get_ConductingEquipment());
//					if (machineType.equals("GENCLS")) {
//						GENCLSMap mapSyncMach= cartografo.create_GENCLSModelicaMap(
//								cartografo.get_CurrentConnectionMap().getConductingEquipment(), 
//								"./res/map/cim_iteslalibrary_gencls.xml", equipmentResource);
//						momachine= constructor.create_MachineComponent(mapSyncMach);
//					}
					if (machineType.equals("GENROU")) {
						GENROUMap mapSyncMach= cartografo.create_GENROUModelicaMap(
								cartografo.get_CurrentConnectionMap().get_ConductingEquipment(), 
								"./res/map/ipsl/machines/cim_iteslalibrary_genrou.xml", equipmentResource);
						momachine= constructor.create_MachineComponent(mapSyncMach);
					}
					if (machineType.equals("GENSAL")){
						GENSALMap mapSyncMach= cartografo.create_GENSALModelicaMap(
								cartografo.get_CurrentConnectionMap().get_ConductingEquipment(), 
								"./res/map/ipsl/machines/cim_iteslalibrary_gensal.xml", equipmentResource);
						momachine= constructor.create_MachineComponent(mapSyncMach);
					}
					if (machineType.equals("GENROE")){
						GENROEMap mapSyncMach= cartografo.create_GENROEModelicaMap(
								cartografo.get_CurrentConnectionMap().get_ConductingEquipment(), 
								"./res/map/ipsl/machines/cim_iteslalibrary_genroe.xml", equipmentResource);
						momachine= constructor.create_MachineComponent(mapSyncMach);
					}
					momachine.add_Terminal(mopin);
					//TODO check power flow values, reading SV
					momachine.update_powerFlow(mopin);
					factory_Plant(momachine, machineType, mopin);
				}
				/* EnergyConsumer has one terminal */
				if (equipmentResource[1].equals("EnergyConsumer"))
				{
					LoadMap mapEnergyC= cartografo.create_LoadModelicaMap(
							cartografo.get_CurrentConnectionMap().get_ConductingEquipment(), 
							"./res/map/ipsl/loads/cim_iteslalibrary_load.xml", equipmentResource);
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
								"./res/map/ipsl/branches/cim_iteslalibrary_pwline.xml", equipmentResource);
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
										"./res/map/ipsl/buses/cim_iteslalibrary_pwbus.xml", topologyResource);
						mobus= constructor.create_BusComponent(mapTopoNode);
						mobus.add_Terminal(mopin);
						constructor.add_equipmentNetwork(mobus);
					}
				}
			}
			if (cimClassResource[1].equals("PowerTransformerEnd"))
			{
				String [] transformerResource, terminalResource;
				TransformerEndAuxiliarMap auxiliarTwtMap= cartografo.create_TransformerModelicaMap(key, 
								"./res/map/ipsl/branches/cim_iteslalibrary_twowindingtransformer.xml", 
								cimClassResource);
				TwoWindingTransformerMap twtMap= auxiliarTwtMap.get_TransformerEnd_Map();
				transformerResource= cartografo.get_EquipmentClassName(auxiliarTwtMap.get_PowerTransformer_Resource());
				terminalResource= cartografo.get_EquipmentClassName(auxiliarTwtMap.get_Terminal_Resource());
				PwPinMap mapTerminal= cartografo.create_TerminalModelicaMap(
						auxiliarTwtMap.get_Terminal_Resource(), "./res/map/ipsl/connectors/cim_iteslalibrary_pwpin.xml", 
						terminalResource);
				/*Create the terminal object associated with this PowerTransformerEnd*/
				MOConnector mopin= constructor.create_PinConnector(mapTerminal);
				/*Create additional attribute for the ratio tap changer associated with the PowerTransformerEnd*/
				ArrayList<MOAttribute> moPowTransEnd= constructor.create_AttTransformerEnd(twtMap);
				/*check if the PowerTransformer is already in the network*/
				MOClass moTransformer= constructor.get_equipmentNetwork(transformerResource[0]);
				if (moTransformer!= null) {
					moTransformer.add_Terminal(mopin);
					for(MOAttribute moparam: moPowTransEnd)
						moTransformer.add_Attribute(moparam);
				}
				else {
					moTransformer= constructor.create_TransformerComponent(twtMap);
					moTransformer.add_Terminal(mopin);
					for(MOAttribute moparam: moPowTransEnd)
						moTransformer.add_Attribute(moparam);
					constructor.add_equipmentNetwork(moTransformer);
				}
				moPowTransEnd.clear(); moPowTransEnd= null;
			}

		}
		constructor.connect_Components(cartografo.get_ConnectionMap());
		constructor.save_ModelicaFile(constructor.get_Network().to_ModelicaClass());
	}
}
