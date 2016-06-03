package cim2model.modelica.ipsl.controls.es;

import cim2model.modelica.MOClass;

public class IPSLExcitationSystem extends MOClass
{
	/* Inputs */
	private String ETERM= "ETERM";
	private String EFD0= "EFD0";
	/* Outputs */
	private String EFD= "EFD";
	
	public IPSLExcitationSystem(String _name) {
		super(_name);
	}

	/**
	 * @return the eTERM
	 */
	public String getETERM() {
		return ETERM;
	}

	/**
	 * @return the eFD0
	 */
	public String getEFD0() {
		return EFD0;
	}

	/**
	 * @return the eFD
	 */
	public String getEFD() {
		return EFD;
	}
	
}
