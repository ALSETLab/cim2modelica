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

public class EQProfileModel extends CIMProfile {
    static final String CIMns = "http://iec.ch/TC57/2013/CIM-schema-cim16#";
    // private String id;
    private Map<Resource, RDFNode> component;
    private Map<Resource, String> substations;
    // definition of rdf properties, to get values from mapping
    // TODO Good to get this properties from mapping rules, somehow, so the
    // class gets the map file or class
    final Resource substationTag = ResourceFactory.createResource(CIMns + "Substation");
    final Property substationNameTag = ResourceFactory.createProperty(CIMns + "IdentifiedObject.name");
    final Property baseV = ResourceFactory.createProperty(CIMns + "ConductingEquipment.BaseVoltage");
    final Resource BaseVoltageTag = ResourceFactory.createResource(CIMns + "BaseVoltage");
    final Property nominalVoltage = ResourceFactory.createProperty(CIMns + "BaseVoltage.nominalVoltage");
    final Resource BasePowerTag = ResourceFactory.createResource(CIMns + "BasePower");
    final Property basePower = ResourceFactory.createProperty(CIMns + "BasePower.basePower");

    /**
     * 
     * @param _model
     */
    public EQProfileModel(String _source_SV_profile) {
	super(_source_SV_profile);
	component = new HashMap<Resource, RDFNode>();
    }

    /**
     * 
     * @return Hashmap containing Component ID (Subject), CIM name for the
     *         Component (Object): URL#Class
     */
    public Map<Resource, String> gather_SubstationResource() {
	Resource attTag;
	substations = new HashMap<Resource, String>();

	for (final ResIterator it = this.rdfModel.listResourcesWithProperty(RDF.type, substationTag); it.hasNext();) {
	    attTag = it.next();
	    substations.put(attTag, attTag.getProperty(substationNameTag).getLiteral().getValue().toString());

	}
	return substations;
    }

    /**
     * 
     * @return Hashmap containing Component ID (Subject), CIM name for the
     *         Component (Object): URL#Class
     */
    public Map<Resource, RDFNode> gatherComponents() {
	Resource s, p;
	RDFNode o;
	Statement stmt;
	StmtIterator stmtiter = this.rdfModel.listStatements();
	while (stmtiter.hasNext()) {
	    stmt = stmtiter.next();
	    s = stmt.getSubject();
	    p = stmt.getPredicate();
	    o = stmt.getObject();
	    // p as "type" means that the statment is refering to the component
	    if (p.isURIResource() && p.getLocalName().equals("type")) {
		this.component.put(s, o);
		// System.out.print("Subject : "+ s.getLocalName());
		// System.out.print("--> Predicate : "+ p.getLocalName());
		// System.out.println("--> Object : "+ o.toString());
		// String [] componentName= o.toString().split("#");
		// System.out.println(componentName[0]+ " : "+
		// componentName[1]);
	    }
	}
	stmtiter = null;
	return this.component;
	// post: Hashtable with cim id of the class (key) and the rdf name of
	// the cim component (value)
    }

    /**
     * 
     * @param _subject
     * @return Array containing Component ID, Component/Class name
     */
    public String[] get_EquipmentRdfID(Resource _subject) {
	RDFNode aux;

	aux = this.component.get(_subject);
	String[] object = aux.toString().split("#");

	return new String[] { _subject.getLocalName(), object[1] };
    }

    /**
     * General method to get the literal values from the corresponding tag of a
     * CIM Class. This method is suitable for classes with no relation with
     * other classes.
     * 
     * @param _subject
     * @return Hashmap containing Complete name of attribute (Class.Attribute),
     *         value of the attribute The value can be either a string or
     *         number, or the CIM ID of a component: URL#ComponentID
     */
    public Map<String, Object> gather_CIMClassAtt(Resource _subject) {
	StmtIterator statements = _subject.listProperties();
	Statement stmt;
	attribute = new HashMap<String, Object>();
	while (statements.hasNext()) {
	    stmt = statements.next();
	    if (stmt.getAlt().isLiteral()) {
		// System.out.println("Literal Value -> "+
		// stmt.getLiteral().getValue());
		this.attribute.put(stmt.getPredicate().getLocalName(), stmt.getLiteral().getValue());
	    }
	}
	statements = null;
	return this.attribute;
    }

