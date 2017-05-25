package cim2model.modelica.ipsl.controls.es;

import cim2model.modelica.MOClass;

public class IPSLExcitationSystem extends MOClass
{
	/* Inputs */
	public String EFD0= "EFD0";
	public String ECOMP= "ECOMP";
	public String VOTHSG= "VOTHSG";
	public String VUEL= "VUEL";
	public String VOEL= "VOEL";
	/* Outputs */
	public String EFD= "EFD";
	
	private boolean EFD0connected;
	private boolean ECOMPconnected;
	private boolean VOTHSGconnected;
	private boolean VUELconnected;
	private boolean VOELconnected;
	
	public IPSLExcitationSystem(String _name) {
		super(_name);
		this.EFD0connected= this.ECOMPconnected= this.VOTHSGconnected= this.VUELconnected= this.VOELconnected= false;
	}
	
	public boolean isConnected(String _input)
	{
		boolean connected= false;
		
		if (_input.equals(EFD0))
			connected= this.EFD0connected;
		if (_input.equals(ECOMP))
			connected= this.ECOMPconnected;
		if (_input.equals(VOTHSG))
			connected= this.VOTHSGconnected;
		if (_input.equals(VUEL))
			connected= this.VUELconnected;
		if (_input.equals(VOEL))
			connected= this.VOELconnected;
		
		return connected;
	}
	
	public void setConnected(String _input)
	{
		if (_input.equals(EFD0))
			this.EFD0connected= true;
		if (_input.equals(ECOMP))
			this.ECOMPconnected= true;
		if (_input.equals(VOTHSG))
			this.VOTHSGconnected= true;
		if (_input.equals(VUEL))
			this.VUELconnected= true;
		if (_input.equals(VOEL))
			this.VOELconnected= true;
	}
}
