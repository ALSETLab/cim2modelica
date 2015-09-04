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
import cim2model.mapping.*;
import cim2model.model.cim.CIMModel;
import cim2model.model.modelica.MOClass;
import cim2model.model.modelica.ModelicaFactory;
import cim2model.model.modelica.MOModel;

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
	
	private static final String RDF_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private static final String CIM16_NS = "http://iec.ch/TC57/2013/CIM-schema-cim16#";
    
    private static final String MD_NS= "http://iec.ch/TC57/61970-552/ModelDescription/1#";
    
    private static final String ENTSOE_NS= "http://entsoe.eu/Secretariat/2013/ProfileExtension/3#";
	
	public static void main(String[] args) 
	{
		//TODO Read full CIM model
		Map<Resource, RDFNode> components;
		Map<String, Object> modelCimClass;
		CIMReaderJENA cimReader;
		CIMModel cim;
		String [] onlyOneID;
		String _cimSource= args[0];
		
		cimReader= new CIMReaderJENA(_cimSource);
		cim = new CIMModel(cimReader.readModel());
		components = cim.gatherComponents();
		
		for (Resource key : components.keySet())
		{	
			onlyOneID= cim.retrieveComponentName(key); 
			System.out.println("onlyOneID: "+ onlyOneID[0] + " value: "+ onlyOneID[1]);
			//1. buscar la referencia de ACLineSegment
			if (onlyOneID[1].equals("ACLineSegment"))
			{
				// Create PwLine from a ACLineSegment and MOClass
				PwLineMap mapACLine= pwlineXMLToObject("./res/cim_iteslalibrary_pwline.xml");
				//TODO: use factory class
//				MOClass pwline= new MOClass(mapACLine.getName());
				modelCimClass= cim.retrieveAttributes(key); //attributes contain <name,value>
				//2. guardar en CimAttribute del objeto mapping id, nombre, terminalid, otros attributos
				ArrayList<MapAttribute> mapAttList= (ArrayList<MapAttribute>)mapACLine.getMapAttribute();
				Iterator<MapAttribute> imapAttList= mapAttList.iterator();
				MapAttribute currentmapAtt, newmapAtt;
				while (icimAttList.hasNext()) {
					currentCimAtt= icimAttList.next();
					newCimAtt= new MapAttribute();
					newCimAtt.setName(currentCimAtt.getName());
					newCimAtt.setContent((String)modelCimClass.get(currentCimAtt.getName()));
					mapACLine.setMapAttribute(currentCimAtt, newCimAtt);
				}
				System.out.println("xxx Final list of cim attributes ");
				icimAttList= cimAttList.iterator();
				while (icimAttList.hasNext()) {
					System.out.println(icimAttList.next().toString());
				}
			}
			if (onlyOneID[1].equals("Terminal"))
			{
				//2. crear el objeto PwPin con valores
				//3. buscar las referencias de Terminal en el CIMModel
				//3.1. crear objeto class segun aparezca referencia de objeto Terminal
				
			}

			
			//3.1. guardar en CimAttribute del objeto mapping id, nombre, otros attributos
			//3.2. crear el objeto PwPin con valores
			//4. Update objeto PwLine con los objetos PwPin
		}
		
		
		
		//TODO: Crear PwPin 
		//MOConnector	
		
		//TODO: Assign PwPin (x2) to a PwLine component 
		
//		ModelicaFactory mofactory= ModelicaFactory.get_Factory("");
//		MOModel classe= ModelicaFactory.make_Class("pwline");
//		classe.from_XMLMapping();
//		MOModel connector= ModelicaFactory.make_Class("pwpin");
//		connector.from_XMLMapping();
		
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
