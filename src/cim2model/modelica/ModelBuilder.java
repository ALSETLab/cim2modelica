package cim2model.modelica;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import cim2model.cim.map.AttributeMap;
import cim2model.cim.map.ComponentMap;
import cim2model.cim.map.ConnectionMap;
import cim2model.cim.map.ipsl.branches.PwLineMap;
import cim2model.cim.map.ipsl.buses.Bus;
import cim2model.cim.map.ipsl.buses.PwBusMap;
import cim2model.cim.map.ipsl.connectors.PwPinMap;
import cim2model.cim.map.ipsl.loads.LoadMap;
import cim2model.cim.map.ipsl.transformers.TwoWindingTransformerMap;
import cim2model.modelica.ipsl.branches.PwLine;
import cim2model.modelica.ipsl.controls.es.IPSLExcitationSystem;
import cim2model.modelica.ipsl.controls.tg.IPSLTurbineGovernor;
import cim2model.modelica.ipsl.machines.IPSLMachine;

public class ModelBuilder 
{
	private MONetwork powsys;
	private MOClass _currentEquipment, _currentNode;
	private String _currentIDEquipment, _currentIDNode;
	
	public ModelBuilder(String _network)
	{
		powsys= new MONetwork(_network);
		this._currentEquipment= null;
		this._currentIDEquipment= "";
		this._currentNode= null;
		this._currentIDNode= "";
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
	public MOClass get_CurrentEquipment(){
		return _currentEquipment;
	}
	/**
	 * 
	 * @param _device
	 * @param _id
	 */
	public void set_CurrentEquipment(MOClass _device, String _id){
		this._currentEquipment= _device;
		this._currentIDEquipment= _id;
	}
	/**
	 * 
	 * @param _id
	 * @return
	 */
	public boolean exist_CurrentEquipment(String _id)
	{
		if (this._currentIDEquipment.equals(_id))
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 * @return
	 */
	public MOClass get_CurrentNode(){
		return _currentNode;
	}
	/**
	 * 
	 * @param _device
	 * @param _id
	 */
	public void set_CurrentNode(MOClass _device, String _id){
		this._currentNode= _device;
		this._currentIDNode= _id;
	}
	/**
	 * 
	 * @param _id
	 * @return
	 */
	public boolean exist_CurrentNode(String _id)
	{
		if (this._currentIDNode.equals(_id))
			return true;
		else
			return false;
	}
	
	public MOConnector create_PinConnector(PwPinMap _terminalMap)
	{
		MOConnector pin= new MOConnector(_terminalMap.getName());
		ArrayList<AttributeMap> mapAttList= 
				(ArrayList<AttributeMap>)_terminalMap.getAttributeMap();
		Iterator<AttributeMap> imapAttList= mapAttList.iterator();
		imapAttList= mapAttList.iterator();
		AttributeMap current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("IdentifiedObject.name")){
				if (current.getContent().equals("T1"))
						pin.set_InstanceName("p");
				if (current.getContent().equals("T2"))
						pin.set_InstanceName("n");
			}
			else{
				MOAttribute variable= new MOAttribute();
				variable.set_Name(current.getName());
				variable.set_Value(current.getContent());
				variable.set_Variability(current.getVariability());
				variable.set_Visibility(current.getVisibility());
				variable.set_Flow(Boolean.valueOf(current.getFlow()));
				pin.set_Attribute(variable);
			}
		}
		pin.set_Stereotype(_terminalMap.getStereotype());
		pin.set_Package(_terminalMap.getPackage());
		//for internal identification only
		pin.set_RdfId(_terminalMap.getRdfId());
		
		return pin;
	}
	
	/**
	 * 
	 * @param _component
	 */
	public void add_equipmentNetwork(MOClass _component)
	{
		Iterator<MOClass> iComponents= this.powsys.get_Equipment().iterator();
		boolean exists= false;
		while (!exists && iComponents.hasNext()){
			exists= iComponents.next().get_RdfId().equals(_component.get_RdfId());
		}
		if (!exists)
			this.powsys.add_Component(_component);
	}

