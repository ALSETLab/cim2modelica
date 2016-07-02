package cim2model.modelica.ipsl.controls.es;

import cim2model.modelica.MOClass;

public class IPSLExcitationSystem extends MOClass
{
	/* Inputs */
	public String ETERM= "ETERM";
	public String EFD0= "EFD0";
	/* Outputs */
	public String EFD= "EFD";
	
	public IPSLExcitationSystem(String _name) {
		super(_name);
	}
	
}
