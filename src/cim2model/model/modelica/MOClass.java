package cim2model.model.modelica;

import java.util.ArrayList;

public class MOClass extends ModelicaModel
{
	private String visibility;
	private String variability;
	private ArrayList<MOAttribute> attributes;
	private ArrayList<MOConnector> terminals;
	private ArrayList<MOEquation> equations;
	
	public MOClass(String _name) 
	{
		super(_name, "class");
		this.attributes= new ArrayList<MOAttribute>();
		this.terminals= new ArrayList<MOConnector>();
		this.equations= new ArrayList<MOEquation>();
	}
	
	/**
	 * @return the name
	 */
	public String get_Name() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void set_Name(String name) {
		this.name = name;
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
	public String get_Stereotype() {
		return stereotype;
	}

	/**
	 * @param stereotype the stereotype to set
	 */
	public void set_Stereotype(String stereotype) {
		this.stereotype = stereotype;
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
	 * @return the terminals
	 */
	public ArrayList<MOConnector> get_Terminals() {
		return terminals;
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
		for (MOAttribute item: this.attributes)
		{
			pencil.append("\t");
			pencil.append(item.getVariability()); pencil.append(" ");
			pencil.append(item.getDatatype()); pencil.append(" ");
			pencil.append(item.getName());
			if (item.getVariability().equals("parameter") || item.getVariability().equals("constant")){
				pencil.append("=");
				pencil.append(item.getValue());
				pencil.append(" ");
			}
			else{
				pencil.append("(start=");
				pencil.append(item.getValue());
				pencil.append(", fixed=");
				if (item.isFixed())
					pencil.append("true");
				else
					pencil.append("false");
				pencil.append(") ");
			}
			pencil.append('"');
			pencil.append(item.getAnnotation());
			pencil.append('"');
			pencil.append(";\n");
		}
		for (MOConnector pin: this.terminals)
		{
			pencil.append("\t");
			pencil.append(pin.to_ModelicaInstance());
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
	public String toModelicaInstance()
	{
		String code= "";
		StringBuilder pencil= new StringBuilder();
		
		if (!this.visibility.equals("public"))
		{
			pencil.append(this.visibility); 
			pencil.append(" ");
		}
		pencil.append(this.name);
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
		
		code= pencil.toString();
		
		return code;
	}
}
