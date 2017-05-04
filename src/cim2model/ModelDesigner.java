package cim2model;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import cim2model.cim.DYProfileModel;
import cim2model.cim.EQProfileModel;
import cim2model.cim.SVProfileModel;
import cim2model.cim.TPProfileModel;
import cim2model.cim.map.*;
import cim2model.cim.map.ipsl.branches.*;
import cim2model.cim.map.ipsl.buses.*;
import cim2model.cim.map.ipsl.connectors.*;
import cim2model.cim.map.ipsl.controls.es.ESDC1AMap;
import cim2model.cim.map.ipsl.loads.*;
import cim2model.cim.map.ipsl.machines.*;
import cim2model.cim.map.ipsl.transformers.*;
import cim2model.io.ReaderCIM;

/**
 * Read mapping files and create appropriate objects ComponentMap, Get corresponding values from CIM model 
 * into objects ComponentMap, Save objects ComponentMap in memory
 * @author fran_jo
 *
 */
public class ModelDesigner 
{
	ArrayList<ConnectionMap> connections;
	Map<Resource, RDFNode> classes_EQ;
	Map<Resource, RDFNode> tagsTP_tn;
	Map<Resource, RDFNode> classes_SV;
	ReaderCIM reader_EQ_profile;
	ReaderCIM reader_TP_profile;
	ReaderCIM reader_SV_profile;
	ReaderCIM reader_DY_profile;
	EQProfileModel profile_EQ;
	TPProfileModel profile_TP;
	SVProfileModel profile_SV;
	DYProfileModel profile_DY;
	
	public ModelDesigner(String _source_EQ_profile, String _source_TP_profile, 
			String _source_SV_profile, String _source_DY_profile)
	{
		reader_EQ_profile= new ReaderCIM(_source_EQ_profile);
		reader_TP_profile= new ReaderCIM(_source_TP_profile);
		reader_SV_profile= new ReaderCIM(_source_SV_profile);
		reader_DY_profile= new ReaderCIM(_source_DY_profile);
		this.connections= new ArrayList<ConnectionMap>();
	}
	
	public Map<Resource, RDFNode> load_EQ_profile(String _xmlns_cim)
	{
		profile_EQ = new EQProfileModel(reader_EQ_profile.read_profile(_xmlns_cim));
		classes_EQ = profile_EQ.gatherComponents();
		
		return classes_EQ;
	}
	
	public void load_TP_profile(String _xmlns_cim)
	{
		profile_TP = new TPProfileModel(reader_TP_profile.read_profile(_xmlns_cim));
		profile_TP.gather_TopologicalNodes();
		profile_TP.gather_Terminals();
	}
	
	public void load_SV_profile(String _xmlns_cim)
	{
		profile_SV = new SVProfileModel(reader_SV_profile.read_profile(_xmlns_cim));
		profile_SV.gather_SvPowerFlow();
		profile_SV.gather_SvVoltage();
	}
	
	public void load_DY_profile(String _xmlns_cim)
	{
		profile_DY = new DYProfileModel(reader_DY_profile.read_profile(_xmlns_cim));
		profile_DY.gather_SynchronousMachines();
		profile_DY.gather_ExcitationSystems();
		profile_DY.gather_TurbineGovernors();
	}
	
	/**
	 * 
	 * @param _key
	 * @return
	 */
	public String[] get_EquipmentClassName(Resource _key)
	{
		return profile_EQ.get_ComponentName(_key);
	}
	
	/**
	 * 
	 * @param _key
	 * @return
	 */
	public String[] get_TopoNodeClassName(Resource _key)
	{
		return profile_TP.get_ComponentName(_key);
	}

	/**
	 * 
	 * @return
	 */
	public ConnectionMap get_CurrentConnectionMap(){
		int last= this.connections.size()- 1;
		return this.connections.get(last);
	}
	/**
	 * 
	 * @return
	 */
	public ArrayList<ConnectionMap> get_ConnectionMap(){
		return this.connections;
	}
	
