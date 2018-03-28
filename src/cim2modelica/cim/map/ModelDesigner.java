package cim2modelica.cim.map;

import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

import cim2modelica.cim.CIMProfile;
import cim2modelica.cim.DYProfileModel;
import cim2modelica.cim.EQProfileModel;
import cim2modelica.cim.SVProfileModel;
import cim2modelica.cim.TPProfileModel;
import cim2modelica.cim.map.openipsl.DynamicComponentType;
import cim2modelica.cim.map.openipsl.branches.LineMapFactory;
import cim2modelica.cim.map.openipsl.branches.PwLineMap;
import cim2modelica.cim.map.openipsl.buses.BusesMapFactory;
import cim2modelica.cim.map.openipsl.buses.PwBusMap;
import cim2modelica.cim.map.openipsl.connectors.PwPinMap;
import cim2modelica.cim.map.openipsl.controls.es.ESDC1AMap;
import cim2modelica.cim.map.openipsl.controls.es.ESST1AMap;
import cim2modelica.cim.map.openipsl.controls.es.ExcSEXSMap;
import cim2modelica.cim.map.openipsl.controls.es.ExcSysMapFactory;
import cim2modelica.cim.map.openipsl.controls.es.SEXSMap;
import cim2modelica.cim.map.openipsl.controls.tg.HYGOVMap;
import cim2modelica.cim.map.openipsl.controls.tg.IEESGOMap;
import cim2modelica.cim.map.openipsl.controls.tg.TGovMapFactory;
import cim2modelica.cim.map.openipsl.loads.LoadMap;
import cim2modelica.cim.map.openipsl.loads.LoadMapFactory;
import cim2modelica.cim.map.openipsl.machines.GENROEMap;
import cim2modelica.cim.map.openipsl.machines.GENROUMap;
import cim2modelica.cim.map.openipsl.machines.GENSALMap;
import cim2modelica.cim.map.openipsl.machines.SynchMachineMapFactory;
import cim2modelica.cim.map.openipsl.transformers.TransformerEndAuxiliarMap;
import cim2modelica.cim.map.openipsl.transformers.TransformerMapFactory;
import cim2modelica.cim.map.openipsl.transformers.TwoWindingTransformerMap;

/**
 * Read mapping files and create appropriate objects ComponentMap, Get
 * corresponding values from CIM model into objects ComponentMap, Save objects
 * ComponentMap in memory
 * 
 * @author fran_jo
 *
 */
public class ModelDesigner {
    static final String CIMns = "http://iec.ch/TC57/2013/CIM-schema-cim16#";
    ArrayList<ConnectionMap> connections;
    EQProfileModel profile_EQ;
    TPProfileModel profile_TP;
    SVProfileModel profile_SV;
    DYProfileModel profile_DY;

    public ModelDesigner(CIMProfile _profileEQ, CIMProfile _profileTP, CIMProfile _profileSV, CIMProfile _profileDY) {
	this.profile_EQ = (EQProfileModel) _profileEQ;
	this.profile_TP = (TPProfileModel) _profileTP;
	this.profile_SV = (SVProfileModel) _profileSV;
	this.profile_DY = (DYProfileModel) _profileDY;
	this.connections = new ArrayList<ConnectionMap>();
    }

    public Map<Resource, RDFNode> load_EQ_profile() {
	Map<Resource, RDFNode> classes_EQ;
	classes_EQ = profile_EQ.gatherComponents();
	return classes_EQ;
    }

    public void load_TP_profile() {
	profile_TP.gather_TopologicalNodes();
	profile_TP.gather_Terminals();
    }

    public void load_SV_profile() {
	profile_SV.gather_SvPowerFlow();
	profile_SV.gather_SvVoltage();
    }

    public void load_DY_profile() {
	profile_DY.gather_SynchronousMachines();
	profile_DY.gather_ExcitationSystems();
	profile_DY.gather_TurbineGovernors();
    }

    /**
     * 
     * @param _key
     * @return
     */
    public String[] get_EquipmentClassName(Resource _key) {
	return profile_EQ.get_EquipmentRdfID(_key);
    }

