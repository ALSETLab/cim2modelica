package cim2model;

import java.util.Map;

import cim2model.mapping.modelica.*;
import cim2model.model.cim.CIMTerminal;
import cim2model.model.modelica.*;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

public class joke_makeModel {
	
	private static final String RDF_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private static final String CIM16_NS = "http://iec.ch/TC57/2013/CIM-schema-cim16#";
    
    private static final String MD_NS= "http://iec.ch/TC57/61970-552/ModelDescription/1#";
    
    private static final String ENTSOE_NS= "http://entsoe.eu/Secretariat/2013/ProfileExtension/3#";
	
	public static void main(String[] args) 
	{
		Map<Resource, RDFNode> components;
		String [] terminalResource, equipmentResource, topologyResource;
		String _cimSource= args[0];
		ModelDesigner cartografo;
		ModelBuilder constructor;
		
		cartografo= new ModelDesigner(_cimSource);
		constructor= new ModelBuilder("IEEE_9Bus");
		components= cartografo.load_CIMModel();
		for (Resource key : components.keySet())
		{	
			terminalResource= cartografo.get_CIMComponentName(key);
			/* subjectResource[0] is the rfd_id, subjectResource[1] is the CIM name */
			
			if (terminalResource[1].equals("Terminal"))
			{
				System.out.println("rfd_id: "+ terminalResource[0] + " cim name: "+ terminalResource[1]);
				CIMTerminal conector= 
						cartografo.create_TerminalModelicaMap(key, "./res/cim_iteslalibrary_pwpin.xml", terminalResource);
				PwPinMap mapTerminal= conector.get_TerminalMap();
				MOConnector mopin= constructor.create_PinConnector(mapTerminal);
				/* after loading terminal, load the resource connected to it, aka, ConductingEquipment */
				equipmentResource= cartografo.get_CIMComponentName(conector.get_ConductingEquipment());
				topologyResource= cartografo.get_CIMComponentName(conector.get_TopologicalNode());
				//According to CIM Composer, EC has one terminal
				if (equipmentResource[1].equals("SynchronousMachine"))
				{
					System.out.println("rfd_id: "+ equipmentResource[0] + " cim name: "+ equipmentResource[1]);
					GENSALMap mapSyncMach= cartografo.create_MachineModelicaMap(conector.get_ConductingEquipment(), 
							"./res/cim_iteslalibrary_gensal.xml", equipmentResource);
					MOClass momachine= constructor.create_MachineComponent(mapSyncMach);
					momachine.add_Terminal(mopin);
//					System.out.println(moload.to_ModelicaClass());
					System.out.println(momachine.to_ModelicaInstance());
					//TODO: save this to a file
					constructor.add_deviceNetwork(momachine);
				}
				//According to CIM Composer, EC has one terminal
				if (equipmentResource[1].equals("EnergyConsumer"))
				{
					System.out.println("rfd_id: "+ equipmentResource[0] + " cim name: "+ equipmentResource[1]);
					PwLoadPQMap mapEnergyC= cartografo.create_LoadModelicaMap(conector.get_ConductingEquipment(), 
							"./res/cim_iteslalibrary_pwloadpq.xml", equipmentResource);
					MOClass moload= constructor.create_LoadComponent(mapEnergyC);
					moload.add_Terminal(mopin);
//					System.out.println(moload.to_ModelicaClass());
					System.out.println(moload.to_ModelicaInstance());
					//TODO: save this to a file
					constructor.add_deviceNetwork(moload);
				}
				//According to CIM Composer, ACLineSegment has two terminals
				if (equipmentResource[1].equals("ACLineSegment"))
				{
					MOClass moline= constructor.get_equipmentNetwork(equipmentResource[0]);
					if (moline!= null){
						/* condition to check if the line already exist in the model, true, add the second terminal */
						moline.add_Terminal(mopin);
					}
					else 
					{/* false, create map of the line and add the first terminal */
						System.out.println("rfd_id: "+ equipmentResource[0] + " cim name: "+ equipmentResource[1]);
						PwLineMap mapACLine= cartografo.create_LineModelicaMap(conector.get_ConductingEquipment(), 
								"./res/cim_iteslalibrary_pwline.xml", equipmentResource);
						moline= constructor.create_LineComponent(mapACLine);
						moline.add_Terminal(mopin);
						constructor.add_deviceNetwork(moline);
					}
				}
				//According to CIM Composer, TN has 1..N terminals
				if (topologyResource[1].equals("TopologicalNode"))
				{//TODO change type of bus for mapping, use BusExt2 
					MOClass mobus= constructor.get_equipmentNetwork(topologyResource[0]);
					if (mobus!= null){
						/* condition to check if the line already exist in the model, true, add the second terminal */
						mobus.add_Terminal(mopin);
					}
					else
					{
						/* false, create map of the line and add the first terminal */
						System.out.println("rfd_id: "+ topologyResource[0] + " cim name: "+ topologyResource[1]);
						PwBusMap mapTopoNode= cartografo.create_BusModelicaMap(conector.get_TopologicalNode(), 
										"./res/cim_iteslalibrary_pwbus.xml", topologyResource);
						mobus= constructor.create_BusComponent(mapTopoNode);
						mobus.add_Terminal(mopin);
						//TODO: save this to a file
						constructor.add_deviceNetwork(mobus);
					}
				}	
			}
		}
		constructor.connect_Components(cartografo.get_ConnectionMap());
	}
}
