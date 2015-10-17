package cim2model;

import cim2model.model.modelica.*;
import cim2model.mapping.modelica.*;

public class ModelBuilder 
{
	private MONetwork powsys;
	
	public ModelBuilder(String _network)
	{
		powsys= new MONetwork(_network);
	}
	
	public MOConnector create_PinConnector(PwPinMap _pin)
	{
		return null;
	}
	
	public void add_terminalComponent(MOConnector _pin, Object _component)
	{
		//TODO: use factory class
//		MOAttribute att= new MOAttribute();
//		MOConnector pwpin= new MOConnector(_pin.getName());
//		MOClass pwline= new MOClass(_pin.getName());

//		imapAttList= mapAttList.iterator();
//		while (imapAttList.hasNext()) {
//			System.out.println(imapAttList.next().toString());
//		}
	}
	
	public MOClass create_LoadComponent()
	{
		//TODO: Create MOClass for Line with its 1 Terminal
		//utilizar clases from cim2model.model.modelica
//		MOClass pwLoad= new MOClass(mapEnergyC.getName());
//		imapAttList= mapAttList.iterator();
//		MapAttribute current;
//		while (imapAttList.hasNext()) {
//			current= imapAttList.next();
//			MOAttribute param= new MOAttribute();
//			param.setName(current.getMoName());
//			param.setValue(current.getContent());
//			param.setVariability(current.getVariability());
//			param.setVisibility(current.getVisibility());
//			param.setFlow(Boolean.valueOf(current.getFlow()));
//		}
//		pwLoad.setStereotype(mapEnergyC.getStereotype());
//		pwLoad.setPackage(mapEnergyC.getPackage());
//		System.out.println(pwLoad.toModelicaClass());
//		System.out.println(pwLoad.toModelicaInstance());
		return null;
	}
	
	public MOClass create_LineComponent()
	{
		//TODO: Create MOClass for Line with its 2 Terminals,
		//utilizar clases from cim2model.model.modelica
//		MOClass pwline= new MOClass(mapACLine.getName());
//		imapAttList= mapAttList.iterator();
//		MapAttribute current;
//		while (imapAttList.hasNext()) {
//			current= imapAttList.next();
//			MOAttribute param= new MOAttribute();
//			param.setName(current.getMoName());
//			param.setValue(current.getContent());
//			param.setVariability(current.getVariability());
//			param.setVisibility(current.getVisibility());
//			param.setFlow(Boolean.valueOf(current.getFlow()));
//		}
//		pwline.setStereotype(mapACLine.getStereotype());
//		pwline.setPackage(mapACLine.getPackage());
//		System.out.println(pwline.toModelicaClass());
//		System.out.println(pwline.toModelicaInstance());
		return null;
	}
	
	public void add_deviceComponent(MOClass _component)
	{
		
	}
}