    /**
     * 
     * @param _key
     * @return
     */
    public String[] get_TopoNodeClassName(Resource _key) {
	return profile_TP.get_ComponentName(_key);
    }

    /**
     * 
     * @return
     */
    public ConnectionMap get_CurrentConnectionMap() {
	int last = this.connections.size() - 1;
	return this.connections.get(last);
    }

    /**
     * 
     * @return
     */
    public ArrayList<ConnectionMap> get_ConnectionMap() {
	return this.connections;
    }

    private static PwPinMap pwpinXMLToObject(String _xmlmap) {
	JAXBContext context;
	Unmarshaller un;

	try {
	    context = JAXBContext.newInstance(PwPinMap.class);
	    un = context.createUnmarshaller();
	    PwPinMap map = (PwPinMap) un.unmarshal(new File(_xmlmap));
	    return map;
	} catch (JAXBException e) {
	    e.printStackTrace();
	    return null;
	}
        }

    /**
     * Create a new instance of ConnectionMap, relation of Id's between CIM
     * classes Terminal, ConductingEquipment & TopologicalNode. Store the new
     * object into an internal array for ConnectionMap
     * 
     * @param _mapTerminal
     * @param _cn
     * @param _tn
     */
    private void create_newConnectionMap(PwPinMap _mapTerminal, Resource _cn, Resource _tn) {
	ConnectionMap nuevaConnection = new ConnectionMap(_mapTerminal.getRdfId(),
		_mapTerminal.getConductingEquipment().split("#")[1], _mapTerminal.getTopologicalNode().split("#")[1]);
	nuevaConnection.set_ConductingEquipment(_cn);
	nuevaConnection.set_TopologicalNode(_tn);
	this.connections.add(nuevaConnection);
    }

    /**
     * 2) Creates a new instance of ConnectionMap, with Id T, Id Cn & Id Tn with
     * the mapTerminal
     * 
     * @param key
     * @param _source
     * @param _subjectID
     * @return
     */
    public PwPinMap create_TerminalModelicaMap(Resource key, String _source, String[] _subjectID) {
	Map<String, Object> profile_EQ_map, profile_SV_map;
	Resource resourceTN = null;

	PwPinMap mapTerminal = pwpinXMLToObject(_source);
	/* load corresponding tag cim:Terminal */
	profile_EQ_map = profile_EQ.get_TerminalEQ(key);
	mapTerminal.get_AttributeMap("IdentifiedObject.name")
		.setContent((String) profile_EQ_map.get("IdentifiedObject.name"));
	if (profile_SV.has_SvPowerFlow(key)) {
	    profile_SV_map = profile_SV.get_TerminalSvPowerFlow(key);
	    mapTerminal.get_AttributeMap("SvPowerFlow.p").setContent((String) profile_SV_map.get("SvPowerFlow.p"));
	    mapTerminal.get_AttributeMap("SvPowerFlow.q").setContent((String) profile_SV_map.get("SvPowerFlow.q"));
	}
	mapTerminal.setConductingEquipment(profile_EQ_map.get("Terminal.ConductingEquipment").toString());
	if (profile_TP.has_Terminal_TopologialNode(key)) {
	    mapTerminal.setTopologicalNode(profile_TP.get_TerminalTN(key));
	    resourceTN = profile_TP.get_TNTerminal(profile_TP.get_TerminalTN(key));
	    if (profile_SV.has_SvVoltage(resourceTN)) {
		profile_SV_map = profile_SV.get_TopoNodeSvVoltage(resourceTN);
		mapTerminal.get_AttributeMap("SvVoltage.v").setContent((String) profile_SV_map.get("SvVoltage.v"));
		mapTerminal.get_AttributeMap("SvVoltage.angle")
			.setContent((String) profile_SV_map.get("SvVoltage.angle"));
	    }
	}
	mapTerminal.setTopologicalNode(profile_TP.get_TerminalTN(key));
	mapTerminal.setConnectivityNode(profile_EQ.get_TerminalCN(key).get("Terminal.ConnectivityNode").toString());
	mapTerminal.setRdfId(_subjectID[0]);
	mapTerminal.setCimName(_subjectID[1]);
	this.create_newConnectionMap(mapTerminal, (Resource) profile_EQ_map.get("Terminal.ConductingEquipment"),
		profile_TP.get_TNTerminal(profile_TP.get_TerminalTN(key)));
	profile_EQ.clearAttributes();
	profile_SV.clearAttributes();
	profile_TP.clearAttributes();

	return mapTerminal;
    }

