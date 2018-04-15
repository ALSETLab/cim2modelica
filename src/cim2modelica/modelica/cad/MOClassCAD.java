package cim2modelica.modelica.cad;

import java.util.Iterator;

import cim2modelica.cim.DLProfileModel;
import cim2modelica.cim.map.openipsl.connectors.PwPinMap;
import cim2modelica.modelica.MOAttribute;
import cim2modelica.modelica.MOAttributeComplex;
import cim2modelica.modelica.MOClass;
import cim2modelica.modelica.MOConnector;
import cim2modelica.modelica.MOEquation;
    
/**
 * Generic class for implementing the declaration of a modelica class. this
 * class will be used to store characteristics of a component model
 * 
 * @author fragom
 *
 */
public class MOClassCAD extends MOClass {

    public int[] origin = { 0, 0 };
    public int[][] extend = { { -10, -10 }, { 10, 10 } };
    // TODO annotation(Placement(transformation(origin= , extend= , rotation= )

    public MOClassCAD(String _name) {
	super(_name);
	this.annotation = "annotation (Placement(transformation(origin={0,0}, extent={{-10,-10},{10,10}})))";

    }

    @Override
    public MOConnectorCAD get_Terminal(String _rfdId) {
	MOConnectorCAD current;
	Iterator<MOConnector> iPins;

	iPins = this.terminals.iterator();
	boolean exists = false;
	do {
	    current = (MOConnectorCAD) iPins.next();
	    exists = current.get_RdfId().equals(_rfdId);
	} while (!exists && iPins.hasNext());
	if (exists)
	    return current;
	else
	    return null;
    }

    public void update_ComponentAnnotation(PwPinMap _pin, DLProfileModel _diagramLayout) {

	String[] coordPoint = _diagramLayout
		.get_ObjectPoint(_diagramLayout.get_Object(_pin.getConnectivityNode().split("#")[1]), "1");
	this.set_Coord(coordPoint[0], coordPoint[0]);

	StringBuilder pencil = new StringBuilder();
	if (this.coord_eq.size() != 0 || this.coord_eq.size() != 0) {
	    // extent={{-10,-10},{10,10}},
	    pencil.append("annotation (Placement(visible= true, transformation(");
	    pencil.append("origin={");
	    pencil.append(this.coord_eq.get(0));
	    pencil.append(",");
	    pencil.append(this.coord_eq.get(1));
	    pencil.append("}, extent= {{-10,-10},{10,10}})))");
	} else
	    pencil.append("annotation ();");
	this.annotation = pencil.toString();
	pencil = null;
    }

    public void update_ComponentAnnotation(DLProfileModel _diagramLayout) {
	String[] coordPoint = _diagramLayout.get_ObjectPoint(_diagramLayout.get_Object(this.get_RdfId()), "1");
	this.set_Coord(coordPoint[0], coordPoint[0]);

	StringBuilder pencil = new StringBuilder();
	if (this.coord_eq.size() != 0 || this.coord_eq.size() != 0) {
	    pencil.append("annotation (Placement(visible= true, transformation(");
	    pencil.append("origin={");
	    pencil.append(this.coord_eq.get(0));
	    pencil.append(",");
	    pencil.append(this.coord_eq.get(1));
	    pencil.append("}, extent= {{-10,-10},{10,10}})))");
	} else
	    pencil.append("annotation ();");
	this.annotation = pencil.toString();
	pencil = null;
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
	    pencil.append(((MOConnectorCAD) pin).to_ModelicaInstance(false));
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
	pencil.append("end ");
	pencil.append(this.name);
	pencil.append(";");
	code = pencil.toString();

	return code;
    }

    /**
     * Name className (parameter1=?,value2=?,...) "comments";
     * 
     * @return text representation of the instance
     */
    @Override
    public String to_ModelicaInstance() {
	String code = "";
	StringBuilder pencil = new StringBuilder();

	if (!this.visibility.equals("public")) {
	    pencil.append(this.visibility);
	    pencil.append(" ");
	}
	pencil.append(this.pakage);
	pencil.append(".");
	pencil.append(this.name);
	pencil.append(" ");
	pencil.append(this.instanceName);
	pencil.append(" (");
	for (MOAttribute item : this.attributes) {
	    if (item.get_Visibility().equals("public"))
		if (item.get_Datatype().equals("Complex") && item.get_Variability().equals("parameter")) {
		    pencil.append(item.get_Name());
		    pencil.append("(re= ");
		    pencil.append(((MOAttributeComplex) item).get_Real());
		    pencil.append(",");
		    pencil.append("im= ");
		    pencil.append(((MOAttributeComplex) item).get_Imaginary());
		    pencil.append("), ");
		} else {
		    if (item.get_Variability().equals("parameter")) {
			pencil.append(item.get_Name());
			pencil.append("=");
			pencil.append(item.get_Value());
			pencil.append(",");
		    }
		}
	}
	pencil.deleteCharAt(pencil.lastIndexOf(","));
	pencil.append(") ");
	pencil.append('"');
	pencil.append(this.comment);
	pencil.append('"');
	pencil.append(" ");
	pencil.append(this.annotation);
	pencil.append(";\n");
	code = pencil.toString();

	return code;
    }

}
