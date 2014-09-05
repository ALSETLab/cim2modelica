package cim2model.model;

import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.hp.hpl.jena.rdf.model.Model;

import cim2model.type.Attribute;

public class MappingModelica implements Mapping 
{
	Element rootMapping;
	Element child;
	private ArrayList<String> componentName;
	private ArrayList<Attribute> attributes;
	private String newComponent;
	
	public MappingModelica(String _component)
	{
		this.rootMapping= new Element(_component);
	}
	
	public Model loadComponentMapping(String _rdfSource)
	{
		return null;
	}
	
	public void saveComponentMapping()
	{//pre: the template has been used to create a complete mapping for all the
		//CIM attributes and components.
		//The mapping structure will be used to generate the modelica code
		Document docMapping = new Document(rootMapping);
		docMapping.setRootElement(rootMapping);
		//write the file with the mapping
	}
	
	public void setComponent(Element _component)
	{
		child= rootMapping.addContent(_component);
	}
	
	public void setComponent(String _name)
	{
		this.componentName.add(_name);
		this.newComponent= _name;
	}
	
	public void setCIMAttribute(Element _eCIM)
	{
		this.child.addContent(_eCIM);
	}
	
	public void setCIMAttribute(String _cimAtt, String _value)
	{
		Attribute att= new Attribute(this.newComponent);
		att.setCimAttribute(_cimAtt, _value);
	}
	
	public void setMoAttribute(Element _eModelica)
	{
		this.child.addContent(_eModelica);
	}
	
	public void setMoAttribute(String _moAtt, String _value, String _type, String _datatype)
	{
		Attribute att= new Attribute(this.newComponent);
		att.setMoAttribute(_moAtt, _value);
		att.setType(_type);
		att.setDatatype(_value);
	}
	
	public int generateCode(String _language)
	{
		return 1;
	}
}
