package cim2model.mapping;

import org.jdom2.Element;

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
	
	
}
