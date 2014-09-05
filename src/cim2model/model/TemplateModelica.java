package cim2model.model;

import java.io.IOException;

import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class TemplateModelica 
{
	private static TemplateModelica instance;
	private Document jdomtemplate; 
	
	protected TemplateModelica(String _xmlSource)
	{
		//SAXBuilder to create the JDOM2 objects.
        SAXBuilder jdomBuilder = new SAXBuilder();
  
        try {
        	// jdomDocument is the JDOM2 Object
        	jdomtemplate= jdomBuilder.build(_xmlSource);
		} 
        catch (JDOMException e) {
			e.printStackTrace();
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static TemplateModelica getInstance(String _xmlSource)
	{
		if (instance.equals(null))
			instance= new TemplateModelica(_xmlSource);
		
		return instance;
	}
	
	public Element getElementTemplate(String _elem)
	{
		Element etemplate= null;
		
		switch(_elem)
		{
		case "root":
			etemplate= this.jdomtemplate.getRootElement();
			break;
		case "cim":
			etemplate= this.jdomtemplate.getRootElement().getChild("cimAtt");
			break;
		case "modelica": 
			etemplate= this.jdomtemplate.getRootElement().getChild("moAtt");
			break;
		}
		return etemplate;
	}
}
