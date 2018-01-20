package cim2modelica.cim;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;

public class EQProfileModel extends CIMProfile
{
	static final String CIMns= "http://iec.ch/TC57/2013/CIM-schema-cim16#";
//	private String id;
	private Map<Resource, RDFNode> component;
	private Map<Resource, String> substations;
	
	/**
	 * 
	 * @param _model
	 */
	public EQProfileModel(String _source_SV_profile)
	{
		super(_source_SV_profile);
		component= new HashMap<Resource, RDFNode>();
	}
	
	/**
	 * 
	 * @return Hashmap containing Component ID (Subject), CIM name for the Component (Object): URL#Class
	 */
	public Map<Resource,String> gather_SubstationResource()
	{
		final Resource substationTag= ResourceFactory.createResource(CIMns+"Substation");
		final Property nameTag= ResourceFactory.createProperty(CIMns+ "IdentifiedObject.name");
		substations= new HashMap<Resource, String>();
		
		for (final ResIterator it= this.rdfModel.listResourcesWithProperty(RDF.type, substationTag); it.hasNext();)
		{
			final Resource attTag= it.next();
			substations.put(attTag, 
					attTag.getProperty(nameTag).getLiteral().getValue().toString());
			
		}
		return substations;
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
//            	System.out.print("Subject : "+ s.getLocalName());
//            	System.out.print("--> Predicate : "+ p.getLocalName());
//            	System.out.println("--> Object : "+ o.toString());
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
	public String [] get_EquipmentRdfID(Resource _subject)
	{
		RDFNode aux;
		
		aux= this.component.get(_subject);
		String [] object= aux.toString().split("#");
		
		return new String [] {_subject.getLocalName(), object[1]};
	}
	
	/**
	 * General method to get the literal values from the corresponding tag of a CIM Class. This
	 * method is suitable for classes with no relation with other classes.
	 * @param _subject
	 * @return Hashmap containing Complete name of attribute (Class.Attribute), value of the attribute
	 * The value can be either a string or number, or the CIM ID of a component: URL#ComponentID
	 */
	public Map<String, Object> gather_CIMClassAtt(Resource _subject)
	{
		StmtIterator statements= _subject.listProperties();
		attribute= new HashMap<String, Object>();
		while( statements.hasNext() ) 
		{
		    Statement stmt= statements.next();
			if (stmt.getAlt().isLiteral())
			{
//				System.out.println("Literal Value -> "+ stmt.getLiteral().getValue());
				this.attribute.put(stmt.getPredicate().getLocalName(), stmt.getLiteral().getValue());
			}
		}
		return this.attribute;
	}
	
	/**
	 * 
	 * @param _subject
	 * @return
	 */
	public Map<String,Object> get_TerminalEQ(Resource _subject)
	{ 
		Statement terminalAttribute;
		
		this.attribute.clear();
		StmtIterator terminalAttributes= _subject.listProperties();
		while( terminalAttributes.hasNext() ) 
		{
			terminalAttribute= terminalAttributes.next();
			if (terminalAttribute.getAlt().isLiteral())
			{
				this.attribute.put(terminalAttribute.getPredicate().getLocalName(), 
						terminalAttribute.getLiteral().getValue());
			}
			if (terminalAttribute.getAlt().isURIResource())
			{
				if ( terminalAttribute.getPredicate().getLocalName().equals("Terminal.ConductingEquipment"))
				{
					/*
					 * Add the rdf:resource of the CondictingEquipment which
					 * Terminal is related to
					 */
					this.attribute.put(terminalAttribute.getPredicate().getLocalName(), 
							terminalAttribute.getResource());
				}
			}
		}
		terminalAttributes.close();
		terminalAttribute= null;
		
		return this.attribute;
	}
	
	/**
	 * 
	 * @param _subject
	 * @return
	 */
	public Map<String, Object> get_TerminalCN(Resource _subject) {
		final Property resourceCN = ResourceFactory
				.createProperty(CIMns + "Terminal.ConnectivityNode");
		this.attribute.put(
				_subject.getProperty(resourceCN).getPredicate().getLocalName(),
				_subject.getProperty(resourceCN).getObject().asResource());
		return this.attribute;
	}

	/**
	 * 
	 * @param _subject
	 * @return
	 */
	public String checkSynchronousMachineType(Resource _subject)
	{
		Statement attributeClass, classAttribute;
		String machineType= "";
		boolean attfound= false;
		
		StmtIterator iAttributes= _subject.listProperties();
		while( !attfound && iAttributes.hasNext() ) 
		{
			attributeClass= iAttributes.next();
			if ( attributeClass.getPredicate().getLocalName().equals("SynchronousMachine.SynchronousMachineDynamics"))
			{
				StmtIterator machDynamicAtts= attributeClass.getAlt().listProperties();
				while(!attfound && machDynamicAtts.hasNext() ) 
				{
					classAttribute= machDynamicAtts.next();
					if(classAttribute.getAlt().isLiteral() && 
							classAttribute.getPredicate().getLocalName().equals("IdentifiedObject.name")){
						machineType= classAttribute.getString();
						attfound= true;
					}
				}
				machDynamicAtts.close();
			}
		}
		
		return machineType;
	}
	
	/**
	 * 
	 * @param _subject
	 * @return
	 */
	public Entry<String, Resource> checkExcitationSystemType(Resource _subject)
	{
		Statement attributeClass, machAttribute, classAttribute;
		String excsType= "";
		Entry<String, Resource> excsData= null;
		
		boolean attfound= false;
		
		StmtIterator iAttributes= _subject.listProperties();
		while( !attfound && iAttributes.hasNext() ) 
		{
			attributeClass= iAttributes.next();
			if ( attributeClass.getPredicate().getLocalName().equals("SynchronousMachine.SynchronousMachineDynamics"))
			{
				StmtIterator machDynamicAtts= attributeClass.getAlt().listProperties();
				while(!attfound && machDynamicAtts.hasNext() ) 
				{
					machAttribute= machDynamicAtts.next();
					if ( machAttribute.getPredicate().getLocalName().equals("SynchronousMachineDynamics.ExcitationSystemDynamics"))
					{
						StmtIterator excsDynAttribute= machAttribute.getAlt().listProperties();
						while(!attfound && excsDynAttribute.hasNext() ) 
						{
							classAttribute= excsDynAttribute.next();
							if(classAttribute.getAlt().isLiteral() && 
									classAttribute.getPredicate().getLocalName().equals("IdentifiedObject.name")){
								excsType= classAttribute.getString();
								attfound= true;
							}
						}
						excsData= new AbstractMap.SimpleEntry<String, Resource>(
								excsType, machAttribute.getResource());
//						System.out.println("ExcS type> "+ excsType);
//						System.out.println("Resource> "+ machAttribute.getResource());
						excsDynAttribute.close();
					}
				}
				machDynamicAtts.close();
			}
		}
		return excsData;
	}
	
	/**
	 * 
	 * @param _subject
	 * @return
	 */
	public Map<String,Object> gather_SynchronousMachine_Attributes(Resource _subject)
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
			if (attributeClass.getAlt().isURIResource())
			{
				if ( attributeClass.getPredicate().getLocalName().equals("Equipment.EquipmentContainer"))
				{
					this.attribute.put(attributeClass.getPredicate().getLocalName(), 
							attributeClass.getResource());
				}
			}
		}
		return this.attribute;
	}
	
	/**
	 * 
	 * @param _subject
	 * @param _attribute
	 */
	public void gather_BaseVoltage_Attributes(Resource _subject, Map<String,Object> _attribute)
	{
		Statement subjectAtt, baseVClassAtt;
		StmtIterator iAttributes= _subject.listProperties();
		
		while( iAttributes.hasNext() ) 
		{
			subjectAtt= iAttributes.next();
			if (subjectAtt.getAlt().isURIResource())
			{
				if ( subjectAtt.getPredicate().getLocalName().equals("VoltageLevel.BaseVoltage"))
				{
					StmtIterator baseVoltageAttribute= subjectAtt.getAlt().listProperties();
					while( baseVoltageAttribute.hasNext() ) 
					{
						baseVClassAtt= baseVoltageAttribute.next();
						if(baseVClassAtt.getAlt().isLiteral() && 
								!baseVClassAtt.getPredicate().getLocalName().equals("IdentifiedObject.name")){
							_attribute.put(baseVClassAtt.getPredicate().getLocalName(), 
									baseVClassAtt.getLiteral().getValue());
						}
					}
				}
			}
		}
	}
	/**
	 * 
	 * @param _subject
	 * @param _attribute
	 */
	public void gather_BasePower_Attributes(Map<String,Object> _attribute)
	{
		final Resource BasePowerTag= ResourceFactory.createResource(CIMns+"BasePower");
		final Property basePower= ResourceFactory.createProperty(CIMns+ "BasePower.basePower");
		
		for (final ResIterator it= this.rdfModel.listResourcesWithProperty(RDF.type, BasePowerTag); it.hasNext();)
		{
			final Resource attTag= it.next();
			_attribute.put(attTag.getProperty(basePower).getPredicate().getLocalName(), 
					attTag.getProperty(basePower).getLiteral().getValue());
		}
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
	
	/**
	 * 
	 * @param _subject
	 * @return
	 */
	public Map<String,Object> gather_EnergyConsumerAtt(Resource _subject)
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
				if ( attributeClass.getPredicate().getLocalName().equals("EnergyConsumer.LoadResponse"))
				{
					StmtIterator iLoadResponse= attributeClass.getAlt().listProperties();
					while( iLoadResponse.hasNext() ) 
					{
						classAttribute= iLoadResponse.next();
						if (classAttribute.getAlt().isLiteral()
								&& !classAttribute.getPredicate().getLocalName()
										.equals("IdentifiedObject.name")) {
							this.attribute.put(classAttribute.getPredicate().getLocalName(), classAttribute.getString());
						}
					}
					iLoadResponse.close();
				}
				if ( attributeClass.getPredicate().getLocalName().equals("ConductingEquipment.BaseVoltage"))
				{
					//agafar els valor d'aquest component
					StmtIterator svPowerFlowAtts= attributeClass.getAlt().listProperties();
					while( svPowerFlowAtts.hasNext() ) 
					{
						classAttribute= svPowerFlowAtts.next();
						if (classAttribute.getAlt().isLiteral()) {
							this.attribute.put(classAttribute.getPredicate().getLocalName(), classAttribute.getString());
						}
					}
					svPowerFlowAtts.close();
				}
			}
		}
		return this.attribute;
	}
	
	/**
	 * 
	 * cim_name="IdentifiedObject.name"
	 * cim_name="PowerTransformerEnd.r" 
	 * cim_name="PowerTransformerEnd.x"
	 * cim_name="PowerTransformerEnd.g"
	 * cim_name="PowerTransformerEnd.b"
	 * cim_name="TapChanger.normalStep"
	 * cim_name="TapChanger.normalStep"
	 * @param _subject
	 * @return
	 */
	public Map<String,Object> gather_PowerTransformerEnd_Attributes(Resource _subject)
	{ 
		Statement attributeClass, attributeSubClass;
		
		StmtIterator iAttributes= _subject.listProperties();
		while( iAttributes.hasNext() ) 
		{
			attributeClass= iAttributes.next();
			if (attributeClass.getAlt().isLiteral() && 
					!attributeClass.getPredicate().getLocalName().equals("IdentifiedObject.name"))
			{//Take the literals but not when is the attribute IdentifiedObject.description, because this attributes refers to PTE,
				//and we want the IdentifiedObject.description from PT
				this.attribute.put(attributeClass.getPredicate().getLocalName(), attributeClass.getLiteral().getValue());
			}
			if (attributeClass.getAlt().isURIResource())
			{
				if ( attributeClass.getPredicate().getLocalName().equals("PowerTransformerEnd.PowerTransformer"))
				{//From this class, the interest is in the rdf:resource, since there is one transformer with multiple Ends
					StmtIterator powTransAtt= attributeClass.getAlt().listProperties();
					while( powTransAtt.hasNext() ) 
					{
						attributeSubClass= powTransAtt.next();
						if (attributeSubClass.getAlt().isLiteral()) {
							this.attribute.put(attributeSubClass.getPredicate().getLocalName(), attributeSubClass.getString());
						}
					}
					this.attribute.put(attributeClass.getPredicate().getLocalName(), attributeClass.getResource());
					powTransAtt.close();
				}
				if ( attributeClass.getPredicate().getLocalName().equals("TransformerEnd.RatioTapChanger"))
				{//agafar els valor d'aquest component
					StmtIterator ratioTapChangerAtt= attributeClass.getAlt().listProperties();
					while( ratioTapChangerAtt.hasNext() ) 
					{
						attributeSubClass= ratioTapChangerAtt.next();
						if (attributeSubClass.getAlt().isLiteral()) {
							this.attribute.put(attributeSubClass.getPredicate().getLocalName(), attributeSubClass.getString());
						}
					}
					this.attribute.put(attributeClass.getPredicate().getLocalName(), attributeClass.getResource());
					ratioTapChangerAtt.close();
				}
				if ( attributeClass.getPredicate().getLocalName().equals("TransformerEnd.Terminal"))
				{
					this.attribute.put(attributeClass.getPredicate().getLocalName(), attributeClass.getResource());
				}
			}
		}
		return this.attribute;
	}

	
	/**
	 * cim_name="Fault.rGround" 
	 * cim_name="Fault.xGround" 
	 * cim_name="modelica R" 
	 * cim_name="modelica X" 
	 * @param _subject
	 * @return
	 */
	public Map<String,Object> retrieveAttributesFault(Resource _subject)
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
				if ( attributeClass.getPredicate().getLocalName().equals("Fault.FaultyEquipment"))
				{
					//agafar els valor d'aquest component
					StmtIterator svPowerFlowAtts= attributeClass.getAlt().listProperties();
					while( svPowerFlowAtts.hasNext() ) 
					{
						classAttribute= svPowerFlowAtts.next();
						if (classAttribute.getAlt().isLiteral())
						{
//							System.out.println("puta "+ classAttribute.getPredicate().getURI());
							this.attribute.put(classAttribute.getPredicate().getURI(), classAttribute.getString());
						}
					}
					svPowerFlowAtts.close();
				}
			}
		}
		return this.attribute;
	}
	
	@Override
	public void clearAttributes()
	{
		this.attribute.clear();
	}
}
