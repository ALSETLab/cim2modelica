package cim2model.cim;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

public class TPProfileModel {
	
//	private String id;
	private Map<String, Object> attribute;
	private Map<Resource, RDFNode> topologicalNodes;
	private Map<Resource, RDFNode> terminals;
	private Model rdfModel;

	/**
	 * 
	 */
	public TPProfileModel()
	{
		topologicalNodes= new HashMap<Resource, RDFNode>();
		terminals= new HashMap<Resource, RDFNode>();
	}
	/**
	 * 
	 * @param _model
	 */
	public TPProfileModel(Model _model)
	{
		this.rdfModel= _model;
		topologicalNodes= new HashMap<Resource, RDFNode>();
		terminals= new HashMap<Resource, RDFNode>();
	}
	
	/**
	 * 
	 * @return Hashmap containing Component ID (Subject), CIM name for the Component (Object): URL#Class
	 */
	public Map<Resource,RDFNode> gatherTopologicalNodes()
	{
		final StmtIterator stmtiter = this.rdfModel.listStatements();
		while( stmtiter.hasNext() ) 
		{
			Statement stmt= stmtiter.next();
		    Resource s = stmt.getSubject();
            Resource p = stmt.getPredicate();
            RDFNode o = stmt.getObject();
            //p as "type" means that the statment is refering to the component
            if (p.isURIResource() && p.getLocalName().equals("type")) //TODO && otra condicion
            {
            	this.topologicalNodes.put(s, o);
//            	System.out.println("Subject : "+ s.getLocalName());
//            	System.out.println("Predicate : "+ p.getLocalName());
//            	System.out.print("Object : "+ o.toString());
//            	String [] componentName= o.toString().split("#");
//            	System.out.println(componentName[0]+ " : "+ componentName[1]);
            }
		}
		
		return this.topologicalNodes;
		//post: Hashtable with cim id of the class (key) and the rdf name of the cim component (value)
	}
	
