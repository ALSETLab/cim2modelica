package cim2model;

import java.util.ArrayList;
import java.util.Iterator;

import cim2model.model.modelica.*;
import cim2model.mapping.modelica.*;

public class ModelBuilder 
{
	private MONetwork powsys;
	private MOClass _currentComponent;
	private String _currentID;
	
	public ModelBuilder(String _network)
	{
		powsys= new MONetwork(_network);
		this._currentComponent= null;
		this._currentID= "";
	}
	
	public MOClass get_CurrentComponent(){
		return _currentComponent;
	}
	
	public void set_CurrentComponent(MOClass _device, String _id){
		this._currentComponent= _device;
		this._currentID= _id;
	}
	
	public boolean exist_CurrentComponent(String _id)
	{
		if (this._currentID.equals(_id))
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
			MOAttribute param= new MOAttribute();
			param.set_Name(current.getMoName());
			param.set_Value(current.getContent());
			param.set_Variability(current.getVariability());
			param.set_Visibility(current.getVisibility());
			param.set_Flow(Boolean.valueOf(current.getFlow()));
			pin.set_Attribute(param);
		}
		pin.set_Stereotype(_terminalMap.getStereotype());
		pin.set_Package(_terminalMap.getPackage());
		pin.set_InstanceName(_terminalMap.getRfdId());
		return pin;
	}
	
	public void add_deviceNetwork(MOClass _component)
	{
		this.powsys.add_Component(_component);;
	}
	
	public MOClass create_LoadComponent(PwLoadPQMap _mapEnergyC)
	{
		//TODO: Create MOClass for Line with its 1 Terminal
		MOClass pwLoad= new MOClass(_mapEnergyC.getName());
		ArrayList<MapAttribute> mapAttList= 
				(ArrayList<MapAttribute>)_mapEnergyC.getMapAttribute();
		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
		imapAttList= mapAttList.iterator();
		MapAttribute current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (!current.getCimName().equals("Terminal")){
				MOAttribute param= new MOAttribute();
				param.set_Name(current.getMoName());
				param.set_Value(current.getContent());
				param.set_Variability(current.getVariability());
				param.set_Visibility(current.getVisibility());
				param.set_Flow(Boolean.valueOf(current.getFlow()));
				pwLoad.add_Attribute(param);
			}
		}
		pwLoad.set_Stereotype(_mapEnergyC.getStereotype());
		pwLoad.set_Package(_mapEnergyC.getPackage());
		pwLoad.set_InstanceName(_mapEnergyC.getRfdId());
		
		return pwLoad;
	}
	
	public MOClass create_LineComponent(PwLineMap _mapACLine)
	{
		MOClass pwline= new MOClass(_mapACLine.getName());
		ArrayList<MapAttribute> mapAttList= 
				(ArrayList<MapAttribute>)_mapACLine.getMapAttribute();
		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
		MapAttribute current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (!current.getCimName().equals("Terminal")){
			MOAttribute param= new MOAttribute();
				param.set_Name(current.getMoName());
				param.set_Value(current.getContent());
				param.set_Variability(current.getVariability());
				param.set_Visibility(current.getVisibility());
				param.set_Flow(Boolean.valueOf(current.getFlow()));
				pwline.add_Attribute(param);
			}
		}
		pwline.set_Stereotype(_mapACLine.getStereotype());
		pwline.set_Package(_mapACLine.getPackage());
		pwline.set_InstanceName(_mapACLine.getRfdId());
		
		return pwline;
	}
}
