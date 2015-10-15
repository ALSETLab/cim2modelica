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
import cim2model.model.modelica.MOClass;
import cim2model.model.modelica.ModelicaFactory;
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
		Map<String, Object> modelCimClass, cimClassMap;
		CIMReaderJENA cimReader;
		CIMModel cim;
		String [] subjectResource;
		String _cimSource= args[0];
		
		cimReader= new CIMReaderJENA(_cimSource);
		cim = new CIMModel(cimReader.readModel());
		components = cim.gatherComponents();
		
		for (Resource key : components.keySet())
		{	
			subjectResource= cim.retrieveComponentName(key);
			//subjectResource[0] is the rfd_id 
			//subjectResource[1] is the CIM name
			System.out.println("rfd_id: "+ subjectResource[0] + " cim name: "+ subjectResource[1]);
			modelCimClass= cim.retrieveAttributes(key); //attributes contain <name,value>
			//1. buscar la referencia de ACLineSegment
			if (subjectResource[1].equals("ACLineSegment"))
			{
				System.out.println("I FOUND ACLINESEGMENT...");
				PwLineMap mapACLine= pwlineXMLToObject("./res/cim_iteslalibrary_pwline.xml");
				//2. guardar en mapClass del objeto mapping id, nombre, terminalid, otros attributos
				ArrayList<MapAttribute> mapAttList= (ArrayList<MapAttribute>)mapACLine.getMapAttribute();
				Iterator<MapAttribute> imapAttList= mapAttList.iterator();
				MapAttribute currentmapAtt;
				while (imapAttList.hasNext()) {
					currentmapAtt= imapAttList.next();
					currentmapAtt.setContent((String)modelCimClass.get(currentmapAtt.getCimName()));
				}
				// add cim id, used as reference from terminal and connections to other components 
				mapACLine.setRfdId(subjectResource[0]);
				mapACLine.setCimName(subjectResource[1]);
//				System.out.print("ACLineSegment Map: ");
//				System.out.println(mapACLine.toString());
//				imapAttList= mapAttList.iterator();
//				while (imapAttList.hasNext()) {
//					System.out.println(imapAttList.next().toString());
//				}
				//TODO: Create MOClass for Line with its 2 Terminals,
				//utilizar clases from cim2model.model.modelica
				MOClass pwline= new MOClass(mapACLine.getName());
				imapAttList= mapAttList.iterator();
				MapAttribute current;
				while (imapAttList.hasNext()) {
					current= imapAttList.next();
					MOAttribute param= new MOAttribute();
					param.setName(current.getMoName());
					param.setValue(current.getContent());
					param.setVariability(current.getVariability());
					param.setVisibility(current.getVisibility());
					param.setFlow(Boolean.valueOf(current.getFlow()));
				}
				pwline.setStereotype(mapACLine.getStereotype());
				pwline.setPackage(mapACLine.getPackage());
				System.out.println(pwline.toModelicaClass());
				System.out.println(pwline.toModelicaInstance());
			}
			if (subjectResource[1].equals("Terminal"))
			{
				System.out.println("I FOUND TERMINAL...");
				PwPinMap mapTerminal= pwpinXMLToObject("./res/cim_iteslalibrary_pwpin.xml");
				cimClassMap= cim.retrieveAttributesTerminal(key);
				//3. buscar las referencias de Terminal en el CIMModel
				//3.1. crear objeto class segun aparezca referencia de objeto Terminal
				ArrayList<MapAttribute> mapAttList= (ArrayList<MapAttribute>)mapTerminal.getMapAttribute();
				Iterator<MapAttribute> imapAttList= mapAttList.iterator();
				MapAttribute currentmapAtt, newmapAtt;
				while (imapAttList.hasNext()) {
					currentmapAtt= imapAttList.next();
					currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
				}
				mapTerminal.setConductingEquipment(cimClassMap.get("Terminal.ConductingEquipment").toString());
				mapTerminal.setConductingEquipment(cimClassMap.get("Terminal.TopologicalNode").toString());
				try 
				{ //TODO: calculate value for ii from v,angle,p,q
					double voltage= Double.valueOf(mapTerminal.getMapAttribute("vr").getContent());
					double apower= Double.valueOf(mapTerminal.getMapAttribute("P").getContent());
					double angle= Double.valueOf(mapTerminal.getMapAttribute("vi").getContent());
					double current= apower/(voltage*Math.cos(angle));
					newmapAtt= new MapAttribute();
					newmapAtt.setMoName("ir");
					newmapAtt.setContent(Double.toString(current));
					newmapAtt.setDatatype("Real");
					newmapAtt.setVariability("none");
					newmapAtt.setVisibility("public");
					newmapAtt.setFlow("true");
					mapTerminal.setMapAttribute(newmapAtt);
					newmapAtt= new MapAttribute();
					newmapAtt.setMoName("ii");
					newmapAtt.setContent(Double.toString(current));
					newmapAtt.setDatatype("Real");
					newmapAtt.setVariability("none");
					newmapAtt.setVisibility("public");
					newmapAtt.setFlow("true");
					mapTerminal.setMapAttribute(newmapAtt);
				}
				catch (NumberFormatException nfe)
				{
					System.err.println(nfe.getLocalizedMessage());
					System.err.println(nfe.getLocalizedMessage());
				}
				//TODO: assign object terminal/PwPin to its corresponding object MOClass
				// add cim id, used as reference from terminal and connections to other components 
				mapTerminal.setRfdId(subjectResource[0]);
				mapTerminal.setCimName(subjectResource[1]);
				//update object map with refId
				imapAttList= mapAttList.iterator();
				while (imapAttList.hasNext()) {
					System.out.println(imapAttList.next().toString());
				}
				System.out.println("TERMINAL COMPLETED!!");
				//TODO: use factory class
//				MOClass pwline= new MOClass(mapACLine.getName());
			}
			if (subjectResource[1].equals("EnergyConsumer"))
			{
				System.out.println("I FOUND A LOAD...");
				PwLoadPQMap mapEnergyC= pwloadpqXMLToObject("./res/cim_iteslalibrary_pwloadpq.xml");
				cimClassMap= cim.retrieveAttributesEnergyC(key);
				ArrayList<MapAttribute> mapAttList= (ArrayList<MapAttribute>)mapEnergyC.getMapAttribute();
				Iterator<MapAttribute> imapAttList= mapAttList.iterator();
				MapAttribute currentmapAtt;
				while (imapAttList.hasNext()) {
					currentmapAtt= imapAttList.next();
					currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
				}
				// add cim id, used as reference from terminal and connections to other components 
				mapEnergyC.setRfdId(subjectResource[0]);
				mapEnergyC.setCimName(subjectResource[1]);
				System.out.print("EnergyConsumer Map: ");
				System.out.println(mapEnergyC.toString());
				imapAttList= mapAttList.iterator();
				while (imapAttList.hasNext()) {
					System.out.println(imapAttList.next().toString());
				}
				System.out.println("LOAD COMPLETED!!");
				//TODO: Create MOClass for Line with its 1 Terminal
				//utilizar clases from cim2model.model.modelica
				MOClass pwLoad= new MOClass(mapEnergyC.getName());
				imapAttList= mapAttList.iterator();
				MapAttribute current;
				while (imapAttList.hasNext()) {
					current= imapAttList.next();
					MOAttribute param= new MOAttribute();
					param.setName(current.getMoName());
					param.setValue(current.getContent());
					param.setVariability(current.getVariability());
					param.setVisibility(current.getVisibility());
					param.setFlow(Boolean.valueOf(current.getFlow()));
				}
				pwLoad.setStereotype(mapEnergyC.getStereotype());
				pwLoad.setPackage(mapEnergyC.getPackage());
				System.out.println(pwLoad.toModelicaClass());
				System.out.println(pwLoad.toModelicaInstance());
			}
			if (subjectResource[1].equals("TopologicalNode"))
			{
				System.out.println("I FOUND TERMINAL...");
				PwBusMap mapTopoNode= pwbusXMLToObject("./res/cim_iteslalibrary_pwbus.xml");
				cimClassMap= cim.retrieveAttributesTopoNode(key);
				ArrayList<MapAttribute> mapAttList= (ArrayList<MapAttribute>)mapTopoNode.getMapAttribute();
				Iterator<MapAttribute> imapAttList= mapAttList.iterator();
				MapAttribute currentmapAtt;
				while (imapAttList.hasNext()) {
					currentmapAtt= imapAttList.next();
					currentmapAtt.setContent((String)cimClassMap.get(currentmapAtt.getCimName()));
				}
				// add cim id, used as reference from terminal and connections to other components 
				mapTopoNode.setRfdId(subjectResource[0]);
				mapTopoNode.setCimName(subjectResource[1]);
				System.out.print("EnergyConsumer Map: ");
				System.out.println(mapTopoNode.toString());
				//TODO: Create MOClass for Line with its X Terminals
				//utilizar clases from cim2model.model.modelica
//				MOClass pwLoad= new MOClass(mapTopoNode.getName());
			}
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
