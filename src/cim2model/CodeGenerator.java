package cim2model;

import java.util.Map;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import org.jdom2.Element;

import cim2model.io.CIMReaderJENA;
import cim2model.mapping.*;
import cim2model.model.modelica.MOClass;
import cim2model.model.modelica.MOVariable;

public class CodeGenerator 
{
	private Map<Resource, RDFNode> components;
	private Map<String, Object> attributes;
	
	public CodeGenerator()
	{
		this.components= null;
		this.attributes= null;
	}
	public void loadCIMModel(String _rdfResource, Mapping _map) 
	{
		CIMReaderJENA cimReader;
		CIMModel cim;
		String [] onlyOneID;
		
		cimReader= new CIMReaderJENA("C:/Users/fragom/PhD_CIM/JAVA/edu.smartslab.cim2model/res/", _rdfResource);
		cim = new CIMModel(cimReader.readModel());
		components = cim.gatherComponents();
		for (Resource key : components.keySet())
		{
			onlyOneID= cim.retrieveComponentName(key); 
			System.out.println("onlyOneID: "+ onlyOneID[0] + " value: "+ onlyOneID[1]);
			attributes= cim.retrieveAttributes(key);
//			System.out.println(attributes);
//			He de lligar d'alguna manera cada classe amb el mapping i generar les classe modelica.
//			una estructura xml interna, a partir del xml mapping, amb el format del mapping,
//			per cada component del model cim
			if (onlyOneID[1].equals("ACLineSegment"))
			{
//				_map.loadMapping("cim_modelica_connector.xml");
				System.out.println("puto - "+ attributes);
				MOClass aclinesegment= new MOClass("ACLineSegment");
				aclinesegment.setStereotype("class");
				MOVariable variable= new MOVariable();
				for (String str : attributes.keySet())
				{
					String [] name= str.split("\\.");
					System.out.println("fill de puta 1 "+ name[0]);
					variable.setName(name[1]);
					variable.setDatatype("Real");
					variable.setValue(attributes.get(str));
					aclinesegment.setAttribute(variable);
				}
				aclinesegment.toModelicaClass();
			}
			else
			{
				_map.loadMapping("cim_modelica_class.xml");
			}
		}	
	}
	
	/**
	 * Create an internal xml structure with the CIM values from the corresponding CIM class,
	 * previously loaded into memory.
	 */
	public void storeCIMValue(Element _map)
	{
		
	}
	
	
}
