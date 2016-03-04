package cim2model.modelica;

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
	protected String visibility;
	protected String variability;
	protected String instanceName; 
	protected ArrayList<MOAttribute> attributes;
	protected ArrayList<MOConnector> terminals;
	protected ArrayList<MOEquation> equations;
	
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
	
	public boolean exist_Attribute(String _name){
		boolean exists= false;
		MOAttribute current;
		
		Iterator<MOAttribute> iconnections= this.attributes.iterator();
		while (!exists && iconnections.hasNext()) {
			current= iconnections.next();
			exists= (current.get_Name().equals(_name));
		}
		
		return exists;
	}
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
	 * For some devices, there are power flow values that are mapped by Sv classes, and these classes are available either by 
	 * Terminal or TopologicalNode Class. The Modelica class of these devices need this Sv values as parameter. 
	 * SvPowerFlow (P,Q) are included in the classes
	 * SvVoltage (V, angle) are not included and need to be updated from the corresponding Pin component
	 * @param _component
	 * @param _pin
	 */
	public void update_powerFlow(MOConnector _pin)
	{
		//look for attributes SvVoltage.v and SvVoltage.angle
		Iterator<MOAttribute> iAttributes;
		MOAttribute currentAtt= null;
		iAttributes= this.attributes.iterator();
		while (iAttributes.hasNext())
		{//update this attribute values with pin values vr and vi
			currentAtt= iAttributes.next();
			if (currentAtt.get_Name().equals("V_0"))
				currentAtt.set_Value((String)_pin.get_Attribute("vr").get_Value());
			if (currentAtt.get_Name().equals("angle_0"))
				currentAtt.set_Value((String)_pin.get_Attribute("vi").get_Value());
		}
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
			if (item.get_Datatype().equals("Complex")) {
				pencil.append(item.get_Name());
				pencil.append("(re= "); pencil.append(((MOAttributeComplex)item).get_Real()); pencil.append(",");
				pencil.append("im= "); pencil.append(((MOAttributeComplex)item).get_Imaginary()); pencil.append("), ");
			}
			else {
				if (item.get_Variability().equals("parameter")){
					pencil.append(item.get_Name());
					pencil.append("=");
					pencil.append(item.get_Value());
					pencil.append(",");
				}
			}
		}
		pencil.deleteCharAt(pencil.lastIndexOf(","));
		pencil.append(") ");
		pencil.append('"');
		pencil.append(this.comment);
		pencil.append('"'); pencil.append(" ");
		pencil.append(this.annotation);
		pencil.append(";\n");
		code= pencil.toString();
		
		return code;
	}
	
	@Override
    public String toString()
    {
    	return this.stereotype+ ", "+ this.pakage+ ", "+ this.instanceName+ ", "+ this.get_RfdId();
    }
}
