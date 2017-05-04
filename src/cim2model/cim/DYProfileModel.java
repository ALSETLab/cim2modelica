package cim2model.cim;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cim2model.cim.map.ipsl.DynamicComponentType;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

public class DYProfileModel {
	
//	private String id;
	private Map<String, Object> attribute;
	private Map<Resource, RDFNode> excsys;
	private Map<Resource, RDFNode> tgov;
	private Map<Resource, RDFNode> pss;
	private Map<Resource, RDFNode> synchmach;
	private Model rdfModel;

	/**
	 * 
	 */
	public DYProfileModel()
	{
		attribute= new HashMap<String, Object>();
		excsys= new HashMap<Resource, RDFNode>();
		tgov= new HashMap<Resource, RDFNode>();
		pss= new HashMap<Resource, RDFNode>();
		synchmach= new HashMap<Resource, RDFNode>();
	}
	/**
	 * 
	 * @param _model
	 */
	public DYProfileModel(Model _model)
	{
		this.rdfModel= _model;
		attribute= new HashMap<String, Object>();
		excsys= new HashMap<Resource, RDFNode>();
		tgov= new HashMap<Resource, RDFNode>();
		pss= new HashMap<Resource, RDFNode>();
		synchmach= new HashMap<Resource, RDFNode>();
	}
	
	/**
	 * 
	 * @param _subject
	 * @param _component
	 * @return Array containing Component ID, Component/Class name
	 */
	public String [] get_ComponentName(Resource _subject, DynamicComponentType _component)
	{
		RDFNode aux= null;
		
		switch(_component) {
		case SYNCHRONOUS_MACHINE:
			aux= this.synchmach.get(_subject);
		case EXCITATION_SYSTEM:
			aux= this.excsys.get(_subject);
		case TURBINE_GOVERNOR:
			aux= this.tgov.get(_subject);
		case PS_STABILIZER:
			aux= this.pss.get(_subject);
		}
		String [] object= aux.toString().split("#");
		return new String [] {_subject.getLocalName(), object[1]};
	}
	
