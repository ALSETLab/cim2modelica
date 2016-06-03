package cim2model.cim.map.ipsl.buses;

import java.util.ArrayList;

import cim2model.modelica.MOAttribute;
import cim2model.modelica.MOClass;
import cim2model.modelica.MOConnector;

/**
 * BusExt2 is a model of Terminal (a.k.a. Pin) with specific parameters nu and no. Parameter nu
 * stands for number of input pins and no stands for number of output pins
 * @author fran_jo
 *
 */
public class BusExt2 extends MOClass 
{
	private Integer nu, no;
	
	public BusExt2(String _name){
		super(_name);
		this.nu= this.no= 0;
	}
	
	@Override
	public void add_Terminal(MOConnector pin){
		//TODO when add a terminal, increase number of no or nu , use to_ModelicaClass
		//if terminal name contains T1, nu++, elseif contains T2, no++
		CharSequence pinin= "T1";
		CharSequence pinout= "T2";
		if (pin.get_Name().contains(pinin))
			this.nu+= 1;
		if (pin.get_Name().contains(pinout))
			this.no+= 1;
			
		this.terminals.add(pin);
	}
	
	@Override
	public void add_Terminal(ArrayList<MOConnector> terminals) {
		//TODO count input terminals and output terminals, and increase number of no or nu , use to_ModelicaClass
		this.terminals = terminals;
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
			if (item.get_Variability().equals("parameter")){
				pencil.append(item.get_Name());
				pencil.append("=");
				pencil.append(item.get_Value());
				pencil.append(",");
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
