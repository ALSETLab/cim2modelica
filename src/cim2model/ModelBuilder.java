package cim2model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import cim2model.ipsl.branches.PwLine;
import cim2model.ipsl.buses.Bus;
import cim2model.ipsl.cimmap.*;
import cim2model.ipsl.machines.GENROU;
import cim2model.ipsl.machines.GENSAL;
import cim2model.modelica.*;

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
		ArrayList<MapAttribute> mapAttList= 
				(ArrayList<MapAttribute>)_terminalMap.getMapAttribute();
		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
		imapAttList= mapAttList.iterator();
		MapAttribute current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("IdentifiedObject.name")){
				pin.set_InstanceName(current.getContent());
			}
			else{
				MOAttribute variable= new MOAttribute();
				variable.set_Name(current.getMoName());
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
		pin.set_RfdId(_terminalMap.getRfdId());
		
		return pin;
	}
	
	/**
	 * 
	 * @param _component
	 */
	public void add_deviceNetwork(MOClass _component)
	{
		Iterator<MOClass> iComponents= this.powsys.get_Components().iterator();
		boolean exists= false;
		while (!exists && iComponents.hasNext()){
			exists= iComponents.next().get_InstanceName().equals(_component.get_InstanceName());
		}
		if (!exists)
			this.powsys.add_Component(_component);
	}
	/**
	 * 
	 * @param _rfdId
	 * @return
	 */
	public MOClass get_equipmentNetwork(String _rfdId)
	{
		MOClass current= new MOClass("void");
		Iterator<MOClass> iComponents;
		
		iComponents= this.powsys.get_Components().iterator();
		boolean exists= false;
		while (!exists && iComponents.hasNext()){
			current= iComponents.next();
			exists= current.get_RfdId().equals(_rfdId);
		}
		if (exists)
			return current;
		else
			return null;
	}
	
	public MOClass create_GENCLSComponent(GENCLSMap _mapSyncMach)
	{
		MOClass syncMach= new GENSAL(_mapSyncMach.getName());
		Iterator<MapAttribute> imapAttList= _mapSyncMach.getMapAttribute().iterator();
		MapAttribute current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("IdentifiedObject.name")){
				syncMach.set_InstanceName(current.getContent());
			}
			else{
				MOAttribute variable= new MOAttribute();
				variable.set_Name(current.getMoName());
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
		//for internal identification only
		syncMach.set_RfdId(_mapSyncMach.getRfdId());
		
		return syncMach;
	}
	public MOClass create_GENROUComponent(GENROUMap _mapSyncMach)
	{
		MOClass syncMach= new GENROU(_mapSyncMach.getName());
		Iterator<MapAttribute> imapAttList= _mapSyncMach.getMapAttribute().iterator();
		MapAttribute current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("IdentifiedObject.name")){
				syncMach.set_InstanceName(current.getContent());
			}
			else{ 
				MOAttribute variable= new MOAttribute();
				variable.set_Name(current.getMoName());
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
		//for internal identification only
		syncMach.set_RfdId(_mapSyncMach.getRfdId());
		
		return syncMach;
	}
	public MOClass create_GENSALComponent(GENSALMap _mapSyncMach)
	{
		MOClass syncMach= new GENSAL(_mapSyncMach.getName());
		Iterator<MapAttribute> imapAttList= _mapSyncMach.getMapAttribute().iterator();
		MapAttribute current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("IdentifiedObject.name")){
				syncMach.set_InstanceName(current.getContent());
			}
			else{ 
				MOAttribute variable= new MOAttribute();
				variable.set_Name(current.getMoName());
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
		//for internal identification only
		syncMach.set_RfdId(_mapSyncMach.getRfdId());
		
		return syncMach;
	}
	public MOClass create_GENROEComponent(GENROEMap _mapSyncMach)
	{
		MOClass syncMach= new GENSAL(_mapSyncMach.getName());
		Iterator<MapAttribute> imapAttList= _mapSyncMach.getMapAttribute().iterator();
		MapAttribute current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("IdentifiedObject.name")){
				syncMach.set_InstanceName(current.getContent());
			}
			else{ 
				MOAttribute variable= new MOAttribute();
				variable.set_Name(current.getMoName());
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
		//for internal identification only
		syncMach.set_RfdId(_mapSyncMach.getRfdId());
		
		return syncMach;
	}
	
	public MOClass create_LoadComponent(LoadMap _mapEnergyC)
	{
		MOClass pwLoad= new MOClass(_mapEnergyC.getName());
		MOAttributeComplex complejo= null;
		Iterator<MapAttribute> imapAttList= ((ArrayList<MapAttribute>)_mapEnergyC.getMapAttribute()).iterator();
		MapAttribute current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("IdentifiedObject.name")){
				pwLoad.set_InstanceName(current.getContent());
			}
			else {
				if (current.getDatatype().equals("Complex")) {
					String nombre= current.getMoName().split("[.]")[0];
					String parte= current.getMoName().split("[.]")[1];
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
					variable.set_Name(current.getMoName());
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
		pwLoad.set_RfdId(_mapEnergyC.getRfdId());
		
		return pwLoad;
	}
	
	public MOClass create_LineComponent(PwLineMap _mapACLine)
	{
		PwLine pwline= new PwLine(_mapACLine.getName());
		ArrayList<MapAttribute> mapAttList= 
				(ArrayList<MapAttribute>)_mapACLine.getMapAttribute();
		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
		MapAttribute current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("IdentifiedObject.name")){
				pwline.set_InstanceName(current.getContent());
			}
			else{
				MOAttribute variable= new MOAttribute();
				variable.set_Name(current.getMoName());
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
		//for internal identification only
		pwline.set_RfdId(_mapACLine.getRfdId());
		
		return pwline;
	}
	
	public MOClass create_TransformerComponent(TwoWindingTransformerMap _mapPowTrans)
	{
		MOClass twtransformer= new MOClass(_mapPowTrans.getName());
		MapAttribute current;
		
		Iterator<MapAttribute> imapAttList= _mapPowTrans.getMapAttribute().iterator();
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (!current.getCimName().equals("TransformerEnd.endNumber") && 
				!current.getCimName().equals("RatioTapChanger.stepVoltageIncrement") &&
				!current.getCimName().equals("PowerTransformerEnd.ratedU"))
			{
				if (current.getCimName().equals("IdentifiedObject.name"))
					twtransformer.set_InstanceName(current.getContent());
				else
				{
					MOAttribute variable= new MOAttribute();
					variable.set_Name(current.getMoName());
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
		//for internal identification only
		twtransformer.set_RfdId(_mapPowTrans.getRfdId());
		
		return twtransformer;
	}
	/**
	 * Creates differnte attributes for the TwoWindingTransformer model of the Library. This attributes represent values for each winding, and
	 * each winding in CIM is represented by different class instances, for exemple:
	 * attribute t1 corresponds to one instance of PowerTransformerEnd, attribute t2 corresponds another instance of PowerTransformerEnd 
	 * @param _mapPowTrans
	 * @return
	 */
	public ArrayList<MOAttribute> create_AttTransformerEnd(TwoWindingTransformerMap _mapPowTrans)
	{
		MapAttribute current, endNumber= null;
		MOAttribute ratioTapChanger= null, powerTransEnd= null;
		ArrayList<MOAttribute> endAttributes= new ArrayList<MOAttribute>();
		
		Iterator<MapAttribute> imapAttList= _mapPowTrans.getMapAttribute().iterator();
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("TransformerEnd.endNumber"))
				endNumber= current;
			else if (current.getCimName().equals("RatioTapChanger.stepVoltageIncrement")){
				ratioTapChanger= this.create_TransformerEndAttribute(endNumber, current);
				endAttributes.add(ratioTapChanger);
			}
			else if (current.getCimName().equals("PowerTransformerEnd.ratedU")){
				System.out.println(current.getCimName()+ "; "+ current.getMoName());
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
	 * @param _currentAtt - cim:RatioTapChanger.stepVoltageIncrement = mod:(t1,t2); cim:PowerTransformerEnd.ratedU = mod:(VNOM1,VNOM2)
	 * @return modelica attribute for each instance of cim:PowerTransformerEnd
	 */
	private MOAttribute create_TransformerEndAttribute(MapAttribute _endNumber, MapAttribute _currentAtt) 
	{// creates attribute t1, t2 for the twt modelica model, _currentAtt can be:
		// RatioTapChanger.stepVoltageIncrement
		// PowerTransformerEnd.ratedU
		MOAttribute variable= new MOAttribute();
		variable.set_Name(_currentAtt.getMoName()+ _endNumber.getContent());
		if (_currentAtt.getContent()== null)
			variable.set_Value("0");
		else
			variable.set_Value(_currentAtt.getContent());
		variable.set_Variability(_currentAtt.getVariability());
		variable.set_Visibility(_currentAtt.getVisibility());
		variable.set_Flow(Boolean.valueOf(_currentAtt.getFlow()));
		
		return variable;
	}
	
	public MOClass create_BusComponent(PwBusMap _mapTopoNode)
	{
		Bus pwbus= new Bus(_mapTopoNode.getName());
		ArrayList<MapAttribute> mapAttList= 
				(ArrayList<MapAttribute>)_mapTopoNode.getMapAttribute();
		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
		MapAttribute current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("IdentifiedObject.name")){
				pwbus.set_InstanceName(current.getContent());
			}
			else {
				MOAttribute variable= new MOAttribute();
				variable.set_Name(current.getMoName());
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
		//for internal identification only
		pwbus.set_RfdId(_mapTopoNode.getRfdId());
		
		return pwbus;
	}
	
	public MOClass create_FaultComponent(PwFaultMap _mapFault)
	{
		PwLine pwline= new PwLine(_mapFault.getName());
		ArrayList<MapAttribute> mapAttList= 
				(ArrayList<MapAttribute>)_mapFault.getMapAttribute();
		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
		MapAttribute current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("IdentifiedObject.name")){
				pwline.set_InstanceName(current.getContent());
			}
			else{
				MOAttribute variable= new MOAttribute();
				variable.set_Name(current.getMoName());
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
		pwline.set_Stereotype(_mapFault.getStereotype());
		pwline.set_Package(_mapFault.getPackage());
		//for internal identification only
		pwline.set_RfdId(_mapFault.getRfdId());
		
		return pwline;
	}
	
	/**
	 * Sets the connection for all the components in the network model. It creates the connect equation for 
	 * the high-level model 
	 * @param _connectmap - structure with references and ids for Terminal, ConductingEquipment and TopologicalNode
	 */
	public void connect_Components(ArrayList<ConnectionMap> _connectmap)
	{
		Iterator<ConnectionMap> iConnections= _connectmap.iterator();
		ConnectionMap current;
		MOClass equipment, bus; 
		MOConnectNode conexio;
		
		while(iConnections.hasNext())
		{
			try {
			current= iConnections.next();
			// TODO identify machine from equipment, set machine connections
			// TODO identify machine and controls equipment, set control connections with machine
			equipment= this.get_equipmentNetwork(current.get_Ce_id());
			bus= this.get_equipmentNetwork(current.get_Tn_id());
			conexio= new MOConnectNode(equipment.get_InstanceName(), 
					equipment.get_Terminal(current.get_T_id()).get_InstanceName(),
					bus.get_InstanceName(), 
					bus.get_Terminal(current.get_T_id()).get_InstanceName());
			if (!this.powsys.exist_Connection(conexio))
				this.powsys.add_Connection(conexio);
			}
			catch(NullPointerException npe)
			{
				System.err.println("Still some equipment left to map");
			}
		}
		System.out.println(this.powsys.to_ModelicaClass());
	}
	
	public void save_ModelicaFile(String _moCode)
	{
		BufferedWriter writer = null;
		try {
			String fitxer= "./model/"+ this.powsys.get_Name()+ ".mo";
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
