package cim2model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Element;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.RDFNode;

import cim2model.io.*;
import cim2model.model.CIMModel;
import cim2model.model.MappingModelica;
import cim2model.model.TemplateModelica;

public class ControlTranslation {

	public static void main(String[] args) {
		CIMReaderJENA cimReader;
		CIMModel cim;
		Map<Resource, RDFNode> components;
		Map<String, Object> attributes;
//		TemplateModelica template= null;
//		MappingModelica moMap= null;
		Element etemplate= null;
		
		cimReader= new CIMReaderJENA("C:/Users/fragom/PhD_CIM/CIMv16/SmarTSLab/Components", "Generator.xml");
		cim = new CIMModel(cimReader.readModel());
		components = cim.gatherComponents();
//		template= TemplateModelica.getInstance("C:/Users/fragom/PhD_CIM/JAVA/edu.smartslab.cim2model/res/moMappingTemplate.xml");
		for (Resource key : components.keySet())
		{
//			System.out.println("key: "+ key);
//			moMap= new MappingModelica(components.get(key).toString());
			if (key.getLocalName().equals("_b95009c0-1e1e-11e4-a682-080027008896"))
			{//case of a SynchronousMachine object
				String [] componentName= components.get(key).toString().split("#");
				attributes= cim.retrieveAttributes(key);
				//TODO: load/create the mapping for generator
				//load template
//				etemplate= template.getElementTemplate("root");
//				etemplate.setName(componentName[1]);
//				moMap.setComponent(etemplate);
//				for (String sAtt : attributes.keySet())
//				{
//					etamplate= template.getElementTemplate("cimAtt");
//					etemplate.setName(sAtt);
//					etemplate.getValue()
//					etemplate.setText(attributes.get(sAtt).toString());
//					etamplate= template.getElementTemplate("moAtt");
//					etemplate.setName
//				}
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
