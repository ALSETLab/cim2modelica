package cim2modelica.cim;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

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
		StmtIterator stmtiter = this.rdfModel.listStatements();
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
		stmtiter.close();
		stmtiter = null;
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
//				System.out.println("Literal Value -> "+ stmt.getLiteral().getValue());
				this.attribute.put(stmt.getPredicate().getLocalName(), stmt.getLiteral().getValue());
			}
//			if (stmt.getAlt().isURIResource())
//			{
//				System.out.println("URIResource Value -> "+ stmt.getPredicate());
////				if (!stmt.getPredicate().getLocalName().equals("type"))
////					this.retrieveAttributesTerminal(stmt.getResource());
////				this.attribute.put(stmt.getPredicate().getLocalName(), stmt.getAlt().getLocalName());
//			}
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
	public Map<String,Object> getTerminalEQ(Resource _subject)
	{ 
		Statement terminalAttribute, topoNodeAttribute, svPFAttribute, svVoltAttribute;
		StmtIterator terminalAttributes = _subject.listProperties();
		StmtIterator svPowerFlowAtts, topologicalNodeAtts, svVoltageAtts,
				baseVoltage;
		
		while( terminalAttributes.hasNext() ) 
		{
			terminalAttribute= terminalAttributes.next();
			if (terminalAttribute.getAlt().isLiteral())
			{
				this.attribute.put(terminalAttribute.getPredicate().getLocalName(), terminalAttribute.getLiteral().getValue());
			}
			if (terminalAttribute.getAlt().isURIResource())
			{
				if ( terminalAttribute.getPredicate().getLocalName().equals("Terminal.SvPowerFlow"))
				{
					/* retrieve the values of this cim class */
					svPowerFlowAtts = terminalAttribute.getAlt()
							.listProperties();
					while( svPowerFlowAtts.hasNext() ) 
					{
						svPFAttribute= svPowerFlowAtts.next();
						if (svPFAttribute.getAlt().isLiteral())
						{
							this.attribute.put(svPFAttribute.getPredicate().getLocalName(), svPFAttribute.getString());
						}
					}
					svPowerFlowAtts.close();
					svPowerFlowAtts = null;
				}
				if ( terminalAttribute.getPredicate().getLocalName().equals("Terminal.TopologicalNode") )
				{
					topologicalNodeAtts = terminalAttribute.getAlt()
							.listProperties();
					while( topologicalNodeAtts.hasNext() ) 
					{
						topoNodeAttribute= topologicalNodeAtts.next();
						if (topoNodeAttribute.getPredicate().getLocalName().equals("TopologicalNode.SvVoltage"))
						{
							svVoltageAtts = topoNodeAttribute.getAlt()
									.listProperties();
							while( svVoltageAtts.hasNext() ) 
							{
								svVoltAttribute= svVoltageAtts.next();
								if (svVoltAttribute.getAlt().isLiteral())
								{
									this.attribute.put(svVoltAttribute.getPredicate().getLocalName(), svVoltAttribute.getString());
								}
							}
							svVoltageAtts.close();
							svVoltageAtts = null;
						}
						if (topoNodeAttribute.getPredicate().getLocalName().equals("TopologicalNode.BaseVoltage"))
						{
							baseVoltage = topoNodeAttribute.getAlt()
									.listProperties();
							while( baseVoltage.hasNext() ) 
							{
								svVoltAttribute= baseVoltage.next();
								if (svVoltAttribute.getAlt().isLiteral())
								{
									this.attribute.put(svVoltAttribute.getPredicate().getLocalName(), svVoltAttribute.getString());
								}
							}
							baseVoltage.close();
							baseVoltage = null;
						}
					}
					/* Add the rfd_id of the TopologicalNode which Terminal is related to */
					this.attribute.put(terminalAttribute.getPredicate().getLocalName(), terminalAttribute.getResource());
					topologicalNodeAtts.close();
					topologicalNodeAtts = null;
				}
				if ( terminalAttribute.getPredicate().getLocalName().equals("Terminal.ConductingEquipment"))
				{
					/* Add the rfd_id of the CondictingEquipment which Terminal is related to */
					this.attribute.put(terminalAttribute.getPredicate().getLocalName(), terminalAttribute.getResource());
				}
			}
		}
		terminalAttributes.close();
		terminalAttributes = null;
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
		StmtIterator iAttributes = _subject.listProperties();
		StmtIterator machDynamicAtts;
		
		while( !attfound && iAttributes.hasNext() ) 
		{
			attributeClass= iAttributes.next();
			if ( attributeClass.getPredicate().getLocalName().equals("SynchronousMachine.SynchronousMachineDynamics"))
			{
				machDynamicAtts = attributeClass.getAlt().listProperties();
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
				machDynamicAtts = null;
			}
		}
		iAttributes.close();
		iAttributes = null;
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
		StmtIterator iAttributes = _subject.listProperties();
		StmtIterator machDynamicAtts, excsDynAttribute;
		
		while( !attfound && iAttributes.hasNext() ) 
		{
			attributeClass= iAttributes.next();
			if ( attributeClass.getPredicate().getLocalName().equals("SynchronousMachine.SynchronousMachineDynamics"))
			{
				machDynamicAtts = attributeClass.getAlt().listProperties();
				while(!attfound && machDynamicAtts.hasNext() ) 
				{
					machAttribute= machDynamicAtts.next();
					if ( machAttribute.getPredicate().getLocalName().equals("SynchronousMachineDynamics.ExcitationSystemDynamics"))
					{
						excsDynAttribute = machAttribute.getAlt()
								.listProperties();
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
						System.out.println("ExcS type> "+ excsType);
						System.out.println("Resource> "+ machAttribute.getResource());
						excsDynAttribute.close();
						excsDynAttribute = null;
					}
				}
				machDynamicAtts.close();
				machDynamicAtts = null;
			}
		}
		iAttributes.close();
		iAttributes = null;
		return excsData;
	}
	
	/**
	 * 
	 * @param _subject
	 * @return
	 */
	public Map<String,Object> retrieveAttributesSyncMach(Resource _subject)
	{ 
		Statement attributeClass, classAttribute;
		StmtIterator iAttributes= _subject.listProperties();
		StmtIterator iLoadResponse, dynamicAtts;
		while( iAttributes.hasNext() ) 
		{
			attributeClass= iAttributes.next();
			if (attributeClass.getAlt().isLiteral())
			{
				this.attribute.put(attributeClass.getPredicate().getLocalName(), attributeClass.getLiteral().getValue());
			}
			if (attributeClass.getAlt().isURIResource())
			{
				if ( attributeClass.getPredicate().getLocalName().equals("RotatingMachine.GeneratingUnit"))
				{
					iLoadResponse = attributeClass.getAlt().listProperties();
					while( iLoadResponse.hasNext() ) 
					{
						classAttribute= iLoadResponse.next();
						if (classAttribute.getAlt().isLiteral()) {
							this.attribute.put(classAttribute.getPredicate().getLocalName(), classAttribute.getString());
						}
					}
					iLoadResponse.close();
					iLoadResponse = null;
				}
				if ( attributeClass.getPredicate().getLocalName().equals("SynchronousMachine.SynchronousMachineDynamics"))
				{
					dynamicAtts = attributeClass.getAlt().listProperties();
					while( dynamicAtts.hasNext() ) 
					{
						classAttribute= dynamicAtts.next();
//						System.out.println("In CIM ... "+ classAttribute.getAlt().toString());
						if (classAttribute.getAlt().isLiteral() && 
								!classAttribute.getPredicate().getLocalName().equals("IdentifiedObject.name")) {
							this.attribute.put(classAttribute.getPredicate().getLocalName(), classAttribute.getString());
						}
						if(classAttribute.getAlt().isURIResource() && 
								classAttribute.getPredicate().getLocalName().equals("SynchronousMachineTimeConstantReactance.rotorType")){
							this.attribute.put(classAttribute.getPredicate().getLocalName(), 
									classAttribute.getObject().toString().split("#")[1]);
						}
					}
					dynamicAtts.close();
					dynamicAtts = null;
				}
			}
		}
		iAttributes.close();
		iAttributes = null;
		return this.attribute;
	}
	
	/**
	 * 
	 * @param _subject
	 * @return
	 */
	public Map<String,Object> retrieveAttributesExcSys(Resource _subject)
	{ 
		Statement attributeClass;
		StmtIterator iAttributes = _subject.listProperties();
		
		while( iAttributes.hasNext() ) 
		{
			attributeClass= iAttributes.next();
			if (attributeClass.getAlt().isLiteral())
			{
				this.attribute.put(attributeClass.getPredicate().getLocalName(), 
						attributeClass.getLiteral().getValue());
			}
		}
		iAttributes = null;
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
	public Map<String,Object> retrieveAttributesEnergyC(Resource _subject)
	{ 
		Statement attributeClass, classAttribute;
		StmtIterator iAttributes= _subject.listProperties();
		StmtIterator iLoadResponse, svPowerFlowAtts;
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
					iLoadResponse = attributeClass.getAlt().listProperties();
					while( iLoadResponse.hasNext() ) 
					{
						classAttribute= iLoadResponse.next();
						if (classAttribute.getAlt().isLiteral()) {
							this.attribute.put(classAttribute.getPredicate().getLocalName(), classAttribute.getString());
						}
					}
					iLoadResponse.close();
					iLoadResponse = null;
				}
				if ( attributeClass.getPredicate().getLocalName().equals("ConductingEquipment.BaseVoltage"))
				{
					//agafar els valor d'aquest component
					svPowerFlowAtts = attributeClass.getAlt().listProperties();
					while( svPowerFlowAtts.hasNext() ) 
					{
						classAttribute= svPowerFlowAtts.next();
						if (classAttribute.getAlt().isLiteral()) {
							this.attribute.put(classAttribute.getPredicate().getLocalName(), classAttribute.getString());
						}
					}
					svPowerFlowAtts.close();
					svPowerFlowAtts = null;
				}
			}
		}
		iAttributes = null;
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
	public Map<String,Object> retrieveAttributesTransformer(Resource _subject)
	{ 
		Statement attributeClass, attributeSubClass;
		StmtIterator iAttributes = _subject.listProperties();
		StmtIterator powTransAtt, ratioTapChangerAtt;
		
		while( iAttributes.hasNext() ) 
		{
			attributeClass= iAttributes.next();
			if (attributeClass.getAlt().isLiteral() && !attributeClass.getPredicate().getLocalName().equals("IdentifiedObject.name"))
			{//Take the literals but not when is the attribute IdentifiedObject.name, because this attributes refers to PTE,
				//and we want the IdentifiedObject.name from PT
				this.attribute.put(attributeClass.getPredicate().getLocalName(), attributeClass.getLiteral().getValue());
			}
			if (attributeClass.getAlt().isURIResource())
			{
				if ( attributeClass.getPredicate().getLocalName().equals("PowerTransformerEnd.PowerTransformer"))
				{
					//From this class, the interest is in the RFD_ID value, since there is one transformer with multiple Ends
					powTransAtt = attributeClass.getAlt().listProperties();
					while( powTransAtt.hasNext() ) 
					{
						attributeSubClass= powTransAtt.next();
						if (attributeSubClass.getAlt().isLiteral()) {
							this.attribute.put(attributeSubClass.getPredicate().getLocalName(), attributeSubClass.getString());
						}
					}
					/* Add the rfd_id of the TopologicalNode which Terminal is related to */
					this.attribute.put(attributeClass.getPredicate().getLocalName(), attributeClass.getResource());
					powTransAtt.close();
				}
				if ( attributeClass.getPredicate().getLocalName().equals("TransformerEnd.RatioTapChanger"))
				{
					ratioTapChangerAtt = attributeClass.getAlt()
							.listProperties();
					while( ratioTapChangerAtt.hasNext() ) 
					{
						attributeSubClass= ratioTapChangerAtt.next();
						if (attributeSubClass.getAlt().isLiteral()) {
							this.attribute.put(attributeSubClass.getPredicate().getLocalName(), attributeSubClass.getString());
						}
					}
					/* Add the rfd_id of the TopologicalNode which Terminal is related to */
					this.attribute.put(attributeClass.getPredicate().getLocalName(), attributeClass.getResource());
					ratioTapChangerAtt.close();
					ratioTapChangerAtt = null;
				}
				if ( attributeClass.getPredicate().getLocalName().equals("TransformerEnd.Terminal"))
				{
					/* Add the rfd_id of the CondictingEquipment which Terminal is related to */
					this.attribute.put(attributeClass.getPredicate().getLocalName(), attributeClass.getResource());
					this.getTerminalEQ(attributeClass.getResource());
				}
			}
		}
		iAttributes = null;
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
		StmtIterator iAttributes = _subject.listProperties();
		StmtIterator svPowerFlowAtts;
		
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
					svPowerFlowAtts = attributeClass.getAlt().listProperties();
					while( svPowerFlowAtts.hasNext() ) 
					{
						classAttribute= svPowerFlowAtts.next();
						if (classAttribute.getAlt().isLiteral())
						{
							this.attribute.put(classAttribute.getPredicate().getLocalName(), classAttribute.getString());
						}
					}
					svPowerFlowAtts.close();
					svPowerFlowAtts = null;
				}
				if ( attributeClass.getPredicate().getLocalName().equals("TopologicalNode.BaseVoltage"))
				{
					svPowerFlowAtts = attributeClass.getAlt().listProperties();
					while( svPowerFlowAtts.hasNext() ) 
					{
						classAttribute= svPowerFlowAtts.next();
						if (classAttribute.getAlt().isLiteral())
						{
							this.attribute.put(classAttribute.getPredicate().getLocalName(), classAttribute.getString());
						}
					}
					svPowerFlowAtts.close();
					svPowerFlowAtts = null;
				}
			}
		}
		iAttributes = null;
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
		StmtIterator iAttributes = _subject.listProperties();
		StmtIterator svPowerFlowAtts;
		
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
					svPowerFlowAtts = attributeClass.getAlt().listProperties();
					while( svPowerFlowAtts.hasNext() ) 
					{
						classAttribute= svPowerFlowAtts.next();
						if (classAttribute.getAlt().isLiteral())
						{
							this.attribute.put(classAttribute.getPredicate().getURI(), classAttribute.getString());
						}
					}
					svPowerFlowAtts.close();
					svPowerFlowAtts = null;
				}
			}
		}
		iAttributes = null;
		return this.attribute;
	}
	
	public void clearAttributes()
	{
		this.attribute.clear();
	}
}
