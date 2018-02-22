package cim2modelica.modelica;

import java.util.ArrayList;
import java.util.Iterator;

import cim2modelica.cim.map.AttributeMap;
import cim2modelica.cim.map.ComponentMap;
import cim2modelica.cim.map.ConnectionMap;
import cim2modelica.cim.map.openipsl.branches.PwLineMap;
import cim2modelica.cim.map.openipsl.buses.Bus;
import cim2modelica.cim.map.openipsl.buses.PwBusMap;
import cim2modelica.cim.map.openipsl.connectors.PwPinMap;
import cim2modelica.cim.map.openipsl.loads.LoadMap;
import cim2modelica.cim.map.openipsl.transformers.TwoWindingTransformerMap;
import cim2modelica.modelica.openipsl.branches.PwLine;
import cim2modelica.modelica.openipsl.controls.es.OpenIPSLExcitationSystem;
import cim2modelica.modelica.openipsl.controls.tg.OpenIPSLTurbineGovernor;
import cim2modelica.modelica.openipsl.machines.OpenIPSLMachine;

public class ModelCADBuilder {
    private MONetwork powsys;
    private MOClass _currentEquipment, _currentNode;
    private String _currentIDEquipment, _currentIDNode;

    public ModelCADBuilder(String _network) {
	powsys = new MONetwork(_network);
	this._currentEquipment = null;
	this._currentIDEquipment = "";
	this._currentNode = null;
	this._currentIDNode = "";
    }

    /**
     * 
     * @return
     */
    public MONetwork get_Network() {
	return this.powsys;
    }

    /**
     * 
     * @return
     */
    public MOClass get_CurrentEquipment() {
	return _currentEquipment;
    }

    /**
     * 
     * @param _device
     * @param _id
     */
    public void set_CurrentEquipment(MOClass _device, String _id) {
	this._currentEquipment = _device;
	this._currentIDEquipment = _id;
    }

    /**
     * 
     * @param _id
     * @return
     */
    public boolean exist_CurrentEquipment(String _id) {
	if (this._currentIDEquipment.equals(_id))
	    return true;
	else
	    return false;
    }

    /**
     * 
     * @return
     */
    public MOClass get_CurrentNode() {
	return _currentNode;
    }

    /**
     * 
     * @param _device
     * @param _id
     */
    public void set_CurrentNode(MOClass _device, String _id) {
	this._currentNode = _device;
	this._currentIDNode = _id;
    }

    /**
     * 
     * @param _id
     * @return
     */
    public boolean exist_CurrentNode(String _id) {
	if (this._currentIDNode.equals(_id))
	    return true;
	else
	    return false;
    }

    public MOConnector create_PinConnector(PwPinMap _terminalMap) {
	MOConnector pin = new MOConnector(_terminalMap.getName());
	Iterator<AttributeMap> imapAttList = _terminalMap.getAttributeMap().iterator();
	AttributeMap current;
	while (imapAttList.hasNext()) {
	    current = imapAttList.next();
	    if (current.getCimName().equals("IdentifiedObject.name")) {
		if (current.getContent().equals("T1"))
		    pin.set_InstanceName("p");
		if (current.getContent().equals("T2"))
		    pin.set_InstanceName("n");
		// System.out.println("after= " + current.getContent() + " - " +
		// pin.get_InstanceName());
	    } else {
		MOAttribute variable = new MOAttribute();
		variable.set_Name(current.getName());
		variable.set_Value(current.getContent());
		variable.set_Variability(current.getVariability());
		variable.set_Visibility(current.getVisibility());
		variable.set_Flow(Boolean.valueOf(current.getFlow()));
		pin.set_Attribute(variable);
	    }
	}
	imapAttList = null;
	pin.set_Stereotype(_terminalMap.getStereotype());
	pin.set_Package(_terminalMap.getPackage());
	// for internal identification only
	pin.set_RdfId(_terminalMap.getRdfId());

	return pin;
    }