    /* SYNCHRONOUS MACHINES */
    /**
     * rfdid of the SynchronousMachine is available
     * 
     * @param key
     *            - resource from the EQ profile,
     * @return
     */
    public String typeOf_SynchronousMachine(Resource key) {
	String rotorType = profile_DY.check_SynchronousMachineType(key);

	return rotorType;
    }
    // private static GENCLSMap genclsXMLToObject(String _xmlmap) {
    // JAXBContext context;
    // Unmarshaller un;
    //
    // try{
    // context = JAXBContext.newInstance(GENCLSMap.class);
    // un = context.createUnmarshaller();
    // GENCLSMap map = (GENCLSMap) un.unmarshal(new File(_xmlmap));
    // return map;
    // }
    // catch (JAXBException e) {
    // e.printStackTrace();
    // return null;
    // }
    // }

    // /**
    // *
    // * @param key
    // * @param _source
    // * @param _subjectID
    // * @return
    // */
    // public GENCLSMap create_GENCLSModelicaMap(Resource key, String _source,
    // String[] _subjectID)
    // {
    // GENCLSMap mapSyncMach= genclsXMLToObject(_source);
    // Map<String, Object> cimClassMap=
    // profile_DY.retrieveAttributesSyncMach(key);
    // Iterator<AttributeMap> imapAttList=
    // mapSyncMach.getAttributeMap().iterator();
    // AttributeMap currentmapAtt;
    // while (imapAttList.hasNext()) {
    // currentmapAtt= imapAttList.next();
    // currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
    // }
    // mapSyncMach.setName("GENCLS");
    // mapSyncMach.setRfdId(_subjectID[0]);
    // mapSyncMach.setCimName(_subjectID[1]);
    //
    // profile_DY.clearAttributes();
    //
    // return mapSyncMach;
    // }
    /**
     * 
     * @param key
     * @param _source
     * @param _subjectID
     * @return
     */
    public GENROUMap create_GENROUModelicaMap(Resource key, String _source, String[] _subjectID) {
	Iterator<AttributeMap> imapAttList;
	Map<String, Object> cimEQDY;
	GENROUMap mapSynchMach = SynchMachineMapFactory.getInstance().genrouXMLToObject(_source);
	AttributeMap currentmapAtt;
	/* Attributes from EQ */
	cimEQDY = profile_EQ.gather_SynchronousMachine_Attributes(key);
	profile_EQ.gather_BasePower_Attributes(cimEQDY);
	profile_EQ.gather_BaseVoltage_Attributes((Resource) cimEQDY.get("Equipment.EquipmentContainer"), cimEQDY);
	profile_DY.gather_SynchronousMachineDynamics_Attributes(key, cimEQDY);
	imapAttList = mapSynchMach.getAttributeMap().iterator();
	while (imapAttList.hasNext()) {
	    currentmapAtt = imapAttList.next();
	    currentmapAtt.setContent((String) cimEQDY.get(currentmapAtt.getCimName()));
	}

	// mapSyncMach.setName("GENROU");
	mapSynchMach.setRdfId(_subjectID[0]);
	mapSynchMach.setCimName(_subjectID[1]);
	profile_EQ.clearAttributes();
	profile_DY.clearAttributes();

	return mapSynchMach;
    }

