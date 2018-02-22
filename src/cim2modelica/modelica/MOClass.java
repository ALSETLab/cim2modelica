package cim2modelica.modelica;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Generic class for implementing the declaration of a modelica class. this
 * class will be used to store characteristics of a component model
 * 
 * @author fragom
 *
 */
public class MOClass extends MOModel
{
    protected String visibility;
    protected String variability;
    protected String instanceName;
    protected ArrayList<MOAttribute> attributes;
    protected ArrayList<MOConnector> terminals;
    protected ArrayList<MOEquation> equations;

    public MOClass(String _name) {
	super(_name, "class");
	this.annotation = "annotation ()";
	this.visibility = "public";
	this.variability = "parameter";
	this.attributes = new ArrayList<MOAttribute>();
	this.terminals = new ArrayList<MOConnector>();
	this.equations = new ArrayList<MOEquation>();
    }

    /**
     * @return the instanceName
     */
    public String get_InstanceName() {
	return instanceName;
    }

    /**
     * @param instanceName
     *            is the name when it is used as instance in other components
     */
    public void set_InstanceName(String instanceName) {
	this.instanceName = instanceName;
    }

    /**
     * @return the variability
     */
    public String get_Variability() {
	return variability;
    }

    /**
     * @param visibility
     *            the visibility to set
     */
    public void set_Variability(String variability) {
	this.variability = variability;
    }

    /**
     * @return the visibility
     */
    public String get_Visibility() {
	return visibility;
    }

    /**
     * @param visibility
     *            the visibility to set
     */
    public void set_Visibility(String visibility) {
	this.visibility = visibility;
    }

    /**
     * @return the stereotype
     */
    public String getStereotype() {
	return stereotype;
    }

    /**
     * @param stereotype
     *            the stereotype to set
     */
    public void setStereotype(String stereotype) {
	this.stereotype = stereotype;
    }

    public boolean exist_Attribute(String _name) {
	boolean exists = false;
	MOAttribute current;

	Iterator<MOAttribute> iconnections = this.attributes.iterator();
	while (!exists && iconnections.hasNext()) {
	    current = iconnections.next();
	    exists = (current.get_Name().equals(_name));
	}

	return exists;
    }

    public MOAttribute get_Attribute(String _name) {
	boolean exists = false;
	MOAttribute current = null;

	Iterator<MOAttribute> iconnections = this.attributes.iterator();
	while (!exists && iconnections.hasNext()) {
	    current = iconnections.next();
	    exists = (current.get_Name().equals(_name));
	}
	return current;
    }

    /**
     * @return the attributes
     */
    public ArrayList<MOAttribute> get_Attributes() {
	return attributes;
    }

    /**
     * 
     * @param variable
     */
    public void add_Attribute(MOAttribute variable) {
	this.attributes.add(variable);
    }

    /**
     * @param attributes
     *            the attributes to set
     */
    public void add_Attribute(ArrayList<MOAttribute> attributes) {
	this.attributes = attributes;
    }

    /**
     * 
     * @param variable
     */
    public void add_Terminal(MOConnector pin) {
	this.terminals.add(pin);
    }

    /**
     * @param terminals
     *            the terminals to set
     */
    public void add_Terminal(ArrayList<MOConnector> terminals) {
	this.terminals = terminals;
    }

    /**
     * @return the terminals
     */
    public ArrayList<MOConnector> get_Terminals() {
	return terminals;
    }

    public MOConnector get_Terminal(String _rfdId) {
	MOConnector current;
	Iterator<MOConnector> iPins;

	iPins = this.terminals.iterator();
	boolean exists = false;
	do {
	    current = iPins.next();
	    exists = current.get_RdfId().equals(_rfdId);
	} while (!exists && iPins.hasNext());
	if (exists)
	    return current;
	else
	    return null;
    }

    /**
     * 
     * @param variable
     */
    public void add_Equation(MOEquation equation) {
	this.equations.add(equation);
    }

    /**
     * 
     * @param _pin
     */
    private void update_pin_current(MOConnector _pin) {
	double v, angle, p, q, vr, vi, ir, ii;

	v = Double.parseDouble((String) _pin.get_Attribute("vr").get_Value());
	angle = Double.parseDouble((String) _pin.get_Attribute("vi").get_Value());
	p = Double.parseDouble((String) _pin.get_Attribute("ir").get_Value());
	q = Double.parseDouble((String) _pin.get_Attribute("ii").get_Value());
	// update vr, vi values
	vr = v * Math.cos(angle);
	vi = v * Math.sin(angle);
	_pin.get_Attribute("vr").set_Value(vr);
	_pin.get_Attribute("vi").set_Value(vi);
	// updated ir, ii values
	if (vr == 0)
	    ir = 0;
	else
	    ir = p / vr;
	if (vi == 0)
	    ii = 0;
	else
	    ii = q / vi;
	_pin.get_Attribute("ir").set_Value(ir);
	_pin.get_Attribute("ii").set_Value(ii);
    }

    /**
     * For some devices, there are power flow values that are mapped by Sv
     * classes, and these classes are available either by Terminal or
     * TopologicalNode Class. The Modelica class of these devices need this Sv
     * values as parameter. SvPowerFlow (P,Q) are included in the classes
     * SvVoltage (V, angle) are not included and need to be updated from the
     * corresponding Pin component
     * 
     * @param _component
     * @param _pin
     */
    public void update_powerFlow(MOConnector _pin) {
	Iterator<MOAttribute> iAttributes;
	MOAttribute attbaseV, currentAtt = null;
	double baseV = 1, componentV = 0;
	boolean found = false;
	iAttributes = this.attributes.iterator();
	while (!found && iAttributes.hasNext()) {
	    attbaseV = iAttributes.next();
	    if (attbaseV.get_Name().equals("V_b")) {
		found = true;
		baseV = Double.parseDouble((String) attbaseV.get_Value());
	    }
	}
	iAttributes = this.attributes.iterator();
	while (iAttributes.hasNext()) {
	    currentAtt = iAttributes.next();
	    if (currentAtt.get_Name().equals("V_0")) {
		componentV = Double.parseDouble((String) _pin.get_Attribute("vr").get_Value());
		currentAtt.set_Value(String.valueOf(componentV / baseV));
	    }
	    if (currentAtt.get_Name().equals("angle_0"))
		currentAtt.set_Value(_pin.get_Attribute("vi").get_Value());
	    if (currentAtt.get_Name().equals("P_0"))
		currentAtt.set_Value(_pin.get_Attribute("ir").get_Value());
	    if (currentAtt.get_Name().equals("Q_0"))
		currentAtt.set_Value(_pin.get_Attribute("ii").get_Value());
	}
	iAttributes = null;
	update_pin_current(_pin);
    }

    /**
     * class name "some comments" parameter ... equation ... end name;
     * 
     * @return text representation of the class
     */
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
    public String to_ModelicaInstance() {
	String code = "";
	StringBuilder pencil = new StringBuilder();

	if (!this.visibility.equals("public")) {
	    pencil.append(this.visibility);
	    pencil.append(" ");
	}
	// pencil.append(this.variability);
	// pencil.append(" ");
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

    @Override
    public String toString() {
	return this.stereotype + ", " + this.pakage + ", " + this.instanceName + ", " + this.get_RdfId();
    }
}
