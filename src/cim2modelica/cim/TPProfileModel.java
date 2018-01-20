package cim2modelica.cim;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

public class TPProfileModel extends CIMProfile {
	//TODO use of JENA Properties for finding tags (see EQProfileModel:gather_BasePower_Attributes(...))
//	private String id;
	private Map<Resource, RDFNode> topologicalNodes;
	private Map<Resource, RDFNode> terminals;

	/**
	 * 
	 * @param _model
	 */
	public TPProfileModel(String _source_SV_profile)
	{
		super(_source_SV_profile);
		topologicalNodes= new HashMap<Resource, RDFNode>();
		terminals= new HashMap<Resource, RDFNode>();
	}
	
	/**
	 * 
	 * @param _subject
	 * @return Array containing Component ID, Component/Class name
	 */
	public String [] get_ComponentName(Resource _subject)
	{
		RDFNode aux;
		
		aux= this.topologicalNodes.get(_subject);
		String [] object= aux.toString().split("#");
		
		return new String [] {_subject.getLocalName(), object[1]};
	}
	
	/**
	 * 
	 * @return Hashmap containing Component ID (Subject), CIM name for the Component (Object): URL#Class
	 */
	public Map<Resource,RDFNode> gather_TopologicalNodes()
	{
		Resource s,p;
		RDFNode o;
		Statement stmt;
		
		final StmtIterator stmtiter = this.rdfModel.listStatements();
		while( stmtiter.hasNext() ) 
		{
			stmt= stmtiter.next();
		    s = stmt.getSubject();
            p = stmt.getPredicate();
            o = stmt.getObject();
            String [] componentName= o.toString().split("#");
            //p as "type" means that the statement is referring to the component
            if (p.isURIResource() && p.getLocalName().equals("type") 
            		&& componentName[1].equals("TopologicalNode"))
            {
//            	System.out.println("Subject :"+ s.getURI());
//            	System.out.println("Subject : "+ s.getLocalName());
//            	System.out.println("Predicate : "+ p.getLocalName());
//            	System.out.println("Object : "+ o.toString());
            	this.topologicalNodes.put(s, o);
            }
		}
		return this.topologicalNodes;
	}
	
	/**
	 * 
	 * @return Hashmap containing Component ID (Subject), CIM name for the Component (Object): URL#Class
	 */
	public Map<Resource,RDFNode> gather_Terminals()
	{
		Resource s,p;
		RDFNode o;
		Statement stmt;
		
		final StmtIterator stmtiter = this.rdfModel.listStatements();
		while( stmtiter.hasNext() ) 
		{
			stmt= stmtiter.next();
		    s = stmt.getSubject();
            p = stmt.getPredicate();
            o = stmt.getObject();
//            String [] componentName= o.toString().split("#");
//            System.out.println("Subject :"+ s.getURI());
//            System.out.println("Subject : "+ s.getLocalName());
//        	System.out.println("Predicate : "+ p.getLocalName());
//        	System.out.println("Object : "+ o.toString());
            //p as "type" means that the statment is refering to the component
            if (p.isURIResource() && p.getLocalName().equals("Terminal.TopologicalNode")) 
            {
            	this.terminals.put(s, o);
            }
		}
		
		return this.terminals;
		//post: Hashtable with cim id of the class (key) and the rdf name of the cim component (value)
	}
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean has_TerminalTN(Resource _t)
	{
		final StmtIterator stmtiter = this.rdfModel.listStatements();
		boolean found= false;
		Resource s, p;
        Statement stmt;
//		System.out.println("T local name: "+ _t.getLocalName());
//    	System.out.println("T URI: "+ _t.getURI());
		while( !found && stmtiter.hasNext() ) 
		{
			stmt= stmtiter.next();
			p = stmt.getPredicate();
            s = stmt.getSubject();
            if (p.getLocalName().equals("Terminal.TopologicalNode")){
            	found= s.getLocalName().equals(_t.getLocalName());
            }
		}
		
		return found;
	}
	
	/**
	 * 
	 * @return boolean
	 */
	public String get_TerminalTN(Resource _t)
	{
		final StmtIterator stmtiter = this.rdfModel.listStatements();
		boolean found= false;
		Resource s, p;
        RDFNode o;
        Statement stmt;
        String id_TN= "";
        
		while( !found && stmtiter.hasNext() ) 
		{
			stmt= stmtiter.next();
			p = stmt.getPredicate();
            s = stmt.getSubject();
            o = stmt.getObject();
            if (p.getLocalName().equals("Terminal.TopologicalNode")){
            	found= s.getLocalName().equals(_t.getLocalName());
            	if (found)
        			id_TN= o.toString();
            }
		}
		
		return id_TN;
	}
	
	/**
	 * 
	 * @return boolean
	 */
	public Resource get_TNTerminal(String _id_TN)
	{
		Resource foundTagTN= null;
		boolean found= false;
		
		Iterator<Resource> tagsTN= this.topologicalNodes.keySet().iterator();
		while (!found && tagsTN.hasNext()){
			foundTagTN= tagsTN.next();
			found= foundTagTN.getURI().equals(_id_TN);
		}
		return foundTagTN;
	}
	
	/**
	 * 
	 * @param _subject
	 * @return
	 */
	public Map<String,Object> gather_TopoNodeAtt(Resource _subject)
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
}