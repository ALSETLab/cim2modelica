package cim2model.electrical.connectors;

import cim2model.model.modelica.*;

public class PwPin extends MOConnector
{
	private float vr;
	private float vi;
	private float ir;
	private float ii;
	
	public PwPin(float _vr, float _vi, float _ir, float _ii)
	{
		super(PwPin.class.getName());
		this.vr= _vr;
		this.vi= _vi;
		this.ir= _ir;
		this.ii= _ii;
	}

	/**
	 * @return the vr
	 */
	public float get_Vr() {
		return vr;
	}

	/**
	 * @return the vi
	 */
	public float get_Vi() {
		return vi;
	}

	/**
	 * @return the ir
	 */
	public float get_Ir() {
		return ir;
	}

	/**
	 * @return the ii
	 */
	public float get_Ii() {
		return ii;
	}
	
}
