package cim2model;

import java.util.Map;

import cim2model.cim.*;
import cim2model.ipsl.buses.Bus;
import cim2model.ipsl.cimmap.*;
import cim2model.modelica.*;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

public class CIMFactory 
{
	public static void main(String[] args) 
	{
		Map<Resource, RDFNode> components;
		String [] cimClassResource;
		String _cimSource= args[0];
		ModelDesigner cartografo;
		ModelBuilder constructor;
		
		cartografo= new ModelDesigner(_cimSource);
		constructor= new ModelBuilder("CIM_IEE_9Bus");
		components= cartografo.load_CIMModel();
		for (Resource key : components.keySet())
		{	
			cimClassResource= cartografo.get_CIMComponentName(key);
			/* subjectResource[0] is the rfd_id, subjectResource[1] is the CIM name */
			if (cimClassResource[1].equals("Terminal"))
			{
				
			}
		}
		constructor.connect_Components(cartografo.get_ConnectionMap());
		constructor.save_ModelicaFile(constructor.get_Network().to_ModelicaClass());
	}
}
