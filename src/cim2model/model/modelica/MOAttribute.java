package cim2model.model.modelica;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic class to implement and map the declaration of a Modelica Parameter.
 * @author fragom
 *
 */
public class MOAttribute 
{
	private String paramtype;
	private String datatype;
	private String name;
	private float value;
	private Map<String, Float> properties;
	private String annotation;
	
	public MOAttribute(String datatype, String name) {
		super();
		this.paramtype= "parameter";
		this.datatype = datatype;
		this.name = name;
		this.value = 0;
		this.properties= new HashMap<String, Float>();
		this.annotation= "annotation";
	}
	/**
	 * @return the paramtype
	 */
	public String getParamtype() {
		return paramtype;
	}
	/**
	 * @param paramtype the paramtype to set
	 */
	public void setParamtype(String paramtype) {
		this.paramtype = paramtype;
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the value
	 */
	public float getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(float value) {
		this.value = value;
	}
	/**
	 * @return the properties
	 */
	public Map<String, Float> getProperties() {
		return properties;
	}
	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Map<String, Float> properties) {
		this.properties = properties;
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
	
	
}
