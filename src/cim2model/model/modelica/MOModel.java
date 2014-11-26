package cim2model.model.modelica;

import java.util.ArrayList;

public class MOModel 
{
	protected String name;
	protected String stereotype;
	protected String annotation;
	protected ArrayList<MOComponent> components;
		
	public MOModel(String name) 
	{
		super();
		this.name = name;
		this.stereotype= "model";
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
	/**
	 * @return the components
	 */
	public ArrayList<MOComponent> getComponents() {
		return this.components;
	}
	/**
	 * 
	 * @param component
	 */
	public void setComponent(MOComponent component){
		this.components.add(component);
	}
	/**
	 * @param attributes the components to set
	 */
	public void setComponents(ArrayList<MOComponent> components) {
		this.components = components;
	}
}
