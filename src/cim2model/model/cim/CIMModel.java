package cim2model.model.cim;

import java.util.HashMap;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class CIMModel {
	
//	private String id;
	private Map<String, Object> attribute;
	private Map<Resource, RDFNode> component;
	private Model rdfModel;

	/**
	 * 
	 */
	public CIMModel()
	{
//		id= "";
		component= new HashMap<Resource, RDFNode>();
	}
	
	/**
	 * 
	 * @param _model
	 */
	public CIMModel(Model _model)
	{
		this.rdfModel= _model;
//		id= "";
		attribute= new HashMap<String, Object>();
		component= new HashMap<Resource, RDFNode>();
	}
	
	/**
	 * 
	 * @return Hashmap containing Component ID (Subject), CIM name for the Component (Object): URL#Class
	 */
	public Map<Resource,RDFNode> gatherComponents()
	{
		final StmtIterator stmtiter = this.rdfModel.listStatements();
		while( stmtiter.hasNext() ) 
		{
			Statement stmt= stmtiter.next();
		    Resource s = stmt.getSubject();
            Resource p = stmt.getPredicate();
            RDFNode o = stmt.getObject();
            //p as "type" means that the statment is refering to the component
            if (p.isURIResource() && p.getLocalName().equals("type"))
            {
            	this.component.put(s, o);
//            	System.out.println("Subject : "+ s.getLocalName());
//            	System.out.println("Predicate : "+ p.getLocalName());
//            	System.out.print("Object : "+ o.toString());
//            	String [] componentName= o.toString().split("#");
//            	System.out.println(componentName[0]+ " : "+ componentName[1]);
            }
		}
		
		return this.component;
		//post: Hashtable with cim id of the class (key) and the rdf name of the cim component (value)
	}
	
	/**
	 * 
	 * @param _subject
	 * @return Array containing Component ID, Component/Class name
	 */
	public String [] retrieveComponentName(Resource _subject)
	{
		RDFNode aux;
		
		aux= this.component.get(_subject);
		String [] object= aux.toString().split("#");
		
		return new String [] {_subject.getLocalName(), object[1]};
	}
	
	/**
	 * 
	 * @param _subject
	 * @return Hashmap containing Complete name of attribute (Class.Attribute), value of the attribute
	 * The value can be either a string or number, or the CIM ID of a component: URL#ComponentID
	 */
	public void terminalAttributes(Resource _subject)
	{
		System.out.println("Attributes");
		System.out.println(_subject.toString());
		StmtIterator statements= _subject.listProperties();
		while( statements.hasNext() ) 
		{
		    Statement stmt= statements.next();
			System.out.println("Statement -> "+ stmt);
			System.out.println("Predicate -> "+ stmt.getPredicate());
			System.out.println("Attribute -> "+ stmt.getPredicate().getLocalName()); //name of the variable
			System.out.println("Value -> "+ stmt.getAlt()); //value of the variable as String
			this.attribute.put(stmt.getPredicate().getLocalName(), stmt.getAlt());
//			this.component.put(stmt.getSubject(), stmt.getObject());
		}
	}
	
	/**
	 * 
	 * @param _subject
	 * @return Hashmap containing Complete name of attribute (Class.Attribute), value of the attribute
	 * The value can be either a string or number, or the CIM ID of a component: URL#ComponentID
	 */
	public Map<String, Object> retrieveAttributes(Resource _subject)
	{
//		System.out.println("Attributes");
//		System.out.println(_subject.toString());
		StmtIterator statements= _subject.listProperties();
		attribute= new HashMap<String, Object>();
		while( statements.hasNext() ) 
		{
		    Statement stmt= statements.next();
//			System.out.println("Statement -> "+ stmt);
//			System.out.println("Predicate -> "+ stmt.getPredicate());
//			System.out.println("Attribute -> "+ stmt.getPredicate().getLocalName()); //name of the variable
//			System.out.println("Value -> "+ stmt.getAlt()); //value of the variable as String
//			System.out.println("Literal -> "+ stmt.getAlt().isLiteral());
//			System.out.println("URIResource -> "+ stmt.getAlt().isURIResource());
			if (stmt.getAlt().isLiteral())
			{
				System.out.println("Literal Value -> "+ stmt.getLiteral().getValue());
				this.attribute.put(stmt.getPredicate().getLocalName(), stmt.getLiteral().getValue());
			}
			if (stmt.getAlt().isURIResource())
			{
				//TODO: extract references to other classes ? Another method
				System.out.println("URIResource Value -> "+ stmt.getPredicate());
//				if (!stmt.getPredicate().getLocalName().equals("type"))
//					this.retrieveAttributesTerminal(stmt.getResource());
//				this.attribute.put(stmt.getPredicate().getLocalName(), stmt.getAlt().getLocalName());
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
	public Map<String,Object> retrieveAttributesTerminal(Resource _subject)
	{ 
		Statement terminalAttribute, topoNodeAttribute, svPFAttribute, svVoltAttribute;
		
		System.out.print("Terminal id ");
		System.out.println(_subject.toString());
		StmtIterator terminalAttributes= _subject.listProperties();
		
		while( terminalAttributes.hasNext() ) 
		{
			terminalAttribute= terminalAttributes.next();
//			System.out.println("ME CAGUEN TU PUTA MADRE ");
//			System.out.println("Att_Subject -> "+ terminalAttribute.getSubject());
//			System.out.println("Att_Predicate -> "+ terminalAttribute.getPredicate());
//			System.out.println("Att_Object -> "+ terminalAttribute.getObject());
//			System.out.println("Att_Attribute -> "+ terminalAttribute.getPredicate().getLocalName()); //name of the variable
//			System.out.println("Att_Value -> "+ terminalAttribute.getAlt()); //value of the variable as String
//			predicate and object has the values to retrieve the next component
			if ( terminalAttribute.getPredicate().getLocalName().equals("Terminal.SvPowerFlow"))
			{
				//agafar els valor d'aquest component
				StmtIterator svPowerFlowAtts= terminalAttribute.getAlt().listProperties();
				while( svPowerFlowAtts.hasNext() ) 
				{
					svPFAttribute= svPowerFlowAtts.next();
					if (svPFAttribute.getAlt().isLiteral())
					{
//						System.out.println("1. isLiteral?"+ svPFAttribute.getAlt().isLiteral());
//						System.out.println("1. isLiteral?"+ svPFAttribute.getString());
//						System.out.print(svPFAttribute.getPredicate().getLocalName());
//						System.out.println(svPFAttribute.getString());
						this.attribute.put(svPFAttribute.getPredicate().getLocalName(), svPFAttribute.getString());
					}
				}
				svPowerFlowAtts.close();
			}
			if ( terminalAttribute.getPredicate().getLocalName().equals("Terminal.TopologicalNode") )
			{
				StmtIterator topologicalNodeAtts= terminalAttribute.getAlt().listProperties();
				while( topologicalNodeAtts.hasNext() ) 
				{
					topoNodeAttribute= topologicalNodeAtts.next();
					if (topoNodeAttribute.getPredicate().getLocalName().equals("TopologicalNode.SvVoltage"))
					{
						StmtIterator svVoltageAtts= topoNodeAttribute.getAlt().listProperties();
						while( svVoltageAtts.hasNext() ) 
						{
							svVoltAttribute= svVoltageAtts.next();
							if (svVoltAttribute.getAlt().isLiteral())
							{
//								System.out.println("2. TopologicalNode.SvVoltage.isLiteral?"+  svVoltAttribute.getAlt().toString());
								this.attribute.put(svVoltAttribute.getPredicate().getLocalName(), svVoltAttribute.getAlt().toString());
							}
						}
						svVoltageAtts.close();
					}
					if (topoNodeAttribute.getPredicate().getLocalName().equals("TopologicalNode.BaseVoltage"))
					{
						StmtIterator svVoltageAtts= topoNodeAttribute.getAlt().listProperties();
						while( svVoltageAtts.hasNext() ) 
						{
							svVoltAttribute= svVoltageAtts.next();
							if (svVoltAttribute.getAlt().isLiteral())
							{
//								System.out.println("2. TopologicalNode.BaseVoltage.isLiteral?"+  svVoltAttribute.getAlt().toString());
								this.attribute.put(svVoltAttribute.getPredicate().getLocalName(), svVoltAttribute.getAlt().toString());
							}
						}
						svVoltageAtts.close();
					}
				}
				// Add the rfd_id of the TopologicalNode which Terminal is related to
				String[] id= terminalAttribute.getAlt().toString().split("#");
				this.attribute.put(terminalAttribute.getPredicate().getLocalName(), id[1]);
				topologicalNodeAtts.close();
			}
			if ( terminalAttribute.getPredicate().getLocalName().equals("Terminal.ConductingEquipment"))
			{
				// Add the rfd_id of the CondictingEquipment which Terminal is related to
				String[] id= terminalAttribute.getAlt().toString().split("#");
//				System.out.println("2. getAlt?"+ id[1]);
//				System.out.println("2. Terminal.ConductingEquipment.isLiteral?"+  id[1]);
				this.attribute.put(terminalAttribute.getPredicate().getLocalName(), id[1]);
			}
		}
		    
//		    System.out.println("Statement -> "+ stmt);
//			System.out.println("Predicate -> "+ stmt.getPredicate());
//			System.out.println("Attribute -> "+ stmt.getPredicate().getLocalName()); //name of the variable
////			System.out.println("Value -> "+ stmt.getBag()); //value of the variable as String
//			System.out.println("Value -> "+ stmt.getAlt()); //value of the variable as String
//		System.out.println("isLiteral? -> "+ stmt.getAlt().isLiteral());
//		if (stmt.getAlt().isResource() && !stmt.getAlt().isLiteral())
//		{
//			System.out.println("isResource? -> "+ stmt.getAlt().isResource());
//			String [] onlyOneID= this.retrieveComponentName(stmt.getAlt()); 
//			System.out.println("onlyOneID: "+ onlyOneID[0] + " value: "+ onlyOneID[1]);
//			if (onlyOneID[1].equals("SvPowerFlow"))
//				this.retrieveAttributes(stmt.getAlt());
//			if (onlyOneID[1].equals("TopologicalNode"))
//				this.retrieveAttributes(stmt.getAlt());
//			if (onlyOneID[1].equals("TopologicalNode"))
//				this.retrieveAttributes(stmt.getAlt());
//		}
//		else
//	//		System.out.println("Life sucks!");
//			
//			this.attribute.put(stmt.getPredicate().getLocalName(), stmt.getAlt());
		
		return this.attribute;
	}
	
	public void clearAttributes()
	{
		this.attribute.clear();
		this.component.clear();
	}
}
