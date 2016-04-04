package cim2model.cim;

import cim2model.ipsl.cimmap.PwPinMap;

import org.apache.jena.rdf.model.Resource;

/**
 * Keeps the relationship of Terminal class with ConductingEquipment and TopologicalNode
 * @author fragom
 *
 */
public class CIMTerminal 
{
	private PwPinMap terminalMap;
	private Resource conductingEquipment;
	private String ce_id;
	private Resource topologicalNode;
	private String tn_id;
	
	public CIMTerminal(PwPinMap _terminal, Resource _conductingEquipment,
			Resource _topologicalNode) {
		super();
		this.terminalMap = _terminal;
		this.conductingEquipment = _conductingEquipment;
		this.topologicalNode = _topologicalNode;
		this.ce_id= this.tn_id= "";
	}
	/**
	 * @return the terminal
	 */
	public PwPinMap get_TerminalMap() {
		return terminalMap;
	}
	/**
	 * @param terminal the terminal to set
	 */
	public void set_TerminalMap(PwPinMap _terminalMap) {
		this.terminalMap = _terminalMap;
	}
	/**
	 * @return the conductingEquipment
	 */
	public Resource get_ConductingEquipmentMap() {
		return conductingEquipment;
	}
	/**
	 * @param conductingEquipment the conductingEquipment to set
	 */
	public void set_ConductingEquipmentMap(Resource _conductingEquipment) {
		this.conductingEquipment = _conductingEquipment;
	}
	/**
	 * @return the topologicalNode
	 */
	public Resource get_TopologicalNodeMap() {
		return topologicalNode;
	}
	/**
	 * @param topologicalNode the topologicalNode to set
	 */
	public void set_TopologicalNodeMap(Resource _topologicalNode) {
		this.topologicalNode = _topologicalNode;
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
	public void set_Ce_id(String _ce_id) {
		this.ce_id = _ce_id;
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
	public void set_Tn_id(String _tn_id) {
		this.tn_id = _tn_id;
	}
	
}
