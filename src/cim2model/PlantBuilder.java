package cim2model;

import cim2model.modelica.MOClass;
import cim2model.modelica.MOConnectNode;
import cim2model.modelica.MOPlant;
import cim2model.modelica.ipsl.controls.es.IPSLExcitationSystem;
import cim2model.modelica.ipsl.controls.tg.IPSLTurbineGovernor;
import cim2model.modelica.ipsl.machines.IPSLMachine;

public class PlantBuilder 
{
	private IPSLMachine machine= null;
	private IPSLExcitationSystem excitationSystem= null;
	private IPSLTurbineGovernor turbineGovernor= null;
	private MOClass stabilizer= null;
	
	public PlantBuilder() {
		
	}
	
	public MOPlant buildPlant()
    {
		MOPlant newPlant= new MOPlant(machine, excitationSystem, turbineGovernor, stabilizer);
		this.connect_plant(newPlant);
		//TODO check input connections (boolean variables) for each regulator equipment
		//TODO create Modelica.Blocks.Sources.Constant const(k=0) for VOTHSG, VOEL and the rest
		//TODO create Modelica.Blocks.Sources.Constant const1(k=-Modelica.Constants.inf) for VUEL
		return newPlant;
    }

    public PlantBuilder machine(IPSLMachine _machine)
    {
        this.machine = _machine;
        return this;
    }

    public PlantBuilder excitationSystem(IPSLExcitationSystem _excitationSystem)
    {
        this.excitationSystem = _excitationSystem;
        return this;
    }

    public PlantBuilder turbineGovernor(IPSLTurbineGovernor _turbineGovernor)
    {
        this.turbineGovernor = _turbineGovernor;
        return this;
    }
    
    public PlantBuilder stabilizer(MOClass _stabilizer)
    {
        this.stabilizer = _stabilizer;
        return this;
    }
    
    public void connect_plant(MOPlant _planta)
    {
    	MOConnectNode connect;
    	if (_planta.has_excitationSystem()){
    		connect= new MOConnectNode(_planta.getMachine().get_InstanceName(), 
    				_planta.getMachine().EFD0,
    				_planta.getExcitationSystem().get_InstanceName(),
    				_planta.getExcitationSystem().EFD0);
    		_planta.add_Connection(connect);
    		_planta.getExcitationSystem().setConnected("EFD0");
    		connect= new MOConnectNode(_planta.getMachine().get_InstanceName(), 
    				_planta.getMachine().ETERM,
    				_planta.getExcitationSystem().get_InstanceName(),
    				_planta.getExcitationSystem().ECOMP);
    		_planta.add_Connection(connect);
    		_planta.getExcitationSystem().setConnected("ECOMP");
    		connect= new MOConnectNode(_planta.getExcitationSystem().get_InstanceName(), 
    				_planta.getExcitationSystem().EFD,
    				_planta.getMachine().get_InstanceName(),
    				_planta.getMachine().EFD);
    		_planta.add_Connection(connect);
    		connect= new MOConnectNode(_planta.getMachine().get_InstanceName(), 
    				_planta.getMachine().PMECH0,
    				_planta.getMachine().get_InstanceName(),
    				_planta.getMachine().PMECH);
    		_planta.add_Connection(connect);
    	}
    	else if (_planta.has_turbineGovernor()){
    		connect= new MOConnectNode(_planta.getMachine().get_InstanceName(), 
    				_planta.getMachine().SPEED,
    				_planta.getTurbineGovernor().get_InstanceName(),
    				_planta.getTurbineGovernor().SPEED);
    		_planta.add_Connection(connect);
    		_planta.getTurbineGovernor().setConnected("SPEED");
    		connect= new MOConnectNode(_planta.getMachine().get_InstanceName(), 
    				_planta.getMachine().PMECH0,
    				_planta.getTurbineGovernor().get_InstanceName(),
    				_planta.getTurbineGovernor().PMECH0);
    		_planta.add_Connection(connect);
    		_planta.getTurbineGovernor().setConnected("PMECH0");
    		connect= new MOConnectNode(_planta.getMachine().get_InstanceName(), 
    				_planta.getMachine().PMECH,
    				_planta.getTurbineGovernor().get_InstanceName(),
    				_planta.getTurbineGovernor().PMECH);
    		_planta.add_Connection(connect);
    		
    	}
    	else if (_planta.has_powerStabilizer()){
    		//connect pss with es
    		//connect es with machine
    	}
    	else{
    		connect= new MOConnectNode(_planta.getMachine().get_InstanceName(), 
    				_planta.getMachine().PMECH0,
    				_planta.getMachine().get_InstanceName(),
    				_planta.getMachine().PMECH);
    		_planta.add_Connection(connect);
    		connect= new MOConnectNode(_planta.getMachine().get_InstanceName(), 
    				_planta.getMachine().EFD0,
    				_planta.getMachine().get_InstanceName(),
    				_planta.getMachine().EFD);
    		_planta.add_Connection(connect);
    	}
    }
}