    /**
     * 
     * @param key
     * @param _source
     * @param _subjectID
     * @return
     */
    public GENSALMap create_GENSALModelicaMap(Resource key, String _source, String[] _subjectID) {
	Iterator<AttributeMap> imapAttList;
	Map<String, Object> cimEQDY;
	GENSALMap mapSynchMach = SynchMachineMapFactory.getInstance().gensalXMLToObject(_source);
	/* Attributes from EQ */
	cimEQDY = profile_EQ.gather_SynchronousMachine_Attributes(key);
	profile_EQ.gather_BasePower_Attributes(cimEQDY);
	profile_EQ.gather_BaseVoltage_Attributes((Resource) cimEQDY.get("Equipment.EquipmentContainer"), cimEQDY);
	profile_DY.gather_SynchronousMachineDynamics_Attributes(key, cimEQDY);
	imapAttList = mapSynchMach.getAttributeMap().iterator();
	AttributeMap currentmapAtt;
	while (imapAttList.hasNext()) {
	    currentmapAtt = imapAttList.next();
	    currentmapAtt.setContent((String) cimEQDY.get(currentmapAtt.getCimName()));
	}
	// mapSyncMach.setName("GENROU");
	mapSynchMach.setRdfId(_subjectID[0]);
	mapSynchMach.setCimName(_subjectID[1]);
	profile_EQ.clearAttributes();
	profile_DY.clearAttributes();

	return mapSynchMach;
    }

    /**
     * Attributes from EQ profile plus DY profile
     * 
     * @param key
     * @param _source
     * @param _subjectID
     * @return
     */
    public GENROEMap create_GENROEModelicaMap(Resource key, String _source, String[] _subjectID) {
	Iterator<AttributeMap> imapAttList;
	Map<String, Object> cimEQDY;
	GENROEMap mapSynchMach = SynchMachineMapFactory.getInstance().genroeXMLToObject(_source);
	cimEQDY = profile_EQ.gather_SynchronousMachine_Attributes(key);
	profile_EQ.gather_BaseVoltage_Attributes((Resource) cimEQDY.get("Equipment.EquipmentContainer"), cimEQDY);
	profile_DY.gather_SynchronousMachineDynamics_Attributes(key, cimEQDY);
	imapAttList = mapSynchMach.getAttributeMap().iterator();
	AttributeMap currentmapAtt;
	while (imapAttList.hasNext()) {
	    currentmapAtt = imapAttList.next();
	    currentmapAtt.setContent((String) cimEQDY.get(currentmapAtt.getCimName()));
	}
	// mapSyncMach.setName("GENROE");
	mapSynchMach.setRdfId(_subjectID[0]);
	mapSynchMach.setCimName(_subjectID[1]);
	profile_EQ.clearAttributes();
	profile_DY.clearAttributes();

	return mapSynchMach;
    }

    /* EXCITATION SYSTEMS */
    /**
     * 
     * @param key
     *            is a SynchronousMachine resource from the EQ profile
     * @return
     */
    public Entry<String, Resource> typeOf_ExcitationSystem(Resource _key) {
	Entry<String, Resource> excSysData = null;
	final Property nameTag = ResourceFactory.createProperty(CIMns + "IdentifiedObject.name");
	Resource machDynamics = profile_DY.find_SynchronousMachineDynamic_Tag(_key);
	Entry<Resource, RDFNode> excSysTag = profile_DY.find_ExcitationSystem(machDynamics);
	if (excSysTag != null) {
	    excSysData = new AbstractMap.SimpleEntry<String, Resource>(
		    excSysTag.getKey().getProperty(nameTag).getLiteral().getValue().toString(), excSysTag.getKey());
	}
	return excSysData;
    }

