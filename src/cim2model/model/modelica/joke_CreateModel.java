package cim2model.model.modelica;

import java.util.ArrayList;

public class joke_CreateModel {

	public static void main(String[] args) 
	{
		/* MAPPING A MODELICA VARIABLE */
		ArrayList<MOVariable> variables = new ArrayList<MOVariable>();
		MOVariable p= new MOVariable();
		MOVariable q= new MOVariable();
		
		p.setFlow(false);
		p.setVariability("parameter");
		p.setName("V");
		p.setDatatype("Real");
		p.setValue(49.5);
		p.setAnnotation("Voltage");
		variables.add(p);
		q.setFlow(true);
		q.setVariability("parameter");
		q.setName("I");
		q.setDatatype("Real");
		q.setValue(0.25);
		q.setFixed(false);
		q.setAnnotation("Current");
		variables.add(q);
//		for(MOVariable item: variables)
//		{
//			System.out.println(item.toModelica());
//		}
		/* MAPPING A MODELICA CLASS: CONNECTOR */
		MOConnector port= new MOConnector("Terminal");
		port.setName("pin");
		port.setAttributes(variables);
		port.setVisibility("public");
		port.setAnnotation("Connector Class");
		System.out.println(port.toModelicaClass());
		System.out.println(port.toModelicaInstance());
		
		/* MAPPING A MODELICA CLASS: COMPONENT or CLASS */
		MOComponent line= new MOComponent("ACLineSegment");
		line.setStereotype("class");
		line.setAnnotation("ACLineSegment");
		MOVariable r= new MOVariable();
		r.setVariability("parameter");
		r.setName("R");
		r.setDatatype("Real");
		r.setValue(0.15);
		r.setAnnotation("Resistance");
		line.setAttribute(r);
		MOVariable x= new MOVariable();
		x.setVariability("parameter");
		x.setName("X");
		x.setDatatype("Real");
		x.setValue(0.05);
		x.setAnnotation("Reactance");
		line.setAttribute(x);
		MOVariable b= new MOVariable();
		b.setVariability("parameter");
		b.setName("B");
		b.setDatatype("Real");
		b.setValue(0.1);
		b.setAnnotation("Susceptance");
		line.setAttribute(b);
		MOVariable g= new MOVariable();
		g.setVariability("parameter");
		g.setName("G");
		g.setDatatype("Real");
		g.setValue(0.25);
		g.setAnnotation("Conductance");
		line.setAttribute(g);
		MOVariable lenght= new MOVariable();
		lenght.setVariability("constant");
		lenght.setName("lenght");
		lenght.setDatatype("Real");
		lenght.setValue(100);
		lenght.setAnnotation("Line lenght");
		line.setAttribute(lenght);
		line.setTerminal(port);
		System.out.println(line.toModelicaClass());
	}

}
