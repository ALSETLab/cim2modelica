package cim2model.model.modelica;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Generic class for implementing the declaration of a modelica class. this class will be used to store 
 * characteristics of a component model
 * @author fragom
 *
 */
public class MOClass extends MOModel
{
	private String visibility;
	private String variability;
	private String instanceName; 
	private ArrayList<MOAttribute> attributes;
	private ArrayList<MOConnector> terminals;
	private ArrayList<MOEquation> equations;
	
	public MOClass(String _name) 
	{
		super(_name, "class");
		this.visibility= "public";
		this.variability= "parameter";
		this.attributes= new ArrayList<MOAttribute>();
		this.terminals= new ArrayList<MOConnector>();
		this.equations= new ArrayList<MOEquation>();
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
	 * @return the stereotype
	 */
	public String getStereotype() {
		return stereotype;
	}

	/**
	 * @param stereotype the stereotype to set
	 */
	public void setStereotype(String stereotype) {
		this.stereotype = stereotype;
	}
	
	/**
	 * @return the attributes
	 */
	public ArrayList<MOAttribute> get_Attributes() {
		return attributes;
	}
	/**
	 * 
	 * @param variable
	 */
	public void add_Attribute(MOAttribute variable){
		this.attributes.add(variable);
	}
	/**
	 * @param attributes the attributes to set
	 */
	public void add_Attribute(ArrayList<MOAttribute> attributes) {
		this.attributes = attributes;
	}
	
	/**
	 * 
	 * @param variable
	 */
	public void add_Terminal(MOConnector pin){
		this.terminals.add(pin);
	}
	/**
	 * @param terminals the terminals to set
	 */
	public void add_Terminal(ArrayList<MOConnector> terminals) {
		this.terminals = terminals;
	}
	/**
	 * @return the terminals
	 */
	public ArrayList<MOConnector> get_Terminals() {
		return terminals;
	}
	public MOConnector get_Terminal(String _rfdId)
	{
		MOConnector current;
		Iterator<MOConnector> iPins;
		
		iPins= this.terminals.iterator();
		boolean exists= false;
		do {
			current= iPins.next();
			exists= current.get_RfdId().equals(_rfdId);
		} while (!exists && iPins.hasNext());
		if (exists)
			return current;
		else
			return null;
	}
	
	/**
	 * class name "some comments"
	 * 		parameter 
	 * 		...
	 * 		equation
	 * 			...
	 * end name;
	 * @return text representation of the class
	 */
	public String to_ModelicaClass()
	{
		String code= "";
		StringBuilder pencil= new StringBuilder();
		
		/* ATTRIBUTES SECTION */
		pencil.append(this.stereotype); pencil.append(" ");
		pencil.append(this.name); pencil.append(" ");
		pencil.append('"');
		pencil.append(this.annotation);
		pencil.append('"'); pencil.append("\n");
		for (MOConnector pin: this.terminals)
		{
			pencil.append("\t");
			pencil.append(pin.to_ModelicaInstance());
		}
		for (MOAttribute item: this.attributes)
		{
			pencil.append("\t");
			if (!item.get_Variability().equals("none"))
				pencil.append(item.get_Variability()); pencil.append(" ");
			pencil.append(item.get_Datatype()); pencil.append(" ");
			pencil.append(item.get_Name());
			if (item.get_Variability().equals("parameter") || 
					item.get_Variability().equals("constant"))
			{// write initialization according to variability of the attribute
				pencil.append("=");
				pencil.append(item.get_Value());
				pencil.append(" ");
			}
			else{
				pencil.append("(start=");
				pencil.append(item.get_Value());
				pencil.append(", fixed=");
				if (item.is_Fixed())
					pencil.append("true");
				else
					pencil.append("false");
				pencil.append(") ");
			}
			pencil.append('"');
			pencil.append(item.get_Annotation());
			pencil.append('"');
			pencil.append(";\n");
		}
		/* EQUATION SECTION */
		pencil.append("\t");
		pencil.append("equation\n");
		
		pencil.append("end ");
		pencil.append(this.name);
		pencil.append(";");
		code= pencil.toString();
		
		return code;
	}
	
	/**
	 * Name className (parameter1=?,value2=?,...) "comments";
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
//		pencil.append(this.variability);
//		pencil.append(" ");
		pencil.append(this.pakage);
		pencil.append(".");
		pencil.append(this.name);
		pencil.append(" ");
		pencil.append(this.instanceName);
		pencil.append("(");
		for (MOAttribute item: this.attributes)
		{
			if (item.get_Variability().equals("parameter")){
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
		
		code= pencil.toString();
		
		return code;
	}
}
