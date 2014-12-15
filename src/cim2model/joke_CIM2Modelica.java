package cim2model;

import cim2model.mapping.Mapping;

public class joke_CIM2Modelica {

	public static void main(String[] args) 
	{
		CodeGenerator.loadCIMModel("cim_modelica_ACLineSegment.xml");
		
		Mapping map= CodeGenerator.FactoryMapping("modelica");
		
		map.createModel("modelica");
	}

}
