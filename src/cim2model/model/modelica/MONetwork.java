package cim2model.model.modelica;

import java.util.ArrayList;

/**
 * Class with the definition of a high level modelica class, aka model
 * @author fragom
 *
 */
public class MONetwork extends MOModel
{
	protected ArrayList<MOClass> components;
	private MOConnect connection;
	protected ArrayList<MOConnect> network;
		
	public MONetwork(String _name) 
	{
		super(_name, "model");
		this.components= new ArrayList<MOClass>();
	}

	/**
	 * @return the components
	 */
	public ArrayList<MOClass> get_Components() {
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
