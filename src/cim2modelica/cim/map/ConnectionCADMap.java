package cim2modelica.cim.map;

/**
 * Stores the relation of conducting equipment and topological node through the
 * name of the terminal
 * 
 * @author fragom
 *
 */
public class ConnectionCADMap extends ConnectionMap {
    private String[] t_pos = { "0", "0" };
    private String[] ce_pos = { "0", "0" };
    private String[] tn_pos = { "0", "0" };
    // TODO related to annotation(Line(points=

    public ConnectionCADMap(String t_id, String ce_id, String tn_id) {
	super(t_id, ce_id, tn_id);
    }

    /**
     * 
     * @param t_pos_x
     * @param t_pos_y
     */
    public void setT_Pos(String t_pos_x, String t_pos_y) {
	this.t_pos[0] = t_pos_x;
	this.t_pos[1] = t_pos_y;
    }

    /**
     * 
     * @return
     */
    public String[] getT_pos() {
	return this.t_pos;
    }

    /**
     * @param ce_pos
     *            the ce_pos to set
     */
    public void setCe_pos(String ce_pos_x, String ce_pos_y) {
	this.ce_pos[0] = ce_pos_x;
	this.ce_pos[1] = ce_pos_y;
    }

    /**
     * @return the ce_pos
     */
    public String[] getCe_pos() {
	return ce_pos;
    }

    /**
     * @param tn_pos
     *            the tn_pos to set
     */
    public void setTn_pos(String tn_pos_x, String tn_pos_y) {
	this.tn_pos[0] = tn_pos_x;
	this.tn_pos[1] = tn_pos_y;
    }
    /**
     * @return the tn_pos
     */
    public String[] getTn_pos() {
	return tn_pos;
    }


}
