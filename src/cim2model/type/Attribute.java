package cim2model.type;

import java.util.ArrayList;
import java.util.Map;

public class Attribute 
{
	private String component;
	private Map<String, Object> cimAttribute;
	private Map<String, Object> moAttribute;
	private ArrayList<String> type;
	private ArrayList<String> datatype;
	
	public Attribute(String component) {
		super();
		this.component = component;
	}
	/**
	 * @return the component
	 */
	public String getComponent() {
		return component;
	}
	/**
	 * @param component the component to set
	 */
	public void setComponent(String component) {
		this.component = component;
	}
	/**
	 * @return the cimAttribute
	 */
	public Map<String, Object> getCimAttribute() {
		return cimAttribute;
	}
	/**
	 * @param cimAttribute the cimAttribute to set
	 */
	public void setCimAttribute(Map<String, Object> cimAttribute) {
		this.cimAttribute = cimAttribute;
	}
	/**
	 * @param cimAttribute the cimAttribute to set
	 */
	public void setCimAttribute(String _name, Object _value) {
		this.cimAttribute.put(_name, _value);
	}
	/**
	 * @return the moAttribute
	 */
	public Map<String, Object> getMoAttribute() {
		return moAttribute;
	}
	/**
	 * @param moAttribute the moAttribute to set
	 */
	public void setMoAttribute(Map<String, Object> moAttribute) {
		this.moAttribute = moAttribute;
	}
	/**
	 * @param moAttribute the moAttribute to set
	 */
	public void setMoAttribute(String _name, Object _value) {
		this.moAttribute.put(_name, _value);
	}
	/**
	 * @return the type
	 */
	public ArrayList<String> getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(ArrayList<String> type) {
		this.type = type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String _value) {
		this.type.add(_value);
	}
	/**
	 * @return the datatype
	 */
	public ArrayList<String> getDatatype() {
		return datatype;
	}
	/**
	 * @param datatype the datatype to set
	 */
	public void setDatatype(ArrayList<String> datatype) {
		this.datatype = datatype;
	}
	/**
	 * @param datatype the datatype to set
	 */
	public void setDatatype(String _value) {
		this.datatype.add(_value);
	}
	
}
