package cim2modelica;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import cim2modelica.cim.CIMProfileType;
import cim2modelica.cim.DYProfileModel;
import cim2modelica.cim.EQProfileModel;
import cim2modelica.cim.SVProfileModel;
import cim2modelica.cim.TPProfileModel;
import cim2modelica.cim.map.ComponentMap;
import cim2modelica.cim.map.ModelDesigner;
import cim2modelica.cim.map.openipsl.branches.PwLineMap;
import cim2modelica.cim.map.openipsl.buses.PwBusMap;
import cim2modelica.cim.map.openipsl.connectors.PwPinMap;
import cim2modelica.cim.map.openipsl.loads.LoadMap;
import cim2modelica.cim.map.openipsl.machines.GENROEMap;
import cim2modelica.cim.map.openipsl.machines.GENROUMap;
import cim2modelica.cim.map.openipsl.machines.GENSALMap;
import cim2modelica.cim.map.openipsl.transformers.TransformerEndAuxiliarMap;
import cim2modelica.cim.map.openipsl.transformers.TwoWindingTransformerMap;
import cim2modelica.modelica.MOAttribute;
import cim2modelica.modelica.MOClass;
import cim2modelica.modelica.MOConnector;
import cim2modelica.modelica.MOPlant;
import cim2modelica.modelica.ModelBuilder;
import cim2modelica.modelica.openipsl.controls.es.OpenIPSLExcitationSystem;
import cim2modelica.modelica.openipsl.controls.tg.OpenIPSLTurbineGovernor;
import cim2modelica.modelica.openipsl.machines.OpenIPSLMachine;
import cim2modelica.utils.ModelWriter;
import cim2modelica.utils.ProfileFactory;
import cim2modelica.utils.ProfileReader;

public class CIM2MOD {
    private static ModelDesigner cartografo;
    private static ModelBuilder constructor;
	
    /**
     * <p>
     * Method for initialization of the tool. Loads the CIM Profile files into
     * the internal tools' model.<br>
     * if args[0] is -d, the tool reads the profiles from their folder.<br>
     * if args[0] is -p, the tool reads the profiles files directly.<br>
     * </p>
     * 
     * @param args
     */
    public static void setUp(String[] args) {
	if (args[0].equals("-d")) {
	    ProfileReader lector = new ProfileReader(args[2]);
	    ProfileFactory profiFact = new ProfileFactory();
	    lector.read_Directory();
	    cartografo = new ModelDesigner(
		    profiFact.getProfile(lector.get_source_EQ_profile(), CIMProfileType.EQUIPMENT),
		    profiFact.getProfile(lector.get_source_TP_profile(), CIMProfileType.TOPOLOGY),
		    profiFact.getProfile(lector.get_source_SV_profile(), CIMProfileType.STATE_VARIABLE),
		    profiFact.getProfile(lector.get_source_DY_profile(), CIMProfileType.DYNAMICS));
	    constructor = new ModelBuilder(args[1]);
	    lector = null;
	    profiFact = null;
	}
	if (args[0].equals("-p")) { // read the cim profile files separately
	    cartografo = new ModelDesigner(new EQProfileModel(args[2]), new TPProfileModel(args[3]),
		    new SVProfileModel(args[4]), new DYProfileModel(args[5]));
	    constructor = new ModelBuilder(args[1]);
	}
    }
	
    /**
     * <p>
     * This method is invoked when a ConductingEquipment resources is of type
     * SynchronousMachine
     * </p>
     * 
     * @param _machineType
     * @param _mopin
     * @param _equipmentResource
     * @return
     */
    public static OpenIPSLMachine factory_Machine(String _machineType, MOConnector _mopin,
	    String[] _equipmentResource) {
	OpenIPSLMachine momachine = null;
	if (_machineType.equals("GENROU")) {
	    GENROUMap mapSyncMach = cartografo.create_GENROUModelicaMap(
		    cartografo.get_CurrentConnectionMap().get_ConductingEquipment(),
		    "./res/map/openipsl/machines/cim_openipsl_genrou.xml", _equipmentResource);
	    momachine = constructor.create_MachineComponent(mapSyncMach);
	}
	if (_machineType.equals("GENSAL")) {
	    GENSALMap mapSyncMach = cartografo.create_GENSALModelicaMap(
		    cartografo.get_CurrentConnectionMap().get_ConductingEquipment(),
		    "./res/map/openipsl/machines/cim_openipsl_gensal.xml", _equipmentResource);
	    momachine = constructor.create_MachineComponent(mapSyncMach);
	}
	if (_machineType.equals("GENROE")) {
	    GENROEMap mapSyncMach = cartografo.create_GENROEModelicaMap(
		    cartografo.get_CurrentConnectionMap().get_ConductingEquipment(),
		    "./res/map/openipsl/machines/cim_openipsl_genroe.xml", _equipmentResource);
	    momachine = constructor.create_MachineComponent(mapSyncMach);
	}
	momachine.add_Terminal(_mopin);
	momachine.update_powerFlow(_mopin);
	return momachine;
    }
	
