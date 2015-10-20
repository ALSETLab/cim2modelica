package cim2model;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import cim2model.electrical.branches.PwLine;
import cim2model.io.CIMReaderJENA;
import cim2model.mapping.modelica.*;
import cim2model.model.cim.CIMModel;
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
		constructor= new ModelBuilder("ieee_9bus");
		components= cartografo.load_CIMModel();
		for (Resource key : components.keySet())
		{	
			terminalResource= cartografo.get_CIMComponentName(key);
			/* subjectResource[0] is the rfd_id, subjectResource[1] is the CIM name */
			System.out.println("rfd_id: "+ terminalResource[0] + " cim name: "+ terminalResource[1]);
			if (terminalResource[1].equals("Terminal"))
			{
				System.out.println("I FOUND TERMINAL...");
//				Map.Entry<PwPinMap,Resource> mapa= 
//						cartografo.create_TerminalModelicaMap(key, "./res/cim_iteslalibrary_pwpin.xml", subjectResource);
				ConnectMap conector= 
						cartografo.create_TerminalModelicaMap(key, "./res/cim_iteslalibrary_pwpin.xml", terminalResource);
				PwPinMap mapTerminal= conector.get_TerminalMap();
				System.out.println("TERMINAL COMPLETED!!");
				MOConnector mopin= constructor.create_PinConnector(mapTerminal);
				/* after loading terminal, load the resource connected to it, aka, ConductingEquipment */
				equipmentResource= cartografo.get_CIMComponentName(conector.get_ConductingEquipment());
				topologyResource= cartografo.get_CIMComponentName(conector.get_TopologicalNode());
				System.out.println("rfd_id: "+ equipmentResource[0] + " cim name: "+ equipmentResource[1]);
				System.out.println("rfd_id: "+ topologyResource[0] + " cim name: "+ topologyResource[1]);
				if (equipmentResource[1].equals("EnergyConsumer"))
				{
					System.out.println("I FOUND A LOAD...");
					PwLoadPQMap mapEnergyC= cartografo.create_LoadModelicaMap(conector.get_ConductingEquipment(), 
							"./res/cim_iteslalibrary_pwloadpq.xml", equipmentResource);
					System.out.println("LOAD COMPLETED!!");
					MOClass moload= constructor.create_LoadComponent(mapEnergyC);
					moload.add_Terminal(mopin);
					System.out.println(moload.to_ModelicaClass());
					System.out.println(moload.to_ModelicaInstance());
					//TODO: save this to a file
					constructor.add_deviceNetwork(moload);
				}
				if (equipmentResource[1].equals("ACLineSegment"))
				{
					if (constructor.exist_CurrentEquipment(equipmentResource[0]))
					{/* condition to check if the line already exist in the model, true, add the second terminal */
						MOClass moline= constructor.get_CurrentEquipment();
						moline.add_Terminal(mopin);
						constructor.add_deviceNetwork(moline);
						constructor.set_CurrentEquipment(null, "");
						System.out.println(moline.to_ModelicaClass());
						System.out.println(moline.to_ModelicaInstance());
					}
					else 
					{/* false, create map of the line and add the first terminal */
						System.out.println("I FOUND ACLINESEGMENT...");
						PwLineMap mapACLine= cartografo.create_LineModelicaMap(conector.get_ConductingEquipment(), 
								"./res/cim_iteslalibrary_pwline.xml", equipmentResource);
						System.out.println("LINE COMPLETED!!");
						MOClass moline= constructor.create_LineComponent(mapACLine);
						moline.add_Terminal(mopin);
						//TODO: save this to a file
//						System.out.println(moline.to_ModelicaClass());
//						System.out.println(moline.to_ModelicaInstance());
						constructor.set_CurrentEquipment(moline, equipmentResource[0]);
					}
				}
				if (topologyResource[1].equals("TopologicalNode"))
				{
					if (constructor.exist_CurrentNode(topologyResource[0]))
					{/* condition to check if the line already exist in the model, true, add the second terminal */
						MOClass mobus= constructor.get_CurrentNode();
						mobus.add_Terminal(mopin);
						constructor.add_deviceNetwork(mobus);
						System.out.println(mobus.to_ModelicaClass());
						System.out.println(mobus.to_ModelicaInstance());
					}
					else 
					{/* false, create map of the line and add the first terminal */
						System.out.println("I FOUND TOPOLOGICALNODE...");
						PwBusMap mapTopoNode= cartografo.create_BusModelicaMap(conector.get_TopologicalNode(), 
										"./res/cim_iteslalibrary_pwbus.xml", topologyResource);
						System.out.println("TOPOLOGICALNODE COMPLETED!!");
						MOClass mobus= constructor.create_BusComponent(mapTopoNode);
						mobus.add_Terminal(mopin);
						//TODO: save this to a file
//						System.out.println(mobus.to_ModelicaClass());
//						System.out.println(mobus.to_ModelicaInstance());
						constructor.set_CurrentNode(mobus, topologyResource[0]);
					}
				}	
			}
			
		}
		
		//
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
