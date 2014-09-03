package cim2model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.RDFNode;

import cim2model.io.*;
import cim2model.model.CIMModel;

public class ControlTranslation {

	public static void main(String[] args) {
		CIMReaderJENA cimReader;
		CIMModel cim;
		Map<Resource, RDFNode> components;
		Map<String, Object> attributes;
		
		cimReader= new CIMReaderJENA("C:/Users/fragom/PhD_CIM/CIMv16/SmarTSLab/Components", "Generator.xml");
		cim = new CIMModel(cimReader.readModel());
		components = cim.gatherComponents();
		for (Resource key : components.keySet())
		{
			if (key.getLocalName().equals("_6501d560-074e-11e4-aa24-080027008896"))
			{
				String component= key.getLocalName();
				attributes= cim.retrieveAttributes(key);
				//TODO: load/create the mapping for generator
				
				//TODO: generate the modelica code with starting values
			}
			else
				System.out.println("Not a SynchronousMachine");
		}
		
		/**
		 * JDOM Example
		 */
//		CIMReaderJDOM cimlector= new CIMReaderJDOM("C:/Users/fragom/PhD_CIM/CIMv16/SmarTSLab/Components",
//				"Generator.xml");
//		System.out.println(cimlector.printRoot());
//		
//		CIMModel modcim= new CIMModel();
//		//TODO: Save info in CIMModel object
//		List<Element> alist = cimlector.getModelComponents();
//		for (int i = 0; i < alist.size(); i++) 
//		{//component
//			Element node = (Element) alist.get(i);
//			System.out.println("Element: "+ node.getName());
//			modcim.setComponent(node.getName(), "");
//			System.out.println("rfd:ID: "+ cimlector.getComponentId(node));
//			List<Element> clist = cimlector.getComponentParameters(node);
//			for (int j = 0; j < clist.size(); j++) 
//			{//attribute
//				Element child = (Element) clist.get(j);
//				System.out.println("Child: "+ child.getName());
//				System.out.println("Child value: "+ child.getValue());
//			}
//		}
	}
}
