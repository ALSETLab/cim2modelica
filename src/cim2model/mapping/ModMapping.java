package cim2model.mapping;

import java.util.Map;

import org.jdom2.Element;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import cim2model.io.CIMReaderJENA;
import cim2model.io.XMLReaderJDOM;

public class ModMapping implements Mapping 
{
	XMLReaderJDOM xmlReader; //object reader for xml mapping
	
	public ModMapping(String _mapp)
	{
		xmlReader= new XMLReaderJDOM("C:/Users/fragom/PhD_CIM/CIMv16/Mapping","cim_modelica_connector.xml");
	}
	
	public int createModel(String _language)
	{
		//case, model of a connector
		return 1;
	}

	@Override
	public void loadMapping(String _xmlResource)
	{
		Element root;  
		Element varMap;
		
		root= xmlReader.getRoot();
		System.out.println(root.getName());
		System.out.println(root.getAttributeValue("name"));
		System.out.println(root.getAttributeValue("stereotype"));
		
		varMap= root.getChildren().iterator().next();
		System.out.println(varMap.getName());
		System.out.println(varMap.getAttributeValue("name"));
		System.out.println(varMap.getAttributeValue("datatype"));
		System.out.println(varMap.getAttributeValue("variability"));
		System.out.println(varMap.getAttributeValue("visibility"));
	}
	
	@Override
	public void loadCIMModel(String _rdfResource) 
	{
		CIMReaderJENA cimReader;
		CIMModel cim;
		Map<Resource, RDFNode> components;
		String [] onlyOneID;
		Map<String, Object> attributes;
		
		cimReader= new CIMReaderJENA("C:/Users/fragom/PhD_CIM/CIMv16/SmarTSLab/Components", "line.xml");
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
}
