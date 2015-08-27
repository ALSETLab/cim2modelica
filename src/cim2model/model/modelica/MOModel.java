package cim2model.model.modelica;

import java.util.ArrayList;

public class MOModel extends ModelicaModel
{
	protected ArrayList<MOClass> components;
		
	public MOModel(String _name) 
	{
		super(_name, "model");
		this.components= new ArrayList<MOClass>();
	}

	/**
	 * @return the components
	 */
	public ArrayList<MOClass> getComponents() {
		return this.components;
	}
	/**
	 * 
	 * @param component
	 */
	public void add_Component(MOClass component){
		this.components.add(component);
	}
	/**
	 * @param attributes the components to set
	 */
	public void add_Component(ArrayList<MOClass> components) {
		this.components = components;
	}
}