    /**
     * 
     * @param key
     * @param _source
     * @param _subjectID
     * @return
     */
    public ESDC1AMap create_ESDC1AModelicaMap(Resource _key, String _source, String _subjectName) {
	ESDC1AMap mapExcSys = ExcSysMapFactory.getInstance().esdc1aXMLToObject(_source);
	Map<String, Object> cimClassMap = profile_DY.gather_ExcitationSystem_Attributes(_key);
	Iterator<AttributeMap> imapAttList = mapExcSys.getAttributeMap().iterator();
	AttributeMap currentmapAtt;
	while (imapAttList.hasNext()) {
	    currentmapAtt = imapAttList.next();
	    currentmapAtt.setContent((String) cimClassMap.get(currentmapAtt.getCimName()));
	    // System.out.println("currentmapatt: "+ currentmapAtt.getCimName()+
	    // "= "+ currentmapAtt.getContent()+ "; "+ currentmapAtt.getName());
	}
	String[] dynamicResource = profile_DY.get_ComponentName(_key, DynamicComponentType.EXCITATION_SYSTEM);
	mapExcSys.setRdfId(dynamicResource[0]);
	mapExcSys.setCimName(dynamicResource[1]);

	profile_DY.clearAttributes();

	return mapExcSys;
    }

    /**
     * 
     * @param key
     * @param _source
     * @param _subjectID
     * @return
     */
    public ExcSEXSMap create_ExcSEXSModelicaMap(Resource _key, String _source, String _subjectName) {
	ExcSEXSMap mapExcSys = ExcSysMapFactory.getInstance().excSexsXMLToObject(_source);
	Map<String, Object> cimClassMap = profile_DY.gather_ExcitationSystem_Attributes(_key);
	Iterator<AttributeMap> imapAttList = mapExcSys.getAttributeMap().iterator();
	AttributeMap currentmapAtt;
	while (imapAttList.hasNext()) {
	    currentmapAtt = imapAttList.next();
	    currentmapAtt.setContent((String) cimClassMap.get(currentmapAtt.getCimName()));
	    // System.out.println("currentmapatt: "+ currentmapAtt.getCimName()+
	    // "= "+ currentmapAtt.getContent()+ "; "+ currentmapAtt.getName());
	}
	String[] dynamicResource = profile_DY.get_ComponentName(_key, DynamicComponentType.EXCITATION_SYSTEM);
	mapExcSys.setRdfId(dynamicResource[0]);
	mapExcSys.setCimName(dynamicResource[1]);

	profile_DY.clearAttributes();

	return mapExcSys;
    }

    /**
     * 
     * @param key
     * @param _source
     * @param _subjectID
     * @return
     */
    public SEXSMap create_SEXSModelicaMap(Resource _key, String _source, String _subjectName) {
	SEXSMap mapExcSys = ExcSysMapFactory.getInstance().sexsXMLToObject(_source);
	Map<String, Object> cimClassMap = profile_DY.gather_ExcitationSystem_Attributes(_key);
	Iterator<AttributeMap> imapAttList = mapExcSys.getAttributeMap().iterator();
	AttributeMap currentmapAtt;
	while (imapAttList.hasNext()) {
	    currentmapAtt = imapAttList.next();
	    currentmapAtt.setContent((String) cimClassMap.get(currentmapAtt.getCimName()));
	    // System.out.println("currentmapatt: "+ currentmapAtt.getCimName()+
	    // "= "+ currentmapAtt.getContent()+ "; "+ currentmapAtt.getName());
	}
	String[] dynamicResource = profile_DY.get_ComponentName(_key, DynamicComponentType.EXCITATION_SYSTEM);
	mapExcSys.setRdfId(dynamicResource[0]);
	mapExcSys.setCimName(dynamicResource[1]);

	profile_DY.clearAttributes();

	return mapExcSys;
    }

    /**
     * 
     * @param key
     * @param _source
     * @param _subjectID
     * @return
     */
    public ESST1AMap create_ESST1AModelicaMap(Resource _key, String _source, String _subjectName) {
	ESST1AMap mapExcSys = ExcSysMapFactory.getInstance().esst1aXMLToObject(_source);
	Map<String, Object> cimClassMap = profile_DY.gather_ExcitationSystem_Attributes(_key);
	Iterator<AttributeMap> imapAttList = mapExcSys.getAttributeMap().iterator();
	AttributeMap currentmapAtt;
	while (imapAttList.hasNext()) {
	    currentmapAtt = imapAttList.next();
	    currentmapAtt.setContent((String) cimClassMap.get(currentmapAtt.getCimName()));
	    // System.out.println("currentmapatt: "+ currentmapAtt.getCimName()+
	    // "= "+ currentmapAtt.getContent()+ "; "+ currentmapAtt.getName());
	}
	String[] dynamicResource = profile_DY.get_ComponentName(_key, DynamicComponentType.EXCITATION_SYSTEM);
	mapExcSys.setRdfId(dynamicResource[0]);
	mapExcSys.setCimName(dynamicResource[1]);

	profile_DY.clearAttributes();

	return mapExcSys;
    }

