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
	//TODO method find_xxx to be unified for regulators component
	//TODO use of JENA Properties for finding tags (see EQProfileModel:gather_BasePower_Attributes(...))
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
			break;
		case EXCITATION_SYSTEM:
			aux= this.excsys.get(_subject);
			break;
		case TURBINE_GOVERNOR:
			aux= this.tgov.get(_subject);
			break;
		case PS_STABILIZER:
			aux= this.pss.get(_subject);
			break;
		default: break;
		}
		String [] object= aux.toString().split("#");
		return new String [] {_subject.getLocalName(), object[1]};
	}
	
	
//	/**
//	 * 
//	 * @return Hashmap containing Component ID (Subject), CIM name for the Component (Object): URL#Class
//	 */
//	public Map<Resource,RDFNode> gather_PSStabilizers()
//	{
//		Resource s,p;
//		RDFNode o;
//		Statement stmt;
//		
//		final StmtIterator stmtiter = this.rdfModel.listStatements();
//		while( stmtiter.hasNext() ) 
//		{
//			stmt= stmtiter.next();
//		    s = stmt.getSubject();
//            p = stmt.getPredicate();
//            o = stmt.getObject();
//            String [] componentName= o.toString().split("#");
//            //p as "type" means that the statement is referring to the component
//            if (p.isURIResource() && p.getLocalName().equals("type") 
//            		&& componentName[1].equals("GovHydro1"))
//            {
////            	System.out.println("Subject :"+ s.getURI());
////            	System.out.println("Subject : "+ s.getLocalName());
////            	System.out.println("Predicate : "+ p.getLocalName());
////            	System.out.println("Object : "+ o.toString());
//            	this.pss.put(s, o);
//            }
//		}
//		return this.pss;
//		//post: Hashtable with cim id of the class (key) and the rdf name of the cim component (value)
//	}
		
	
	public void clearAttributes()
	{
		this.attribute.clear();
	}
	
	/* SYNCHRONOUS MACHINES */
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
	 * @param _key is a resource from the EQ profile
	 * @return
	 */
	public Resource find_SynchronousMachineDynamic_Tag(Resource _key)
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
	public String check_SynchronousMachineType(Resource _key) 
	{
		Statement classAttributes;
		StmtIterator iAttributes;
		String machineType= "";
		Resource synchMachDY;
		
		synchMachDY= this.find_SynchronousMachineDynamic_Tag(_key);
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
	/**
	 * 
	 * @param _key
	 * @param _attribute
	 */
	public void gather_SynchronousMachineDynamics_Attributes(
			Resource _key, Map<String,Object> _attribute)
	{
		Statement classAttributes;
		StmtIterator iAttributes;
		Resource synchMachDY;
		
		//place existing attributes into the current attribute list
		this.attribute= _attribute;
		synchMachDY= this.find_SynchronousMachineDynamic_Tag(_key);
		if (synchMachDY!= null) {
			iAttributes= synchMachDY.listProperties();
			while(iAttributes.hasNext() ) 
			{
				classAttributes= iAttributes.next();
				if(classAttributes.getAlt().isLiteral() && 
						!classAttributes.getPredicate().getLocalName().equals("IdentifiedObject.name")){
					_attribute.put(classAttributes.getPredicate().getLocalName(), 
							classAttributes.getLiteral().getValue());
				}
			}
			iAttributes.close();
		}
	}

	/* EXCITATION SYSTEMS */
	/**
	 * 
	 * @return Hashmap containing Component ID (Subject), CIM name for the Component (Object): URL#Class
	 */
	public Map<Resource,RDFNode> gather_ExcitationSystems()
	{//TODO gather all TG models without using the name explicitly
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
	 * @param _machDyn the SynchronousMachine Dynamic resource 
	 * @return
	 */
	public Entry<Resource,RDFNode> find_ExcitationSystem(Resource _machDyn)
	{
		Statement classAttributes;
		StmtIterator iAttributes;
		boolean found= false;
		Entry<Resource,RDFNode> excSysTag= null;
		String[] rdf_resource, rdfid_synchmach;
		
		rdfid_synchmach= _machDyn.getURI().split("#");
//		System.out.println(rdfid_synchmach[0]+ " : "+ rdfid_synchmach[1]);
		Iterator<Entry<Resource,RDFNode>> iTags= this.excsys.entrySet().iterator();
		while(!found && iTags.hasNext() ) 
		{
			excSysTag= iTags.next();
			iAttributes= excSysTag.getKey().listProperties();
			while(!found && iAttributes.hasNext() ) 
			{
				classAttributes= iAttributes.next();
				if (classAttributes.getPredicate().getLocalName().equals("ExcitationSystemDynamics.SynchronousMachineDynamics"))
				{
					rdf_resource= classAttributes.getObject().toString().split("#");
//					System.out.println(rdf_resource[0]+ " : "+ rdf_resource[1]);
					found= rdf_resource[1].equals(rdfid_synchmach[1]);
				}
			}
		}
		if(found)
			return excSysTag;
		else
			return null;
	}
	/**
	 * 
	 * @param _subject
	 * @return
	 */
	public Map<String,Object> gather_ExcitationSystem_Attributes(Resource _subject)
	{ 
Statement attributeClass;
		
		StmtIterator iAttributes= _subject.listProperties();
		while( iAttributes.hasNext() ) 
		{
			attributeClass= iAttributes.next();
			if (attributeClass.getAlt().isLiteral())
			{
				this.attribute.put(attributeClass.getPredicate().getLocalName(), 
						attributeClass.getLiteral().getValue());
			}
		}
		return this.attribute;
	}
	
	/* TURBINE GOVERNORS */
	/**
	 * 
	 * @return Hashmap containing Component ID (Subject), CIM name for the Component (Object): URL#Class
	 */
	public Map<Resource,RDFNode> gather_TurbineGovernors()
	{//TODO gather all TG models wwithout using the name explicitly
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
            		&& (componentName[1].equals("GovHydro1") || 
            			componentName[1].equals("GovSteamSGO")))
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
	 * @param _machDyn the SynchronousMachine Dynamic resource 
	 * @return
	 */
	public Entry<Resource,RDFNode> find_TurbineGovernor(Resource _machDyn)
	{
		Statement classAttributes;
		StmtIterator iAttributes;
		boolean found= false;
		Entry<Resource,RDFNode> turbGovTag= null;
		String[] rdf_resource, rdfid_synchmach;
		
		rdfid_synchmach= _machDyn.getURI().split("#");
//		System.out.println(rdfid_synchmach[0]+ " : "+ rdfid_synchmach[1]);
		Iterator<Entry<Resource,RDFNode>> iTags= this.tgov.entrySet().iterator();
		while(!found && iTags.hasNext() ) 
		{
			turbGovTag= iTags.next();
			iAttributes= turbGovTag.getKey().listProperties();
			while(!found && iAttributes.hasNext() ) 
			{
				classAttributes= iAttributes.next();
				if (classAttributes.getPredicate().getLocalName().equals("TurbineGovernorDynamics.SynchronousMachineDynamics"))
				{
					rdf_resource= classAttributes.getObject().toString().split("#");
//					System.out.println(rdf_resource[0]+ " : "+ rdf_resource[1]);
					found= rdf_resource[1].equals(rdfid_synchmach[1]);
				}
			}
		}
		if(found)
			return turbGovTag;
		else
			return null;
	}
	/**
	 * 
	 * @param _subject
	 * @return
	 */
	public Map<String,Object> gather_TurbineGovernor_Attributes(Resource _subject)
	{ 
		Statement attributeClass;
		
		StmtIterator iAttributes= _subject.listProperties();
		while( iAttributes.hasNext() ) 
		{
			attributeClass= iAttributes.next();
			if (attributeClass.getAlt().isLiteral())
			{
				this.attribute.put(attributeClass.getPredicate().getLocalName(), 
						attributeClass.getLiteral().getValue());
			}
		}
		return this.attribute;
	}
}
