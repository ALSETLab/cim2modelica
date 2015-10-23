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
	protected ArrayList<MOConnect> conexions;
		
	public MONetwork(String _name) 
	{
		super(_name, "model");
		this.components= new ArrayList<MOClass>();
		this.conexions= new ArrayList<MOConnect>();
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
	public void add_Component(MOClass _value){
		this.components.add(_value);
	}
	/**
	 * @param attributes the components to set
	 */
	public void add_Component(ArrayList<MOClass> components) {
		this.components = components;
	}
	
	/**
	 * @return the connections
	 */
	public ArrayList<MOConnect> get_Connections() {
		return this.conexions;
	}
	/**
	 * 
	 * @param component
	 */
	public void add_Connection(MOConnect _value){
		this.conexions.add(_value);
	}
	
	public String to_ModelicaClass()
	{
		String code= "";
		StringBuilder pencil= new StringBuilder();
		
		/* HEADER */
		pencil.append(this.stereotype); pencil.append(" ");
		pencil.append(this.name); pencil.append(" ");
		pencil.append('"');
		pencil.append(this.annotation);
		pencil.append('"'); pencil.append("\n");
		/* VARIABLE SECTION */
		for (MOClass component: this.components)
		{
			pencil.append("\t");
			pencil.append(component.to_ModelicaInstance());
		}
		/* EQUATION SECTION */
		pencil.append("equation\n");
		for (MOConnect conexio: this.conexions)
		{
			pencil.append("\t");
			pencil.append(conexio.to_ModelicaEquation());
		}
		pencil.append("end ");
		pencil.append(this.name);
		pencil.append(";");
		code= pencil.toString();
		
		return code;
	}
}
