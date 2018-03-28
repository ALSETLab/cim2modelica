package cim2modelica.cim;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

public class SVProfileModel extends CIMProfile {
    // TODO use of JENA Properties for finding tags (see
    // EQProfileModel:gather_BasePower_Attributes(...))
    // private String id;
    private Map<Resource, RDFNode> svPowerFlow;
    private Map<Resource, RDFNode> svVoltage;

    /**
     * 
     * @param _model
     */
    public SVProfileModel(String _source_SV_profile) {
	super(_source_SV_profile);
	svPowerFlow = new HashMap<Resource, RDFNode>();
	svVoltage = new HashMap<Resource, RDFNode>();
    }

    /**
     * 
     * @return Hashmap containing Component ID (Subject), CIM name for the
     *         Component (Object): URL#Class
     */
    public Map<Resource, RDFNode> gather_SvPowerFlow() {
	Resource s, p;
	RDFNode o;
	Statement stmt;
	StmtIterator stmtiter = this.rdfModel.listStatements();

	while (stmtiter.hasNext()) {
	    stmt = stmtiter.next();
	    s = stmt.getSubject();
	    p = stmt.getPredicate();
	    o = stmt.getObject();
	    // String [] componentName= o.toString().split("#");
	    // System.out.println("Subject :"+ s.getURI());
	    // System.out.println("Subject : "+ s.getLocalName());
	    // System.out.println("Predicate : "+ p.getLocalName());
	    // System.out.println("Object : "+ o.toString());
	    if (p.isURIResource() && p.getLocalName().equals("SvPowerFlow.Terminal")) {
		this.svPowerFlow.put(s, o);
	    }
	}
	stmtiter.close();
	stmtiter = null;
	return this.svPowerFlow;
	// post: Hashtable with cim id of the class (key) and the rdf name of
	// the cim component (value)
    }

    /**
     * 
     * @return boolean
     */
    public boolean has_SvPowerFlow(Resource _t) {
	StmtIterator stmtiter = this.rdfModel.listStatements();
	boolean found = false;
	Resource p;
	RDFNode o;
	Statement stmt;
	// System.out.println("T local name: "+ _t.getLocalName());
	// System.out.println("T URI: "+ _t.getURI());
	while (!found && stmtiter.hasNext()) {
	    stmt = stmtiter.next();
	    p = stmt.getPredicate();
	    o = stmt.getObject();
	    if (p.getLocalName().equals("SvPowerFlow.Terminal")) {
		String[] componentName = o.toString().split("#");
		// System.out.println(componentName[0]+ " : "+
		// componentName[1]);
		found = componentName[1].equals(_t.getLocalName());
	    }
	}
	stmtiter.close();
	stmtiter = null;
	return found;
    }

    /**
     * 
     * @return Hashmap containing Component ID (Subject), CIM name for the
     *         Component (Object): URL#Class
     */
    public Map<String, Object> get_TerminalSvPowerFlow(Resource _t) {
	boolean found = false;
	Entry<Resource, RDFNode> current;
	RDFNode rdf_resource;
	Resource tag_svPowerFlow;
	String[] rdf_id_terminal;
	Statement attPowerFlow;
	StmtIterator iProperties = null;
	Iterator<Entry<Resource, RDFNode>> iTags = this.svPowerFlow.entrySet().iterator();

	while (!found && iTags.hasNext()) {
	    current = iTags.next();
	    rdf_resource = current.getValue();
	    rdf_id_terminal = rdf_resource.toString().split("#");
	    found = rdf_id_terminal[1].equals(_t.getLocalName());
	    if (found) {
		tag_svPowerFlow = current.getKey();
		iProperties = tag_svPowerFlow.listProperties();
		while (iProperties.hasNext()) {
		    attPowerFlow = iProperties.next();
		    if (attPowerFlow.getAlt().isLiteral()) {
			this.attribute.put(attPowerFlow.getPredicate().getLocalName(),
				attPowerFlow.getLiteral().getValue());
			// System.out.println(attPowerFlow.getPredicate().getLocalName()+
			// ": "+
			// attPowerFlow.getLiteral().getValue());
		    }
		}
		iProperties = null;
	    }
	}
	iTags = null;
	return this.attribute;
	// post: Hashtable with cim id of the class (key) and the rdf name of
	// the cim component (value)
    }