    /* TURBINE GOVERNORS */
    /**
     * 
     * @param key
     *            is a SynchronousMachine resource from the EQ profile
     * @return
     */
    public Entry<String, Resource> typeOf_TurbineGovernor(Resource _key) {
	Entry<String, Resource> turbGovData = null;
	Resource machDynamics = profile_DY.find_SynchronousMachineDynamic_Tag(_key);
	Entry<Resource, RDFNode> turbGovTag = profile_DY.find_TurbineGovernor(machDynamics);

	if (turbGovTag != null) {
	    turbGovData = new AbstractMap.SimpleEntry<String, Resource>(turbGovTag.getValue().toString().split("#")[1],
		    turbGovTag.getKey());
	}
	return turbGovData;
    }

    /**
     * 
     * @param _key
     * @param _source
     * @param _subjectName
     * @return
     */
    public IEESGOMap create_IEESGOModelicaMap(Resource _key, String _source, String _subjectName) {
	IEESGOMap regulator = TGovMapFactory.getInstance().ieesgoXMLToObject(_source);
	Map<String, Object> cimClassMap = profile_DY.gather_ExcitationSystem_Attributes(_key);
	Iterator<AttributeMap> imapAttList = regulator.getAttributeMap().iterator();
	AttributeMap currentmapAtt;
	while (imapAttList.hasNext()) {
	    currentmapAtt = imapAttList.next();
	    currentmapAtt.setContent((String) cimClassMap.get(currentmapAtt.getCimName()));
	    // System.out.println("currentmapatt: "+ currentmapAtt.getCimName()+
	    // "= "+ currentmapAtt.getContent()+ "; "+ currentmapAtt.getName());
	}
	String[] dynamicResource = profile_DY.get_ComponentName(_key, DynamicComponentType.TURBINE_GOVERNOR);
	regulator.setRdfId(dynamicResource[0]);
	regulator.setCimName(dynamicResource[1]);

	profile_DY.clearAttributes();

	return regulator;
    }

    /**
     * 
     * @param value
     * @param string
     * @param key
     * @return
     */
    public HYGOVMap create_HyGOVModelicaMap(Resource _key, String _source, String _subjectName) {
	HYGOVMap regulator = TGovMapFactory.getInstance().hygovXMLToObject(_source);
	Map<String, Object> cimClassMap = profile_DY.gather_ExcitationSystem_Attributes(_key);
	Iterator<AttributeMap> imapAttList = regulator.getAttributeMap().iterator();
	AttributeMap currentmapAtt;
	while (imapAttList.hasNext()) {
	    currentmapAtt = imapAttList.next();
	    currentmapAtt.setContent((String) cimClassMap.get(currentmapAtt.getCimName()));
	    // System.out.println("currentmapatt: "+ currentmapAtt.getCimName()+
	    // "= "+ currentmapAtt.getContent()+ "; "+ currentmapAtt.getName());
	}
	String[] dynamicResource = profile_DY.get_ComponentName(_key, DynamicComponentType.TURBINE_GOVERNOR);
	regulator.setRdfId(dynamicResource[0]);
	regulator.setCimName(dynamicResource[1]);

	profile_DY.clearAttributes();

	return regulator;
    }

    /* NETWORK COMPONENTS MAP */

