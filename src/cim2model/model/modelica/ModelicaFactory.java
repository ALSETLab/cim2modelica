package cim2model.model.modelica;

import cim2model.io.XMLReaderJDOM;
import cim2model.model.modelica.*;
import cim2model.model.xml.*;;

/**
 * This class has the responsability to create the corresponding mapping to generate modelica classes. 
 * These classes will be created according to the component refered in the mapping file, i.e. ModMapping loads
 * the xml mapping of a component and creates the corresponding component class
 * @author fragom
 *
 */
public class ModelicaFactory
{	
	private static MOModel modelo;
	private static ModelicaFactory fabrica;
	
	private ModelicaFactory(String _component)
	{
		modelo= null;
	}
	
	public static ModelicaFactory get_Factory(String _component)
	{
		if (fabrica== null)
			fabrica= new ModelicaFactory(_component);
		return fabrica;
	}
	
	public static MONetwork make_Network(String _name)
	{
		modelo = new MONetwork(_name);
        return (MONetwork)modelo;
	}

	public static MOClass make_Class(String _name)
	{
		modelo = new MOClass(_name);
        return (MOClass)modelo;
	}	
	
	public static MOConnector make_Connector(String _name)
	{
		modelo = new MOConnector(_name);
		return (MOConnector)modelo;
	}	
	
	public static MOClass make_Class(String _name, String _cimsource)
	{
		modelo = new MOClass(_name);
		// 1 load mapping
		// "C:/Users/fragom/PhD_CIM/JAVA/edu.smartslab.cim2model/res/","cim_iteslalibrary_"+ _name+ ".xml";
		XMLReaderJDOM mapping= new XMLReaderJDOM(_cimsource);
		// 2 create class from mapping
		MOClassXML xmlparser= new MOClassXML(_name);
		xmlparser.create_Class(mapping);
		
		// 3 save class
        return (MOClass)modelo;
	}	
	
	public static MOConnector make_Connector(String _name, String _cimsource)
	{
		modelo = new MOConnector(_name);
		// 1 load mapping
		// "C:/Users/fragom/PhD_CIM/JAVA/edu.smartslab.cim2model/res/", "cim_iteslalibrary_"+ _name+ ".xml";
		XMLReaderJDOM mapping= new XMLReaderJDOM(_cimsource);
		// 2 create class from mapping
		MOConnectorXML xmlparser= new MOConnectorXML(_name);
		xmlparser.create_Class(mapping);
		
		// 3 save class
		return (MOConnector)modelo;
	}	
	
}