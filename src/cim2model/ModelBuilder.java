package cim2model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import cim2model.model.modelica.*;
import cim2model.mapping.modelica.*;

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
				MOAttribute param= new MOAttribute();
				param.set_Name(current.getMoName());
				param.set_Value(current.getContent());
				param.set_Variability(current.getVariability());
				param.set_Visibility(current.getVisibility());
				param.set_Flow(Boolean.valueOf(current.getFlow()));
				pin.set_Attribute(param);
			}
		}
		pin.set_Stereotype(_terminalMap.getStereotype());
		pin.set_Package(_terminalMap.getPackage());
		
		return pin;
	}
	
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
	
	public MOClass create_LoadComponent(PwLoadPQMap _mapEnergyC)
	{
		MOClass pwLoad= new MOClass(_mapEnergyC.getName());
		ArrayList<MapAttribute> mapAttList= 
				(ArrayList<MapAttribute>)_mapEnergyC.getMapAttribute();
		Iterator<MapAttribute> imapAttList= mapAttList.iterator();
		imapAttList= mapAttList.iterator();
		MapAttribute current;
		while (imapAttList.hasNext()) {
			current= imapAttList.next();
			if (current.getCimName().equals("IdentifiedObject.name")){
				pwLoad.set_InstanceName(current.getContent());
			}
			else{
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
			if (current.getCimName().equals("IdentifiedObject.name")){
				pwline.set_InstanceName(current.getContent());
			}
			else{
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
		
		return pwline;
	}
	
	public MOClass create_BusComponent(PwBusMap _mapTopoNode)
	{
		MOClass pwbus= new MOClass(_mapTopoNode.getName());
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
				MOAttribute param= new MOAttribute();
				param.set_Name(current.getMoName());
				param.set_Value(current.getContent());
				param.set_Variability(current.getVariability());
				param.set_Visibility(current.getVisibility());
				param.set_Flow(Boolean.valueOf(current.getFlow()));
				pwbus.add_Attribute(param);
			}
		}
		pwbus.set_Stereotype(_mapTopoNode.getStereotype());
		pwbus.set_Package(_mapTopoNode.getPackage());
		
		return pwbus;
	}
	
	public void connect_Components()
	{
		System.out.println(this.powsys.to_ModelicaClass());
		MOClass component, componentConnectat;
		MOConnector pin, pinConnectat;
		boolean trobat;
		MOConnect conexio;
		//recorregut components de la xarxa
		Iterator<MOClass> iComponents= this.powsys.get_Components().iterator();
		Iterator<MOClass> iComponentConnectat;
		Iterator<MOConnector> iPin, iPinConnectat; 
		while (iComponents.hasNext())
		{
			/* retrieve first component to connect */
			component= iComponents.next(); 
			System.out.println("component 1 "+ component.get_InstanceName());
			iPin= component.get_Terminals().iterator();
			while (iPin.hasNext())
			{
				/* retrieve pin of the first component */
				pin= iPin.next();
				System.out.println("pin 1 "+ pin.get_InstanceName());
				iComponentConnectat= this.powsys.get_Components().iterator();
				while (iComponentConnectat.hasNext())
				{
					/* retrieve the second component to connect */
					componentConnectat= iComponentConnectat.next();
					System.out.println("component 2 "+ componentConnectat.get_InstanceName());
					/* if first component is different to the second component */
					if (!component.get_InstanceName().equals(componentConnectat.get_InstanceName()))
					{
						/* retrieve the pins of the second component */
						iPinConnectat= componentConnectat.get_Terminals().iterator();
						do
						{//si hay buses, comprovar las connexiones componente bus, no componente componente
							pinConnectat= iPinConnectat.next();
							System.out.println("pin 2 "+ pin.get_InstanceName());
							trobat= pin.get_InstanceName().equals(pinConnectat.get_InstanceName());
							/* check wheather pin names are equal or not */
						}
						while (!trobat && iPinConnectat.hasNext());
						if (trobat){ /*if pin name are equal, connect */
							conexio= new MOConnect(component.get_InstanceName(),pin.get_InstanceName(),
									componentConnectat.get_InstanceName(), pinConnectat.get_InstanceName());
							if (!this.powsys.exist_Connection(conexio))
								this.powsys.add_Connection(conexio);
						}
					}
				}
			}
		}
		System.out.println(this.powsys.to_ModelicaClass());
	}
}
