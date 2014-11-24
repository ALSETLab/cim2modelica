package cim2model.model.modelica;

import java.util.ArrayList;

public class joke_ModVariable {

	public static void main(String[] args) 
	{
		ArrayList<MOVariable> variables = new ArrayList<MOVariable>();
		MOVariable var1= new MOVariable();
		MOVariable var2= new MOVariable();
		
		var2.setFlow(false);
		var1.setVariability("parameter");
		var1.setName("voltage");
		var1.setDatatype("Real");
		var1.setValue(120);
		var1.setAnnotation("Voltage Magnitude");
		variables.add(var1);
		
		var2.setFlow(true);
		var2.setVariability("variable");
		var2.setName("current");
		var2.setDatatype("Real");
		var2.setValue(6);
		var2.setFixed(false);
		var2.setAnnotation("Current Magnitude");
		variables.add(var2);
		
		for(MOVariable item: variables)
		{
			System.out.println(item.toModelica());
		}
	}

}
