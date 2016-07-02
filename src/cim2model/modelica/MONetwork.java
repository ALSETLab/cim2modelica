package cim2model.modelica;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class with the definition of a high level modelica class, aka model
 * @author fragom
 *
 */
public class MONetwork extends MOModel
{
	protected ArrayList<MOPlant> elecPlants;
	protected ArrayList<MOClass> equipment;
	protected ArrayList<MOConnectNode> conexions;
		
	public MONetwork(String _name) 
	{
		super(_name, "model");
		this.elecPlants= new ArrayList<MOPlant>();
		this.equipment= new ArrayList<MOClass>();
		this.conexions= new ArrayList<MOConnectNode>();
	}

	/**
	 * @return the equipment
	 */
	public ArrayList<MOPlant> get_planta() {
		return this.elecPlants;
	}
	/**
	 * 
	 * @param component
	 */
	public void add_Plant(MOPlant _plant){
		this.elecPlants.add(_plant);
	}
	/**
	 * @param attributes the equipment to set
	 */
	public void add_Plant(ArrayList<MOPlant> _planta) {
		this.elecPlants = _planta;
	}
	
	/**
	 * @return the equipment
	 */
	public ArrayList<MOClass> get_Equipment() {
		return this.equipment;
	}
	/**
	 * 
	 * @param component
	 */
	public void add_Component(MOClass _value){
		this.equipment.add(_value);
	}
	/**
	 * @param attributes the equipment to set
	 */
	public void add_Component(ArrayList<MOClass> equipment) {
		this.equipment = equipment;
	}
	
	/**
	 * @return the connections
	 */
	public ArrayList<MOConnectNode> get_Connections() {
		return this.conexions;
	}
	/**
	 * 
	 * @param component
	 */
	public void add_Connection(MOConnectNode _value){
		this.conexions.add(_value);
	}
	public boolean exist_Connection(MOConnectNode _value){
		boolean exists= false;
		MOConnectNode current;
		
		Iterator<MOConnectNode> iconnections= this.conexions.iterator();
		while (!exists && iconnections.hasNext())
		{
			current= iconnections.next();
			exists= (current.getId_component_u().equals(_value.getId_component_u()) &&
					current.getId_component_y().equals(_value.getId_component_y())) ||
					(current.getId_component_u().equals(_value.getId_component_y()) &&
					current.getId_component_y().equals(_value.getId_component_u()));
		}
		
		return exists;
	}
	
	public String to_ModelicaClass()
	{
		String code= "";
		StringBuilder pencil= new StringBuilder();
		
		/* HEADER */
		pencil.append(this.stereotype); pencil.append(" ");
		pencil.append(this.name); 
		pencil.append(" ");
		pencil.append('"');
		pencil.append(this.comment);
		pencil.append('"'); pencil.append("\n");
		/* VARIABLE SECTION */
		for (MOClass component: this.equipment)
		{
			pencil.append("\t");
			pencil.append(component.to_ModelicaInstance());
		}
		for (MOPlant plant: this.elecPlants)
		{
			pencil.append("\t");
			pencil.append(plant.to_ModelicaInstance());
		}
		/* EQUATION SECTION */
		pencil.append("equation\n");
		for (MOConnectNode conexio: this.conexions)
		{
			pencil.append("\t");
			pencil.append(conexio.to_ModelicaEquation("network"));
		}
		/* PLANT CONNECTIONS */
		for (MOPlant plant: this.elecPlants)
		{
			pencil.append(plant.to_ModelicaConnection());
		}
		pencil.append("end ");
		pencil.append(this.name);
		pencil.append(";");
		code= pencil.toString();
		
		return code;
	}
}
