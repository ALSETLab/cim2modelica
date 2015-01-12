package cim2model.model.modelica;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic class to implement and map the declaration of a Modelica Parameter.
 * @author fragom
 *
 */
public class MOVariable 
{
	private String visibility;
	private String variability;
	private String datatype;
	private String name;
	private String value;
	private String annotation;
	private Boolean fixed;
	private Boolean flow;
	
	public MOVariable() {
		super();
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
	public String getVisibility() {
		return visibility;
	}
	/**
	 * @param paramtype the variability to set
	 */
	public void setVisibility(String _visibility) {
		this.visibility = _visibility;
	}
	/**
	 * @return the variability
	 */
	public String getVariability() {
		return variability;
	}
	/**
	 * @param paramtype the variability to set
	 */
	public void setVariability(String _variability) {
		if (_variability.equals("variable"))
			this.variability= "";
		else
			this.variability= _variability;
	}
	/**
	 * @return the datatype
	 */
	public String getDatatype() {
		return datatype;
	}
	/**
	 * @param datatype the datatype to set
	 */
	public void setDatatype(String _datatype) {
		this.datatype = _datatype;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
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
	 * @return the fixed
	 */
	public Boolean isFixed() {
		return fixed;
	}
	/**
	 * @param fixed the fixed to set
	 */
	public void setFixed(Boolean fixed) {
		this.fixed = fixed;
	}
	/**
	 * @return the flow
	 */
	public Boolean getFlow() {
		return flow;
	}
	/**
	 * @param fixed the fixed to set
	 */
	public void setFlow(Boolean flow) {
		this.flow = flow;
	}
	
	public String toModelica()
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
