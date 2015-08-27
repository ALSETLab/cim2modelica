package cim2model;

import cim2model.mapping.*;
import cim2model.model.modelica.ModelicaFactory;
import cim2model.model.modelica.ModelicaModel;

public class joke_makeModel {
	
	public static void main(String[] args) 
	{
		
		ModelicaFactory mofactory= ModelicaFactory.get_Factory("");
		ModelicaModel classe= ModelicaFactory.make_Class("pwline");
		classe.from_XMLMapping();
		ModelicaModel connector= ModelicaFactory.make_Class("pwpin");
		connector.from_XMLMapping();
		
		//TODO: Crear PwPin
		//TODO: Add PwPin (x2) in PwLine
		//TODO: Repeat ToDo 2, 3 for Load, Bus, Generator
		//TODO: Connect components
		
		
//		Mapping map= FactoryMapping.loadMapping("modelica");
//		CodeGenerator codeGen= new CodeGenerator();
//		codeGen.loadCIMModel("ACLineSegment.xml", map);
		
//		Mapping map= FactoryMapping.loadMapping("modelica");
//		map.loadMapping("cim_modelica_class.xml");
		
//		codeGen.storeCIMValue(((ModMapping)map).getXmlModelName()); 
//		ModelicaModel connector= map.createModel(ModelStereotype.CONNECTOR);
//		
//		ModelicaModel component= map.createModel(ModelStereotype.CLASS);
	}

}