    public Map<String, Object> gatther_ACLineSegment_Attributes(Resource _subject) {
	// _subject is an ACLineSegment Resource
	StmtIterator statements = _subject.listProperties();
	Statement stmt;
	attribute = new HashMap<String, Object>();
	while (statements.hasNext()) {
	    stmt = statements.next();
	    if (stmt.getAlt().isLiteral()) {
		this.attribute.put(stmt.getPredicate().getLocalName(), stmt.getLiteral().getValue());
	    }
	}
	statements = null;
	this.attribute.put(
		_subject.getProperty(baseV).getAlt().getProperty(nominalVoltage).getPredicate().getLocalName(),
		_subject.getProperty(baseV).getAlt().getProperty(nominalVoltage).getLiteral().getValue());
	return this.attribute;
    }

    /**
     * 
     * @param _subject
     * @return
     */
    public Map<String, Object> get_TerminalEQ(Resource _subject) {
	Statement terminalAttribute;

	this.attribute.clear();
	StmtIterator terminalAttributes = _subject.listProperties();
	while (terminalAttributes.hasNext()) {
	    terminalAttribute = terminalAttributes.next();
	    if (terminalAttribute.getAlt().isLiteral()) {
		this.attribute.put(terminalAttribute.getPredicate().getLocalName(),
			terminalAttribute.getLiteral().getValue());
	    }
	    if (terminalAttribute.getAlt().isURIResource()) {
		if (terminalAttribute.getPredicate().getLocalName().equals("Terminal.ConductingEquipment")) {
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
	terminalAttribute = null;

	return this.attribute;
    }

    /**
     * 
     * @param _subject
     * @return
     */
    public Map<String, Object> get_TerminalCN(Resource _subject) {
	final Property resourceCN = ResourceFactory.createProperty(CIMns + "Terminal.ConnectivityNode");
	this.attribute.put(_subject.getProperty(resourceCN).getPredicate().getLocalName(),
		_subject.getProperty(resourceCN).getObject().asResource());
	return this.attribute;
    }

    /**
     * 
     * @param _subject
     * @return
     */
    public String checkSynchronousMachineType(Resource _subject) {
	Statement attributeClass, classAttribute;
	String machineType = "";
	boolean attfound = false;
	StmtIterator iAttributes = _subject.listProperties();
	StmtIterator machDynamicAtts;
	while (!attfound && iAttributes.hasNext()) {
	    attributeClass = iAttributes.next();
	    if (attributeClass.getPredicate().getLocalName().equals("SynchronousMachine.SynchronousMachineDynamics")) {
		machDynamicAtts = attributeClass.getAlt().listProperties();
		while (!attfound && machDynamicAtts.hasNext()) {
		    classAttribute = machDynamicAtts.next();
		    if (classAttribute.getAlt().isLiteral()
			    && classAttribute.getPredicate().getLocalName().equals("IdentifiedObject.name")) {
			machineType = classAttribute.getString();
			attfound = true;
		    }
		}
		machDynamicAtts = null;
	    }
	}
	iAttributes = null;
	return machineType;
    }

    /**
     * 
     * @param _subject
     * @return
     */
    public Entry<String, Resource> checkExcitationSystemType(Resource _subject) {
	Statement attributeClass, machAttribute, classAttribute;
	String excsType = "";
	Entry<String, Resource> excsData = null;
	boolean attfound = false;
	StmtIterator iAttributes = _subject.listProperties();
	StmtIterator machDynamicAtts, excsDynAttribute;
	while (!attfound && iAttributes.hasNext()) {
	    attributeClass = iAttributes.next();
	    if (attributeClass.getPredicate().getLocalName().equals("SynchronousMachine.SynchronousMachineDynamics")) {
		machDynamicAtts = attributeClass.getAlt().listProperties();
		while (!attfound && machDynamicAtts.hasNext()) {
		    machAttribute = machDynamicAtts.next();
		    if (machAttribute.getPredicate().getLocalName()
			    .equals("SynchronousMachineDynamics.ExcitationSystemDynamics")) {
			excsDynAttribute = machAttribute.getAlt().listProperties();
			while (!attfound && excsDynAttribute.hasNext()) {
			    classAttribute = excsDynAttribute.next();
			    if (classAttribute.getAlt().isLiteral()
				    && classAttribute.getPredicate().getLocalName().equals("IdentifiedObject.name")) {
				excsType = classAttribute.getString();
				attfound = true;
			    }
			}
			excsData = new AbstractMap.SimpleEntry<String, Resource>(excsType, machAttribute.getResource());
			// System.out.println("ExcS type> "+ excsType);
			// System.out.println("Resource> "+
			// machAttribute.getResource());
			excsDynAttribute = null;
		    }
		}
		machDynamicAtts = null;
	    }
	}
	iAttributes = null;
	return excsData;
    }

    /**
     * 
     * @param _subject
     * @return
     */
    public Map<String, Object> gather_SynchronousMachine_Attributes(Resource _subject) {
	Statement attributeClass;

	StmtIterator iAttributes = _subject.listProperties();
	while (iAttributes.hasNext()) {
	    attributeClass = iAttributes.next();
	    if (attributeClass.getAlt().isLiteral()) {
		this.attribute.put(attributeClass.getPredicate().getLocalName(),
			attributeClass.getLiteral().getValue());
	    }
	    if (attributeClass.getAlt().isURIResource()) {
		if (attributeClass.getPredicate().getLocalName().equals("Equipment.EquipmentContainer")) {
		    this.attribute.put(attributeClass.getPredicate().getLocalName(), attributeClass.getResource());
		}
	    }
	}
	iAttributes = null;
	return this.attribute;
    }

    /**
     * 
     * @param _subject
     * @param _attribute
     */
    public void gather_BaseVoltage_Attributes(Resource _subject, Map<String, Object> _attribute) {
	Statement subjectAtt, baseVClassAtt;
	StmtIterator iAttributes = _subject.listProperties();
	StmtIterator baseVoltageAttribute;

	while (iAttributes.hasNext()) {
	    subjectAtt = iAttributes.next();
	    if (subjectAtt.getAlt().isURIResource()) {
		if (subjectAtt.getPredicate().getLocalName().equals("VoltageLevel.BaseVoltage")) {
		    baseVoltageAttribute = subjectAtt.getAlt().listProperties();
		    while (baseVoltageAttribute.hasNext()) {
			baseVClassAtt = baseVoltageAttribute.next();
			if (baseVClassAtt.getAlt().isLiteral()
				&& !baseVClassAtt.getPredicate().getLocalName().equals("IdentifiedObject.name")) {
			    _attribute.put(baseVClassAtt.getPredicate().getLocalName(),
				    baseVClassAtt.getLiteral().getValue());
			}
		    }
		    baseVoltageAttribute = null;
		}
	    }
	}
	iAttributes = null;
    }

    /**
     * Auxiliar mehtod to get the BaseVoltage value associated to a specified
     * component
     * 
     * @param _attributecimClass
     * @param _cimClassID
     *            - id of the specified component
     */
    public void gather_BaseVoltage_Attributes(Map<String, Object> _attributecimClass, String _cimClassID) {
	ResIterator it = this.rdfModel.listResourcesWithProperty(RDF.type, BaseVoltageTag);
	Resource attTag;
	boolean found = false;
	while (!found & it.hasNext()) {
	    attTag = it.next();
	    if (attTag.getURI().split("#")[1].equals(_cimClassID)) {
		found = true;
		_attributecimClass.put(attTag.getProperty(nominalVoltage).getPredicate().getLocalName(),
			attTag.getProperty(nominalVoltage).getLiteral().getValue());
	    }
	}
	it.close();
	it = null;
    }

    /**
     * Auxiliar method to get the BasePower value of the entire model
     * 
     * @param _subject
     * @param _attribute
     */
    public void gather_BasePower_Attributes(Map<String, Object> _attributecimClass) {
	ResIterator it = this.rdfModel.listResourcesWithProperty(RDF.type, BasePowerTag);
	Resource attTag;
	while (it.hasNext()) {
	    attTag = it.next();
	    _attributecimClass.put(attTag.getProperty(basePower).getPredicate().getLocalName(),
		    attTag.getProperty(basePower).getLiteral().getValue());
	}
	it.close();
	it = null;
    }

    /**
     * 
     * @param _subject
     * @return
     */
    public Map<String, Object> gather_ExcitationSystem_Attributes(Resource _subject) {
	Statement attributeClass;
	StmtIterator iAttributes = _subject.listProperties();
	while (iAttributes.hasNext()) {
	    attributeClass = iAttributes.next();
	    if (attributeClass.getAlt().isLiteral()) {
		this.attribute.put(attributeClass.getPredicate().getLocalName(),
			attributeClass.getLiteral().getValue());
	    }
	}
	iAttributes.close();
	return this.attribute;
    }

    /**
     * 
     * @param _subject
     * @return
     */
    public Map<String, Object> gather_EnergyConsumerAtt(Resource _subject) {
	Statement attributeClass, classAttribute;
	StmtIterator iAttributes = _subject.listProperties();
	StmtIterator svPowerFlowAtts, iLoadResponse;
	while (iAttributes.hasNext()) {
	    attributeClass = iAttributes.next();
	    if (attributeClass.getAlt().isLiteral()) {
		this.attribute.put(attributeClass.getPredicate().getLocalName(),
			attributeClass.getLiteral().getValue());
	    }
	    if (attributeClass.getAlt().isURIResource()) {
		if (attributeClass.getPredicate().getLocalName().equals("Equipment.EquipmentContainer")) {
		    this.attribute.put(attributeClass.getPredicate().getLocalName(), attributeClass.getResource());
		}
		if (attributeClass.getPredicate().getLocalName().equals("EnergyConsumer.LoadResponse")) {
		    iLoadResponse = attributeClass.getAlt().listProperties();
		    while (iLoadResponse.hasNext()) {
			classAttribute = iLoadResponse.next();
			if (classAttribute.getAlt().isLiteral()
				&& !classAttribute.getPredicate().getLocalName().equals("IdentifiedObject.name")) {
			    this.attribute.put(classAttribute.getPredicate().getLocalName(),
				    classAttribute.getString());
			}
		    }
		    iLoadResponse.close();
		}
		if (attributeClass.getPredicate().getLocalName().equals("ConductingEquipment.BaseVoltage")) {
		    // agafar els valor d'aquest component
		    svPowerFlowAtts = attributeClass.getAlt().listProperties();
		    while (svPowerFlowAtts.hasNext()) {
			classAttribute = svPowerFlowAtts.next();
			if (classAttribute.getAlt().isLiteral()) {
			    this.attribute.put(classAttribute.getPredicate().getLocalName(),
				    classAttribute.getString());
			}
		    }
		    svPowerFlowAtts.close();
		}
	    }
	}
	iAttributes.close();
	return this.attribute;
    }

    /**
     * 
     * cim_name="IdentifiedObject.name" cim_name="PowerTransformerEnd.r"
     * cim_name="PowerTransformerEnd.x" cim_name="PowerTransformerEnd.g"
     * cim_name="PowerTransformerEnd.b" cim_name="TapChanger.normalStep"
     * cim_name="TapChanger.normalStep"
     * 
     * @param _subject
     * @return
     */
    public Map<String, Object> gather_PowerTransformerEnd_Attributes(Resource _subject) {
	Statement attributeClass, attributeSubClass;
	StmtIterator iAttributes = _subject.listProperties();
	StmtIterator ratioTapChangerAtt, powTransAtt;
	while (iAttributes.hasNext()) {
	    attributeClass = iAttributes.next();
	    if (attributeClass.getAlt().isLiteral()
		    && !attributeClass.getPredicate().getLocalName().equals("IdentifiedObject.name")) {
		this.attribute.put(attributeClass.getPredicate().getLocalName(),
			attributeClass.getLiteral().getValue());
	    }
	    if (attributeClass.getAlt().isURIResource()) {
		if (attributeClass.getPredicate().getLocalName().equals("PowerTransformerEnd.PowerTransformer")) {
		    powTransAtt = attributeClass.getAlt().listProperties();
		    while (powTransAtt.hasNext()) {
			attributeSubClass = powTransAtt.next();
			if (attributeSubClass.getAlt().isLiteral()) {
			    this.attribute.put(attributeSubClass.getPredicate().getLocalName(),
				    attributeSubClass.getString());
			}
		    }
		    this.attribute.put(attributeClass.getPredicate().getLocalName(), attributeClass.getResource());
		    powTransAtt.close();
		}
		if (attributeClass.getPredicate().getLocalName().equals("TransformerEnd.RatioTapChanger")) {
		    ratioTapChangerAtt = attributeClass.getAlt().listProperties();
		    while (ratioTapChangerAtt.hasNext()) {
			attributeSubClass = ratioTapChangerAtt.next();
			if (attributeSubClass.getAlt().isLiteral()) {
			    this.attribute.put(attributeSubClass.getPredicate().getLocalName(),
				    attributeSubClass.getString());
			}
		    }
		    this.attribute.put(attributeClass.getPredicate().getLocalName(), attributeClass.getResource());
		    ratioTapChangerAtt.close();
		}
		if (attributeClass.getPredicate().getLocalName().equals("TransformerEnd.Terminal")) {
		    this.attribute.put(attributeClass.getPredicate().getLocalName(), attributeClass.getResource());
		}
	    }
	}
	iAttributes.close();
	return this.attribute;
    }

    /**
     * cim_name="Fault.rGround" cim_name="Fault.xGround" cim_name="modelica R"
     * cim_name="modelica X"
     * 
     * @param _subject
     * @return
     */
    public Map<String, Object> retrieveAttributesFault(Resource _subject) {
	Statement attributeClass, classAttribute;
	StmtIterator iAttributes = _subject.listProperties();
	StmtIterator svPowerFlowAtts;
	while (iAttributes.hasNext()) {
	    attributeClass = iAttributes.next();
	    if (attributeClass.getAlt().isLiteral()) {
		this.attribute.put(attributeClass.getPredicate().getLocalName(),
			attributeClass.getLiteral().getValue());
	    }
	    if (attributeClass.getAlt().isURIResource()) {
		if (attributeClass.getPredicate().getLocalName().equals("Fault.FaultyEquipment")) {
		    // agafar els valor d'aquest component
		    svPowerFlowAtts = attributeClass.getAlt().listProperties();
		    while (svPowerFlowAtts.hasNext()) {
			classAttribute = svPowerFlowAtts.next();
			if (classAttribute.getAlt().isLiteral()) {
			    // System.out.println("puta "+
			    // classAttribute.getPredicate().getURI());
			    this.attribute.put(classAttribute.getPredicate().getURI(), classAttribute.getString());
			}
		    }
		    svPowerFlowAtts.close();
		}
	    }
	}
	iAttributes.close();
	return this.attribute;
    }

    @Override
    public void clearAttributes() {
	this.attribute.clear();
    }

}
