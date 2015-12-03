package cim2model.model.xml;

import java.util.Iterator;
import java.util.List;

import org.jdom2.Element;

import cim2model.io.XMLReaderJDOM;
import cim2model.modelica.MOConnector;
import cim2model.modelica.MOModel;

public class MOConnectorXML 
{
	XMLReaderJDOM xmlComponent; //object reader for xml mapping
	Element xmlModelName;
	List<Element> cimChild;
	List<Element> modelicaChild;
	MOModel modelo;
	
	public MOConnectorXML(String _name)
	{
		this.modelo= new MOConnector(_name);
		this.cimChild= null;
		this.modelicaChild= null;
		this.xmlModelName= null;
	}
		
	/**
	 * set the attriubtes from the mapping to the class
	 */
	public void create_Class(XMLReaderJDOM _mapping)
	{
		xmlModelName= _mapping.getRoot();
		this.cimChild= _mapping.getRoot().getChild("cim").getChildren();
		this.modelicaChild= _mapping.getRoot().getChild("modelica").getChildren();
		System.out.println(this.xmlModelName);
		Iterator<Element> cimChildren= this.cimChild.iterator();
		Element attribute; 
		while (cimChildren.hasNext())
		{
			attribute= cimChildren.next();
			System.out.println(attribute.getAttributeValue("name"));
			System.out.println(attribute.getValue());
		}
		Iterator<Element> modChildren= this.modelicaChild.iterator();
		while (modChildren.hasNext())
		{
			attribute= modChildren.next();
			System.out.println(attribute.getAttributeValue("name"));
			System.out.println(attribute.getValue());
		}
		attribute= null;
	}
}
