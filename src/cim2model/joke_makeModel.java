package cim2model;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import cim2model.electrical.branches.PwLine;
import cim2model.io.CIMReaderJENA;
import cim2model.mapping.modelica.*;
import cim2model.model.cim.CIMModel;
import cim2model.model.modelica.*;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

public class joke_makeModel {
	
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
	
	private static PwLoadPQMap pwloadpqXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(PwLoadPQMap.class);
	        un = context.createUnmarshaller();
	        PwLoadPQMap map = (PwLoadPQMap) un.unmarshal(new File(_xmlmap));
	        return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
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
	
	private static final String RDF_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private static final String CIM16_NS = "http://iec.ch/TC57/2013/CIM-schema-cim16#";
    
    private static final String MD_NS= "http://iec.ch/TC57/61970-552/ModelDescription/1#";
    
    private static final String ENTSOE_NS= "http://entsoe.eu/Secretariat/2013/ProfileExtension/3#";
	
	public static void main(String[] args) 
	{
		Map<Resource, RDFNode> components;
		String [] subjectResource;
		String _cimSource= args[0];
		ModelDesigner cartografo;
		
		cartografo= new ModelDesigner(_cimSource);
		components= cartografo.load_CIMModel();
		for (Resource key : components.keySet())
		{	
			subjectResource= cartografo.get_CIMComponentName(key);
			//subjectResource[0] is the rfd_id 
			//subjectResource[1] is the CIM name
			System.out.println("rfd_id: "+ subjectResource[0] + " cim name: "+ subjectResource[1]);
			//TODO: create the modelica files, with line, terminal, load and connect function
			if (subjectResource[1].equals("Terminal"))
			{
				System.out.println("I FOUND TERMINAL...");
				Map.Entry<PwPinMap,Resource> mapa= 
						cartografo.create_TerminalModelicaMap(key, "./res/cim_iteslalibrary_pwpin.xml", subjectResource);
				PwPinMap pwpin= mapa.getKey();
				System.out.println("TERMINAL COMPLETED!!");
				//after loading terminal, load the resource connected to it, aka, ConductingEquipment
				subjectResource= cartografo.get_CIMComponentName(mapa.getValue());
				if (subjectResource[1].equals("EnergyConsumer"))
				{
					System.out.println("I FOUND A LOAD...");
					PwLoadPQMap pwload= cartografo.create_LoadModelicaMap(mapa.getValue(), 
							"./res/cim_iteslalibrary_pwloadpq.xml", subjectResource, pwpin.getRfdId());
					System.out.println("LOAD COMPLETED!!");
				}
				if (subjectResource[1].equals("ACLineSegment"))
				{//TODO: create xml line with proper topologicalnode attributes, i.e. in EnergyConsumer
					System.out.println("I FOUND ACLINESEGMENT...");
					PwLineMap pwline= cartografo.create_LineModelicaMap(mapa.getValue(), 
							"./res/cim_iteslalibrary_pwline.xml", subjectResource, pwpin.getRfdId());
					System.out.println("LINE COMPLETED!!");
				}
			}
//			if (subjectResource[1].equals("TopologicalNode"))
//			{
//				System.out.println("I FOUND TERMINAL...");
//				PwBusMap mapTopoNode= pwbusXMLToObject("./res/cim_iteslalibrary_pwbus.xml");
//				cimClassMap= cim.retrieveAttributesTopoNode(key);
//				ArrayList<MapAttribute> mapAttList= (ArrayList<MapAttribute>)mapTopoNode.getMapAttribute();
//				Iterator<MapAttribute> imapAttList= mapAttList.iterator();
//				MapAttribute currentmapAtt;
//				while (imapAttList.hasNext()) {
//					currentmapAtt= imapAttList.next();
//					currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
//				}
//				// add cim id, used as reference from terminal and connections to other components 
//				mapTopoNode.setRfdId(subjectResource[0]);
//				mapTopoNode.setCimName(subjectResource[1]);
//				System.out.print("EnergyConsumer Map: ");
//				System.out.println(mapTopoNode.toString());
//				//TODO: Create MOClass for Line with its X Terminals
//				//utilizar clases from cim2model.model.modelica
////				MOClass pwLoad= new MOClass(mapTopoNode.getName());
//			}
		}
		
		//TODO: Connect components
		
		
//		Mapping map= FactoryMapping.loadMapping("modelica");
//		CodeGenerator codeGen= new CodeGenerator();
//		codeGen.loadCIMModel("ACLineSegment.xml", map);
		
//		Mapping map= FactoryMapping.loadMapping("modelica");
//		map.loadMapping("cim_modelica_class.xml");
		
//		codeGen.storeCIMValue(((ModMapping)map).getXmlModelName()); 
//		ModelicaModel connector= map.createModel(ModelStereotype.CONNECTOR);
//		
//		ModelicaModel component= map.createModel(ModelStereotype.CLASS);
	}
}
