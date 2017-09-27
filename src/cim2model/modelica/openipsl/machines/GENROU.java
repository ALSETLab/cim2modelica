package cim2model.modelica.ipsl.machines;

import cim2model.modelica.MOClass;

public class GENROU extends MOClass
{
	/* Inputs */
	double PMECH;
	double EFD;
	/* Outputs */
	double ANGLE;
	double SPEED;
	double PMECH0;
	double PELEC;
	double ETERM;
	double EFD0;
	double ISORCE;
	double XADIFD;
	
	public GENROU(String _name) {
		super(_name);
	}

	/**
	 * 
	 * @return
	 */
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
	/**
	 * 
	 * @return
	 */
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
