package cim2model;

import java.util.Map;

import cim2model.cim.*;
import cim2model.ipsl.cimmap.PwBusMap;
import cim2model.modelica.*;
import cim2model.modelica.cimmap.*;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

public class BetaFactoryCIMOD 
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
//				System.out.println(mopin.to_ModelicaInstance());
//				System.out.println("me caguen la puta");
				TopologyMap lastNodeAdded= cartografo.get_ConnectionMap().get(cartografo.get_ConnectionMap().size()- 1);
				equipmentResource= cartografo.get_CIMComponentName(lastNodeAdded.get_Ce_id());
				System.out.println("rfd_id: "+ equipmentResource[0] + " cim name: "+ equipmentResource[1]);
				topologyResource= cartografo.get_CIMComponentName(lastNodeAdded.get_Tn_id());
				System.out.println("rfd_id: "+ topologyResource[0] + " cim name: "+ topologyResource[1]);
				/* According to CIM Composer, ACLine has 2 terminals */
				if (equipmentResource[1].equals("ACLineSegment"))
				{
					MOClass moline= constructor.get_equipmentNetwork(equipmentResource[0]);
					if (moline!= null){/* condition to check if the line already exist in the model, true, add the second terminal */
						moline.add_Terminal(mopin);
						System.out.println(moline.to_ModelicaClass());
						System.out.println(moline.to_ModelicaInstance());
					}
					else 
					{/* false, create map of the line and add the first terminal */
//						System.out.println("rfd_id: "+ equipmentResource[0] + " cim name: "+ equipmentResource[1]);
						ACLineSegmentMap mapACLine= cartografo.create_LineModelicaMap(lastNodeAdded.get_Ce_id(), 
								"./res/map/general/cim_modelica_aclinesegment.xml", equipmentResource);
						moline= constructor.create_ACLineComponent(mapACLine);
						moline.add_Terminal(mopin);
						constructor.add_deviceNetwork(moline);
					}
				}
				/* According to CIM Composer, TN has 1..N terminals */
				if (topologyResource[1].equals("TopologicalNode"))
				{
					MOClass mobus= constructor.get_equipmentNetwork(topologyResource[0]);
					if (mobus!= null){
						/* condition to check if the line already exist in the model, true, add the second terminal */
						mobus.add_Terminal(mopin);
//						System.out.println(mobus.to_ModelicaClass());
//						System.out.println(mobus.to_ModelicaInstance());
					}
					else
					{/* false, create map of the line and add the first terminal */
//						System.out.println("rfd_id: "+ topologyResource[0] + " cim name: "+ topologyResource[1]);
						TopologicalNodeMap mapTopoNode= cartografo.create_LineModelicaMap(lastNodeAdded.get_Tn_id(), 
								"./res/map/general/cim_modelica_busbar.xml", topologyResource);
						mobus= constructor.create_BusComponent(mapTopoNode);
						mobus.add_Terminal(mopin);
						constructor.add_deviceNetwork(mobus);
					}
				}
			}
		}
//		constructor.connect_Components(cartografo.get_ConnectionMap());
//		constructor.save_ModelicaFile(constructor.get_Network().to_ModelicaClass());
	}
}
