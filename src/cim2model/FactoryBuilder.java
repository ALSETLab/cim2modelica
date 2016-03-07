package cim2model;

import java.util.Iterator;
import org.apache.commons.math3.complex.*;
import org.apache.commons.math3.analysis.function.*;

import cim2model.modelica.*;
import cim2model.modelica.cimmap.*;

public class FactoryBuilder 
{
	private MONetwork powsys;
	private MOClass _currentEquipment, _currentNode;
	private String _currentIDEquipment, _currentIDNode;
	
	public FactoryBuilder(String _network)
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
	
	public MOConnector create_TerminalConnector(TerminalMap _terminalMap)
	{
		MOConnector pin= new MOConnector(_terminalMap.getCimName());
		MOAttribute variable;
		/* From SvPowerFlowMap and SvVoltageMap build the variables V and I, complex*/
		SvVoltageMap svVolt= _terminalMap.getTopologicalNodeMap().getSvVoltageMap();
		SvPowerFlowMap svPF= _terminalMap.getSvPowerFlowMap();
		double [] complexVolt= {0,0}; int i= 0; 
		for (ModelVariableMap att : svVolt.getModelVariableMap()){
			/* complexVolt[0] = voltage; complexVolt[1] = angle */
			if (att.getContent()!= null)
				complexVolt[i]= Double.valueOf(att.getContent());
			else
				complexVolt[i]= 1;
			i+= 1;
		}
		Complex Voltage= new Complex(complexVolt[0]*Math.cos(complexVolt[1]), complexVolt[0]*Math.sin(complexVolt[1]));
		variable= new MOAttributeComplex();
		variable.set_Name("V");
		((MOAttributeComplex)variable).set_Real(Voltage.getReal());
		((MOAttributeComplex)variable).set_Imaginary(Voltage.getImaginary());
		variable.set_Datatype("Complex");
		variable.set_Variability("variable");
		variable.set_Visibility("public");
		variable.set_Flow(false);
		pin.set_Attribute(variable);
		/* build I variable */
		double [] complexPower= {0,0}; i= 0; 
		for (ModelVariableMap att : svPF.getModelVariableMap()){
			/* complexPower[0] = active power; complexPower[1] = reactive power */
			if (att.getContent()!= null)
				complexPower[i]= Double.valueOf(att.getContent());
			else
				complexPower[i]= 1;
			i+= 1;
		}
		double angle= Math.atan2(complexPower[1], complexPower[0]);
		Complex Current= new Complex(complexPower[0] / (complexVolt[0]*Math.cos(angle)), 
				complexPower[1] / (complexVolt[0]*Math.sin(angle)));
		variable= new MOAttributeComplex();
		variable.set_Name("I");
		((MOAttributeComplex)variable).set_Real(Current.getReal());
		((MOAttributeComplex)variable).set_Imaginary(Current.getImaginary());
		variable.set_Datatype("Complex");
		variable.set_Variability("variable");
		variable.set_Visibility("public");
		variable.set_Flow(true);
		pin.set_Attribute(variable);
		/* rest of attributes for the pin */
		pin.set_Name("Terminal");
		pin.set_InstanceName(_terminalMap.getCimName());
		pin.set_Package(_terminalMap.getMoPackage());
		pin.set_Stereotype(_terminalMap.getMoStereotype());
		/* rfd_id for internal identification only */
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
		MOClass current= null;
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
	public MOClass create_ACLineComponent(ACLineSegmentMap _mapACLine)
	{
		MOClass pwline= new MOClass(_mapACLine.getCimName());
		ModelVariableMap currentVar;
		BindingVariableMap currentBindVar; 
		/* Create the variables that come from CIM, with CIM values */
		Iterator<ModelVariableMap> iVar= _mapACLine.getModelVariableMap().iterator();
		while (iVar.hasNext()) {
			currentVar= iVar.next();
			if (currentVar.getCimName().equals("IdentifiedObject.name")){
				pwline.set_InstanceName(currentVar.getContent());
			}
			else{
				MOAttribute variable= new MOAttribute();
				String[] splitName= currentVar.getCimName().split("[.]");
				variable.set_Name(splitName[1]);
				if (currentVar.getContent()== null)
					variable.set_Value("0");
				else
					variable.set_Value(currentVar.getContent());
				variable.set_Variability(currentVar.getMoVariability());
				variable.set_Visibility(currentVar.getMoVisibility());
				pwline.add_Attribute(variable);
			}
		}
		/* Create the variables specific for the component, from the map */
		Iterator<BindingVariableMap> iBindVar= _mapACLine.getBindingVariableMap().iterator();
		while (iBindVar.hasNext()) {
			currentBindVar= iBindVar.next();
			if (currentBindVar.getMoDatatype().equals("Complex")){
				MOAttributeComplex variable= new MOAttributeComplex();
				String[] splitContent= currentBindVar.getContent().split(";");
				variable.set_Real(splitContent[0]);
				variable.set_Imaginary(splitContent[1]);
				variable.set_Name(currentBindVar.getMoName());
				variable.set_Datatype(currentBindVar.getMoDatatype());
				variable.set_Variability(currentBindVar.getMoVariability());
				variable.set_Visibility(currentBindVar.getMoVisibility());
				pwline.add_Attribute(variable);
			}
			if (currentBindVar.getMoDatatype().equals("Real")){
				MOAttribute variable= new MOAttribute();
				variable.set_Value(currentBindVar.getContent());
				variable.set_Name(currentBindVar.getMoName());
				variable.set_Datatype(currentBindVar.getMoDatatype());
				variable.set_Variability(currentBindVar.getMoVariability());
				variable.set_Visibility(currentBindVar.getMoVisibility());
				pwline.add_Attribute(variable);
			}
		}
		/* Create the equations for the component, from the map */
		Iterator<DynamicEquationMap> iEquation= _mapACLine.getDynamicEquationMap().iterator();
		while (iEquation.hasNext()) {
			MOEquation equ= new MOEquation("");
			equ.setEquation(iEquation.next().getContent());
			pwline.add_Equation(equ);
		}
		/* Base power and base frequency attributes */
		MOAttribute variable= new MOAttribute();
		variable.set_Name(_mapACLine.getBasePowerMap().getModelVariableMap().getCimName());
		variable.set_Value(_mapACLine.getBasePowerMap().getModelVariableMap().getContent());
		variable.set_Datatype(_mapACLine.getBasePowerMap().getModelVariableMap().getMoDatatype());
		variable.set_Variability(_mapACLine.getBasePowerMap().getModelVariableMap().getMoVariability());
		variable.set_Visibility(_mapACLine.getBasePowerMap().getModelVariableMap().getMoVisibility());
		pwline.add_Attribute(variable);
		
		pwline.set_Name("ACLineSegment");
		pwline.set_InstanceName(_mapACLine.getCimName());
		pwline.set_Stereotype(_mapACLine.getMoStereotype());
		pwline.set_Package(_mapACLine.getMoPackage());
		//for internal identification only
		pwline.set_RfdId(_mapACLine.getRfdId());
		
		return pwline;
	}
	
	public MOClass create_BusComponent(TopologicalNodeMap _mapTopoNode)
	{
		MOClass pwbus= new MOClass(_mapTopoNode.getCimName());
		MOAttribute variable;
		
		SvVoltageMap svVolt= _mapTopoNode.getSvVoltageMap();
		for (ModelVariableMap att : svVolt.getModelVariableMap()){
			variable= new MOAttribute();
			variable.set_Name(att.getMoName());
			if (att.getContent()== null)
				variable.set_Value("0");
			else
				variable.set_Value(att.getContent());
			variable.set_Variability(att.getMoVariability());
			variable.set_Visibility(att.getMoVisibility());
			pwbus.add_Attribute(variable);
		}
		/* Create the variables specific for the component, from the map */
		for (BindingVariableMap bindVar: _mapTopoNode.getBindingVariableMap()) {
			variable= new MOAttribute();
			variable.set_Value(bindVar.getContent());
			variable.set_Name(bindVar.getMoName());
			variable.set_Datatype(bindVar.getMoDatatype());
			variable.set_Variability(bindVar.getMoVariability());
			variable.set_Visibility(bindVar.getMoVisibility());
			pwbus.add_Attribute(variable);
		}
		pwbus.setStereotype(_mapTopoNode.getMoStereotype());
		pwbus.set_Package(_mapTopoNode.getMoPackage());
		//for internal identification only
		pwbus.set_RfdId(_mapTopoNode.getRfdId());
		
		return pwbus;
	}
//	
//	public MOClass create_GENROUComponent(GENROUMap _mapSyncMach)
//	{
//		MOClass syncMach= new GENROU(_mapSyncMach.getName());
//		ArrayList<MapAttribute> mapAttList= 
//				(ArrayList<MapAttribute>)_mapSyncMach.getMapAttribute();
//		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
//		imapAttList= mapAttList.iterator();
//		MapAttribute current;
//		while (imapAttList.hasNext()) {
//			current= imapAttList.next();
//			if (current.getCimName().equals("IdentifiedObject.name")){
//				syncMach.set_InstanceName(current.getContent());
//			}
//			else{ //TODO check current.getContent(), if null then 0
//				MOAttribute variable= new MOAttribute();
//				variable.set_Name(current.getMoName());
//				if (current.getContent()== null)
//					variable.set_Value("0");
//				else
//					variable.set_Value(current.getContent());
//				variable.set_Variability(current.getVariability());
//				variable.set_Visibility(current.getVisibility());
//				variable.set_Flow(Boolean.valueOf(current.getFlow()));
//				syncMach.add_Attribute(variable);
//			}
//		}
//		syncMach.set_Stereotype(_mapSyncMach.getStereotype());
//		syncMach.set_Package(_mapSyncMach.getPackage());
//		//for internal identification only
//		syncMach.set_RfdId(_mapSyncMach.getRfdId());
//		
//		return syncMach;
//	}
//	public MOClass create_GENSALComponent(GENSALMap _mapSyncMach)
//	{
//		MOClass syncMach= new GENSAL(_mapSyncMach.getName());
//		ArrayList<MapAttribute> mapAttList= 
//				(ArrayList<MapAttribute>)_mapSyncMach.getMapAttribute();
//		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
//		imapAttList= mapAttList.iterator();
//		MapAttribute current;
//		while (imapAttList.hasNext()) {
//			current= imapAttList.next();
//			if (current.getCimName().equals("IdentifiedObject.name")){
//				syncMach.set_InstanceName(current.getContent());
//			}
//			else{ //TODO check current.getContent(), if null then 0
//				MOAttribute variable= new MOAttribute();
//				variable.set_Name(current.getMoName());
//				if (current.getContent()== null)
//					variable.set_Value("0");
//				else
//					variable.set_Value(current.getContent());
//				variable.set_Variability(current.getVariability());
//				variable.set_Visibility(current.getVisibility());
//				variable.set_Flow(Boolean.valueOf(current.getFlow()));
//				syncMach.add_Attribute(variable);
//			}
//		}
//		syncMach.set_Stereotype(_mapSyncMach.getStereotype());
//		syncMach.set_Package(_mapSyncMach.getPackage());
//		//for internal identification only
//		syncMach.set_RfdId(_mapSyncMach.getRfdId());
//		
//		return syncMach;
//	}
//	
//	public MOClass create_LoadComponent(LoadMap _mapEnergyC)
//	{//TODO values for pfixed/qfixed in CIM are in %, convert to p.u. in code
//		MOClass pwLoad= new MOClass(_mapEnergyC.getName());
//		MOAttributeComplex complejo= null;
//		ArrayList<MapAttribute> mapAttList= 
//				(ArrayList<MapAttribute>)_mapEnergyC.getMapAttribute();
//		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
//		imapAttList= mapAttList.iterator();
//		MapAttribute current;
//		while (imapAttList.hasNext()) {
//			current= imapAttList.next();
//			if (current.getCimName().equals("IdentifiedObject.name")){
//				pwLoad.set_InstanceName(current.getContent());
//			}
//			else {
//				if (current.getDatatype().equals("Complex")) {
//					String nombre= current.getMoName().split("[.]")[0];
//					String parte= current.getMoName().split("[.]")[1];
//					if (!pwLoad.exist_Attribute(nombre))
//						complejo= new MOAttributeComplex();
//					else
//						complejo= (MOAttributeComplex)pwLoad.get_Attribute(nombre);
//					complejo.set_Name(nombre);
//					if (parte.equals("re")){
//						complejo.set_Real(current.getContent());
//						pwLoad.add_Attribute(complejo);
//					}
//					else {
//						complejo.set_Imaginary(current.getContent());
//						complejo.set_Datatype(current.getDatatype());
//						complejo.set_Variability(current.getVariability());
//						complejo.set_Visibility(current.getVisibility());
//						complejo.set_Flow(Boolean.valueOf(current.getFlow()));
//					}
//				}
//				else {
//					MOAttribute variable= new MOAttribute();
//					variable.set_Name(current.getMoName());
//					if (current.getContent()== null)
//						variable.set_Value("0");
//					else
//						variable.set_Value(current.getContent());
//					variable.set_Datatype(current.getDatatype());
//					variable.set_Variability(current.getVariability());
//					variable.set_Visibility(current.getVisibility());
//					variable.set_Flow(Boolean.valueOf(current.getFlow()));
//					pwLoad.add_Attribute(variable);
//				}
//			}
//		}
//		pwLoad.set_Stereotype(_mapEnergyC.getStereotype());
//		pwLoad.set_Package(_mapEnergyC.getPackage());
//		//for internal identification only
//		pwLoad.set_RfdId(_mapEnergyC.getRfdId());
//		
//		return pwLoad;
//	}
//	

//	
//	public MOClass create_TransformerComponent(TwoWindingTransformerMap _mapPowTrans)
//	{//TODO think about TwoWindingTransformer class, responsibilities, two tap changer attributes from two ratio tap changer
//		MOClass twtransformer= new MOClass(_mapPowTrans.getName());
////		System.out.println("_mapPowTrans.getName() "+ _mapPowTrans.getName());
////		System.out.println("_mapPowTrans.getPowerTransformer() "+ _mapPowTrans.getPowerTransformer());
//		ArrayList<MapAttribute> mapAttList= 
//				(ArrayList<MapAttribute>)_mapPowTrans.getMapAttribute();
//		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
//		MapAttribute current;
//		while (imapAttList.hasNext()) {
//			current= imapAttList.next();
//			//first check for TransformerEnd and RatioTapChanger attributes, this are added later
//			if (!current.getCimName().equals("TransformerEnd.endNumber") &&
//					!current.getCimName().equals("RatioTapChanger.stepVoltageIncrement")){
//				if (current.getCimName().equals("IdentifiedObject.name")){
//					twtransformer.set_InstanceName(current.getContent());
//				}
//				else{
//					MOAttribute variable= new MOAttribute();
//					variable.set_Name(current.getMoName());
//					if (current.getContent()== null)
//						variable.set_Value("0");
//					else
//						variable.set_Value(current.getContent());
//					variable.set_Variability(current.getVariability());
//					variable.set_Visibility(current.getVisibility());
//					variable.set_Flow(Boolean.valueOf(current.getFlow()));
//					twtransformer.add_Attribute(variable);
//				}
//			}
//		}
//		twtransformer.set_Stereotype(_mapPowTrans.getStereotype());
//		twtransformer.set_Package(_mapPowTrans.getPackage());
//		//for internal identification only
//		twtransformer.set_RfdId(_mapPowTrans.getRfdId());
//		
//		return twtransformer;
//	}
//	public MOAttribute create_TransformerEndRatioAttribute(TwoWindingTransformerMap _mapPowTrans) 
//	{
//		MOAttribute variable= new MOAttribute();
//		String sequenceEnd= "0";
//		ArrayList<MapAttribute> mapAttList= 
//				(ArrayList<MapAttribute>)_mapPowTrans.getMapAttribute();
//		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
//		MapAttribute current;
//		while (imapAttList.hasNext()) {
//			current= imapAttList.next();
//			if (current.getCimName().equals("TransformerEnd.endNumber"))
//				sequenceEnd= current.getContent();
//			if (current.getCimName().equals("RatioTapChanger.stepVoltageIncrement")){
//				variable.set_Name(current.getMoName()+ sequenceEnd);
//				if (current.getContent()== null)
//					variable.set_Value("0");
//				else
//					variable.set_Value(current.getContent());
//				variable.set_Variability(current.getVariability());
//				variable.set_Visibility(current.getVisibility());
//				variable.set_Flow(Boolean.valueOf(current.getFlow()));
//			}
//		}
//		return variable;
//	}
//	public MOAttribute create_TransformerEndPrimarySideAttribute(TwoWindingTransformerMap _mapPowTrans)
//	{
//		MOAttribute variable= new MOAttribute();
//		ArrayList<MapAttribute> mapAttList= 
//				(ArrayList<MapAttribute>)_mapPowTrans.getMapAttribute();
//		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
//		MapAttribute current;
//		while (imapAttList.hasNext()) {
//			current= imapAttList.next();
//			if (current.getCimName().equals("TransformerEnd.endNumber"))
//			{
//				variable.set_Name(current.getMoName());
//				if (!current.getContent().equals("1"))
//					variable.set_Value("false");
//				else
//					variable.set_Value("true");
//				variable.set_Variability(current.getVariability());
//				variable.set_Visibility(current.getVisibility());
//				variable.set_Flow(Boolean.valueOf(current.getFlow()));
//			}
//		}
//		return variable;
//	}
//	

//	
//	public MOClass create_FaultComponent(PwFaultMap _mapFault)
//	{
//		PwLine pwline= new PwLine(_mapFault.getName());
//		ArrayList<MapAttribute> mapAttList= 
//				(ArrayList<MapAttribute>)_mapFault.getMapAttribute();
//		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
//		MapAttribute current;
//		while (imapAttList.hasNext()) {
//			current= imapAttList.next();
//			if (current.getCimName().equals("IdentifiedObject.name")){
//				pwline.set_InstanceName(current.getContent());
//			}
//			else{
//				MOAttribute variable= new MOAttribute();
//				variable.set_Name(current.getMoName());
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
//		pwline.set_RfdId(_mapFault.getRfdId());
//		
//		return pwline;
//	}
//	
//	public void connect_Components(ArrayList<ConnectionMap> _connectmap)
//	{
//		Iterator<ConnectionMap> iConnections= _connectmap.iterator();
//		ConnectionMap current;
//		MOClass equipment, bus; 
//		MOConnectNode conexio;
//		
//		while(iConnections.hasNext())
//		{
//			try { //TODO: check equipment not null, still some equipment missing to map
//			current= iConnections.next();
////			System.out.println(current.toString());
//			equipment= this.get_equipmentNetwork(current.get_Ce_id());
//			bus= this.get_equipmentNetwork(current.get_Tn_id());
//			equipment.get_Terminal(current.get_T_id());
//			bus.get_Terminal(current.get_T_id());
//			conexio= new MOConnectNode(equipment.get_InstanceName(), equipment.get_Terminal(current.get_T_id()).get_InstanceName(),
//					bus.get_InstanceName(), bus.get_Terminal(current.get_T_id()).get_InstanceName());
//			if (!this.powsys.exist_Connection(conexio))
//				this.powsys.add_Connection(conexio);
//			}
//			catch(NullPointerException npe)
//			{
//				System.err.println("Still some equipment left to map");
//			}
//		}
//		System.out.println(this.powsys.to_ModelicaClass());
//	}
	
//	public void save_ModelicaFile(String _moCode)
//	{
//		BufferedWriter writer = null;
//		try {
//			String fitxer= "./model/"+ this.powsys.get_Name()+ ".mo";
//		    writer = new BufferedWriter(new FileWriter(fitxer));
//		    writer.write(_moCode);
//
//		}
//		catch ( IOException _e){
//		}
//		finally {
//		    try{
//		        if ( writer != null)
//		        writer.close( );
//		    }
//		    catch ( IOException _e){
//		    }
//		}
//	}
}