	/**
	 * 
	 * @param _rfdId
	 * @return
	 */
	public MOClass get_equipmentNetwork(String _rdfId)
	{
		MOClass current= new MOClass("void");
		Iterator<MOClass> iComponents;
		
		iComponents= this.powsys.get_Equipment().iterator();
		boolean exists= false;
		while (!exists && iComponents.hasNext()){
			current= iComponents.next();
			exists= current.get_RdfId().equals(_rdfId);
		}
		if (exists)
			return current;
		else
			return null;
	}
	
	/**
	 * Use of the RDF_ID for internal identification only
	 * @param _mapSyncMach
	 * @return
	 */
	public IPSLMachine create_MachineComponent(ComponentMap _mapSyncMach)
	{
		IPSLMachine syncMach= new IPSLMachine(_mapSyncMach.getName());
		Iterator<AttributeMap> imapAttList= _mapSyncMach.getAttributeMap().iterator();
		AttributeMap current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("IdentifiedObject.name")){
				syncMach.set_InstanceName("sm_"+ current.getContent().trim());
			}
			else{
				MOAttribute variable= new MOAttribute();
				variable.set_Name(current.getName());
				if (current.getContent()== null)
					variable.set_Value("0");
				else
					variable.set_Value(current.getContent());
				variable.set_Variability(current.getVariability());
				variable.set_Visibility(current.getVisibility());
				variable.set_Flow(Boolean.valueOf(current.getFlow()));
				syncMach.add_Attribute(variable);
			}
		}
		syncMach.set_Stereotype(_mapSyncMach.getStereotype());
		syncMach.set_Package(_mapSyncMach.getPackage());
		syncMach.set_RdfId(_mapSyncMach.getRdfId());
		
		return syncMach;
	}
	
	/**
	 * 
	 * @param _mapExcSys
	 * @return
	 */
	public IPSLExcitationSystem create_ExcSysComponent(ComponentMap _mapExcSys) 
	{
		IPSLExcitationSystem excSys= new IPSLExcitationSystem(_mapExcSys.getName());
		Iterator<AttributeMap> imapAttList= _mapExcSys.getAttributeMap().iterator();
		AttributeMap current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("IdentifiedObject.name")){
				excSys.set_InstanceName("es_"+ current.getContent().trim());
			}
			else{
				MOAttribute variable= new MOAttribute();
				variable.set_Name(current.getName());
				if (current.getContent()== null)
					variable.set_Value("0");
				else
					variable.set_Value(current.getContent());
				variable.set_Variability(current.getVariability());
				variable.set_Visibility(current.getVisibility());
				variable.set_Flow(Boolean.valueOf(current.getFlow()));
				excSys.add_Attribute(variable);
			}
		}
		excSys.set_Stereotype(_mapExcSys.getStereotype());
		excSys.set_Package(_mapExcSys.getPackage());
		//for internal identification only
		excSys.set_RdfId(_mapExcSys.getRdfId());
		return excSys;
	}
	
	/**
	 * 
	 * @param _mapTGov
	 * @return
	 */
	public IPSLTurbineGovernor create_TGovComponent(ComponentMap _mapTGov) 
	{
		IPSLTurbineGovernor tgov= new IPSLTurbineGovernor(_mapTGov.getName());
		Iterator<AttributeMap> imapAttList= _mapTGov.getAttributeMap().iterator();
		AttributeMap current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("IdentifiedObject.name")){
				tgov.set_InstanceName("tgov_"+ current.getContent().trim());
			}
			else{
				MOAttribute variable= new MOAttribute();
				variable.set_Name(current.getName());
				if (current.getContent()== null)
					variable.set_Value("0");
				else
					variable.set_Value(current.getContent());
				variable.set_Variability(current.getVariability());
				variable.set_Visibility(current.getVisibility());
				variable.set_Flow(Boolean.valueOf(current.getFlow()));
				tgov.add_Attribute(variable);
			}
		}
		tgov.set_Stereotype(_mapTGov.getStereotype());
		tgov.set_Package(_mapTGov.getPackage());
		//for internal identification only
		tgov.set_RdfId(_mapTGov.getRdfId());
		
		return tgov;
	}
	
	
	public MOClass create_LoadComponent(LoadMap _mapEnergyC)
	{
		MOClass pwLoad= new MOClass(_mapEnergyC.getName());
		MOAttributeComplex complejo= null;
		Iterator<AttributeMap> imapAttList= ((ArrayList<AttributeMap>)_mapEnergyC.getAttributeMap()).iterator();
		AttributeMap current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("IdentifiedObject.name")){
				pwLoad.set_InstanceName("ld"+ current.getContent().trim());
			}
			else {
				if (current.getDatatype().equals("Complex")) {
					String nombre= current.getName().split("[.]")[0];
					String parte= current.getName().split("[.]")[1];
					if (!pwLoad.exist_Attribute(nombre))
						complejo= new MOAttributeComplex();
					else
						complejo= (MOAttributeComplex)pwLoad.get_Attribute(nombre);
					complejo.set_Name(nombre);
					if (parte.equals("re")){
						complejo.set_Real(current.getContent());
						pwLoad.add_Attribute(complejo);
					}
					else {
						complejo.set_Imaginary(current.getContent());
						complejo.set_Datatype(current.getDatatype());
						complejo.set_Variability(current.getVariability());
						complejo.set_Visibility(current.getVisibility());
						complejo.set_Flow(Boolean.valueOf(current.getFlow()));
					}
				}
				else {
					MOAttribute variable= new MOAttribute();
					variable.set_Name(current.getName());
					if (current.getContent()== null)
						variable.set_Value("0");
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
		pwLoad.set_Stereotype(_mapEnergyC.getStereotype());
		pwLoad.set_Package(_mapEnergyC.getPackage());
		//for internal identification only
		pwLoad.set_RdfId(_mapEnergyC.getRdfId());
		
		return pwLoad;
	}
	
	/**
	 * Use of the RDF_ID for internal identification only
	 * @param _mapACLine
	 * @return
	 */
	public MOClass create_LineComponent(PwLineMap _mapACLine)
	{
		PwLine pwline= new PwLine(_mapACLine.getName());
		ArrayList<AttributeMap> mapAttList= 
				(ArrayList<AttributeMap>)_mapACLine.getAttributeMap();
		Iterator<AttributeMap> imapAttList= mapAttList.iterator();
		AttributeMap current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("IdentifiedObject.name")){
				pwline.set_InstanceName("ln"+ current.getContent().trim());
			}
			else{
				MOAttribute variable= new MOAttribute();
				variable.set_Name(current.getName());
				if (current.getContent()== null)
					variable.set_Value("0");
				else
					variable.set_Value(current.getContent());
				variable.set_Variability(current.getVariability());
				variable.set_Visibility(current.getVisibility());
				variable.set_Flow(Boolean.valueOf(current.getFlow()));
				pwline.add_Attribute(variable);
			}
		}
		pwline.set_Stereotype(_mapACLine.getStereotype());
		pwline.set_Package(_mapACLine.getPackage());
		pwline.set_RdfId(_mapACLine.getRdfId());
		
		return pwline;
	}
	
	/**
	 * Use of the RDF_ID for internal identification only
	 * @param _mapPowTrans
	 * @return
	 */
	public MOClass create_TransformerComponent(TwoWindingTransformerMap _mapPowTrans)
	{
		MOClass twtransformer= new MOClass(_mapPowTrans.getName());
		AttributeMap current;
		String instanceName;
		
		Iterator<AttributeMap> imapAttList= _mapPowTrans.getAttributeMap().iterator();
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (!current.getCimName().equals("TransformerEnd.endNumber") && 
				!current.getCimName().equals("RatioTapChanger.stepVoltageIncrement") &&
				!current.getCimName().equals("PowerTransformerEnd.ratedU") &&
				!current.getCimName().equals("PowerTransformerEnd.nomV"))
			{
				if (current.getCimName().equals("IdentifiedObject.name")){
					instanceName= current.getContent().trim().replace(' ', '_');
					twtransformer.set_InstanceName(instanceName);
				}
				else
				{
					MOAttribute variable= new MOAttribute();
					variable.set_Name(current.getName());
					if (current.getContent()== null)
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
		twtransformer.set_Stereotype(_mapPowTrans.getStereotype());
		twtransformer.set_Package(_mapPowTrans.getPackage());
		twtransformer.set_RdfId(_mapPowTrans.getRdfId());
		
		return twtransformer;
	}
	/**
	 * Creates different attributes for the TwoWindingTransformer model of the Library. This attributes represent values for each winding, and
	 * each winding in CIM is represented by different class instances, for exemple:
	 * attribute t1 corresponds to one instance of PowerTransformerEnd, attribute t2 corresponds another instance of PowerTransformerEnd 
	 * @param _mapPowTrans
	 * @return
	 */
	public ArrayList<MOAttribute> create_AttTransformerEnd(TwoWindingTransformerMap _mapPowTrans)
	{
		AttributeMap current, endNumber= null;
		MOAttribute ratioTapChanger= null, powerTransEnd= null;
		ArrayList<MOAttribute> endAttributes= new ArrayList<MOAttribute>();
		
		Iterator<AttributeMap> imapAttList= _mapPowTrans.getAttributeMap().iterator();
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("TransformerEnd.endNumber"))
				endNumber= current;
			else if (current.getCimName().equals("RatioTapChanger.stepVoltageIncrement")){
				ratioTapChanger= this.create_TransformerEndAttribute(endNumber, current);
				endAttributes.add(ratioTapChanger);
			}
			else if (current.getCimName().equals("PowerTransformerEnd.ratedU") || 
					current.getCimName().equals("PowerTransformerEnd.nomV")){
//				System.out.println(current.getCimName()+ "; "+ current.getMoName());
				powerTransEnd= this.create_TransformerEndAttribute(endNumber, current);
				endAttributes.add(powerTransEnd);
			}
//			else if (current.getCimName().equals("SvVoltage.v"))
//				svvoltage= this.create_TransformerEndAttribute(endNumber, current);
		}
		return endAttributes;
	}
	/**
	 * Creates one attribute for each cim PowerTransformerEnd instance
	 * @param _endNumber - Value for Power transformer ending number
	 * @param _currentAtt - cim:RatioTapChanger.stepVoltageIncrement = mod:(t1,t2); 
	 * cim:PowerTransformerEnd.ratedU = mod:(VNOM1,VNOM2)
	 * @return modelica attribute for each instance of cim:PowerTransformerEnd
	 */
	private MOAttribute create_TransformerEndAttribute(AttributeMap _endNumber, AttributeMap _currentAtt) 
	{
		MOAttribute variable= new MOAttribute();
		variable.set_Name(_currentAtt.getName()+ _endNumber.getContent());
		if (_currentAtt.getContent()== null)
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
	 * @param _mapTopoNode - map structure with the data from the cim:TopologicalNode class
	 * @return
	 */
	public MOClass create_BusComponent(PwBusMap _mapTopoNode)
	{
		Bus pwbus= new Bus(_mapTopoNode.getName());
		ArrayList<AttributeMap> mapAttList= 
				(ArrayList<AttributeMap>)_mapTopoNode.getAttributeMap();
		Iterator<AttributeMap> imapAttList= mapAttList.iterator();
		AttributeMap current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("IdentifiedObject.name")){
				pwbus.set_InstanceName(current.getContent().trim());
			}
			else {
				MOAttribute variable= new MOAttribute();
				variable.set_Name(current.getName());
				if (current.getContent()== null)
					variable.set_Value("0");
				else
					variable.set_Value(current.getContent());
				variable.set_Variability(current.getVariability());
				variable.set_Visibility(current.getVisibility());
				variable.set_Flow(Boolean.valueOf(current.getFlow()));
				pwbus.add_Attribute(variable);
			}
		}
		pwbus.setStereotype(_mapTopoNode.getStereotype());
		pwbus.set_Package(_mapTopoNode.getPackage());
		pwbus.set_RdfId(_mapTopoNode.getRdfId());
		
		return pwbus;
	}
	