	/**
	 * 
	 * @return Hashmap containing Component ID (Subject), CIM name for the Component (Object): URL#Class
	 */
	public Map<Resource,RDFNode> gather_SynchronousMachines()
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
            		&& componentName[1].equals("SynchronousMachineTimeConstantReactance"))
            {
//            	System.out.println("Subject :"+ s.getURI());
//            	System.out.println("Subject : "+ s.getLocalName());
//            	System.out.println("Predicate : "+ p.getLocalName());
//            	System.out.println("Object : "+ o.toString());
            	this.synchmach.put(s, o);
            }
		}
		return this.synchmach;
	}
	
	/**
	 * 
	 * @return Hashmap containing Component ID (Subject), CIM name for the Component (Object): URL#Class
	 */
	public Map<Resource,RDFNode> gather_ExcitationSystems()
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
            		&& componentName[1].equals("ExcSEXS"))
            {
//            	System.out.println("Subject :"+ s.getURI());
//            	System.out.println("Subject : "+ s.getLocalName());
//            	System.out.println("Predicate : "+ p.getLocalName());
//            	System.out.println("Object : "+ o.toString());
            	this.excsys.put(s, o);
            }
		}
		return this.excsys;
		//post: Hashtable with cim id of the class (key) and the rdf name of the cim component (value)
	}
	
	/**
	 * 
	 * @return Hashmap containing Component ID (Subject), CIM name for the Component (Object): URL#Class
	 */
	public Map<Resource,RDFNode> gather_TurbineGovernors()
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
            		&& componentName[1].equals("GovHydro1"))
            {
//            	System.out.println("Subject :"+ s.getURI());
//            	System.out.println("Subject : "+ s.getLocalName());
//            	System.out.println("Predicate : "+ p.getLocalName());
//            	System.out.println("Object : "+ o.toString());
            	this.tgov.put(s, o);
            }
		}
		return this.tgov;
		//post: Hashtable with cim id of the class (key) and the rdf name of the cim component (value)
	}
	/**
	 * 
	 * @return Hashmap containing Component ID (Subject), CIM name for the Component (Object): URL#Class
	 */
	public Map<Resource,RDFNode> gather_PSStabilizers()
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
            		&& componentName[1].equals("GovHydro1"))
            {
//            	System.out.println("Subject :"+ s.getURI());
//            	System.out.println("Subject : "+ s.getLocalName());
//            	System.out.println("Predicate : "+ p.getLocalName());
//            	System.out.println("Object : "+ o.toString());
            	this.pss.put(s, o);
            }
		}
		return this.pss;
		//post: Hashtable with cim id of the class (key) and the rdf name of the cim component (value)
	}
	
	/**
	 * 
	 * @param _syncMach the resource object that might contain an object from Dynamics package
	 * @return
	 */
	public boolean has_TG_Dynamics(Resource _syncMach)
	{
		//TODO iterate into the terminals struct
		final StmtIterator stmtiter = this.rdfModel.listStatements();
		boolean found= false;
		Resource s, p;
        Statement stmt;
//		System.out.println("T local name: "+ _syncMach.getLocalName());
//    	System.out.println("T URI: "+ _syncMach.getURI());
		while( !found && stmtiter.hasNext() ) 
		{
			stmt= stmtiter.next();
			p = stmt.getPredicate();
            s = stmt.getSubject();
            if (p.getLocalName().equals("TurbineGovernorDynamics.SynchronousMachineDynamics")){
            	String [] componentName= s.toString().split("#");
//            	System.out.println(componentName[0]+ " : "+ componentName[1]);
            	found= s.getLocalName().equals(_syncMach.getLocalName());
            }
		}
		
		return found;
	}
	/**
	 * 
	 * @param _syncMach the resource object that might contain an object from Dynamics package
	 * @return
	 */
	public boolean has_ES_Dynamics(Resource _syncMach)
	{
		//TODO iterate into the terminals struct
		final StmtIterator stmtiter = this.rdfModel.listStatements();
		boolean found= false;
		Resource s, p;
        Statement stmt;
//		System.out.println("T local name: "+ _syncMach.getLocalName());
//    	System.out.println("T URI: "+ _syncMach.getURI());
		while( !found && stmtiter.hasNext() ) 
		{
			stmt= stmtiter.next();
			p = stmt.getPredicate();
            s = stmt.getSubject();
            if (p.getLocalName().equals("ExcitationSystemDynamics.SynchronousMachineDynamics")){
            	String [] componentName= s.toString().split("#");
//            	System.out.println(componentName[0]+ " : "+ componentName[1]);
            	found= s.getLocalName().equals(_syncMach.getLocalName());
            }
		}
		
		return found;
	}
	
	/**
	 * 
	 * @return boolean
	 */
	public String get_TG_Dynamics(Resource _syncMach)
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
            if (p.getLocalName().equals("TurbineGovernorDynamics.SynchronousMachineDynamics")){
            	found= s.getLocalName().equals(_syncMach.getLocalName());
            	if (found)
        			id_TN= o.toString();
            }
		}
		
		return id_TN;
	}
	
	/**
	 * 
	 * @param _subject
	 * @return
	 */
	public Map<String,Object> gather_ExcSystemAtt(Resource _subject)
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
				if ( attributeClass.getPredicate().getLocalName().equals("ExcitationSystemDynamics.SynchronousMachineDynamics"))
				{
					//agafar els valor d'aquest component
					StmtIterator esReference= attributeClass.getAlt().listProperties();
					while( esReference.hasNext() ) 
					{
						classAttribute= esReference.next();
						if (classAttribute.getAlt().isLiteral())
						{
							this.attribute.put(classAttribute.getPredicate().getLocalName(), classAttribute.getString());
						}
					}
					esReference.close();
				}
			}
		}
		return this.attribute;
	}
	
	/**
	 * 
	 * @param _subject
	 * @return
	 */
	public Map<String,Object> gather_TGovernorAtt(Resource _subject)
	{ 
		Statement attributeClass;
		
		StmtIterator iAttributes= _subject.listProperties();
		while( iAttributes.hasNext() ) 
		{
			attributeClass= iAttributes.next();
			if (attributeClass.getAlt().isLiteral())
			{
				this.attribute.put(attributeClass.getPredicate().getLocalName(), attributeClass.getLiteral().getValue());
			}
		}
		return this.attribute;
	}
	
	public void clearAttributes()
	{
		this.attribute.clear();
	}
	
	/**
	 * 
	 * @param _key is a resource from the EQ profile
	 * @return
	 */
	private Resource search_SynchronousMachineDynamic_Tag(Resource _key)
	{
		Statement classAttributes;
		StmtIterator iAttributes;
		boolean found= false;
		Resource currentTag= null;
		String[] rdf_resource, rdfid_synchmach;
		
		rdfid_synchmach= _key.getURI().split("#");
//		System.out.println(rdfid_synchmach[0]+ " : "+ rdfid_synchmach[1]);
		Iterator<Resource> iTags= this.synchmach.keySet().iterator();
		while(!found && iTags.hasNext() ) 
		{
			currentTag= iTags.next();
			iAttributes= currentTag.listProperties();
			while(!found && iAttributes.hasNext() ) 
			{
				classAttributes= iAttributes.next();
				if (classAttributes.getPredicate().getLocalName().equals("SynchronousMachineDynamics.SynchronousMachine"))
				{
					rdf_resource= classAttributes.getObject().toString().split("#");
//					System.out.println(rdf_resource[0]+ " : "+ rdf_resource[1]);
					found= rdf_resource[1].equals(rdfid_synchmach[1]);
				}
			}
		}
		if(found)
			return currentTag;
		else
			return null;
	}
	/**
	 * use the hashtable of SynchronousMachineDynamics 
	 * EntrySet<Resource,RDFNode> Resource is the tag of the SynchronousMachineDynamics 
	 * @param _key is a resource from the EQ profile
	 * @return
	 */
	public String checkSynchronousMachineType(Resource _key) 
	{
		Statement classAttributes;
		StmtIterator iAttributes;
		String machineType= "";
		Resource synchMachDY;
		
		synchMachDY= this.search_SynchronousMachineDynamic_Tag(_key);
		if (synchMachDY!= null) {
			iAttributes= synchMachDY.listProperties();
			while(iAttributes.hasNext() ) 
			{
				classAttributes= iAttributes.next();
				if(classAttributes.getAlt().isLiteral() && 
						classAttributes.getPredicate().getLocalName().equals("IdentifiedObject.name")){
					machineType= classAttributes.getString();
//					System.out.println(machineType);
				}
			}
			iAttributes.close();
		}
		return machineType;
	}
	
	public Map<String,Object> gather_SynchronousMachineDynamics_Attributes(Resource _key)
	{
		Statement classAttributes;
		StmtIterator iAttributes;
		Resource synchMachDY;
		
		synchMachDY= this.search_SynchronousMachineDynamic_Tag(_key);
		if (synchMachDY!= null) {
			iAttributes= synchMachDY.listProperties();
			while(iAttributes.hasNext() ) 
			{
				classAttributes= iAttributes.next();
				if(classAttributes.getAlt().isLiteral() && 
						!classAttributes.getPredicate().getLocalName().equals("IdentifiedObject.name")){
					this.attribute.put(classAttributes.getPredicate().getLocalName(), 
							classAttributes.getLiteral().getValue());
					
				}
			}
			iAttributes.close();
		}

		return this.attribute;
	}
}
