package cim2modelica.cim.map;

import org.apache.jena.rdf.model.Resource;

import cim2modelica.modelica.MOPlant;

/**
 * Stores the relation of conducting equipment and topological node through the
 * name of the terminal
 * 
 * @author fragom
 *
 */
public class ConnectionMap {
    protected String t_id;
    protected String ce_id;
    protected String tn_id;
    protected Resource conductingEquipment;
    protected Resource topologicalNode;
    protected MOPlant planta;

    public ConnectionMap(String t_id, String ce_id, String tn_id) {
	super();
	this.t_id = t_id;
	this.ce_id = ce_id;
	this.tn_id = tn_id;
    }

    /**
     * @return the t_id
     */
    public String get_T_id() {
	return t_id;
    }

    /**
     * @param t_id
     *            the t_id to set
     */
    public void set_T_id(String t_id) {
	this.t_id = t_id;
    }

    /**
     * @return the ce_id
     */
    public String get_Ce_id() {
	return ce_id;
    }

    /**
     * @param ce_id
     *            the ce_id to set
     */
    public void set_Ce_id(String ce_id) {
	this.ce_id = ce_id;
    }

    /**
     * @return the tn_id
     */
    public String get_Tn_id() {
	return tn_id;
    }

    /**
     * @param tn_id
     *            the tn_id to set
     */
    public void set_Tn_id(String tn_id) {
	this.tn_id = tn_id;
    }

    /**
     * @return the conductingEquipment
     */
    public Resource get_ConductingEquipment() {
	return conductingEquipment;
    }

    /**
     * @param conductingEquipment
     *            the conductingEquipment to set
     */
    public void set_ConductingEquipment(Resource conductingEquipment) {
	this.conductingEquipment = conductingEquipment;
    }

    /**
     * @return the topologicalNode
     */
    public Resource get_TopologicalNode() {
	return topologicalNode;
    }

    /**
     * @param topologicalNode
     *            the topologicalNode to set
     */
    public void set_TopologicalNode(Resource topologicalNode) {
	this.topologicalNode = topologicalNode;
    }

    public MOPlant get_Planta() {
	return planta;
    }

    public void set_Planta(MOPlant planta) {
	this.planta = planta;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "ConnectionMap [t_id=" + t_id + ", ce_id=" + ce_id + ", tn_id=" + tn_id + "]";
    }

}
