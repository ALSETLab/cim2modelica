package cim2model.modelica.openipsl.controls.tg;

import cim2model.modelica.MOClass;

public class OpenIPSLTurbineGovernor extends MOClass 
{
	/* Inputs */
	public String PMECH0= "PMECH0";
	public String SPEED= "SPEED";
	/* Outputs */
	public String PMECH= "PMECH";
	
	private boolean PMECH0connected;
	private boolean SPEEDconnected;
	
	public OpenIPSLTurbineGovernor(String _name) 
	{
		super(_name);
		this.PMECH0connected= this.SPEEDconnected= false;
	}

	public boolean isConnected(String _input)
	{
		boolean connected= false;
		
		if (_input.equals(PMECH0))
			connected= this.PMECH0connected;
		if (_input.equals(SPEED))
			connected= this.SPEEDconnected;
		
		return connected;
	}
	
	public void setConnected(String _input)
	{
		if (_input.equals(PMECH0))
			this.PMECH0connected= true;
		if (_input.equals(SPEED))
			this.SPEEDconnected= true;
	}
}