    /**
     * 
     * @param _component
     */
    public void add_equipmentNetwork(MOClass _component) {
	Iterator<MOClass> iComponents = this.powsys.get_Equipment().iterator();
	boolean exists = false;
	while (!exists && iComponents.hasNext()) {
	    exists = iComponents.next().get_RdfId().equals(_component.get_RdfId());
	}
	if (!exists)
	    this.powsys.add_Component(_component);
	iComponents = null;
    }

    /**
     * 
     * @param _rfdId
     * @return
     */
    public MOClass get_equipmentNetwork(String _rdfId) {
	MOClass current = new MOClass("void");
	Iterator<MOClass> iComponents;

	iComponents = this.powsys.get_Equipment().iterator();
	boolean exists = false;
	while (!exists && iComponents.hasNext()) {
	    current = iComponents.next();
	    exists = current.get_RdfId().equals(_rdfId);
	}
	iComponents = null;
	if (exists)
	    return current;
	else
	    return null;
    }

    /**
     * Use of the RDF_ID for internal identification only
     * 
     * @param _mapSyncMach
     * @return
     */
    public OpenIPSLMachine create_MachineComponent(ComponentMap _mapSyncMach) {
	OpenIPSLMachine syncMach = new OpenIPSLMachine(_mapSyncMach.getName());
	Iterator<AttributeMap> imapAttList = _mapSyncMach.getAttributeMap().iterator();
	AttributeMap current;
	while (imapAttList.hasNext()) {
	    current = imapAttList.next();
	    if (current.getCimName().equals("IdentifiedObject.name")) {
		syncMach.set_InstanceName("sm_" + current.getContent().trim());
	    } else {
		MOAttribute variable = new MOAttribute();
		variable.set_Name(current.getName());
		if (current.getContent() == null)
		    variable.set_Value("0.001");
		else
		    variable.set_Value(current.getContent());
		variable.set_Variability(current.getVariability());
		variable.set_Visibility(current.getVisibility());
		variable.set_Flow(Boolean.valueOf(current.getFlow()));
		syncMach.add_Attribute(variable);
	    }
	}
	imapAttList = null;
	syncMach.set_Stereotype(_mapSyncMach.getStereotype());
	syncMach.set_Package(_mapSyncMach.getPackage());
	// rdf:id used internally for generatin annotations
	syncMach.set_RdfId(_mapSyncMach.getRdfId());

	return syncMach;
    }

    /**
     * 
     * @param _mapExcSys
     * @return
     */
    public OpenIPSLExcitationSystem create_ExcSysComponent(ComponentMap _mapExcSys) {
	OpenIPSLExcitationSystem excSys = new OpenIPSLExcitationSystem(_mapExcSys.getName());
	Iterator<AttributeMap> imapAttList = _mapExcSys.getAttributeMap().iterator();
	AttributeMap current;
	while (imapAttList.hasNext()) {
	    current = imapAttList.next();
	    if (current.getCimName().equals("IdentifiedObject.name")) {
		excSys.set_InstanceName("es_" + current.getContent().trim());
	    } else {
		MOAttribute variable = new MOAttribute();
		variable.set_Name(current.getName());
		if (current.getContent() == null)
		    variable.set_Value("0.001");
		else
		    variable.set_Value(current.getContent());
		variable.set_Variability(current.getVariability());
		variable.set_Visibility(current.getVisibility());
		variable.set_Flow(Boolean.valueOf(current.getFlow()));
		excSys.add_Attribute(variable);
	    }
	}
	imapAttList = null;
	excSys.set_Stereotype(_mapExcSys.getStereotype());
	excSys.set_Package(_mapExcSys.getPackage());
	// rdf:id used internally for generatin annotations
	excSys.set_RdfId(_mapExcSys.getRdfId());

	return excSys;
    }

