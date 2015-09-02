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
		Map<String, Object> modelCimAtt;
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
				MOClass pwline= new MOClass(mapACLine.getName());
				modelCimAtt= cim.retrieveAttributes(key); //attributes contain <name,value>
				//2. guardar en CimAttribute del objeto mapping id, nombre, terminalid, otros attributos
				ArrayList<CimAttribute> mapCimAtt= (ArrayList<CimAttribute>)mapACLine.getCimAttribute();
				Iterator<CimAttribute> llistaatt= mapCimAtt.iterator();
				System.out.println("1. Map object with empty values");
				while (llistaatt.hasNext())
						System.out.println(llistaatt.next().toString());
				String[] parts;
				CimAttribute newCimAtt;
				System.out.println("2. Values from the CIM model");
				for (String attributo : modelCimAtt.keySet())
				{
					//TODO: update cim attributs from specidic class, necessary?
					System.out.println("2.1. attributo "+ attributo+ " valor "+ (String)modelCimAtt.get(attributo));
//					cimAttributos.get(cimAttributos.indexOf(aclinemap.getCimAttribute(parts[0])));
					newCimAtt= new CimAttribute();
					newCimAtt.setName(attributo);
					newCimAtt.setContent((String)modelCimAtt.get(attributo));
					System.out.println("2.2. nuevos valores "+ newCimAtt.toString());
					System.out.println("2.3. atributo del mapa "+ mapACLine.getCimAttribute(attributo).toString());
					mapACLine.setCimAttribute(mapACLine.getCimAttribute(attributo), newCimAtt);
					//TODO: update modelica attributes from specific class
				}
				llistaatt= mapCimAtt.iterator();
				System.out.println("3. Updated values from CIM model");
				while (llistaatt.hasNext())
						System.out.println(llistaatt.next().toString());
			}

			//2.1. crear el objeto PwLine con valores
			//3. buscar las referencias de Terminal segun terminalid
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