package cim2model;

import java.util.Map;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import cim2model.io.CIMReaderJENA;
import cim2model.mapping.*;

public class CodeGenerator 
{
	public static void loadCIMModel(String _rdfResource) 
	{
		CIMReaderJENA cimReader;
		CIMModel cim;
		Map<Resource, RDFNode> components;
		String [] onlyOneID;
		Map<String, Object> attributes;
		
		cimReader= new CIMReaderJENA("C:/Users/fragom/PhD_CIM/JAVA/edu.smartslab.cim2model/res/", _rdfResource);
		cim = new CIMModel(cimReader.readModel());
		components = cim.gatherComponents();
		for (Resource key : components.keySet())
		{
			onlyOneID= cim.retrieveComponentName(key); 
			System.out.println("onlyOneID: "+ onlyOneID[0] + " value: "+ onlyOneID[1]);
			if (onlyOneID[1].equals("Terminal"))
			{
				attributes= cim.retrieveTerminalAtt(key);
				System.out.println("Attributes"+ attributes);
				//TODO: load/create the mapping for generator
				
				//TODO: generate the modelica code with starting values
			}
			else
				System.out.println("Not a Terminal");
		}
		
	}
	
	public static Mapping FactoryMapping(String _language)
	{
		Mapping map= null;
		
		switch (_language)
		{
		case "modelica":
			map= new ModMapping("modelica");
		}
		
		return map;
	}
}
