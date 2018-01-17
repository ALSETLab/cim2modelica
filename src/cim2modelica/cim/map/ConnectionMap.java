package cim2model.cim.map;

import org.apache.jena.rdf.model.Resource;

import cim2model.modelica.MOPlant;

/**
 * Stores the relation of conducting equipment and topological node through the name of the terminal
 * @author fragom
 *
 */
public class ConnectionMap 
{
	private String t_id;
	private String ce_id;
	private String[] ce_pos;
	private String tn_id;
	private String[] tn_pos;
	private Resource conductingEquipment;
	private Resource topologicalNode;
	private MOPlant planta;
	
	
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
	 * @param t_id the t_id to set
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
	 * @param ce_id the ce_id to set
	 */
	public void set_Ce_id(String ce_id) {
		this.ce_id = ce_id;
	}

	/**
	 * @return the ce_pos
	 */
	public String[] getCe_pos() {
		return ce_pos;
	}

	/**
	 * @param ce_pos
	 *            the ce_pos to set
	 */
	public void setCe_pos(String[] ce_pos) {
		this.ce_pos = ce_pos;
	}
	/**
	 * @return the tn_id
	 */
	public String get_Tn_id() {
		return tn_id;
	}
	/**
	 * @param tn_id the tn_id to set
	 */
	public void set_Tn_id(String tn_id) {
		this.tn_id = tn_id;
	}
	
	/**
	 * @return the tn_pos
	 */
	public String[] getTn_pos() {
		return tn_pos;
	}

	/**
	 * @param tn_pos  the tn_pos to set
	 */
	public void setTn_pos(String[] tn_pos) {
		this.tn_pos = tn_pos;
	}

	/**
	 * @return the conductingEquipment
	 */
	public Resource get_ConductingEquipment() {
		return conductingEquipment;
	}
	/**
	 * @param conductingEquipment the conductingEquipment to set
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
	 * @param topologicalNode the topologicalNode to set
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConnectionMap [t_id=" + t_id + ", ce_id=" + ce_id + ", tn_id="
				+ tn_id + "]";
	}
	
}
