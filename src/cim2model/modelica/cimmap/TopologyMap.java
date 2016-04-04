package cim2model.modelica.cimmap;

/**
 * Stores the relation of conducting equipment and topological node through the name of the terminal
 * @author fragom
 *
 */
import org.apache.jena.rdf.model.Resource;

public class TopologyMap 
{
	private Resource t_id;
	private Resource ce_id;
	private Resource tn_id;
	
	
	public TopologyMap(Resource t_id, Resource ce_id, Resource tn_id) {
		this.t_id = t_id;
		this.ce_id = ce_id;
		this.tn_id = tn_id;
	}
	/**
	 * @return the t_id
	 */
	public Resource get_T_id() {
		return t_id;
	}
	/**
	 * @param t_id the t_id to set
	 */
	public void set_T_id(Resource t_id) {
		this.t_id = t_id;
	}
	/**
	 * @return the ce_id
	 */
	public Resource get_Ce_id() {
		return ce_id;
	}
	/**
	 * @param ce_id the ce_id to set
	 */
	public void set_Ce_id(Resource ce_id) {
		this.ce_id = ce_id;
	}
	/**
	 * @return the tn_id
	 */
	public Resource get_Tn_id() {
		return tn_id;
	}
	/**
	 * @param tn_id the tn_id to set
	 */
	public void set_Tn_id(Resource tn_id) {
		this.tn_id = tn_id;
	}
//	/* (non-Javadoc)
//	 * @see java.lang.Object#toString()
//	 */
//	@Override
//	public String toString() {
//		return "ConnectionMap [t_id=" + t_id + ", ce_id=" + ce_id + ", tn_id="
//				+ tn_id + "]";
//	}
	
}
