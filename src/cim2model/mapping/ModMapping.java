package cim2model.mapping;

import java.util.ArrayList;
import java.util.Iterator;
import org.jdom2.Element;

import cim2model.io.XMLReaderJDOM;
import cim2model.model.modelica.*;

public class ModMapping implements Mapping 
{
	XMLReaderJDOM xmlReader; //object reader for xml mapping
	Element xmlModelName;
	ArrayList<Element>  xmlModelAttribute;
	
	public ModMapping(String _mapp)
	{
		xmlReader= new XMLReaderJDOM("C:/Users/fragom/PhD_CIM/CIMv16/Mapping","cim_modelica_connector.xml");
		this.xmlModelAttribute= new ArrayList<Element>();
		this.xmlModelName= null;
	}
	
	public ModelicaModel createModel(ModelStereotype _modelKind)
	{
		ModelicaModel car = null;
        switch (_modelKind) {
        case MODEL:
            car = new MOModel(this.xmlModelName.getAttributeValue("name"));
            break;
 
        case CLASS:
            car = new MOClass(this.xmlModelName.getAttributeValue("name"));
            break;
 
        case CONNECTOR:
            car = new MOConnector(this.xmlModelName.getAttributeValue("name"));
            break;
 
        default:
            // throw some exception
            break;
        }
        return car;
	}

	@Override
	public void loadMapping(String _xmlResource)
	{
		xmlModelName= xmlReader.getRoot();
//		System.out.println(xmlModelName.getName());
//		System.out.println(xmlModelName.getAttributeValue("name"));
//		System.out.println(xmlModelName.getAttributeValue("stereotype"));
		
		Iterator<Element> iElement= xmlModelName.getChildren().listIterator();
		while (iElement.hasNext())
		{
			this.xmlModelAttribute.add(iElement.next());
		}
	}

	/**
	 * @return the xmlModelName
	 */
	public Element getXmlModelName() {
		return xmlModelName;
	}

	/**
	 * @param xmlModelName the xmlModelName to set
	 */
	public void setXmlModelName(Element xmlModelName) {
		this.xmlModelName = xmlModelName;
	}

	/**
	 * @return the xmlModelAttribute
	 */
	public ArrayList<Element> getXmlModelAttribute() 
	{
//		for (Element e: xmlModelAttribute)
//		{
//			System.out.println(e.getName());
//			System.out.println(e.getAttributeValue("name"));
//			System.out.println(e.getAttributeValue("datatype"));
//			System.out.println(e.getAttributeValue("variability"));
//			System.out.println(e.getAttributeValue("visibility"));
//		}
		
		return xmlModelAttribute;
	}

	/**
	 * @param xmlModelAttribute the xmlModelAttribute to set
	 */
	public void setXmlModelAttribute(ArrayList<Element> xmlModelAttribute) {
		this.xmlModelAttribute = xmlModelAttribute;
	}
	
	
}
