package cim2modelica.cim;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;

public class DLProfileModel extends CIMProfile {
    static final String CIMns = "http://iec.ch/TC57/2013/CIM-schema-cim16#";
    private Map<Resource, RDFNode> diagramPoints;
    private Map<Resource, RDFNode> diagramObjects;
    private Model rdfModel;
    final Resource objectTag = ResourceFactory.createResource(CIMns + "DiagramObject");
    final Property idObject = ResourceFactory.createProperty(CIMns + "DiagramObject.IdentifiedObject");
    final Resource pointTag = ResourceFactory.createResource(CIMns + "DiagramObjectPoint");
    final Property refObject = ResourceFactory.createProperty(CIMns + "DiagramObjectPoint.DiagramObject");

    /**
     * 
     * @param _model
     */
    public DLProfileModel(String _source_DL_profile) {
	super(_source_DL_profile);
	diagramPoints = new HashMap<Resource, RDFNode>();
	diagramObjects = new HashMap<Resource, RDFNode>();
	Logger.getRootLogger().removeAllAppenders();
	Logger.getRootLogger().addAppender(new NullAppender());
    }

    public Map<Resource, RDFNode> gather_Object() {
	for (final ResIterator it = this.rdfModel.listResourcesWithProperty(RDF.type, objectTag); it.hasNext();) {
	    final Resource attTag = it.next();
	    diagramObjects.put(attTag.getProperty(idObject).getSubject(), attTag.getProperty(idObject).getObject());
	}
	return diagramObjects;
    }

    public Map<Resource, RDFNode> gather_ObjectPoints() {
	for (final ResIterator it = this.rdfModel.listResourcesWithProperty(RDF.type, pointTag); it.hasNext();) {
	    final Resource attTag = it.next();
	    this.diagramPoints.put(attTag.getProperty(refObject).getSubject(),
		    attTag.getProperty(refObject).getObject());
	}
	return diagramObjects;
    }

    public Resource get_Object(String _rdfidEQ) {
	Resource diagramObject, currentTag = null;
	String[] identifyObject;
	boolean found = false;
	// search and get
	Iterator<Resource> iTags = this.diagramObjects.keySet().iterator();
	while (!found && iTags.hasNext()) {
	    currentTag = iTags.next();
	    identifyObject = currentTag.getProperty(idObject).getObject().asResource().getURI().split("#");
	    if (identifyObject[1].equals(_rdfidEQ))
		found = true;
	}
	if (found)
	    diagramObject = currentTag;
	else
	    diagramObject = null;
	return diagramObject;
    }

    /**
     * 
     * @param _subject
     *            - diagram Object
     * @return
     */
    public String[] get_ObjectPoint(Resource _subject, String _sequence) {
	final Property attX = ResourceFactory.createProperty(CIMns + "DiagramObjectPoint.xPosition");
	final Property attY = ResourceFactory.createProperty(CIMns + "DiagramObjectPoint.yPosition");
	final Property attSequence = ResourceFactory.createProperty(CIMns + "DiagramObjectPoint.sequenceNumber");
	Resource attTag = null;
	boolean found = false;
	String[] diagramPoint = { "0", "0" };
	String rdfidObject, rdfresourceObject, sequenceNumber;
	// search and get
	rdfidObject = _subject.getURI().split("#")[1];
	final ResIterator it = this.rdfModel.listResourcesWithProperty(RDF.type, pointTag);
	while (!found && it.hasNext()) {
	    attTag = it.next();
	    rdfresourceObject = attTag.getProperty(refObject).getObject().asResource().getURI().split("#")[1];
	    sequenceNumber = attTag.getProperty(attSequence).getLiteral().toString();
	    // TODO take into account DiagramObjectPoint.sequenceNumber
	    if (rdfresourceObject.equals(rdfidObject) && sequenceNumber.equals(_sequence)) {
		diagramPoint[0] = attTag.getProperty(attX).getLiteral().toString();
		diagramPoint[1] = attTag.getProperty(attY).getLiteral().toString();
		found = true;
	    }
	    // diagramObject.put(it.next(),
	    // attTag.getProperty(idObject).getLiteral().getValue().toString());
	}
	return diagramPoint;
    }
    }