    /**
     * 
     * @param _mapTGov
     * @return
     */
    public OpenIPSLTurbineGovernor create_TGovComponent(ComponentMap _mapTGov) {
	OpenIPSLTurbineGovernor tgov = new OpenIPSLTurbineGovernor(_mapTGov.getName());
	Iterator<AttributeMap> imapAttList = _mapTGov.getAttributeMap().iterator();
	AttributeMap current;
	while (imapAttList.hasNext()) {
	    current = imapAttList.next();
	    if (current.getCimName().equals("IdentifiedObject.name")) {
		tgov.set_InstanceName("tgov_" + current.getContent().trim());
	    } else {
		MOAttribute variable = new MOAttribute();
		variable.set_Name(current.getName());
		if (current.getContent() == null)
		    variable.set_Value("0.001");
		else
		    variable.set_Value(current.getContent());
		variable.set_Variability(current.getVariability());
		variable.set_Visibility(current.getVisibility());
		variable.set_Flow(Boolean.valueOf(current.getFlow()));
		tgov.add_Attribute(variable);
	    }
	}
	imapAttList = null;
	tgov.set_Stereotype(_mapTGov.getStereotype());
	tgov.set_Package(_mapTGov.getPackage());
	// rdf:id used internally for generatin annotations
	tgov.set_RdfId(_mapTGov.getRdfId());

	return tgov;
    }

    public MOClass create_LoadComponent(LoadMap _mapEnergyC) {
	MOClass pwLoad = new MOClass(_mapEnergyC.getName());
	MOAttributeComplex complejo = null;
	Iterator<AttributeMap> imapAttList = _mapEnergyC.getAttributeMap().iterator();
	String nombre, parte;
	AttributeMap current;
	while (imapAttList.hasNext()) {
	    current = imapAttList.next();
	    if (current.getCimName().equals("IdentifiedObject.name")) {
		pwLoad.set_InstanceName("ld" + current.getContent().trim());
	    } else {
		if (current.getDatatype().equals("Complex")) {
		    nombre = current.getName().split("[.]")[0];
		    parte = current.getName().split("[.]")[1];
		    if (!pwLoad.exist_Attribute(nombre))
			complejo = new MOAttributeComplex();
		    else
			complejo = (MOAttributeComplex) pwLoad.get_Attribute(nombre);
		    complejo.set_Name(nombre);
		    if (parte.equals("re")) {
			complejo.set_Real(current.getContent());
			pwLoad.add_Attribute(complejo);
		    } else {
			complejo.set_Imaginary(current.getContent());
			complejo.set_Datatype(current.getDatatype());
			complejo.set_Variability(current.getVariability());
			complejo.set_Visibility(current.getVisibility());
			complejo.set_Flow(Boolean.valueOf(current.getFlow()));
		    }
		} else {
		    MOAttribute variable = new MOAttribute();
		    variable.set_Name(current.getName());
		    if (current.getContent() == null)
			variable.set_Value("1");
		    else
			variable.set_Value(current.getContent());
		    variable.set_Datatype(current.getDatatype());
		    variable.set_Variability(current.getVariability());
		    variable.set_Visibility(current.getVisibility());
		    variable.set_Flow(Boolean.valueOf(current.getFlow()));
		    pwLoad.add_Attribute(variable);
		}
	    }
	}
	imapAttList = null;
	pwLoad.set_Stereotype(_mapEnergyC.getStereotype());
	pwLoad.set_Package(_mapEnergyC.getPackage());
	// rdf:id used internally for generatin annotations
	pwLoad.set_RdfId(_mapEnergyC.getRdfId());

	return pwLoad;
    }

