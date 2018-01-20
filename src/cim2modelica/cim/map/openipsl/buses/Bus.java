package cim2modelica.cim.map.openipsl.buses;

import cim2modelica.modelica.MOClass;

public class Bus extends MOClass 
{
	public Bus(String _name){
		super(_name);
	}
	
	@Override
	public String to_ModelicaInstance()
	{
		String code= "";
		StringBuilder pencil= new StringBuilder();
		
		if (!this.visibility.equals("public"))
		{
			pencil.append(this.visibility); 
			pencil.append(" ");
		}
//		pencil.append(this.variability);
//		pencil.append(" ");
		pencil.append(this.pakage);
		pencil.append(".");
		pencil.append(this.name);
		pencil.append(" ");
		pencil.append(this.instanceName);
		pencil.append(" ");
		pencil.append('"');
		pencil.append(this.comment);
		pencil.append('"'); 
		pencil.append(" ");
		pencil.append(this.annotation);
		pencil.append(";\n");
		code= pencil.toString();
		
		code= pencil.toString();
		
		return code;
	}
}
