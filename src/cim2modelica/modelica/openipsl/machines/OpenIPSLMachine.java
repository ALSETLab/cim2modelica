package cim2modelica.modelica.openipsl.machines;

import cim2modelica.modelica.MOAttribute;
import cim2modelica.modelica.MOAttributeComplex;
import cim2modelica.modelica.MOClass;
import cim2modelica.modelica.MOConnector;
import cim2modelica.modelica.MOEquation;

public class OpenIPSLMachine extends MOClass {
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
	protected String annotationIcon;
	
	// TODO annotacion(Placement...) de cada input/output en el constructor

	public OpenIPSLMachine(String _name) {
		super(_name);
		this.annotationIcon = "annotation ("
				+ "Placement( transformation( extent={{-10,-10},{10,10}},"
				+ "rotation=0, origin={110,-90}), "
				+ "iconTransformation(extent={{-8,-8},{8,8}}, rotation=0, origin={108,-90})));";
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
		// TODO add annotation (Line...)
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
		// TODO add annotation (Line...)
		pencil= null;
		
		return code;
	}

	/**
	 * class name "some comments" parameter ... equation ... end name;
	 * 
	 * @return text representation of the class
	 */
	@Override
	public String to_ModelicaClass() {
		String code = "";
		StringBuilder pencil = new StringBuilder();

		/* ATTRIBUTES SECTION */
		pencil.append(this.stereotype);
		pencil.append(" ");
		pencil.append(this.name);
		pencil.append(" ");
		pencil.append('"');
		pencil.append(this.comment);
		pencil.append('"');
		pencil.append("\n");
		for (MOConnector pin : this.terminals) {
			pencil.append("\t");
			pencil.append(pin.to_ModelicaInstance(false));
		}
		for (MOAttribute item : this.attributes) {
			if (item.get_Visibility().equals("public")) {
				pencil.append("\t");
				if (item.get_Datatype().equals("Complex"))
					pencil.append(((MOAttributeComplex) item).to_Modelica());
				else
					pencil.append(item.to_Modelica());
				pencil.append("\n");
			}
		}
		boolean firstIteration = true;
		for (MOAttribute item : this.attributes) {
			if (item.get_Visibility().equals("protected")) {
				if (firstIteration)
					pencil.append("protected\n");
				pencil.append("\t");
				if (item.get_Datatype().equals("Complex"))
					pencil.append(((MOAttributeComplex) item).to_Modelica());
				else
					pencil.append(item.to_Modelica());
				pencil.append("\n");
				firstIteration = false;
			}
		}
		/* EQUATION SECTION */
		pencil.append("equation\n");
		for (MOEquation equ : this.equations) {
			pencil.append("\t");
			pencil.append(equ.getEquation());
			pencil.append(";\n");
		}
		// TODO CAD
		pencil.append(this.annotationIcon);
		pencil.append("end ");
		pencil.append(this.name);
		pencil.append(";");
		code = pencil.toString();

		return code;
	}
}