    /**
     * 
     * @param key
     * @param _source
     * @param _subjectID
     * @return
     */
    public LoadMap create_LoadModelicaMap(Resource key, String _source, String[] _subjectID) {
	LoadMap mapEnergyC = LoadMapFactory.getInstance().loadXMLToObject(_source);
	Map<String, Object> cimClassMap = profile_EQ.gather_EnergyConsumerAtt(key);
	profile_EQ.gather_BaseVoltage_Attributes((Resource) cimClassMap.get("Equipment.EquipmentContainer"),
		cimClassMap);
	Iterator<AttributeMap> imapAttList = mapEnergyC.getAttributeMap().iterator();
	AttributeMap currentmapAtt;
	while (imapAttList.hasNext()) {
	    currentmapAtt = imapAttList.next();
	    currentmapAtt.setContent((String) cimClassMap.get(currentmapAtt.getCimName()));
	    // System.out.println("currentmapatt: "+ currentmapAtt.getCimName()+
	    // "= "+ currentmapAtt.getContent()+ "; "+ currentmapAtt.getName());
	}
	mapEnergyC.setRdfId(_subjectID[0]);
	mapEnergyC.setCimName(_subjectID[1]);

	profile_EQ.clearAttributes();

	return mapEnergyC;
    }

    /**
     * 
     * @param key
     * @param _source
     * @param _subjectID
     * @return
     */
    public PwLineMap create_LineModelicaMap(Resource _key, String _source, String[] _subjectID) {
	PwLineMap mapACLine;
	Map<String, Object> cimClassAtt;
	Iterator<AttributeMap> imapAttList;
	
	mapACLine = LineMapFactory.getInstance().pwlineXMLToObject(_source);
	cimClassAtt= profile_EQ.gatther_ACLineSegment_Attributes(_key);
	profile_EQ.gather_BasePower_Attributes(cimClassAtt);
	imapAttList = mapACLine.getAttributeMap().iterator();
	AttributeMap currentmapAtt;
	while (imapAttList.hasNext()) {
	    currentmapAtt = imapAttList.next();
	    currentmapAtt.setContent((String) cimClassAtt.get(currentmapAtt.getCimName()));
	    // System.out.println("currentmapatt: "+ currentmapAtt.getCimName()+
	    // "= "+ currentmapAtt.getContent()+ "; "+ currentmapAtt.getName());
	}
	mapACLine.setRdfId(_subjectID[0]);
	mapACLine.setCimName(_subjectID[1]);
	profile_EQ.clearAttributes();

	return mapACLine;
    }

    /**
     * 
     * @param key
     * @param _source
     * @param _subjectID
     * @return
     */
    public PwBusMap create_BusModelicaMap(Resource _cimclass, String _source, String[] _subjectID) {
	PwBusMap mapTopoNode = BusesMapFactory.getInstance().pwbusXMLToObject(_source);
	Map<String, Object> tpNodeClassMap = profile_TP.gather_TopologicalNode_Attributes(_cimclass);
	profile_SV.gather_SvVoltage_TopologicalNode(tpNodeClassMap, _subjectID[0]);
	profile_EQ.gather_BaseVoltage_Attributes(tpNodeClassMap,
		tpNodeClassMap.get("TopologicalNode.BaseVoltage").toString());
	Iterator<AttributeMap> imapAttList = mapTopoNode.getAttributeMap().iterator();
	AttributeMap currentmapAtt;
	while (imapAttList.hasNext()) {
	    currentmapAtt = imapAttList.next();
	    currentmapAtt.setContent((String) tpNodeClassMap.get(currentmapAtt.getCimName()));
	}
	imapAttList = null;
	mapTopoNode.setRdfId(_subjectID[0]);
	mapTopoNode.setCimName(_subjectID[1]);

	profile_TP.clearAttributes();
	tpNodeClassMap.clear();
	tpNodeClassMap = null;

	return mapTopoNode;
    }

