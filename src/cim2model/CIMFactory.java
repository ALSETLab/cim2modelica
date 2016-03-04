package cim2model;

import java.util.Map;

import cim2model.cim.*;
import cim2model.modelica.*;
import cim2model.modelica.cimmap.*;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

public class CIMFactory 
{
	public static void main(String[] args) 
	{
		Map<Resource, RDFNode> components;
		String [] cimClassResource;
		String _cimSource= args[0];
		FactoryDesigner cartografo;
		FactoryBuilder constructor;
		
		cartografo= new FactoryDesigner(_cimSource);
		constructor= new FactoryBuilder("CIM_IEE_9Bus");
		components= cartografo.load_CIMModel();
		for (Resource key : components.keySet())
		{	
			cimClassResource= cartografo.get_CIMComponentName(key);
			/* subjectResource[0] is the rfd_id, subjectResource[1] is the CIM name */
			if (cimClassResource[1].equals("Terminal"))
			{
				String [] equipmentResource, topologyResource;
				TerminalMap mapTerminal= cartografo.create_TerminalModelicaMap(
						key, "./res/map/general/cim_modelica_terminal.xml", cimClassResource);
				MOConnector mopin= constructor.create_TerminalConnector(mapTerminal);
				System.out.println(mopin.to_ModelicaClass());
				System.out.println(mopin.to_ModelicaInstance());
				System.out.println("me caguen la puta");
//				equipmentResource= cartografo.get_CIMComponentName(mapTerminal.getConductingEquipmentMap());
//				System.out.println("rfd_id: "+ equipmentResource[0] + " cim name: "+ equipmentResource[1]);
//				topologyResource= cartografo.get_CIMComponentName(mapTerminal.getTopologicalNodeMap());
//				System.out.println("rfd_id: "+ topologyResource[0] + " cim name: "+ topologyResource[1]);
			}
		}
//		constructor.connect_Components(cartografo.get_ConnectionMap());
//		constructor.save_ModelicaFile(constructor.get_Network().to_ModelicaClass());
	}
}