//	public MOClass create_FaultComponent(PwFaultMap _mapFault)
//	{
//		PwLine pwline= new PwLine(_mapFault.getName());
//		ArrayList<AttributeMap> mapAttList= 
//				(ArrayList<AttributeMap>)_mapFault.getAttributeMap();
//		Iterator<AttributeMap> imapAttList= mapAttList.iterator();
//		AttributeMap current;
//		while (imapAttList.hasNext()) {
//			current= imapAttList.next();
//			if (current.getCimName().equals("IdentifiedObject.name")){
//				pwline.set_InstanceName(current.getContent());
//			}
//			else{
//				MOAttribute variable= new MOAttribute();
//				variable.set_Name(current.getName());
//				if (current.getContent()== null)
//					variable.set_Value("0");
//				else
//					variable.set_Value(current.getContent());
//				variable.set_Variability(current.getVariability());
//				variable.set_Visibility(current.getVisibility());
//				variable.set_Flow(Boolean.valueOf(current.getFlow()));
//				pwline.add_Attribute(variable);
//			}
//		}
//		pwline.set_Stereotype(_mapFault.getStereotype());
//		pwline.set_Package(_mapFault.getPackage());
//		//for internal identification only
//		pwline.set_RdfId(_mapFault.getRfdId());
//		
//		return pwline;
//	}
	
	
	/**
	 * 
	 * @param _component
	 */
	public void add_plantNetwork(MOPlant _plant)
	{
		this.powsys.add_Plant(_plant);
	}
	
	
	/**
	 * Sets the connection between all the components in the network model. 
	 * It creates the connect equation to be written in the network model.
	 * @param _connectmap - structure with references and ids for Terminal, ConductingEquipment and TopologicalNode
	 */
	public void connect_Components(ArrayList<ConnectionMap> _connectmap)
	{
		Iterator<ConnectionMap> iConnections= _connectmap.iterator();
		ConnectionMap currentConnection;
		MOClass equipment, bus; 
		MOConnectNode conexio= null;
		Iterator<MOPlant> iPlant;
		boolean foundPlant= false;
		MOPlant currentPlant= null;
		
		while(iConnections.hasNext())
		{
			try {
			currentConnection= iConnections.next();
			equipment= this.get_equipmentNetwork(currentConnection.get_Ce_id());
			bus= this.get_equipmentNetwork(currentConnection.get_Tn_id());
			if (equipment instanceof IPSLMachine)
			{
				iPlant= this.powsys.get_planta().iterator();
				foundPlant= false;
				while (!foundPlant && iPlant.hasNext()){
					currentPlant= iPlant.next();
					foundPlant= currentPlant.getMachine().get_RdfId().equals(currentConnection.get_Ce_id());
				}
				if (foundPlant)
					conexio= new MOConnectNode(currentPlant.getInstanceName(),
							currentPlant.getOutpin().get_InstanceName(),
							bus.get_InstanceName(),
							bus.get_Terminal(currentConnection.get_T_id()).get_InstanceName());
			}
			else
			{
				conexio= new MOConnectNode(equipment.get_InstanceName(), 
						equipment.get_Terminal(currentConnection.get_T_id()).get_InstanceName(),
						bus.get_InstanceName(), 
						bus.get_Terminal(currentConnection.get_T_id()).get_InstanceName());
			}
			if (!this.powsys.exist_Connection(conexio))
				this.powsys.add_Connection(conexio);
			}
			catch(NullPointerException npe)
			{
				// System.err.println("Still some equipment left to map");
				// TODO logging
			}
		}
		// System.out.println(this.powsys.to_ModelicaClass());
	}
	
	public void save_ModelicaFile(String _moCode, String _nameModel, String _folder)
	{
		BufferedWriter writer = null;
		try {
			String nomFitxer = "./model/" + _folder + "/" + _nameModel + ".mo";
			File fitxer = new File(nomFitxer);
			if (!fitxer.getParentFile().exists())
				fitxer.getParentFile().mkdirs();
		    writer = new BufferedWriter(new FileWriter(fitxer));
		    writer.write(_moCode);
		}
		catch ( IOException _e){
		}
		finally {
		    try{
		        if ( writer != null)
		        writer.close( );
		    }
		    catch ( IOException _e){
		    }
		}
	}
}
