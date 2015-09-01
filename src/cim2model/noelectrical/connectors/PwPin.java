package cim2model.noelectrical.connectors;

public class PwPin 
{
	private float vr;
	private float vi;
	private float ir;
	private float ii;
	
	public PwPin(float vr, float vi, float ir, float ii) {
		super();
		this.vr = vr;
		this.vi = vi;
		this.ir = ir;
		this.ii = ii;
	}

	/**
	 * @return the vr
	 */
	public float getVr() {
		return vr;
	}

	/**
	 * @param vr the vr to set
	 */
	public void setVr(float vr) {
		this.vr = vr;
	}

	/**
	 * @return the vi
	 */
	public float getVi() {
		return vi;
	}

	/**
	 * @param vi the vi to set
	 */
	public void setVi(float vi) {
		this.vi = vi;
	}

	/**
	 * @return the ir
	 */
	public float getIr() {
		return ir;
	}

	/**
	 * @param ir the ir to set
	 */
	public void setIr(float ir) {
		this.ir = ir;
	}

	/**
	 * @return the ii
	 */
	public float getIi() {
		return ii;
	}

	/**
	 * @param ii the ii to set
	 */
	public void setIi(float ii) {
		this.ii = ii;
	}
}