    /**
     * 
     * @return Hashmap containing Component ID (Subject), CIM name for the
     *         Component (Object): URL#Class
     */
    public Map<Resource, RDFNode> gather_SvVoltage() {
	Resource s, p;
	RDFNode o;
	Statement stmt;
	StmtIterator stmtiter = this.rdfModel.listStatements();

	while (stmtiter.hasNext()) {
	    stmt = stmtiter.next();
	    s = stmt.getSubject();
	    p = stmt.getPredicate();
	    o = stmt.getObject();
	    // String [] componentName= o.toString().split("#");
	    // System.out.println("Subject :"+ s.getURI());
	    // System.out.println("Subject : "+ s.getLocalName());
	    // System.out.println("Predicate : "+ p.getLocalName());
	    // System.out.println("Object : "+ o.toString());
	    if (p.isURIResource() && p.getLocalName().equals("SvVoltage.TopologicalNode")) {
		this.svVoltage.put(s, o);
	    }
	}
	stmtiter.close();
	stmtiter = null;
	return this.svVoltage;
	// post: Hashtable with cim id of the class (key) and the rdf name of
	// the cim component (value)
    }

    /**
     * 
     * @return boolean
     */
    public boolean has_SvVoltage(Resource _t) {
	StmtIterator stmtiter = this.rdfModel.listStatements();
	boolean found = false;
	Resource p;
	RDFNode o;
	Statement stmt;
	// System.out.println("T local name: "+ _t.getLocalName());
	// System.out.println("T URI: "+ _t.getURI());
	while (!found && stmtiter.hasNext()) {
	    stmt = stmtiter.next();
	    p = stmt.getPredicate();
	    o = stmt.getObject();
	    if (p.getLocalName().equals("SvVoltage.TopologicalNode")) {
		String[] componentName = o.toString().split("#");
		// System.out.println(componentName[0]+ " : "+
		// componentName[1]);
		found = componentName[1].equals(_t.getLocalName());
	    }
	}
	stmtiter.close();
	stmtiter = null;
	return found;
    }

    /**
     * 
     * @return Hashmap containing Component ID (Subject), CIM name for the
     *         Component (Object): URL#Class
     */
    public Map<String, Object> get_TopoNodeSvVoltage(Resource _t) {
	boolean found = false;
	Entry<Resource, RDFNode> current;
	RDFNode rdf_resource;
	Resource tag_svVoltage;
	String[] rdf_id_topoNode;
	Statement attPowerFlow;
	StmtIterator iProperties = null;
	Iterator<Entry<Resource, RDFNode>> iTags = this.svVoltage.entrySet().iterator();

	while (!found && iTags.hasNext()) {
	    current = iTags.next();
	    rdf_resource = current.getValue();
	    rdf_id_topoNode = rdf_resource.toString().split("#");
	    found = rdf_id_topoNode[1].equals(_t.getLocalName());
	    if (found) {
		tag_svVoltage = current.getKey();
		iProperties = tag_svVoltage.listProperties();
		while (iProperties.hasNext()) {
		    attPowerFlow = iProperties.next();
		    if (attPowerFlow.getAlt().isLiteral()) {
			this.attribute.put(attPowerFlow.getPredicate().getLocalName(),
				attPowerFlow.getLiteral().getValue());
		    }
		}
		iProperties = null;
	    }
	}
	iTags = null;
	return this.attribute;
	// post: Hashtable with cim id of the class (key) and the rdf name of
	// the cim component (value)
    }

    public void gather_SvVoltage_TopologicalNode(Map<String, Object> _attributes, String _tpnodeID) {
	// TODO Good to pass the mapping attributes, to avoid the use of strings
	final Property attV = ResourceFactory.createProperty(CIMns + "SvVoltage.v");
	final Property attAngle = ResourceFactory.createProperty(CIMns + "SvVoltage.angle");

	boolean found = false;
	Resource currentSvTag = null;
	RDFNode currentTopoNode = null;
	Iterator<Resource> iSvVoltage = this.svVoltage.keySet().iterator();
	while (!found & iSvVoltage.hasNext()) {
	    currentSvTag = iSvVoltage.next();
	    currentTopoNode = this.svVoltage.get(currentSvTag);
	    found = _tpnodeID.equals(currentTopoNode.asResource().getURI().split("#")[1]);
	}
	if (found) {
	    _attributes.put(currentSvTag.getProperty(attV).getPredicate().getLocalName(),
		    currentSvTag.getProperty(attV).getLiteral().toString());
	    _attributes.put(currentSvTag.getProperty(attAngle).getPredicate().getLocalName(),
		    currentSvTag.getProperty(attAngle).getLiteral().toString());
	}
    }

    @Override
    public void clearAttributes() {
	this.attribute.clear();
    }
}
