package cim2model.modelica.ipsl.machines;

import cim2model.modelica.MOClass;

public class IPSLMachine extends MOClass {
	/* Inputs */
	public String PMECH= "PMECH";
	public String EFD= "EFD";
	/* Outputs */
	protected String ANGLE;
	public String SPEED= "SPEED";
	public String PMECH0= "PMECH0";
	protected String PELEC;
	public String ETERM= "ETERM";
	public String EFD0= "EFD0";
	protected String ISORCE;
	protected String XADIFD;
	
	public IPSLMachine(String _name) {
		super(_name);
	}

	public String default_connectionPMECH(){
		String code= "";
		StringBuilder pencil= new StringBuilder();
		
		pencil.append("connect(");
		pencil.append(this.name);
		pencil.append(".");
		pencil.append(this.PMECH);
		pencil.append(", ");
		pencil.append(this.name);
		pencil.append(".");
		pencil.append(this.PMECH0);
		code= pencil.toString();
		pencil= null;
		
		return code;
	}
	public String default_connectionEFD(){
		String code= "";
		StringBuilder pencil= new StringBuilder();
		
		pencil.append("connect(");
		pencil.append(this.name);
		pencil.append(".");
		pencil.append(this.EFD);
		pencil.append(", ");
		pencil.append(this.name);
		pencil.append(".");
		pencil.append(this.EFD0);
		code= pencil.toString();
		pencil= null;
		
		return code;
	}
}
