package cim2model.io;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class CIMReaderJDOM {
	
	Document document;
	Element rootNode;
	
	public CIMReaderJDOM(String path, String file)
	{
		//open CIM/XML file
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(path +"/"+ file);
		
		try
		{
			this.document = (Document) builder.build(xmlFile);
			this.rootNode = document.getRootElement();
		}
		catch (IOException io) {
			System.out.println("ioe");
			System.out.println(io.getMessage());
		} 
		catch (JDOMException jdomex) {
			System.out.println("jdomex");
			System.out.println(jdomex.getMessage());
	  }
	}

	public Element getRoot()
	{
		return this.rootNode;
	}
	
	public String printRoot()
	{
		StringBuilder format= new StringBuilder();
		
		format.append("Node: ");
		format.append(this.rootNode.getName());
		List<Attribute> list = this.rootNode.getAttributes();
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) 
		{
			Attribute node = (Attribute) list.get(i);
			format.append("Attribute: ");
			format.append(node.getName());
			format.append("Value: ");
			format.append(node.getValue());
			format.append("\n");
			node= null;
		}

		list.clear(); list= null;
		return format.toString();
	}
	
	public String getComponentId(Element _elem)
	{
//		List<Attribute> attribs= _elem.getAttributes();
//		String id= attribs.get(0).getValue();
		String id= _elem.getAttributeValue(":ID");
		return id;
	}
	
	public List<Element> getModelComponents()
	{
		List<Element> list = this.rootNode.getChildren();
//		System.out.println(list.size());
		return list;
	}
	
	public List<Element> getComponentParameters(Element _elem)
	{
		List<Element> list = _elem.getChildren();
//		System.out.println(list.size());
		return list;
	}
}