    /**
     * <p>
     * Creates plant object given a MachineMap. Search the dynamic components
     * associated with the SynchronousMachineDynamic reference.<br>
     * A MachinMap can contain ES[0..1], TG[0..1] or PSS[0..1]
     * </p>
     * 
     * @param _momachine
     * @param _machineType
     * @param _mopin
     */
    public static void factory_Plant(OpenIPSLMachine _momachine, String _machineType, MOConnector _mopin) {
	MOPlant moplanta = null;
	OpenIPSLExcitationSystem moexcsys = null;
	OpenIPSLTurbineGovernor motgov = null;
	MOClass mopss = null;
	ComponentMap mapExcSys = null;
	// ComponentMap mapPSS= null;
	ComponentMap mapTGov = null;

	if (!_machineType.equals("GENCLS")) {
	    Entry<String, Resource> excSysData = cartografo
		    .typeOf_ExcitationSystem(cartografo.get_CurrentConnectionMap().get_ConductingEquipment());
	    if (excSysData != null) {
		switch (excSysData.getKey()) {
		case "ESDC1A":
		    mapExcSys = cartografo.create_ESDC1AModelicaMap(excSysData.getValue(),
			    "./res/map/openipsl/controls/es/cim_openipsl_esdc1a.xml", excSysData.getKey());
		    break;
		case "SEXS":
		    mapExcSys = cartografo.create_SEXSModelicaMap(excSysData.getValue(),
			    "./res/map/openipsl/controls/es/cim_openipsl_sexs.xml", excSysData.getKey());
		    break;
		// case "SEXS":
		// mapExcSys= cartografo.create_ExcSEXSModelicaMap(
		// excSysData.getValue(),
		// "./res/map/openipsl/controls/es/cim_openipsl_excsexs.xml",
		// excSysData.getKey());
		// break;
		case "ESST1A":
		    mapExcSys = cartografo.create_ESST1AModelicaMap(excSysData.getValue(),
			    "./res/map/openipsl/controls/es/cim_openipsl_esst1a.xml", excSysData.getKey());
		    break;
		}
		moexcsys = constructor.create_ExcSysComponent(mapExcSys);
		mapExcSys = null;
	    }
	    Entry<String, Resource> tGovData = cartografo
		    .typeOf_TurbineGovernor(cartografo.get_CurrentConnectionMap().get_ConductingEquipment());
	    if (tGovData != null) {
		switch (tGovData.getKey()) {
		case "GovHydro1":
		    mapTGov = cartografo.create_HyGOVModelicaMap(tGovData.getValue(),
			    "./res/map/openipsl/controls/tg/cim_openipsl_hygov.xml", tGovData.getKey());
		    break;
		case "GovSteamSGO":
		    mapTGov = cartografo.create_IEESGOModelicaMap(tGovData.getValue(),
			    "./res/map/openipsl/controls/tg/cim_openipsl_ieesgo.xml", tGovData.getKey());
		    break;
		}
		motgov = constructor.create_TGovComponent(mapTGov);
		mapTGov = null;
	    }
	    // Entry<String, Resource> pssData=
	    // cartografo.typeOf_ExcitationSystem(
	    // cartografo.get_CurrentConnectionMap().get_ConductingEquipment());
	    // if (pssData!= null)
	    // {
	    // HYGOVMap mapExcSys= cartografo.create_HYGOVModelicaMap(
	    // excSysData.getValue(),
	    // "./res/map/openipsl/controls/es/cim_openipsl_esdc1a.xml",
	    // excSysData.getKey());
	    // moexcsys= constructor.create_ExcSysComponent(mapExcSys);
	    // }
	    moplanta = new PlantBuilder().machine(_momachine).excitationSystem(moexcsys).turbineGovernor(motgov)
		    .stabilizer(mopss).buildPlant();
	    MOConnector pinPlant = new MOConnector(_mopin, "plantaPin");
	    moplanta.add_Terminal(pinPlant);
	    MOClass constblock = constructor.create_ConstantBlock();
	    PlantBuilder.assemble_plant(moplanta, pinPlant, constblock);
	    moplanta.add_ContantBlock(constblock);
	    constructor.add_plantNetwork(moplanta);
	    constructor.add_equipmentNetwork(_momachine);
	} else
	    constructor.add_equipmentNetwork(_momachine);
    }
	
