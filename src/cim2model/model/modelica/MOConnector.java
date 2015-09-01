package cim2model.model.modelica;

import java.util.ArrayList;
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
		this.attributes= new ArrayList<MOAttribute>();
	}
	
	/**
	 * @return the name
	 */
	public String get_Name() {
		return name;
	}
	/**
	 * @param name the name to set when is used as instance in other components
	 */
	public void set_Name(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String get_InstanceName() {
		return instanceName;
	}
	/**
	 * @param name the name to set when is used as instance in other components
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
	 * @return the annotation
	 */
	public String get_Annotation() {
		return annotation;
	}

	/**
	 * @param annotation the annotation to set
	 */
	public void set_Annotation(String annotation) {
		this.annotation = annotation;
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
			pencil.append(item.toModelica());
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
		pencil.append("(");
		for (MOAttribute item: this.attributes)
		{
			pencil.append(item.getName());
			pencil.append("=");
			pencil.append(item.getValue());
			pencil.append(",");
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
