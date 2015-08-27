package cim2model.model.xml;

import org.jdom2.Element;

import java.util.Iterator;
import java.util.List;

import cim2model.io.XMLReaderJDOM;
import cim2model.model.modelica.MOClass;
import cim2model.model.modelica.ModelicaModel;

public class MOClassXML
{
	XMLReaderJDOM xmlComponent; //object reader for xml mapping
	Element xmlModelName;
	List<Element> cimChild;
	List<Element> modelicaChild;
	ModelicaModel modelo;
	
	public MOClassXML(String _name)
	{
		this.modelo= new MOClass(_name);
		this.cimChild= null;
		this.modelicaChild= null;
		this.xmlModelName= null;
	}
		
	/**
	 * set the attriubtes from the mapping to the class
	 */
	public void create_Class(XMLReaderJDOM _mapping)
	{
		Element attribute; 
		xmlModelName= _mapping.getRoot();
		this.cimChild= _mapping.getRoot().getChild("cim").getChildren();
		this.modelicaChild= _mapping.getRoot().getChild("modelica").getChildren();
		System.out.println(this.xmlModelName);
		Iterator<Element> modChildren= this.modelicaChild.iterator();
		while (modChildren.hasNext())
		{
			// 1. With these values, create a JAVA Class
			attribute= modChildren.next();
			System.out.println(attribute.getAttributeValue("name"));
			System.out.println(attribute.getValue());
		}
		
		Iterator<Element> cimChildren= this.cimChild.iterator();
		while (cimChildren.hasNext())
		{
			// 2. Assign these values to the MO-JAVA class created
			attribute= cimChildren.next();
			System.out.println(attribute.getAttributeValue("name"));
			System.out.println(attribute.getValue());
		}
		attribute= null;
	}
	
	//TODO: crear el objecto con sus valores, objeto segun el nombre en la fabrica/mapping
}
