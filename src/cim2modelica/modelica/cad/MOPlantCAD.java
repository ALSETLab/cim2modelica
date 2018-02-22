package cim2modelica.modelica.cad;

import cim2modelica.modelica.MOClass;
import cim2modelica.modelica.MOConnectNode;
import cim2modelica.modelica.MOPlant;
import cim2modelica.modelica.openipsl.controls.es.OpenIPSLExcitationSystem;
import cim2modelica.modelica.openipsl.controls.tg.OpenIPSLTurbineGovernor;
import cim2modelica.modelica.openipsl.machines.OpenIPSLMachine;

/**
 * Class with the definition of a high level modelica class, aka model
 * 
 * @author fragom
 *
 */
public class MOPlantCAD extends MOPlant {

    public MOPlantCAD(OpenIPSLMachine _mach, OpenIPSLExcitationSystem _es, OpenIPSLTurbineGovernor _tg, MOClass _stab) {
	super(_mach, _es, _tg, _stab);
    }

    /**
     * @return the outpin
     */
    @Override
    public MOConnectorCAD getOutpin() {
	return (MOConnectorCAD) outpin;
    }

    /**
     * @param outpin
     *            the outpin to set
     */
    public void setOutpin(MOConnectorCAD outpin) {
	this.outpin = outpin;
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
	pencil.append(this.annotation);
	pencil.append('"');
	pencil.append("\n");
	/* VARIABLE SECTION */
	pencil.append("\t");
	pencil.append(this.getOutpin().to_ModelicaInstance(false));
	// print machine component
	pencil.append("\t");
	pencil.append(this.machine.to_ModelicaInstance());
	// print excitation system component
	if (this.has_excitationSystem()) {
	    pencil.append("\t");
	    pencil.append(this.excitationSystem.to_ModelicaInstance());
	}
	// print turbine governor component
	if (this.has_turbineGovernor()) {
	    pencil.append("\t");
	    pencil.append(this.turbineGovernor.to_ModelicaInstance());
	}
	// print stabilizer component
	if (this.has_powerStabilizer()) {
	}
	if (this.has_constantBlock()) {
	    pencil.append("\t");
	    pencil.append(this.constantBlock.to_ModelicaInstance());
	}
	/* EQUATION SECTION */
	pencil.append("equation\n");
	// if (this.excitationSystem== null && this.turbineGovernor== null){
	// this.machine.default_connectionPMECH();
	// this.machine.default_connectionEFD();
	// }
	for (MOConnectNode conexio : this.conexions) {
	    pencil.append("\t");
	    pencil.append(((MOConnectNodeCAD) conexio).to_ModelicaEquation("plant"));
	}
	pencil.append("end ");
	pencil.append(this.name);
	pencil.append(";");
	code = pencil.toString();

	return code;
    }

    /**
     * generates the code for the instances of the objects with in the plant
     * object
     * 
     * @return
     */
    @Override
    public String to_ModelicaInstance() {
	String code = "";
	StringBuilder pencil = new StringBuilder();

	pencil.append(this.pakage);
	pencil.append(".");
	pencil.append(this.name);
	pencil.append(" ");
	pencil.append(this.instanceName);
	pencil.append(" ");
	pencil.append('"');
	pencil.append(this.comment);
	pencil.append('"');
	pencil.append(" ");
	pencil.append(this.annotation);
	pencil.append(";\n");

	code = pencil.toString();
	pencil = null;

	return code;
    }

    public void update_ComponentAnnotation(OpenIPSLMachine _momachine) {
	this.set_Coord(_momachine.get_Coord().get(0), _momachine.get_Coord().get(0));

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

}
