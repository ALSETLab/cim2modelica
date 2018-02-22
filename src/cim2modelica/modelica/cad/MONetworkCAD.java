package cim2modelica.modelica.cad;

import cim2modelica.modelica.MOClass;
import cim2modelica.modelica.MOConnectNode;
import cim2modelica.modelica.MONetwork;
import cim2modelica.modelica.MOPlant;
import cim2modelica.modelica.openipsl.machines.OpenIPSLMachine;

/**
 * Class with the definition of a high level modelica class, aka model
 * @author fragom
 *
 */
public class MONetworkCAD extends MONetwork
{
    public MONetworkCAD(String _name) {
	super(_name);
    }

    /**
     * This methods creates the connections within the plant object
     * 
     * @return
     */
    public String to_ModelicaEquation(String isNetwork) {
	String code = "";
	// if (isNetwork.equals("network"))
	// code= this.connect_equipmentNetwork();
	// if (isNetwork.equals("plant"))
	// code= this.connect_equipmentPlant();
	return code;
    }

    @Override
    public String to_ModelicaClass(String _package) {
	String code = "";
	StringBuilder pencil = new StringBuilder();

	/* HEADER */
	pencil.append("within ");
	pencil.append(_package);
	pencil.append(";\n");
	pencil.append(this.stereotype);
	pencil.append(" ");
	pencil.append(this.name);
	pencil.append(" ");
	pencil.append('"');
	pencil.append(this.comment);
	pencil.append('"');
	pencil.append("\n");
	/* VARIABLE SECTION */
	for (MOClass component : this.equipment) {
	    if (!(component instanceof OpenIPSLMachine)) {
		pencil.append("\t");
		// TODO CAD component
		pencil.append(component.to_ModelicaInstance());
	    }
	}
	for (MOPlant plant : this.elecPlants) {
	    pencil.append("\t");
	    // TODO CAD component
	    pencil.append(plant.to_ModelicaInstance());
	}
	/* EQUATION SECTION */
	pencil.append("equation\n");
	for (MOConnectNode conexio : this.conexions) {
	    pencil.append("\t");
	    // TODO CAD component
	    pencil.append(conexio.to_ModelicaEquation("network"));
	}
	pencil.append("end ");
	pencil.append(this.name);
	pencil.append(";");
	code = pencil.toString();

	return code;
    }
}
