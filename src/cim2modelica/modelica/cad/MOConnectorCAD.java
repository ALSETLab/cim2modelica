package cim2modelica.modelica.cad;

import cim2modelica.modelica.MOAttribute;
import cim2modelica.modelica.MOAttributeComplex;
import cim2modelica.modelica.MOConnector;
    
/**
 * Generic class for implementation the declaration of modelica connectors
 * 
 * @author fragom
 *
 */
public class MOConnectorCAD extends MOConnector {
    private String annotationIcon;

    public MOConnectorCAD(String _name) {
	super(_name);
	this.annotationIcon = "annotation (Documentation)";
    }

    public MOConnectorCAD(MOConnectorCAD _machinePin, String _plantaPin) {
	super(_machinePin, _plantaPin);
	this.annotationIcon = "annotation (Documentation)";
    }

    public void set_AnnotationIcon(String _annotation) {
	this.annotationIcon = _annotation;
    }

    /**
     * connector name "some comments" parameter ... end name;
     * 
     * @return text representation of the class
     */
    @Override
    public String to_ModelicaClass() {
	String code = "";
	StringBuilder pencil = new StringBuilder();

	pencil.append("connector ");
	pencil.append(this.name);
	pencil.append(" ");
	pencil.append('"');
	pencil.append(this.comment);
	pencil.append('"');
	pencil.append("\n");
	for (MOAttribute item : this.attributes) {
	    pencil.append("\t");
	    pencil.append(item.to_Modelica());
	    pencil.append("\n");
	}
	pencil.append(this.annotationIcon);
	pencil.append("end ");
	pencil.append(this.name);
	pencil.append(";");
	code = pencil.toString();

	return code;
    }

    /**
     * Name connectorName (value1=?,value2=?,...) "comments";
     * 
     * @param instanceParam
     *            - boolean
     * @return text representation of the instance
     */
    @Override
    public String to_ModelicaInstance(boolean instanceParams) {
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
	if (instanceParams) {
	    pencil.append(" (");
	    for (MOAttribute item : this.attributes) {
		if (item.get_Name().equals("vr") | item.get_Name().equals("vi") | item.get_Name().equals("ir")
			| item.get_Name().equals("ii")) {
		    pencil.append(item.get_Name());
		    pencil.append("=");
		    pencil.append(item.get_Value());
		    pencil.append(",");
		}
		if (item instanceof MOAttributeComplex) {
		    pencil.append(item.get_Name());
		    pencil.append("(");
		    pencil.append("re= ");
		    pencil.append(((MOAttributeComplex) item).get_Real());
		    pencil.append(",");
		    pencil.append("im= ");
		    pencil.append(((MOAttributeComplex) item).get_Imaginary());
		    pencil.append("), ");
		}
	    }
	    pencil.deleteCharAt(pencil.lastIndexOf(","));
	    pencil.append(") ");
	}
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
