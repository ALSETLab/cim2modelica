package cim2model;

import cim2model.mapping.FactoryMapping;
import cim2model.mapping.*;
import cim2model.model.modelica.ModelicaModel;

public class joke_CIM2Modelica {

	public static void main(String[] args) 
	{
		Mapping map= FactoryMapping.loadMapping("modelica");
		CodeGenerator codeGen= new CodeGenerator();
		codeGen.loadCIMModel("ACLineSegment.xml", map);
		
//		Mapping map= FactoryMapping.loadMapping("modelica");
//		map.loadMapping("cim_modelica_class.xml");
		
//		codeGen.storeCIMValue(((ModMapping)map).getXmlModelName()); 
//		ModelicaModel connector= map.createModel(ModelStereotype.CONNECTOR);
//		
//		ModelicaModel component= map.createModel(ModelStereotype.CLASS);
	}

}