	/**
	 * 
	 * @return Hashmap containing Component ID (Subject), CIM name for the Component (Object): URL#Class
	 */
	public Map<Resource,RDFNode> gatherTerminals()
	{
		final StmtIterator stmtiter = this.rdfModel.listStatements();
		while( stmtiter.hasNext() ) 
		{
			Statement stmt= stmtiter.next();
		    Resource s = stmt.getSubject();
            Resource p = stmt.getPredicate();
            RDFNode o = stmt.getObject();
            //p as "type" means that the statment is refering to the component
            if (p.isURIResource() && p.getLocalName().equals("type")) //TODO && otra condicion
            {
            	this.topologicalNodes.put(s, o);
//            	System.out.println("Subject : "+ s.getLocalName());
//            	System.out.println("Predicate : "+ p.getLocalName());
//            	System.out.print("Object : "+ o.toString());
//            	String [] componentName= o.toString().split("#");
//            	System.out.println(componentName[0]+ " : "+ componentName[1]);
            }
		}
		
		return this.topologicalNodes;
		//post: Hashtable with cim id of the class (key) and the rdf name of the cim component (value)
	}
	
//	/**
//	 * 
//	 * @param _subject
//	 * @return Array containing Component ID, Component/Class name
//	 */
//	public String [] retrieveComponentName(Resource _subject)
//	{
//		RDFNode aux;
//		
//		aux= this.svpowerflows.get(_subject);
//		String [] object= aux.toString().split("#");
//		
//		return new String [] {_subject.getLocalName(), object[1]};
//	}
	
	
	/**
	 * Look for power flow values P,Q from the corresponding SvPowerFlow instance, 
	 * where its SvPowerFlow.Terminal rdf:resource matches the Terminal Id
	 * cim_name="SvPowerFlow.p" 
	 * cim_name="SvPowerFlow.q"
	 * @param _subject: Terminal Id
	 * @return
	 */
	public Map<String,Object> retrieveAttributesTerminal(String[] _subjectID)
	{ 
		//TODO reimplement the method, looking the subject into the SvPowerFlow.Terminal rdf:resource attribute of the SvPowerFlow classes
		//us this.components
		final StmtIterator stmtiter = this.rdfModel.listStatements();
		Statement attributesSvPowerFlow;
		
		while( stmtiter.hasNext() ) 
		{
			Statement tagSvPowerFlow= stmtiter.next();
		    Resource s = tagSvPowerFlow.getSubject();
            Resource p = tagSvPowerFlow.getPredicate();
            RDFNode o = tagSvPowerFlow.getObject();
            //p as "type" means that the statment is refering to the component
            if (o.isURIResource())
            {
	        	System.out.println("Subject : "+ s.getLocalName());
	        	System.out.println("Predicate : "+ p.getLocalName());
	        	System.out.print("Object : "+ o.toString());
	        	String [] componentName= o.toString().split("#");
	        	System.out.println(componentName[0]+ " : "+ componentName[1]);
	        	if (componentName[1].equals(_subjectID[0]))
	        	{
	        		StmtIterator svPowerFlowAtts= tagSvPowerFlow.getAlt().listProperties();
					while( svPowerFlowAtts.hasNext() ) 
					{
						attributesSvPowerFlow= svPowerFlowAtts.next();
						if (attributesSvPowerFlow.getAlt().isLiteral())
						{
							System.out.print("Attribute: "+ attributesSvPowerFlow.getPredicate().getLocalName());
							System.out.print("Value: "+ attributesSvPowerFlow.getString());
							this.attribute.put(attributesSvPowerFlow.getPredicate().getLocalName(), attributesSvPowerFlow.getString());
						}
					}
	        	}	
            }
		}
		
		return this.attribute;
	}
	
	
	/**
	 * cim_name="SvVoltage.angle" 
	 * cim_name="SvVoltage.v" 
	 * cim_name="SvPowerFlow.p" 
	 * cim_name="SvPowerFlow.q" 
	 * cim_name="BaseVoltage.nominalVoltage"
	 * cim_name="BasePower.basePower"
	 * @param _subject
	 * @return
	 */
	public Map<String,Object> retrieveAttributesTopoNode(Resource _subject)
	{ 
		Statement attributeClass, classAttribute;
		
		StmtIterator iAttributes= _subject.listProperties();
		while( iAttributes.hasNext() ) 
		{
			attributeClass= iAttributes.next();
			if (attributeClass.getAlt().isLiteral())
			{
				this.attribute.put(attributeClass.getPredicate().getLocalName(), attributeClass.getLiteral().getValue());
			}
			if (attributeClass.getAlt().isURIResource())
			{
				if ( attributeClass.getPredicate().getLocalName().equals("TopologicalNode.SvVoltage"))
				{
					//agafar els valor d'aquest component
					StmtIterator svPowerFlowAtts= attributeClass.getAlt().listProperties();
					while( svPowerFlowAtts.hasNext() ) 
					{
						classAttribute= svPowerFlowAtts.next();
						if (classAttribute.getAlt().isLiteral())
						{
							this.attribute.put(classAttribute.getPredicate().getLocalName(), classAttribute.getString());
						}
					}
					svPowerFlowAtts.close();
				}
				if ( attributeClass.getPredicate().getLocalName().equals("TopologicalNode.BaseVoltage"))
				{
					StmtIterator svPowerFlowAtts= attributeClass.getAlt().listProperties();
					while( svPowerFlowAtts.hasNext() ) 
					{
						classAttribute= svPowerFlowAtts.next();
						if (classAttribute.getAlt().isLiteral())
						{
							this.attribute.put(classAttribute.getPredicate().getLocalName(), classAttribute.getString());
						}
					}
					svPowerFlowAtts.close();
				}
			}
		}
		return this.attribute;
	}
	
	public void clearAttributes()
	{
		this.attribute.clear();
	}
}
