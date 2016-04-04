package cim2model;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import cim2model.cim.CIMModel;
import cim2model.cim.CIMTransformerEnd;
import cim2model.io.CIMReaderJENA;
import cim2model.ipsl.cimmap.*;

/**
 * Read mapping files and create appropriate objects ComponentMap, Get corresponding values from CIM model 
 * into objects ComponentMap, Save objects ComponentMap in memory
 * @author fran_jo
 *
 */
public class ModelDesigner 
{
	ArrayList<ConnectionMap> connections;
//	ArrayList<CIMTerminal> connections;
	Map<Object, String> equipment;
	Map<Resource, RDFNode> components;
	CIMReaderJENA cimReader;
	CIMModel modelCIM;
	
	public ModelDesigner(String _cimSource)
	{
		cimReader= new CIMReaderJENA(_cimSource);
	}
	
	public Map<Resource, RDFNode> load_CIMModel()
	{
		modelCIM = new CIMModel(cimReader.readModel());
		components = modelCIM.gatherComponents();
		this.connections= new ArrayList<ConnectionMap>();
//		this.connections= new ArrayList<CIMTerminal>();
		this.equipment= new HashMap<Object, String>();
		
		return components;
	}
	
	/**
	 * 
	 * @param _key
	 * @return
	 */
	public String[] get_CIMComponentName(Resource _key)
	{
		return modelCIM.retrieveComponentName(_key);
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

	
	public Map<Object, String> get_EquipmentMap(){
		return this.equipment;
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
	
	private void add_newConnectionMap(PwPinMap _mapTerminal, Hashtable<String,Resource> _pinComponents){
		//object with pin rfdif, conducting equipment and topologicalnode
		//rfdid use, by modelbuilder, to retrieve instanc_name of pin
		ConnectionMap nuevaConnection = new ConnectionMap(
				_mapTerminal.getRfdId(),
				_pinComponents.get("ConductingEquipment").toString().split("#")[1],
				_pinComponents.get("TopologicalNode").toString().split("#")[1]);
		nuevaConnection.setConductingEquipment(_pinComponents.get("ConductingEquipment"));
		nuevaConnection.setTopologicalNode(_pinComponents.get("TopologicalNode"));
		
//		this.connections.add(new ConnectionMap(,
//				_cimClassMap.get("Terminal.ConductingEquipment").toString().split("#")[1],
//				_cimClassMap.get("Terminal.TopologicalNode").toString().split("#")[1]));
		
		this.connections.add(nuevaConnection);
	}
	/**
	 * 
	 * @param key
	 * @param _source
	 * @param _subjectID
	 * @return
	 */
	public PwPinMap create_TerminalModelicaMap(Resource key, String _source, String[] _subjectID)
	{
		PwPinMap mapTerminal= pwpinXMLToObject(_source);
		/* load corresponding tag cim:Terminal */
		Map<String, Object> cimClassMap= modelCIM.retrieveAttributesTerminal(key);
		/* iterate through map attributes, for storing proper cim values */
		ArrayList<MapAttribute> mapAttList= (ArrayList<MapAttribute>)mapTerminal.getMapAttribute();
		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
		MapAttribute currentmapAtt;
		while (imapAttList.hasNext()) {
			currentmapAtt= imapAttList.next();
			currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
//			System.out.println("currentmapAtt "+ currentmapAtt.toString());
		}
		mapTerminal.setConductingEquipment(cimClassMap.get("Terminal.ConductingEquipment").toString());
		mapTerminal.setTopologicalNode(cimClassMap.get("Terminal.TopologicalNode").toString());
		// add cim id, used as reference from terminal and connections to other components 
		mapTerminal.setRfdId(_subjectID[0]);
		mapTerminal.setCimName(_subjectID[1]);
		// create new entrance to the connection map, is used to draw the connections between components
		Hashtable<String,Resource> pinComponents= new Hashtable<String,Resource>();
		pinComponents.put("ConductingEquipment", (Resource)cimClassMap.get("Terminal.ConductingEquipment"));
		pinComponents.put("TopologicalNode", (Resource)cimClassMap.get("Terminal.TopologicalNode"));
		this.add_newConnectionMap(mapTerminal, pinComponents);
		pinComponents.clear(); pinComponents= null;
		modelCIM.clearAttributes();
		
		return mapTerminal;
	}
	
	private static GENCLSMap genclsXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(GENCLSMap.class);
	        un = context.createUnmarshaller();
	        GENCLSMap map = (GENCLSMap) un.unmarshal(new File(_xmlmap));
	        return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
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
	private static GENROEMap genroeXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(GENROEMap.class);
	        un = context.createUnmarshaller();
	        GENROEMap map = (GENROEMap) un.unmarshal(new File(_xmlmap));
	        return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
	public String typeOfSynchronousMachine(Resource key)
	{
		String rotorKind= modelCIM.checkSynchronousMachineType(key);
		
		return rotorKind;
	}
	/**
	 * 
	 * @param key
	 * @param _source
	 * @param _subjectID
	 * @return
	 */
	public GENCLSMap create_GENCLSModelicaMap(Resource key, String _source, String[] _subjectID)
	{
		GENCLSMap mapSyncMach= genclsXMLToObject(_source);
		Map<String, Object> cimClassMap= modelCIM.retrieveAttributesSyncMach(key);
		Iterator<MapAttribute> imapAttList= mapSyncMach.getMapAttribute().iterator();
		MapAttribute currentmapAtt;
		while (imapAttList.hasNext()) {
			currentmapAtt= imapAttList.next();
			currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
		}
		mapSyncMach.setName("GENCLS");
		mapSyncMach.setRfdId(_subjectID[0]);
		mapSyncMach.setCimName(_subjectID[1]);
		this.equipment.put(mapSyncMach, mapSyncMach.getClass().getName());

		modelCIM.clearAttributes();
		
		return mapSyncMach;
	}
	/**
	 * 
	 * @param key
	 * @param _source
	 * @param _subjectID
	 * @return
	 */
	public GENROUMap create_GENROUModelicaMap(Resource key, String _source, String[] _subjectID)
	{
		GENROUMap mapSyncMach= genrouXMLToObject(_source);
		Map<String, Object> cimClassMap= modelCIM.retrieveAttributesSyncMach(key);
		Iterator<MapAttribute> imapAttList= mapSyncMach.getMapAttribute().iterator();
		MapAttribute currentmapAtt;
		while (imapAttList.hasNext()) {
			currentmapAtt= imapAttList.next();
			currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
		}
		mapSyncMach.setName("GENROU");
		mapSyncMach.setRfdId(_subjectID[0]);
		mapSyncMach.setCimName(_subjectID[1]);
		this.equipment.put(mapSyncMach, mapSyncMach.getClass().getName());

		modelCIM.clearAttributes();
		
		return mapSyncMach;
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
		GENSALMap mapSyncMach= gensalXMLToObject(_source);
		Map<String, Object> cimClassMap= modelCIM.retrieveAttributesSyncMach(key);
		Iterator<MapAttribute> imapAttList= mapSyncMach.getMapAttribute().iterator();
		MapAttribute currentmapAtt;
		while (imapAttList.hasNext()) {
			currentmapAtt= imapAttList.next();
			currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
		}
		mapSyncMach.setName("GENSAL");
		mapSyncMach.setRfdId(_subjectID[0]);
		mapSyncMach.setCimName(_subjectID[1]);
		this.equipment.put(mapSyncMach, mapSyncMach.getClass().getName());

		modelCIM.clearAttributes();
		
		return mapSyncMach;
	}
	/**
	 * 
	 * @param key
	 * @param _source
	 * @param _subjectID
	 * @return
	 */
	public GENROEMap create_GENROEModelicaMap(Resource key, String _source, String[] _subjectID)
	{
		GENROEMap mapSyncMach= genroeXMLToObject(_source);
		Map<String, Object> cimClassMap= modelCIM.retrieveAttributesSyncMach(key);
		Iterator<MapAttribute> imapAttList= mapSyncMach.getMapAttribute().iterator();
		MapAttribute currentmapAtt;
		while (imapAttList.hasNext()) {
			currentmapAtt= imapAttList.next();
			currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
		}
		mapSyncMach.setName("GENROE");
		mapSyncMach.setRfdId(_subjectID[0]);
		mapSyncMach.setCimName(_subjectID[1]);
		this.equipment.put(mapSyncMach, mapSyncMach.getClass().getName());

		modelCIM.clearAttributes();
		
		return mapSyncMach;
	}
	
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
		Map<String, Object> cimClassMap= modelCIM.retrieveAttributesEnergyC(key);
		ArrayList<MapAttribute> mapAttList= (ArrayList<MapAttribute>)mapEnergyC.getMapAttribute();
		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
		MapAttribute currentmapAtt;
		while (imapAttList.hasNext()) {
			currentmapAtt= imapAttList.next();
			currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
		}
//		mapEnergyC.setLoadResponse(cimClassMap.get("EnergyConsumer.LoadResponse").toString());
		// add cim id, used as reference from terminal and connections to other components 
		mapEnergyC.setRfdId(_subjectID[0]);
		mapEnergyC.setCimName(_subjectID[1]);
		this.equipment.put(mapEnergyC, mapEnergyC.getClass().getName());

		modelCIM.clearAttributes();
		
		return mapEnergyC;
	}
	
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
	public PwLineMap create_LineModelicaMap(Resource key, String _source, String[] _subjectID)
	{
		PwLineMap mapACLine= pwlineXMLToObject(_source);
		Map<String, Object> cimClassMap= modelCIM.retrieveAttributes(key);
		ArrayList<MapAttribute> mapAttList= (ArrayList<MapAttribute>)mapACLine.getMapAttribute();
		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
		MapAttribute currentmapAtt;
		while (imapAttList.hasNext()) {
			currentmapAtt= imapAttList.next();
			currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
		}
		mapACLine.setRfdId(_subjectID[0]);
		mapACLine.setCimName(_subjectID[1]);
		this.equipment.put(mapACLine, mapACLine.getClass().getName());

		modelCIM.clearAttributes();
		
		return mapACLine;
	}
	
	private static TwoWindingTransformerMap twtXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(TwoWindingTransformerMap.class);
	        un = context.createUnmarshaller();
	        TwoWindingTransformerMap map = (TwoWindingTransformerMap) un.unmarshal(new File(_xmlmap));
	        return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
	public CIMTransformerEnd create_TransformerModelicaMap(Resource key, String _source, String[] _subjectID)
	{//TODO this function must handle retrieve the terminals and transformerends, return complete map
		TwoWindingTransformerMap mapPowTrans= twtXMLToObject(_source);
		CIMTransformerEnd transformerEnd;
		
		Map<String, Object> cimClassMap= modelCIM.retrieveAttributesTransformer(key);
		Iterator<MapAttribute> imapAttList= mapPowTrans.getMapAttribute().iterator();
		MapAttribute currentmapAtt;
		while (imapAttList.hasNext()) { //get the values of the attributes
			currentmapAtt= imapAttList.next();
			if (cimClassMap.get(currentmapAtt.getCimName())!= null)
				currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
			System.out.println("currentmapatt: "+ currentmapAtt.getCimName()+ "= "+ currentmapAtt.getContent());
		}
//		mapPowTrans.setPowerTransformer(cimClassMap.get("TransformerEnd.RatioTapChanger").toString());
		mapPowTrans.setTerminal(cimClassMap.get("TransformerEnd.Terminal").toString());
//		System.out.println("TwT terminal: "+ mapPowTrans.getTerminal());
		// add cim id, used as reference from terminal and connections to other components 
		mapPowTrans.setRfdId(cimClassMap.get("PowerTransformerEnd.PowerTransformer").toString().split("#")[1]);
		mapPowTrans.setCimName(_subjectID[1]);
		this.equipment.put(mapPowTrans, mapPowTrans.getClass().getName());
		
		transformerEnd= new CIMTransformerEnd(mapPowTrans, 
				(Resource)cimClassMap.get("PowerTransformerEnd.PowerTransformer"),
//				(Resource)cimClassMap.get("TransformerEnd.RatioTapChanger"),
				null,
				(Resource)cimClassMap.get("TransformerEnd.Terminal"));
		transformerEnd.set_Pt_id(cimClassMap.get("PowerTransformerEnd.PowerTransformer").toString().split("#")[1]);
//		transformerEnd.set_Rtc_id(cimClassMap.get("TransformerEnd.RatioTapChanger").toString().split("#")[1]);
		transformerEnd.set_Te_id(cimClassMap.get("TransformerEnd.Terminal").toString().split("#")[1]);

		modelCIM.clearAttributes();
		
		return transformerEnd;
	}
	
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
	public PwBusMap create_BusModelicaMap(Resource key, String _source, String[] _subjectID)
	{
		PwBusMap mapTopoNode= pwbusXMLToObject(_source);
		Map<String, Object> cimClassMap= modelCIM.retrieveAttributesTopoNode(key);
		ArrayList<MapAttribute> mapAttList= (ArrayList<MapAttribute>)mapTopoNode.getMapAttribute();
		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
		MapAttribute currentmapAtt;
		while (imapAttList.hasNext()) {
			currentmapAtt= imapAttList.next();
			currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
		}
		mapTopoNode.setRfdId(_subjectID[0]);
		mapTopoNode.setCimName(_subjectID[1]);
		this.equipment.put(mapTopoNode, mapTopoNode.getClass().getName());

		modelCIM.clearAttributes();
		
		return mapTopoNode;
	}

	private static PwFaultMap pwfaultXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(PwFaultMap.class);
	        un = context.createUnmarshaller();
	        PwFaultMap map = (PwFaultMap) un.unmarshal(new File(_xmlmap));
	        return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
	public PwFaultMap create_FaultModelicaMap(Resource key, String _source, String[] _subjectID) 
	{
		PwFaultMap mapFault= pwfaultXMLToObject(_source);
		Map<String, Object> cimClassMap= modelCIM.retrieveAttributesFault(key);
		ArrayList<MapAttribute> mapAttList= (ArrayList<MapAttribute>)mapFault.getMapAttribute();
		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
		MapAttribute currentmapAtt;
		while (imapAttList.hasNext()) {
			currentmapAtt= imapAttList.next();
			// condition to process attributes from ipsl not present in CIM
			if (!currentmapAtt.getCimName().equals("none"))
				currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
		}
		mapFault.setRfdId(_subjectID[0]);
		mapFault.setCimName(_subjectID[1]);
		this.equipment.put(mapFault, mapFault.getClass().getName());

		// create the connection here
//		this.connections.add(new ConnectionMap(mapTerminal.getRfdId(),
//				cimClassMap.get("Terminal.ConductingEquipment").toString().split("#")[1],
//				cimClassMap.get("Terminal.TopologicalNode").toString().split("#")[1]));
		
		modelCIM.clearAttributes();
		
		return mapFault;
	}
	
	/* extends QuiescentModelWithInheritance(gamma=0.3, delta=0.01); 
	 * will focus on create the high level model from cim, with
	 * component instances
	 * connect equations
	 * loading the components from the library, that means that the first step of the conversion
	 * will use the components of the library
	 */
}
