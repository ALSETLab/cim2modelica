package cim2modelica.cim.map.openipsl.transformers;

import org.apache.jena.rdf.model.Resource;

/**
 * Keeps the relationship of Terminal class with ConductingEquipment and TopologicalNode
 * @author fragom
 *
 */
public class TransformerEndAuxiliarMap 
{
	private TwoWindingTransformerMap twtmap;
	private Resource powerTransformer;
	private String pt_id;
	private Resource terminal;
	private String t_id;
	
	public TransformerEndAuxiliarMap(TwoWindingTransformerMap _twtmap, 
			Resource _powerTransformer,  
			Resource _terminal) {
		super();
		this.twtmap = _twtmap;
		this.powerTransformer = _powerTransformer;
		this.terminal = _terminal;
		this.pt_id= ""; 
		this.t_id= "";
	}
	
	/**
	 * @return the terminal
	 */
	public TwoWindingTransformerMap get_TransformerEnd_Map() {
		return twtmap;
	}
	/**
	 * @param terminal the terminal to set
	 */
	public void set_TransformerEnd_Map(TwoWindingTransformerMap _twtmap) {
		this.twtmap = _twtmap;
	}
	/**
	 * @return the conductingEquipment
	 */
	public Resource get_PowerTransformer_Resource() {
		return powerTransformer;
	}
	/**
	 * @param conductingEquipment the conductingEquipment to set
	 */
	public void set_PowerTransformer_Resource(Resource _powerTransformer) {
		this.powerTransformer = _powerTransformer;
	}
	
	/**
	 * @return the terminalEnd
	 */
	public Resource get_Terminal_Resource() {
		return terminal;
	}
	/**
	 * @param _terminalEnd the terminalEnd to set
	 */
	public void set_Terminal_Resource(Resource _terminal) {
		this.terminal = _terminal;
	}
	
	/**
	 * @return the ce_id
	 */
	public String get_PowerTransformer_RdfID() {
		return pt_id;
	}
	/**
	 * @param ce_id the ce_id to set
	 */
	public void set_PowerTransformer_RdfID(String _pt_id) {
		this.pt_id = _pt_id;
	}
	/**
	 * @return the te_id
	 */
	public String get_Terminal_RdfID() {
		return t_id;
	}
	/**
	 * @param te_id the te_id to set
	 */
	public void set_Terminal_RdfID(String _t_id) {
		this.t_id = _t_id;
	}
}