    /**
     * Use of the RDF_ID for internal identification only
     * 
     * @param _mapACLine
     * @return
     */
    public MOClass create_LineComponent(PwLineMap _mapACLine) {
	PwLine pwline = new PwLine(_mapACLine.getName());
	Iterator<AttributeMap> imapAttList = _mapACLine.getAttributeMap().iterator();
	AttributeMap current;
	while (imapAttList.hasNext()) {
	    current = imapAttList.next();
	    if (current.getCimName().equals("IdentifiedObject.name")) {
		pwline.set_InstanceName("ln" + current.getContent().trim());
	    } else {
		MOAttribute variable = new MOAttribute();
		variable.set_Name(current.getName());
		if (current.getContent() == null)
		    variable.set_Value("0");
		else
		    variable.set_Value(current.getContent());
		variable.set_Variability(current.getVariability());
		variable.set_Visibility(current.getVisibility());
		variable.set_Flow(Boolean.valueOf(current.getFlow()));
		pwline.add_Attribute(variable);
	    }
	}
	imapAttList = null;
	pwline.set_Stereotype(_mapACLine.getStereotype());
	pwline.set_Package(_mapACLine.getPackage());
	// rdf:id used internally for generatin annotations
	pwline.set_RdfId(_mapACLine.getRdfId());

	return pwline;
    }

    /**
     * Use of the RDF_ID for internal identification only
     * 
     * @param _mapPowTrans
     * @return
     */
    public MOClass create_TransformerComponent(TwoWindingTransformerMap _mapPowTrans) {
	MOClass twtransformer = new MOClass(_mapPowTrans.getName());
	AttributeMap current;
	String instanceName;

	Iterator<AttributeMap> imapAttList = _mapPowTrans.getAttributeMap().iterator();
	while (imapAttList.hasNext()) {
	    current = imapAttList.next();
	    if (!current.getCimName().equals("TransformerEnd.endNumber")
		    && !current.getCimName().equals("RatioTapChanger.stepVoltageIncrement")
		    && !current.getCimName().equals("PowerTransformerEnd.ratedU")
		    && !current.getCimName().equals("PowerTransformerEnd.nomV")) {
		if (current.getCimName().equals("IdentifiedObject.name")) {
		    instanceName = current.getContent().trim().replace(' ', '_');
		    twtransformer.set_InstanceName(instanceName);
		} else {
		    MOAttribute variable = new MOAttribute();
		    variable.set_Name(current.getName());
		    if (current.getContent() == null)
			variable.set_Value("0");
		    else
			variable.set_Value(current.getContent());
		    variable.set_Variability(current.getVariability());
		    variable.set_Visibility(current.getVisibility());
		    variable.set_Flow(Boolean.valueOf(current.getFlow()));
		    twtransformer.add_Attribute(variable);
		}
	    }
	}
	imapAttList = null;
	twtransformer.set_Stereotype(_mapPowTrans.getStereotype());
	twtransformer.set_Package(_mapPowTrans.getPackage());
	// rdf:id used internally for generatin annotations
	twtransformer.set_RdfId(_mapPowTrans.getRdfId());

	return twtransformer;
    }

    /**
     * Creates different attributes for the TwoWindingTransformer model of the
     * Library. This attributes represent values for each winding, and each
     * winding in CIM is represented by different class instances, for exemple:
     * attribute t1 corresponds to one instance of PowerTransformerEnd,
     * attribute t2 corresponds another instance of PowerTransformerEnd
     * 
     * @param _mapPowTrans
     * @return
     */
    public ArrayList<MOAttribute> create_AttTransformerEnd(TwoWindingTransformerMap _mapPowTrans) {
	AttributeMap current, endNumber = null;
	MOAttribute ratioTapChanger = null, powerTransEnd = null;
	ArrayList<MOAttribute> endAttributes = new ArrayList<MOAttribute>();

	Iterator<AttributeMap> imapAttList = _mapPowTrans.getAttributeMap().iterator();
	while (imapAttList.hasNext()) {
	    current = imapAttList.next();
	    if (current.getCimName().equals("TransformerEnd.endNumber"))
		endNumber = current;
	    else if (current.getCimName().equals("RatioTapChanger.stepVoltageIncrement")) {
		ratioTapChanger = this.create_TransformerEndAttribute(endNumber, current);
		endAttributes.add(ratioTapChanger);
	    } else if (current.getCimName().equals("PowerTransformerEnd.ratedU")
		    || current.getCimName().equals("PowerTransformerEnd.nomV")) {
		powerTransEnd = this.create_TransformerEndAttribute(endNumber, current);
		endAttributes.add(powerTransEnd);
	    }
	    // else if (current.getCimName().equals("SvVoltage.v"))
	    // svvoltage= this.create_TransformerEndAttribute(endNumber,
	    // current);
	}
	imapAttList = null;
	ratioTapChanger = null;
	powerTransEnd = null;
	return endAttributes;
    }

