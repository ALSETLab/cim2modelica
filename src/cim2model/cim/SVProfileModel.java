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

public class SVProfileModel {
	
//	private String id;
	private Map<String, Object> attribute;
	private Map<Resource, RDFNode> svPowerFlow;
	private Map<Resource, RDFNode> svVoltage;
	private Model rdfModel;

	/**
	 * 
	 */
	public SVProfileModel()
	{
		attribute= new HashMap<String, Object>();
		svPowerFlow= new HashMap<Resource, RDFNode>();
		svVoltage= new HashMap<Resource, RDFNode>();
	}
	/**
	 * 
	 * @param _model
	 */
	public SVProfileModel(Model _model)
	{
		this.rdfModel= _model;
		attribute= new HashMap<String, Object>();
		svPowerFlow= new HashMap<Resource, RDFNode>();
		svVoltage= new HashMap<Resource, RDFNode>();
	}
	
	/**
	 * 
	 * @return Hashmap containing Component ID (Subject), CIM name for the Component (Object): URL#Class
	 */
	public Map<Resource,RDFNode> gatherSvPowerFlow()
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
            if (p.isURIResource() && p.getLocalName().equals("SvPowerFlow.Terminal")) 
            {//s is the tag SvPowerFlow; o is the attribute with rdf:resource of the tag
            	this.svPowerFlow.put(s, o);
            }
		}
		
		return this.svPowerFlow;
		//post: Hashtable with cim id of the class (key) and the rdf name of the cim component (value)
	}
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean has_SvPowerFlow(Resource _t)
	{
		final StmtIterator stmtiter = this.rdfModel.listStatements();
		boolean found= false;
		Resource p;
        RDFNode o;
        Statement stmt;
		System.out.println("T local name: "+ _t.getLocalName());
    	System.out.println("T URI: "+ _t.getURI());
		while( !found && stmtiter.hasNext() ) 
		{
			stmt= stmtiter.next();
			p = stmt.getPredicate();
            o = stmt.getObject();
            if (p.getLocalName().equals("SvPowerFlow.Terminal")){
            	String [] componentName= o.toString().split("#");
            	System.out.println(componentName[0]+ " : "+ componentName[1]);
            	found= componentName[1].equals(_t.getLocalName());
            }
		}
		
		return found;
	}
	
	
	/**
	 * 
	 * @return Hashmap containing Component ID (Subject), CIM name for the Component (Object): URL#Class
	 */
	public Map<String, Object> get_TerminalPF(Resource _t)
	{
		boolean found= false;
		Entry<Resource,RDFNode> current;
		RDFNode rdf_resource;
		Resource tag_svPowerFlow;
		String [] rdf_id_terminal;
        Statement attPowerFlow;
        StmtIterator iProperties= null;
        Iterator<Entry<Resource,RDFNode>> iTags= this.svPowerFlow.entrySet().iterator();
        
		while( !found && iTags.hasNext() ) 
		{
			current= iTags.next();
			rdf_resource= current.getValue();
			rdf_id_terminal= rdf_resource.toString().split("#");
			found= rdf_id_terminal[1].equals(_t.getLocalName());
			if (found)
			{
				tag_svPowerFlow= current.getKey();
				iProperties= tag_svPowerFlow.listProperties();
				while( iProperties.hasNext() )
				{
					attPowerFlow= iProperties.next();
					if (attPowerFlow.getAlt().isLiteral())
					{
						this.attribute.put(attPowerFlow.getPredicate().getLocalName(), 
								attPowerFlow.getLiteral().getValue());
						System.out.println(attPowerFlow.getPredicate().getLocalName()+ ": "+ 
	    						attPowerFlow.getLiteral().getValue());
					}
				}
			}
		}	
		iProperties= null; iTags= null;
		return this.attribute;
		//post: Hashtable with cim id of the class (key) and the rdf name of the cim component (value)
	}
	
	/**
	 * 
	 * @return Hashmap containing Component ID (Subject), CIM name for the Component (Object): URL#Class
	 */
	public Map<Resource,RDFNode> gatherSvVoltage()
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
            if (p.isURIResource() && p.getLocalName().equals("SvVoltage.TopologicalNode")) 
            {
            	this.svVoltage.put(s, o);
            }
		}
		
		return this.svVoltage;
		//post: Hashtable with cim id of the class (key) and the rdf name of the cim component (value)
	}
	
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean hasSvVoltage(Resource _tn)
	{
		final StmtIterator stmtiter = this.rdfModel.listStatements();
		boolean found= false;
		Resource p;
        RDFNode o;
        Statement stmt;
		System.out.println("T local name: "+ _tn.getLocalName());
    	System.out.println("T URI: "+ _tn.getURI());
		while( !found && stmtiter.hasNext() ) 
		{
			stmt= stmtiter.next();
			p = stmt.getPredicate();
            o = stmt.getObject();
            if (p.getLocalName().equals("SvVoltage.TopologicalNode")){
            	String [] componentName= o.toString().split("#");
            	System.out.println(componentName[0]+ " : "+ componentName[1]);
            	found= componentName[1].equals(_tn.getLocalName());
            }
		}
		
		return found;
	}
	
	
	/**
	 * 
	 * @return Hashmap containing Component ID (Subject), CIM name for the Component (Object): URL#Class
	 */
	public Map<String, Object> getTopoNodeV(Resource _t)
	{
		boolean found= false;
        Statement attVoltage;
        StmtIterator iProperties= null;
    	final Iterator<Resource> iResource = this.svVoltage.keySet().iterator();
		while( !found && iResource.hasNext() ) 
		{
			iProperties= ((Resource)iResource.next()).listProperties();
			while (iProperties.hasNext())
			{
				attVoltage= iProperties.next();
				//TODO if it matches, get literals
				if (attVoltage.getAlt().isLiteral())
				{
					this.attribute.put(attVoltage.getPredicate().getLocalName(), 
							attVoltage.getLiteral().getValue());
					System.out.println(attVoltage.getPredicate().getLocalName()+ ": "+ 
							attVoltage.getLiteral().getValue());
				}
				if (attVoltage.getAlt().isURIResource())
				{
					//TODO check _t.id matches this one -> break, found true;
					
				}
			}
		}	
		return this.attribute;
		//post: Hashtable with cim id of the class (key) and the rdf name of the cim component (value)
	}
	
	public void clearAttributes()
	{
		this.attribute.clear();
	}
}
