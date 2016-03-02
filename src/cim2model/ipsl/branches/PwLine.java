package cim2model.ipsl.branches;

import cim2model.modelica.MOAttribute;
import cim2model.modelica.MOClass;

public class PwLine extends MOClass 
{
	public PwLine(String _name) {
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
		pencil.append("(");
		for (MOAttribute item: this.attributes)
		{
			if (!item.get_Name().equals("length")){
				if (item.get_Variability().equals("parameter")){
					pencil.append(item.get_Name());
					pencil.append("=");
					pencil.append(item.get_Value());
					pencil.append(",");
				}
			}
		}
		pencil.deleteCharAt(pencil.lastIndexOf(","));
		pencil.append(") ");
		pencil.append('"');
		pencil.append(this.annotation);
		pencil.append('"'); pencil.append(";\n");
		code= pencil.toString();
		
		code= pencil.toString();
		
		return code;
	}
}
