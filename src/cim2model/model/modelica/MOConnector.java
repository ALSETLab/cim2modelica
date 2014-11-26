package cim2model.model.modelica;

import java.util.ArrayList;

public class MOConnector 
{
	private String visibility;
	private String name;
	private String datatype;
	private String annotation;
	private ArrayList<MOVariable> attributes;
	
	public MOConnector(String _datatype)
	{
		this.datatype= _datatype;
		this.attributes= new ArrayList<MOVariable>();
	}

	/**
	 * @return the name
	 */
	public String getDatatype() {
		return name;
	}
	/**
	 * @param name the name to set when is used as instance in other components
	 */
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set when is used as instance in other components
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the visibility
	 */
	public String getVisibility() {
		return visibility;
	}

	/**
	 * @param visibility the visibility to set
	 */
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	/**
	 * @return the annotation
	 */
	public String getAnnotation() {
		return annotation;
	}

	/**
	 * @param annotation the annotation to set
	 */
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	/**
	 * @return the attributes
	 */
	public ArrayList<MOVariable> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttribute(MOVariable attribute) {
		this.attributes.add(attribute);
	}
	
	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(ArrayList<MOVariable> attributes) {
		this.attributes = attributes;
	}
	
	/**
	 * connector name "some comments"
	 * 		parameter 
	 * 		...
	 * end name;
	 * @return text representation of the class
	 */
	public String toModelicaClass()
	{
		String code= "";
		StringBuilder pencil= new StringBuilder();
		
		pencil.append("connector ");
		pencil.append(this.datatype);
		pencil.append(" "); pencil.append('"');
		pencil.append(this.annotation);
		pencil.append('"'); pencil.append("\n");
		for (MOVariable item: this.attributes)
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
	public String toModelicaInstance()
	{
		String code= "";
		StringBuilder pencil= new StringBuilder();
		
		if (!this.visibility.equals("public"))
		{
			pencil.append(this.visibility); 
			pencil.append(" ");
		}
		pencil.append(this.datatype);
		pencil.append(" ");
		pencil.append(this.name);
		pencil.append("(");
		for (MOVariable item: this.attributes)
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
