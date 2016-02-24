package cim2model.modelica;

import java.util.ArrayList;
import java.util.Iterator;

import cim2model.model.xml.*;

/**
 * Generic class for implementation the declaration of modelica connectors
 * @author fragom
 *
 */
public class MOConnector extends MOModel
{
	private String visibility;
	private String variability;
	private String instanceName; 
	private ArrayList<MOAttribute> attributes;
	
	public MOConnector(String _name)
	{
		super(_name, "connector");
		this.visibility= "public";
		this.variability= "none";
		this.attributes= new ArrayList<MOAttribute>();
	}

	/**
	 * @return the instanceName
	 */
	public String get_InstanceName() {
		return instanceName;
	}
	/**
	 * @param instanceName is the name when it is used as instance in other components
	 */
	public void set_InstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	
	/**
	 * @return the variability
	 */
	public String get_Variability() {
		return variability;
	}

	/**
	 * @param visibility the visibility to set
	 */
	public void set_Variability(String variability) {
		this.variability = variability;
	}
	
	/**
	 * @return the visibility
	 */
	public String get_Visibility() {
		return visibility;
	}

	/**
	 * @param visibility the visibility to set
	 */
	public void set_Visibility(String visibility) {
		this.visibility = visibility;
	}

	/**
	 * get attribute by name
	 * @param _name
	 * @return
	 */
	public MOAttribute get_Attribute(String _name){
		boolean exists= false;
		MOAttribute current= null;
		
		Iterator<MOAttribute> iconnections= this.attributes.iterator();
		while (!exists && iconnections.hasNext()) {
			current= iconnections.next();
			exists= (current.get_Name().equals(_name));
		}
		return current;
	}
	
	/**
	 * @return the attributes
	 */
	public ArrayList<MOAttribute> get_Attributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void set_Attribute(MOAttribute attribute) {
		this.attributes.add(attribute);
	}
	
	/**
	 * @param attributes the attributes to set
	 */
	public void set_Attributes(ArrayList<MOAttribute> attributes) {
		this.attributes = attributes;
	}
	
	/**
	 * connector name "some comments"
	 * 		parameter 
	 * 		...
	 * end name;
	 * @return text representation of the class
	 */
	public String to_ModelicaClass()
	{
		String code= "";
		StringBuilder pencil= new StringBuilder();
		
		pencil.append("connector ");
		pencil.append(this.name);
		pencil.append(" "); pencil.append('"');
		pencil.append(this.annotation);
		pencil.append('"'); pencil.append("\n");
		for (MOAttribute item: this.attributes)
		{
			pencil.append("\t");
			pencil.append(item.to_Modelica());
			pencil.append("\n");
		}
		pencil.append("end ");
		pencil.append(this.name);
		pencil.append(";");
		code= pencil.toString();
		
		return code;
	}
	
	/**
	 * Name connectorName (value1=?,value2=?,...) "comments";
	 * @return text representation of the instance
	 */
	public String to_ModelicaInstance()
	{
		String code= "";
		StringBuilder pencil= new StringBuilder();
		
		if (!this.visibility.equals("public"))
		{
			pencil.append(this.visibility); 
			pencil.append(" ");
		}
		pencil.append(this.name);
		pencil.append(" ");
		pencil.append(this.instanceName);
		pencil.append(" (");
		for (MOAttribute item: this.attributes)
		{
			if (item.get_Name().equals("vr") | item.get_Name().equals("vi") |
					item.get_Name().equals("ir") | item.get_Name().equals("ii")){
				pencil.append(item.get_Name());
				pencil.append("=");
				pencil.append(item.get_Value());
				pencil.append(",");
			}
		}
		pencil.deleteCharAt(pencil.lastIndexOf(","));
		pencil.append(") ");
		pencil.append('"');
		pencil.append(this.annotation);
		pencil.append('"'); pencil.append(";\n");
		code= pencil.toString();
		
		return code;
	}
}