    /**
     * The TwoWindingTransformerMap(1) is referring to the
     * PowerTransformerEnd(2) CIM class. For each cim class (2) there is one map
     * class (1). The builder class is responsible to build the final
     * TwoWindindTransformer
     * 
     * @param key
     * @param _source
     * @param _subjectID
     * @return
     */
    public TransformerEndAuxiliarMap create_TransformerModelicaMap(Resource key, String _source, String[] _subjectID) {
	TwoWindingTransformerMap transformerEndMap = TransformerMapFactory.getInstance().twtXMLToObject(_source);
	TransformerEndAuxiliarMap auxiliarMap;

	Map<String, Object> cimClassMap = profile_EQ.gather_PowerTransformerEnd_Attributes(key);
	Iterator<AttributeMap> imapAttList = transformerEndMap.getAttributeMap().iterator();
	AttributeMap currentmapAtt;
	while (imapAttList.hasNext()) {
	    currentmapAtt = imapAttList.next();
	    if (cimClassMap.get(currentmapAtt.getCimName()) != null)
		currentmapAtt.setContent((String) cimClassMap.get(currentmapAtt.getCimName()));
	}
	// I need rdf_id and cim_name (subject split in two
	transformerEndMap.setPowerTransformer(cimClassMap.get("PowerTransformerEnd.PowerTransformer").toString());
	// mapPowTrans.setPowerTransformer(cimClassMap.get("TransformerEnd.RatioTapChanger").toString());
	transformerEndMap.setTerminal(cimClassMap.get("TransformerEnd.Terminal").toString());
	transformerEndMap.setRdfId(cimClassMap.get("PowerTransformerEnd.PowerTransformer").toString().split("#")[1]);
	transformerEndMap.setCimName(_subjectID[1]);

	auxiliarMap = new TransformerEndAuxiliarMap(transformerEndMap,
		(Resource) cimClassMap.get("PowerTransformerEnd.PowerTransformer"),
		// (Resource)cimClassMap.get("TransformerEnd.RatioTapChanger")
		(Resource) cimClassMap.get("TransformerEnd.Terminal"));
	auxiliarMap.set_PowerTransformer_RdfID(
		cimClassMap.get("PowerTransformerEnd.PowerTransformer").toString().split("#")[1]);
	// transformerEnd.set_Rtc_id(cimClassMap.get("TransformerEnd.RatioTapChanger").toString().split("#")[1]);
	auxiliarMap.set_Terminal_RdfID(cimClassMap.get("TransformerEnd.Terminal").toString().split("#")[1]);

	profile_EQ.clearAttributes();

	return auxiliarMap;
    }

    // private static PwFaultMap pwfaultXMLToObject(String _xmlmap) {
    // JAXBContext context;
    // Unmarshaller un;
    //
    // try{
    // context = JAXBContext.newInstance(PwFaultMap.class);
    // un = context.createUnmarshaller();
    // PwFaultMap map = (PwFaultMap) un.unmarshal(new File(_xmlmap));
    // return map;
    // }
    // catch (JAXBException e) {
    // e.printStackTrace();
    // return null;
    // }
    // }
    // public PwFaultMap create_FaultModelicaMap(Resource key, String _source,
    // String[] _subjectID)
    // {
    // PwFaultMap mapFault= pwfaultXMLToObject(_source);
    // Map<String, Object> cimClassMap= modelCIM.retrieveAttributesFault(key);
    // Iterator<AttributeMap> imapAttList=
    // mapFault.getAttributeMap().iterator();
    // AttributeMap currentmapAtt;
    // while (imapAttList.hasNext()) {
    // currentmapAtt= imapAttList.next();
    // // condition to process attributes from ipsl not present in CIM
    // if (!currentmapAtt.getCimName().equals("none"))
    // currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
    // }
    // mapFault.setRfdId(_subjectID[0]);
    // mapFault.setCimName(_subjectID[1]);

    // create the connection here
    // this.connections.add(new ConnectionMap(mapTerminal.getRfdId(),
    // cimClassMap.get("Terminal.ConductingEquipment").toString().split("#")[1],
    // cimClassMap.get("Terminal.TopologicalNode").toString().split("#")[1]));
    //
    // modelCIM.clearAttributes();
    //
    // return mapFault;
    // }
    }
