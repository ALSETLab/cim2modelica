package cim2model;

import cim2model.modelica.MOClass;
import cim2model.modelica.MOConnectNode;
import cim2model.modelica.MOPlant;
import cim2model.modelica.ipsl.controls.es.IPSLExcitationSystem;
import cim2model.modelica.ipsl.machines.IPSLMachine;

public class PlantBuilder 
{
	private IPSLMachine machine= null;
	private IPSLExcitationSystem excitationSystem= null;
	private MOClass turbineGovernor= null;
	private MOClass stabilizer= null;
	
	public PlantBuilder() {
		
	}
	
	public MOPlant buildPlant()
    {
		MOPlant newPlant= new MOPlant(machine, excitationSystem, turbineGovernor, stabilizer);
		this.connect_plant(newPlant);
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

    public PlantBuilder turbineGovernor(MOClass _turbineGovernor)
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
			//connect tg with machine
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
