package cim2model.modelica;

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
	protected String value;
	protected String annotation;
	protected Boolean fixed;
	protected Boolean flow;
	
	public MOAttribute() {
		this.variability= "parameter";
		this.datatype = "Real";
		this.name = "name";
		this.value = "0";
		this.fixed = false;
		this.annotation= "annotation";
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
	public String get_Value() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void set_Value(String value) {
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
		StringBuilder line= new StringBuilder();
		
		if (this.flow) {
			line.append("flow "); line.append(" ");
		}
		line.append(this.variability); line.append(" ");
		line.append(this.datatype); line.append(" ");
		line.append(this.name); line.append(" ");
		if (this.variability.equals("variable"))
		{
			line.append("(start="); line.append(" ");
			line.append(value); line.append(", fixed=");
			if(this.fixed){
				line.append("True)"); line.append(" ");
			}
			else {
				line.append("False)"); line.append(" ");
			}
		}
		else {
			line.append("= ");
			line.append(value); line.append(" ");
		}
		line.append('"'+ this.annotation+ '"');
		line.append(";");
		code= line.toString();
		
		return code;
	}
}
