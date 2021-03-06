package cim2modelica.modelica;

/**
 * Generic class to implement the declaration of a Modelica Parameter.
 * @author fragom
 *
 */
public class MOAttribute 
{
	protected String visibility;
	protected String variability;
	protected String datatype;
	protected String name;
	protected Object value;
	protected String comment;
	protected String annotation;
	protected Boolean fixed;
	protected Boolean flow;
	
	public MOAttribute() {
		this.variability= "parameter";
		this.datatype = "Real";
		this.name = "name";
		this.value = "0";
		this.fixed = false;
		this.comment= "something here";
		this.annotation= "annotation ()";
		this.flow= false;
	}
	/**
	 * @return the visibility
	 */
	public String get_Visibility() {
		return visibility;
	}
	/**
	 * @param paramtype the variability to set
	 */
	public void set_Visibility(String _visibility) {
		this.visibility = _visibility;
	}
	/**
	 * @return the variability
	 */
	public String get_Variability() {
		return variability;
	}
	/**
	 * @param paramtype the variability to set
	 */
	public void set_Variability(String _variability) {
		if (_variability.equals("variable"))
			this.variability= "";
		else
			this.variability= _variability;
	}
	/**
	 * @return the datatype
	 */
	public String get_Datatype() {
		return datatype;
	}
	/**
	 * @param datatype the datatype to set
	 */
	public void set_Datatype(String _datatype) {
		this.datatype = _datatype;
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
	 * @return the value
	 */
	public Object get_Value() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void set_Value(Object value) {
		this.value = value;
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
	 * @return the fixed
	 */
	public Boolean is_Fixed() {
		return fixed;
	}
	/**
	 * @param fixed the fixed to set
	 */
	public void set_Fixed(Boolean fixed) {
		this.fixed = fixed;
	}
	/**
	 * @return the flow
	 */
	public Boolean get_Flow() {
		return flow;
	}
	/**
	 * @param fixed the fixed to set
	 */
	public void set_Flow(Boolean flow) {
		this.flow = flow;
	}
	
	public String to_Modelica()
	{
		String code= "";
		StringBuilder pencil= new StringBuilder();
		
		if (this.flow) {
			pencil.append("flow ");
		}
		pencil.append(this.variability); pencil.append(" ");
		pencil.append(this.datatype); pencil.append(" ");
		pencil.append(this.name); pencil.append(" ");
		if (this.variability.equals("variable"))
		{
			pencil.append("(start="); pencil.append(" ");
			pencil.append(value); pencil.append(", fixed=");
			if(this.fixed){
				pencil.append("True)"); pencil.append(" ");
			}
			else {
				pencil.append("False)"); pencil.append(" ");
			}
		}
		else {
			pencil.append("= ");
			pencil.append(value); pencil.append(" ");
		}
		pencil.append('"');
		pencil.append(this.comment);
		pencil.append('"'); 
		pencil.append(" ");
		pencil.append(this.annotation);
		pencil.append(";");
		code= pencil.toString();
		
		return code;
	}
}
