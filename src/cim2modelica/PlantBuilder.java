package cim2modelica;

import cim2modelica.modelica.MOClass;
import cim2modelica.modelica.MOConnectNode;
import cim2modelica.modelica.MOConnector;
import cim2modelica.modelica.MOPlant;
import cim2modelica.modelica.openipsl.controls.es.OpenIPSLExcitationSystem;
import cim2modelica.modelica.openipsl.controls.tg.OpenIPSLTurbineGovernor;
import cim2modelica.modelica.openipsl.machines.OpenIPSLMachine;

public class PlantBuilder 
{
	private OpenIPSLMachine machine= null;
	private OpenIPSLExcitationSystem excitationSystem= null;
	private OpenIPSLTurbineGovernor turbineGovernor= null;
	private MOClass stabilizer= null;
	
	public PlantBuilder() {
		
	}
	
	public MOPlant buildPlant()
    {
		MOPlant newPlant= new MOPlant(machine, excitationSystem, turbineGovernor, stabilizer);
		String nameModel= machine.get_Name();
		newPlant.set_Package("PowerPlant");
		if (excitationSystem != null){
    		nameModel+= "_"+ excitationSystem.get_Name();
    	}
    	else if (turbineGovernor != null){
    		nameModel+= "_"+ turbineGovernor.get_Name();
    	}
    	else if (stabilizer != null){
    		nameModel+= "_"+ stabilizer.get_Name();
    	}
		nameModel += machine.get_RdfId().split("-")[0];
		newPlant.set_Name(nameModel);
		newPlant.setInstanceName(machine.get_InstanceName());
		this.connect_plant(newPlant);
		//TODO create Modelica.Blocks.Sources.Constant const(k=0) for VOTHSG, VOEL and the rest
		//TODO create Modelica.Blocks.Sources.Constant const1(k=-Modelica.Constants.inf) for VUEL
		return newPlant;
    }

    public PlantBuilder machine(OpenIPSLMachine _machine)
    {
        this.machine = _machine;
        return this;
    }

    public PlantBuilder excitationSystem(OpenIPSLExcitationSystem _excitationSystem)
    {
        this.excitationSystem = _excitationSystem;
        return this;
    }

    public PlantBuilder turbineGovernor(OpenIPSLTurbineGovernor _turbineGovernor)
    {
        this.turbineGovernor = _turbineGovernor;
        return this;
    }
    
    public PlantBuilder stabilizer(MOClass _stabilizer)
    {
        this.stabilizer = _stabilizer;
        return this;
    }
    
	private void connect_stabilizier_excitation(MOPlant _planta) {

	}
    /**
     * Method creates the internal connections of the plant: connections between generator and controls
     * @param _planta
     */
    private void connect_plant(MOPlant _planta)
	{
    	MOConnectNode connect;
    	if (_planta.has_excitationSystem()){
//    		_planta.getExcitationSystem().set_InstanceName(_planta.getMachine().get_InstanceName()+
//    				"_"+ _planta.getExcitationSystem().get_InstanceName());
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
    	}
		else if (_planta.has_powerStabilizer()) {
			// connect pss with es
			// connect es with machine
		} else {
			connect = new MOConnectNode(_planta.getMachine().get_InstanceName(), _planta.getMachine().EFD0,
					_planta.getMachine().get_InstanceName(), _planta.getMachine().EFD);
			_planta.add_Connection(connect);
		}
		if (_planta.has_turbineGovernor()) {
//    		_planta.getTurbineGovernor().set_InstanceName(_planta.getMachine().get_InstanceName()+
//    				"_"+ _planta.getTurbineGovernor().get_InstanceName());
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
    	else{
    		connect= new MOConnectNode(_planta.getMachine().get_InstanceName(), 
    				_planta.getMachine().PMECH0,
    				_planta.getMachine().get_InstanceName(),
    				_planta.getMachine().PMECH);
    		_planta.add_Connection(connect);

    	}
    }
    
	private static void constant_connect_controls(MOPlant _planta, MOClass _cteblock) {
		MOConnectNode connect;
		if (_planta.has_excitationSystem()) {
			connect = new MOConnectNode(_cteblock.get_InstanceName(), "y",
					_planta.getExcitationSystem().get_InstanceName(), _planta.getExcitationSystem().VOTHSG);
			_planta.add_Connection(connect);
			_planta.getExcitationSystem().setConnected("VOTHSG");
			connect = new MOConnectNode(_cteblock.get_InstanceName(), "y",
					_planta.getExcitationSystem().get_InstanceName(), _planta.getExcitationSystem().VUEL);
			_planta.add_Connection(connect);
			_planta.getExcitationSystem().setConnected("VUEL");
			connect = new MOConnectNode(_cteblock.get_InstanceName(), "y",
					_planta.getExcitationSystem().get_InstanceName(), _planta.getExcitationSystem().VOEL);
			_planta.add_Connection(connect);
			connect = new MOConnectNode(_cteblock.get_InstanceName(), "y",
					_planta.getExcitationSystem().get_InstanceName(), _planta.getExcitationSystem().XADIFD);
			_planta.add_Connection(connect);
		}
	}
	/**
	 * Automatically connects the machine component pin with the plant component
	 * pin. This last pin is used to connect the plant component to the rest of
	 * the network. 2nd, connects remainin input/output pins of controlers to a
	 * Modelica.Blocks.Sources.Constant block
	 * 
	 * @param _planta
	 * @param _pin
	 */
	// public static void assemble_plant(MOPlant _planta, MOConnector _pin)
	// {
	// MOConnectNode connect;
	// connect= new MOConnectNode(_planta.getMachine().get_InstanceName(),
	// _planta.getMachine().get_Terminal(_pin.get_RdfId()).get_InstanceName(),
	// "",
	// _planta.getOutpin().get_InstanceName());
	// _planta.add_Connection(connect);
	// }
	public static void assemble_plant(MOPlant _planta, MOConnector _pin, MOClass _cteblock)
    {
    	MOConnectNode connect;
    	connect= new MOConnectNode(_planta.getMachine().get_InstanceName(), 
				_planta.getMachine().get_Terminal(_pin.get_RdfId()).get_InstanceName(),
				"",
				_planta.getOutpin().get_InstanceName());
		_planta.add_Connection(connect);
		constant_connect_controls(_planta, _cteblock);
    }
}