    /**
     * Creates one attribute for each cim PowerTransformerEnd instance
     * 
     * @param _endNumber
     *            - Value for Power transformer ending number
     * @param _currentAtt
     *            - cim:RatioTapChanger.stepVoltageIncrement = mod:(t1,t2);
     *            cim:PowerTransformerEnd.ratedU = mod:(VNOM1,VNOM2)
     * @return modelica attribute for each instance of cim:PowerTransformerEnd
     */
    private MOAttribute create_TransformerEndAttribute(AttributeMap _endNumber, AttributeMap _currentAtt) {
	MOAttribute variable = new MOAttribute();
	variable.set_Name(_currentAtt.getName() + _endNumber.getContent());
	if (_currentAtt.getContent() == null)
	    variable.set_Value("0");
	else
	    variable.set_Value(_currentAtt.getContent());
	variable.set_Variability(_currentAtt.getVariability());
	variable.set_Visibility(_currentAtt.getVisibility());
	variable.set_Flow(Boolean.valueOf(_currentAtt.getFlow()));

	return variable;
    }

    /**
     * Creates an OpenIPSL Bus component from the map of cim:TopologicalNode.
     * Use of the RDF_ID for internal identification only
     * 
     * @param _mapTopoNode
     *            - map structure with the data from the cim:TopologicalNode
     *            class
     * @return
     */
    public MOClass create_BusComponent(PwBusMap _mapTopoNode) {
	Bus pwbus = new Bus(_mapTopoNode.getName());
	Iterator<AttributeMap> imapAttList = _mapTopoNode.getAttributeMap().iterator();
	AttributeMap current;
	while (imapAttList.hasNext()) {
	    current = imapAttList.next();
	    if (current.getCimName().equals("IdentifiedObject.name")) {
		pwbus.set_InstanceName(current.getContent().trim().replace(' ', '_'));
	    } else {
		MOAttribute variable = new MOAttribute();
		variable.set_Name(current.getName());
		if (current.getContent() == null)
		    variable.set_Value("0.001");
		else
		    variable.set_Value(current.getContent());
		variable.set_Variability(current.getVariability());
		variable.set_Visibility(current.getVisibility());
		variable.set_Flow(Boolean.valueOf(current.getFlow()));
		pwbus.add_Attribute(variable);
	    }
	}
	imapAttList = null;
	pwbus.setStereotype(_mapTopoNode.getStereotype());
	pwbus.set_Package(_mapTopoNode.getPackage());
	pwbus.set_RdfId(_mapTopoNode.getRdfId());

	return pwbus;
    }

    /**
     * Use of the RDF_ID for internal identification only
     * 
     * @param _mapACLine
     * @return
     */
    public MOClass create_ConstantBlock() {
	MOClass constantblock = new MOClass("Constant");

	MOAttribute variable = new MOAttribute();
	variable.set_Name("k");
	variable.set_Value(0);
	variable.set_Variability("parameter");
	variable.set_Visibility("public");
	variable.set_Flow(false);
	constantblock.add_Attribute(variable);

	constantblock.set_InstanceName("const");
	constantblock.set_Stereotype("block");
	constantblock.set_Package("Modelica.Blocks.Sources");
	constantblock.set_RdfId("none");

	return constantblock;
    }

