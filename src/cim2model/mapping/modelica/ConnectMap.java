package cim2model.mapping.modelica;

import com.hp.hpl.jena.rdf.model.Resource;

public class ConnectMap 
{
	private PwPinMap terminalMap;
	private Resource conductingEquipment;
	private Resource topologicalNode;
	
	
	
	public ConnectMap(PwPinMap _terminal, Resource _conductingEquipment,
			Resource _topologicalNode) {
		super();
		this.terminalMap = _terminal;
		this.conductingEquipment = _conductingEquipment;
		this.topologicalNode = _topologicalNode;
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
	public void set_Terminal(PwPinMap _terminalMap) {
		this.terminalMap = _terminalMap;
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
	public void set_ConductingEquipment(Resource _conductingEquipment) {
		this.conductingEquipment = _conductingEquipment;
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
	public void set_TopologicalNode(Resource _topologicalNode) {
		this.topologicalNode = _topologicalNode;
	}
	
	
}