    /**
     * Method to create the final file organization of the Modelica model. This
     * method organizes the Plant models into different Modelica files and
     * creates the correspondent packages, package.mo and package.order files
     * 
     * @param _cartografo
     * @param _constructor
     */
    public static void assemble_ModelicaModel(ModelDesigner _cartografo, ModelBuilder _constructor) {
	ModelWriter escriptor = new ModelWriter();
	String plantPackage = "", plantPackageFolder = "";
	ArrayList<String> plantsName = new ArrayList<String>();
	constructor.connect_Components(cartografo.get_ConnectionMap());
	for (MOPlant plant : constructor.get_Network().get_planta()) {
	    plantPackageFolder = constructor.get_Network().get_Name() + "/PowerPlant";
	    plantPackage = constructor.get_Network().get_Name() + ".PowerPlant";
	    escriptor.save_ModelicaFile(plant.to_ModelicaClass(plantPackage), plant.get_Name(), plantPackageFolder);
	    plantsName.add(plant.get_Name());
	}
	escriptor.save_ModelicaFile(constructor.get_Network().to_ModelicaClass(constructor.get_Network().get_Name()),
		constructor.get_Network().get_Name(), constructor.get_Network().get_Name());
	escriptor.pack_ModelicaPackage(
		constructor.package_information(constructor.get_Network().get_Name(), "PowerPlant"), plantsName,
		constructor.get_Network().get_Name() + "/PowerPlant");
	escriptor.pack_ModelicaModel(
		constructor.model_package_information(constructor.get_Network().get_Name(),
			"Automatically generated comment"),
		"PowerPlant", constructor.get_Network().get_Name(), constructor.get_Network().get_Name());
	escriptor = null;
	plantsName = null;
    }

