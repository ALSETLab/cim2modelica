package cim2model.model.modelica;

/**
 * Generic class which contains general attributes to create a Modelica class
 * @author fragom
 *
 */
public abstract class MOModel 
{
	protected String name;
	protected String stereotype;
	protected String annotation;
	
	public MOModel(String _name, String _stereotype)
	{
		this.name= _name;
		this.stereotype= _stereotype;
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
	
	public void from_XMLMapping(){}
}