    /**
     * 
     * @param _component
     */
    public void add_plantNetwork(MOPlant _plant) {
	this.powsys.add_Plant(_plant);
    }

    /**
     * Sets the connection between all the components in the network model. It
     * creates the connect equation to be written in the network model.
     * 
     * @param _connectmap
     *            - structure with references and ids for Terminal,
     *            ConductingEquipment and TopologicalNode
     */
    public void connect_Components(ArrayList<ConnectionMap> _connectmap) {
	Iterator<ConnectionMap> iConnections = _connectmap.iterator();
	ConnectionMap currentConnection;
	MOClass equipment, bus;
	MOConnectNode conexio = null;
	Iterator<MOPlant> iPlant;
	boolean foundPlant = false;
	MOPlant currentPlant = null;

	while (iConnections.hasNext()) {
	    try {
		currentConnection = iConnections.next();
		equipment = this.get_equipmentNetwork(currentConnection.get_Ce_id());
		bus = this.get_equipmentNetwork(currentConnection.get_Tn_id());
		if (equipment instanceof OpenIPSLMachine) {
		    iPlant = this.powsys.get_planta().iterator();
		    foundPlant = false;
		    while (!foundPlant && iPlant.hasNext()) {
			currentPlant = iPlant.next();
			foundPlant = currentPlant.getMachine().get_RdfId().equals(currentConnection.get_Ce_id());
		    }
		    iPlant = null;
		    if (foundPlant)
			conexio = new MOConnectNode(currentPlant.getInstanceName(),
				currentPlant.getOutpin().get_InstanceName(), bus.get_InstanceName(),
				bus.get_Terminal(currentConnection.get_T_id()).get_InstanceName());
		} else {
		    conexio = new MOConnectNode(equipment.get_InstanceName(),
			    equipment.get_Terminal(currentConnection.get_T_id()).get_InstanceName(),
			    bus.get_InstanceName(), bus.get_Terminal(currentConnection.get_T_id()).get_InstanceName());
		}
		if (!this.powsys.exist_Connection(conexio))
		    this.powsys.add_Connection(conexio);
	    } catch (NullPointerException npe) {
		// System.err.println("Still some equipment left to map");
	    }
	}
	iConnections = null;
	// System.out.println(this.powsys.to_ModelicaClass());
    }

    /**
     * 
     * @param _modelName
     * @param _packeteName
     * @return
     */
    public String package_information(String _modelName, String _packeteName) {
	StringBuilder llapis = new StringBuilder();
	String packeteInfo;

	llapis.append("within ");
	llapis.append(_modelName);
	llapis.append(";\n");
	llapis.append("package ");
	llapis.append(_packeteName);
	llapis.append(" ");
	llapis.append('"');
	llapis.append("Automatically Generated Comment");
	llapis.append('"');
	llapis.append("\n");
	llapis.append("annotation (Documentation);");
	llapis.append("\nend ");
	llapis.append(_packeteName);
	llapis.append(";\n");

	packeteInfo = llapis.toString();
	llapis = null;
	return packeteInfo;
    }

    /**
     * 
     * @param _packeteName
     * @param _comment
     * @return
     */
    public String model_package_information(String _packeteName, String _comment) {
	StringBuilder llapis = new StringBuilder();
	String packeteInfo;

	llapis.append("package ");
	llapis.append(_packeteName);
	llapis.append(" ");
	llapis.append('"');
	llapis.append(_comment);
	llapis.append('"');
	llapis.append("\n");
	llapis.append("annotation (uses(OpenIPSL(version=\"1.5.0\"), Modelica(version=\"3.2.2\")),Documentation);\n");
	llapis.append("end ");
	llapis.append(_packeteName);
	llapis.append(";");
	packeteInfo = llapis.toString();
	llapis = null;
	return packeteInfo;
    }
    }