    public static void main(String[] args) {
	setUp(args);
	// Map<Resource, RDFNode> profile_SV;
	String[] cimClassResource;
	String[] equipmentResource, topologyResource;
	// TODO return only Terminal Class
	Map<Resource, RDFNode> profile_EQ = cartografo.load_EQ_profile();
	cartografo.load_TP_profile();
	cartografo.load_SV_profile();
	cartografo.load_DY_profile();
	for (Resource key : profile_EQ.keySet()) {
	    cimClassResource = cartografo.get_EquipmentClassName(key);
	    /*
	     * subjectResource[0] is the rdf_id, subjectResource[1] is the CIM
	     * name
	     */
	    if (cimClassResource[1].equals("Terminal")) {
		// System.out.println(cimClassResource[0]+ " is the rfd_id; "+
		// cimClassResource[1]+ " is the CIM name");
		PwPinMap mapTerminal = cartografo.create_TerminalModelicaMap(key,
			"./res/map/openipsl/connectors/cim_openipsl_pwpin.xml", cimClassResource);
		MOConnector mopin = constructor.create_PinConnector(mapTerminal);
		MOConnector mopinbus = constructor.create_PinConnector(mapTerminal);
		mapTerminal = null;
		/*
		 * after loading terminal, load the resource connected to it,
		 * a.k.a., ConductingEquipment a.k.a. TopologicalNode
		 */
		equipmentResource = cartografo
			.get_EquipmentClassName(cartografo.get_CurrentConnectionMap().get_ConductingEquipment());
		topologyResource = cartografo
			.get_TopoNodeClassName(cartografo.get_CurrentConnectionMap().get_TopologicalNode());
		// System.out.println(equipmentResource[0]+ " is the rfd_id; "+
		// equipmentResource[1]+ " is the CIM name");
		// System.out.println(topologyResource[0]+ " is the rfd_id; "+
		// topologyResource[1]+ " is the CIM name");
		/* According to CIM Composer, SynchMachine has one terminal */
		if (equipmentResource[1].equals("SynchronousMachine")) {
		    String machineType = cartografo
			    .typeOf_SynchronousMachine(cartografo.get_CurrentConnectionMap().get_ConductingEquipment());
		    OpenIPSLMachine momachine = factory_Machine(machineType, mopin, equipmentResource);
		    factory_Plant(momachine, machineType, mopin);
		}
		/* EnergyConsumer has one terminal */
		if (equipmentResource[1].equals("EnergyConsumer") || equipmentResource[1].equals("NonConformLoad")
			|| equipmentResource[1].equals("ConformLoad")) {
		    LoadMap mapEnergyC = cartografo.create_LoadModelicaMap(
			    cartografo.get_CurrentConnectionMap().get_ConductingEquipment(),
			    "./res/map/openipsl/loads/cim_openipsl_load.xml", equipmentResource);
		    MOClass moload = constructor.create_LoadComponent(mapEnergyC);
		    mapEnergyC = null;
		    moload.add_Terminal(mopin);
		    moload.update_powerFlow(mopin);
		    constructor.add_equipmentNetwork(moload);
		}
		/* ACLineSegment has two terminals */
		if (equipmentResource[1].equals("ACLineSegment")) {
		    MOClass moline = constructor.get_equipmentNetwork(equipmentResource[0]);
		    if (moline != null) {/*
					  * condition to check if the line
					  * already exist in the model, true,
					  * add the second terminal
					  */
			moline.add_Terminal(mopin);
		    } else {/*
			     * false, create map of the line and add the first
			     * terminal
			     */
			PwLineMap mapACLine = cartografo.create_LineModelicaMap(
				cartografo.get_CurrentConnectionMap().get_ConductingEquipment(),
				"./res/map/openipsl/branches/cim_openipsl_pwline.xml", equipmentResource);
			moline = constructor.create_LineComponent(mapACLine);
			mapACLine = null;
			moline.add_Terminal(mopin);
			constructor.add_equipmentNetwork(moline);
		    }
		}
		/* TN has 1..N terminals */
		if (topologyResource[1].equals("TopologicalNode")) {
		    MOClass mobus = constructor.get_equipmentNetwork(topologyResource[0]);
		    if (mobus != null) {
			mopinbus.set_InstanceName("p"); // trick to set all pin
			mobus.add_Terminal(mopinbus);
		    } else {/*
			     * false, create map of the line and add the first
			     * terminal
			     */
			PwBusMap mapTopoNode = cartografo.create_BusModelicaMap(
				cartografo.get_CurrentConnectionMap().get_TopologicalNode(),
				"./res/map/openipsl/buses/cim_openipsl_pwbus.xml", topologyResource);
			mobus = constructor.create_BusComponent(mapTopoNode);
			mapTopoNode = null;
			mopinbus.set_InstanceName("p"); // trick to set all pin
			mobus.add_Terminal(mopinbus);
			constructor.add_equipmentNetwork(mobus);
		    }
		}
	    }
	    if (cimClassResource[1].equals("PowerTransformerEnd")) {
		String[] transformerResource, terminalResource;
		TransformerEndAuxiliarMap auxiliarTwtMap = cartografo.create_TransformerModelicaMap(key,
			"./res/map/openipsl/branches/cim_openipsl_twowindingtransformer.xml", cimClassResource);
		TwoWindingTransformerMap twtMap = auxiliarTwtMap.get_TransformerEnd_Map();
		transformerResource = cartografo.get_EquipmentClassName(auxiliarTwtMap.get_PowerTransformer_Resource());
		terminalResource = cartografo.get_EquipmentClassName(auxiliarTwtMap.get_Terminal_Resource());
		PwPinMap mapTerminal = cartografo.create_TerminalModelicaMap(auxiliarTwtMap.get_Terminal_Resource(),
			"./res/map/openipsl/connectors/cim_openipsl_pwpin.xml", terminalResource);
		auxiliarTwtMap = null;
		/*
		 * Create the terminal object associated with this
		 * PowerTransformerEnd
		 */
		MOConnector mopin = constructor.create_PinConnector(mapTerminal);
		/*
		 * Create additional attribute for the ratio tap changer
		 * associated with the PowerTransformerEnd
		 */
		ArrayList<MOAttribute> moPowTransEnd = constructor.create_AttTransformerEnd(twtMap);
		/* check if the PowerTransformer is already in the network */
		MOClass moTransformer = constructor.get_equipmentNetwork(transformerResource[0]);
		if (moTransformer != null) {
		    moTransformer.add_Terminal(mopin);
		    for (MOAttribute moparam : moPowTransEnd)
			moTransformer.add_Attribute(moparam);
		} else {
		    moTransformer = constructor.create_TransformerComponent(twtMap);
		    moTransformer.add_Terminal(mopin);
		    for (MOAttribute moparam : moPowTransEnd)
			moTransformer.add_Attribute(moparam);
		    constructor.add_equipmentNetwork(moTransformer);
		}
		twtMap = null;
		moPowTransEnd.clear();
		moPowTransEnd = null;
	    }
	}
	assemble_ModelicaModel(cartografo, constructor);
    }
}