	private static PwPinMap pwpinXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(PwPinMap.class);
	        un = context.createUnmarshaller();
	        PwPinMap map = (PwPinMap) un.unmarshal(new File(_xmlmap));
	        return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	/**
	 * Create a new instance of ConnectionMap, relation of Id's between CIM classes
	 * Terminal, ConductingEquipment & TopologicalNode. Store the new object into an internal
	 * array for ConnectionMap
	 * @param _mapTerminal
	 * @param _cn
	 * @param _tn
	 */
	private void add_newConnectionMap(PwPinMap _mapTerminal, Resource _cn, Resource _tn)
	{
		ConnectionMap nuevaConnection = new ConnectionMap(
				_mapTerminal.getRdfId(), _mapTerminal.getConductingEquipment(),
				_mapTerminal.getTopologicalNode());
		nuevaConnection.set_ConductingEquipment(_cn);
		nuevaConnection.set_TopologicalNode(_tn);
		
		this.connections.add(nuevaConnection);
	}
	/**
	 * 
	 * 2) Creates a new instance of ConnectionMap, with Id T, Id Cn & Id Tn with the mapTerminal
	 * @param key
	 * @param _source
	 * @param _subjectID
	 * @return
	 */
	public PwPinMap create_TerminalModelicaMap(Resource key, String _source, String[] _subjectID)
	{
		Map<String, Object> profile_EQ_map, profile_SV_map;
		
		PwPinMap mapTerminal= pwpinXMLToObject(_source);
		/* load corresponding tag cim:Terminal */
		profile_EQ_map= profile_EQ.get_TerminalEQ(key);
		mapTerminal.get_AttributeMap("IdentifiedObject.name").setContent(
				(String)profile_EQ_map.get("IdentifiedObject.name"));
		if (profile_SV.has_SvPowerFlow(key))
		{
			profile_SV_map= profile_SV.get_TerminalPF(key);
			mapTerminal.get_AttributeMap("SvPowerFlow.p").setContent(
					(String)profile_SV_map.get("SvPowerFlow.p"));
			mapTerminal.get_AttributeMap("SvPowerFlow.q").setContent(
					(String)profile_SV_map.get("SvPowerFlow.q"));
//			ArrayList<AttributeMap> mapAttList= (ArrayList<AttributeMap>)mapTerminal.getAttributeMap();
//			Iterator<AttributeMap> imapAttList= mapAttList.iterator();
//			AttributeMap currentmapAtt;
//			while (imapAttList.hasNext()) {
//				currentmapAtt= imapAttList.next();
//				currentmapAtt.setContent((String)profile_SV_map.get(currentmapAtt.getCimName()));
//			}
		}
		mapTerminal.setConductingEquipment(
				profile_EQ_map.get("Terminal.ConductingEquipment").toString());
		if (profile_TP.has_TerminalTN(key)) {
			mapTerminal.setTopologicalNode(profile_TP.get_TerminalTN(key));
		}
		// add the rfd_id, as reference from terminal and connections to other components 
		mapTerminal.setRdfId(_subjectID[0]);
		mapTerminal.setCimName(_subjectID[1]);
		//
		this.add_newConnectionMap(mapTerminal, 
				(Resource)profile_EQ_map.get("Terminal.ConductingEquipment"),
				profile_TP.get_TNTerminal(profile_TP.get_TerminalTN(key)));
		profile_EQ.clearAttributes();
		profile_SV.clearAttributes();
		profile_TP.clearAttributes();
		
		return mapTerminal;
	}
	
//	private static GENCLSMap genclsXMLToObject(String _xmlmap) {
//		JAXBContext context;
//		Unmarshaller un;
//		
//		try{
//			context = JAXBContext.newInstance(GENCLSMap.class);
//	        un = context.createUnmarshaller();
//	        GENCLSMap map = (GENCLSMap) un.unmarshal(new File(_xmlmap));
//	        return map;
//        } 
//        catch (JAXBException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
	private static GENROUMap genrouXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(GENROUMap.class);
	        un = context.createUnmarshaller();
	        GENROUMap map = (GENROUMap) un.unmarshal(new File(_xmlmap));
	        return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
	private static GENSALMap gensalXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(GENSALMap.class);
	        un = context.createUnmarshaller();
	        GENSALMap map = (GENSALMap) un.unmarshal(new File(_xmlmap));
	        return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
//	private static GENROEMap genroeXMLToObject(String _xmlmap) {
//		JAXBContext context;
//		Unmarshaller un;
//		
//		try{
//			context = JAXBContext.newInstance(GENROEMap.class);
//	        un = context.createUnmarshaller();
//	        GENROEMap map = (GENROEMap) un.unmarshal(new File(_xmlmap));
//	        return map;
//        } 
//        catch (JAXBException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//	/**
//	 * 
//	 * @param key
//	 * @param _source
//	 * @param _subjectID
//	 * @return
//	 */
//	public GENCLSMap create_GENCLSModelicaMap(Resource key, String _source, String[] _subjectID)
//	{
//		GENCLSMap mapSyncMach= genclsXMLToObject(_source);
//		Map<String, Object> cimClassMap= profile_DY.retrieveAttributesSyncMach(key);
//		Iterator<AttributeMap> imapAttList= mapSyncMach.getAttributeMap().iterator();
//		AttributeMap currentmapAtt;
//		while (imapAttList.hasNext()) {
//			currentmapAtt= imapAttList.next();
//			currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
//		}
//		mapSyncMach.setName("GENCLS");
//		mapSyncMach.setRfdId(_subjectID[0]);
//		mapSyncMach.setCimName(_subjectID[1]);
//
//		profile_DY.clearAttributes();
//		
//		return mapSyncMach;
//	}
	/**
	 * 
	 * @param key
	 * @param _source
	 * @param _subjectID
	 * @return
	 */
	public GENROUMap create_GENROUModelicaMap(Resource key, String _source, String[] _subjectID)
	{
		Iterator<AttributeMap> imapAttList;
		Map<String, Object> cimEQMap, cimDYMap; 
		GENROUMap mapSynchMach= genrouXMLToObject(_source);
		AttributeMap currentmapAtt;
		/* Attributes from EQ */
		cimEQMap= profile_EQ.gather_SynchronousMachine_Attributes(key);
		imapAttList= mapSynchMach.getAttributeMap().iterator();
		while (imapAttList.hasNext()) {
			currentmapAtt= imapAttList.next();
			currentmapAtt.setContent((String)cimEQMap.get(currentmapAtt.getCimName()));
		}
		/* Attributes from DY */
		imapAttList= mapSynchMach.getAttributeMap().iterator();
		cimDYMap= profile_DY.gather_SynchronousMachineDynamics_Attributes(key);
		while (imapAttList.hasNext()) {
			currentmapAtt= imapAttList.next();
			currentmapAtt.setContent((String)cimDYMap.get(currentmapAtt.getCimName()));
		}
//		mapSyncMach.setName("GENROU");
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
	public GENSALMap create_GENSALModelicaMap(Resource key, String _source, String[] _subjectID)
	{
		Iterator<AttributeMap> imapAttList;
		Map<String, Object> cimEQMap, cimDYMap; 
		GENSALMap mapSynchMach= gensalXMLToObject(_source);
		/* Attributes from EQ */
		cimEQMap= profile_EQ.gather_SynchronousMachine_Attributes(key);
		imapAttList= mapSynchMach.getAttributeMap().iterator();
		AttributeMap currentmapAtt;
		while (imapAttList.hasNext()) {
			currentmapAtt= imapAttList.next();
			currentmapAtt.setContent((String)cimEQMap.get(currentmapAtt.getCimName()));
		}
		/* Attributes from DY */
		imapAttList= mapSynchMach.getAttributeMap().iterator();
		cimDYMap= profile_DY.gather_SynchronousMachineDynamics_Attributes(key);
		while (imapAttList.hasNext()) {
			currentmapAtt= imapAttList.next();
			currentmapAtt.setContent((String)cimDYMap.get(currentmapAtt.getCimName()));
		}
//		mapSyncMach.setName("GENROU");
		mapSynchMach.setRdfId(_subjectID[0]);
		mapSynchMach.setCimName(_subjectID[1]);
		profile_EQ.clearAttributes();
		profile_DY.clearAttributes();
		
		return mapSynchMach;
	}
//	/**
//	 * 
//	 * @param key
//	 * @param _source
//	 * @param _subjectID
//	 * @return
//	 */
//	public GENROEMap create_GENROEModelicaMap(Resource key, String _source, String[] _subjectID)
//	{
//		GENROEMap mapSyncMach= genroeXMLToObject(_source);
//		Map<String, Object> cimClassMap= modelCIM.retrieveAttributesSyncMach(key);
//		Iterator<AttributeMap> imapAttList= mapSyncMach.getAttributeMap().iterator();
//		AttributeMap currentmapAtt;
//		while (imapAttList.hasNext()) {
//			currentmapAtt= imapAttList.next();
//			currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
//		}
//		mapSyncMach.setName("GENROE");
//		mapSyncMach.setRfdId(_subjectID[0]);
//		mapSyncMach.setCimName(_subjectID[1]);
//
//		modelCIM.clearAttributes();
//		
//		return mapSyncMach;
//	}
	
//	public Entry<String, Resource> typeOfExcitationSystem(Resource key)
//	{
//		Entry<String, Resource> excsData= profile_DY.checkExcitationSystemType(key);
//		
//		return excsData;
//	}
//	private static ESDC1AMap esdc1aXMLToObject(String _xmlmap) {
//		JAXBContext context;
//		Unmarshaller un;
//		
//		try{
//			context = JAXBContext.newInstance(ESDC1AMap.class);
//	        un = context.createUnmarshaller();
//	        ESDC1AMap map = (ESDC1AMap) un.unmarshal(new File(_xmlmap));
//	        return map;
//        } 
//        catch (JAXBException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//	/**
//	 * 
//	 * @param key
//	 * @param _source
//	 * @param _subjectID
//	 * @return
//	 */
//	public ESDC1AMap create_ESDC1ModelicaMap(Resource _key, String _source, String _subjectName)
//	{
//		ESDC1AMap mapExcSys= esdc1aXMLToObject(_source);
//		Map<String, Object> cimClassMap= profile_DY.retrieveAttributesExcSys(_key);
//		Iterator<AttributeMap> imapAttList= mapExcSys.getAttributeMap().iterator();
//		AttributeMap currentmapAtt;
//		while (imapAttList.hasNext()) {
//			currentmapAtt= imapAttList.next();
//			currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
//		}
//		String[] dynamicResource= profile_DY.retrieveComponentName(_key);
//		mapExcSys.setRfdId(dynamicResource[0]);
//		mapExcSys.setCimName(dynamicResource[1]);
//
//		profile_DY.clearAttributes();
//		
//		return mapExcSys;
//	}
	
	/**
	 * 
	 * @param _xmlmap
	 * @return
	 */
	private static LoadMap loadXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(LoadMap.class);
	        un = context.createUnmarshaller();
	        LoadMap map = (LoadMap) un.unmarshal(new File(_xmlmap));
	        return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
	/**
	 * 
	 * @param key
	 * @param _source
	 * @param _subjectID
	 * @return
	 */
	public LoadMap create_LoadModelicaMap(Resource key, String _source, String[] _subjectID)
	{
		LoadMap mapEnergyC= loadXMLToObject(_source);
		Map<String, Object> cimClassMap= profile_EQ.gather_EnergyConsumerAtt(key);
		Iterator<AttributeMap> imapAttList= mapEnergyC.getAttributeMap().iterator();
		AttributeMap currentmapAtt;
		while (imapAttList.hasNext()) {
			currentmapAtt= imapAttList.next();
			currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
		} 
		mapEnergyC.setRdfId(_subjectID[0]);
		mapEnergyC.setCimName(_subjectID[1]);

		profile_EQ.clearAttributes();
		
		return mapEnergyC;
	}
	
	/**
	 * 
	 * @param _xmlmap
	 * @return
	 */
	private static PwLineMap pwlineXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(PwLineMap.class);
	        un = context.createUnmarshaller();
	        PwLineMap map = (PwLineMap) un.unmarshal(new File(_xmlmap));
	        return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
	/**
	 * 
	 * @param key
	 * @param _source
	 * @param _subjectID
	 * @return
	 */
	public PwLineMap create_LineModelicaMap(Resource key, String _source, String[] _subjectID)
	{
		PwLineMap mapACLine= pwlineXMLToObject(_source);
		Map<String, Object> cimClassMap= profile_EQ.gather_CIMClassAtt(key);
		Iterator<AttributeMap> imapAttList= mapACLine.getAttributeMap().iterator();
		AttributeMap currentmapAtt;
		while (imapAttList.hasNext()) {
			currentmapAtt= imapAttList.next();
			currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
		}
		mapACLine.setRdfId(_subjectID[0]);
		mapACLine.setCimName(_subjectID[1]);

		profile_EQ.clearAttributes();
		
		return mapACLine;
	}
	
	/**
	 * 
	 * @param _xmlmap
	 * @return
	 */
	private static PwBusMap pwbusXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(PwBusMap.class);
	        un = context.createUnmarshaller();
	        PwBusMap map = (PwBusMap) un.unmarshal(new File(_xmlmap));
	        return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
	/**
	 * 
	 * @param key
	 * @param _source
	 * @param _subjectID
	 * @return
	 */
	public PwBusMap create_BusModelicaMap(Resource key, String _source, String[] _subjectID)
	{
		PwBusMap mapTopoNode= pwbusXMLToObject(_source);
		Map<String, Object> cimClassMap= profile_TP.gather_TopoNodeAtt(key);
		Iterator<AttributeMap> imapAttList= mapTopoNode.getAttributeMap().iterator();
		AttributeMap currentmapAtt;
		while (imapAttList.hasNext()) {
			currentmapAtt= imapAttList.next();
			currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
		}
		mapTopoNode.setRdfId(_subjectID[0]);
		mapTopoNode.setCimName(_subjectID[1]);

		profile_TP.clearAttributes();
		
		return mapTopoNode;
	}
	
	/* SYNCHRONOUS MACHINES */
	/**
	 * rfdid of the SynchronousMachine is available
	 * @param key - resource from the EQ profile, 
	 * @return
	 */
	public String typeOfSynchronousMachine(Resource key)
	{
		String rotorType= profile_DY.checkSynchronousMachineType(key);
		
		return rotorType;
	}
//	private static TwoWindingTransformerMap twtXMLToObject(String _xmlmap) {
//		JAXBContext context;
//		Unmarshaller un;
//		
//		try{
//			context = JAXBContext.newInstance(TwoWindingTransformerMap.class);
//	        un = context.createUnmarshaller();
//	        TwoWindingTransformerMap map = (TwoWindingTransformerMap) un.unmarshal(new File(_xmlmap));
//	        return map;
//        } 
//        catch (JAXBException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//	public CIMTransformerEnd create_TransformerModelicaMap(Resource key, String _source, String[] _subjectID)
//	{
//		TwoWindingTransformerMap mapPowTrans= twtXMLToObject(_source);
//		CIMTransformerEnd transformerEnd;
//		
//		Map<String, Object> cimClassMap= profile_EQ.retrieveAttributesTransformer(key);
//		Iterator<AttributeMap> imapAttList= mapPowTrans.getAttributeMap().iterator();
//		AttributeMap currentmapAtt;
//		while (imapAttList.hasNext()) { //get the values of the attributes
//			currentmapAtt= imapAttList.next();
//			if (cimClassMap.get(currentmapAtt.getCimName())!= null)
//				currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
////			System.out.println("currentmapatt: "+ currentmapAtt.getCimName()+ "= "+ currentmapAtt.getContent()+ "; "+ currentmapAtt.getMoName());
//		}
////		mapPowTrans.setPowerTransformer(cimClassMap.get("TransformerEnd.RatioTapChanger").toString());
//		mapPowTrans.setTerminal(cimClassMap.get("TransformerEnd.Terminal").toString());
////		System.out.println("TwT terminal: "+ mapPowTrans.getTerminal());
//		// add cim id, used as reference from terminal and connections to other components 
//		mapPowTrans.setRfdId(cimClassMap.get("PowerTransformerEnd.PowerTransformer").toString().split("#")[1]);
//		mapPowTrans.setCimName(_subjectID[1]);
//		
//		transformerEnd= new CIMTransformerEnd(mapPowTrans, 
//				(Resource)cimClassMap.get("PowerTransformerEnd.PowerTransformer"),
////				(Resource)cimClassMap.get("TransformerEnd.RatioTapChanger")
//				(Resource)cimClassMap.get("TransformerEnd.Terminal"));
//		transformerEnd.set_Pt_id(cimClassMap.get("PowerTransformerEnd.PowerTransformer").toString().split("#")[1]);
////		transformerEnd.set_Rtc_id(cimClassMap.get("TransformerEnd.RatioTapChanger").toString().split("#")[1]);
//		transformerEnd.set_Te_id(cimClassMap.get("TransformerEnd.Terminal").toString().split("#")[1]);
//
//		profile_EQ.clearAttributes();
//		
//		return transformerEnd;
//	}
	

//	private static PwFaultMap pwfaultXMLToObject(String _xmlmap) {
//		JAXBContext context;
//		Unmarshaller un;
//		
//		try{
//			context = JAXBContext.newInstance(PwFaultMap.class);
//	        un = context.createUnmarshaller();
//	        PwFaultMap map = (PwFaultMap) un.unmarshal(new File(_xmlmap));
//	        return map;
//        } 
//        catch (JAXBException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//	public PwFaultMap create_FaultModelicaMap(Resource key, String _source, String[] _subjectID) 
//	{
//		PwFaultMap mapFault= pwfaultXMLToObject(_source);
//		Map<String, Object> cimClassMap= modelCIM.retrieveAttributesFault(key);
//		Iterator<AttributeMap> imapAttList= mapFault.getAttributeMap().iterator();
//		AttributeMap currentmapAtt;
//		while (imapAttList.hasNext()) {
//			currentmapAtt= imapAttList.next();
//			// condition to process attributes from ipsl not present in CIM
//			if (!currentmapAtt.getCimName().equals("none"))
//				currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
//		}
//		mapFault.setRfdId(_subjectID[0]);
//		mapFault.setCimName(_subjectID[1]);

		// create the connection here
//		this.connections.add(new ConnectionMap(mapTerminal.getRfdId(),
//				cimClassMap.get("Terminal.ConductingEquipment").toString().split("#")[1],
//				cimClassMap.get("Terminal.TopologicalNode").toString().split("#")[1]));
//		
//		modelCIM.clearAttributes();
//		
//		return mapFault;
//	}
}
