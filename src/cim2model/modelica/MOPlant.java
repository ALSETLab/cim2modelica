package cim2model.modelica;

import java.util.ArrayList;
import java.util.Iterator;

import cim2model.modelica.ipsl.controls.es.IPSLExcitationSystem;
import cim2model.modelica.ipsl.controls.tg.IPSLTurbineGovernor;
import cim2model.modelica.ipsl.machines.IPSLMachine;

/**
 * Class with the definition of a high level modelica class, aka model
 * @author fragom
 *
 */
public class MOPlant extends MOModel
{
	private String visibility;
	private String variability;
	private String instanceName; 
	private IPSLMachine machine;
	private IPSLExcitationSystem excitationSystem;
	private IPSLTurbineGovernor turbineGovernor;
	private MOClass stabilizer;
	private MOConnector outpin;
	private ArrayList<MOConnectNode> conexions;
		
	public MOPlant(IPSLMachine _mach, IPSLExcitationSystem _es, 
			IPSLTurbineGovernor _tg, MOClass _stab) 
	{
		super(_mach.instanceName, "model");
		this.machine= _mach;
		this.excitationSystem= _es;
		this.turbineGovernor= _tg;
		this.stabilizer= _stab;
		this.outpin= null;
		this.conexions= new ArrayList<MOConnectNode>();
	}
	
	/**
	 * @return the visibility
	 */
	public String getVisibility() {
		return visibility;
	}

	/**
	 * @param visibility the visibility to set
	 */
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	/**
	 * @return the variability
	 */
	public String getVariability() {
		return variability;
	}

	/**
	 * @param variability the variability to set
	 */
	public void setVariability(String variability) {
		this.variability = variability;
	}

	/**
	 * @return the instanceName
	 */
	public String getInstanceName() {
		return instanceName;
	}

	/**
	 * @param instanceName the instanceName to set
	 */
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	/**
	 * @return the outpin
	 */
	public MOConnector getOutpin() {
		return outpin;
	}

	/**
	 * @param outpin the outpin to set
	 */
	public void setOutpin(MOConnector outpin) {
		this.outpin = outpin;
	}

	/**
	 * @return the machine
	 */
	public IPSLMachine getMachine() {
		return machine;
	}

	/**
	 * 
	 * @return
	 */
	public boolean has_excitationSystem(){
		return this.excitationSystem!= null;
	}
	/**
	 * @return the excitationSystem
	 */
	public IPSLExcitationSystem getExcitationSystem() {
		return excitationSystem;
	}

	/**
	 * 
	 * @return
	 */
	public boolean has_turbineGovernor(){
		return this.turbineGovernor!= null;
	}
	/**
	 * @return the turbineGovernor
	 */
	public IPSLTurbineGovernor getTurbineGovernor() {
		return turbineGovernor;
	}

	/**
	 * 
	 * @return
	 */
	public boolean has_powerStabilizer(){
		return this.stabilizer!= null;
	}
	/**
	 * @return the stabilizer
	 */
	public MOClass getStabilizer() {
		return stabilizer;
	}

	/**
	 * 
	 * @param variable
	 */
	public void add_Terminal(MOConnector pin){
		this.outpin= pin;
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
		pencil.append(this.name); pencil.append(" ");
		pencil.append('"');
		pencil.append(this.annotation);
		pencil.append('"'); pencil.append("\n");
		/* VARIABLE SECTION */
		pencil.append("\t");
		pencil.append(this.getOutpin().to_ModelicaInstance());
		// print machine component
		pencil.append("\t");
		pencil.append(this.machine.to_ModelicaInstance());
		// print excitation system component
		if (this.has_excitationSystem()){
			pencil.append("\t");
			pencil.append(this.excitationSystem.to_ModelicaInstance());
		}
		// print turbine governor component
		if (this.has_turbineGovernor()){
			pencil.append("\t");
			pencil.append(this.turbineGovernor.to_ModelicaInstance());
		}
		// print stabilizer component
		if (this.has_powerStabilizer()){}
		/* EQUATION SECTION */
		pencil.append("equation\n");
//		if (this.excitationSystem== null && this.turbineGovernor== null){
//			this.machine.default_connectionPMECH();
//			this.machine.default_connectionEFD();
//		}
		for (MOConnectNode conexio: this.conexions)
		{
			pencil.append("\t");
			pencil.append(conexio.to_ModelicaEquation("plant"));
		}
		pencil.append("end ");
		pencil.append(this.name);
		pencil.append(";");
		code= pencil.toString();
		
		return code;
	}
	
	/**
	 * generates the code for the instances of the objects with in the plant 
	 * object
	 * @return
	 */
	public String to_ModelicaInstance(){
		String code= "";
		StringBuilder pencil= new StringBuilder();
		
		pencil.append(this.pakage);
		pencil.append(".");
		pencil.append(this.name);
		pencil.append(" ");
		pencil.append(this.instanceName);
		pencil.append(" ");
		pencil.append('"');
		pencil.append(this.comment);
		pencil.append('"'); 
		pencil.append(" ");
		pencil.append(this.annotation);
		pencil.append(";\n");
		
		code= pencil.toString(); pencil= null;
		
		return code;
	}
	
	/**
	 * This methods creates the connections within the plant object
	 * @return
	 */
	public String to_ModelicaEquation(String isNetwork)
	{
		String code= "";
//		if (isNetwork.equals("network"))
//			code= this.connect_equipmentNetwork();
//		if (isNetwork.equals("plant"))
//			code= this.connect_equipmentPlant();
		return code;
	}
	
}
