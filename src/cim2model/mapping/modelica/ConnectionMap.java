package cim2model.mapping.modelica;

/**
 * Stores the relation of conducting equipment and topological node through the name of the terminal
 * @author fragom
 *
 */
public class ConnectionMap 
{
	private String t_id;
	private String ce_id;
	private String tn_id;
	
	
	
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConnectionMap [t_id=" + t_id + ", ce_id=" + ce_id + ", tn_id="
				+ tn_id + "]";
	}
	
}
