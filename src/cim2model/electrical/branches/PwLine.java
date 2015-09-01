package cim2model.electrical.branches;

import java.util.ArrayList;

import cim2model.electrical.connectors.PwPin;
import cim2model.model.modelica.*;

public class PwLine extends MOClass
{
	private float length;
	private float R;
	private float X;
	private float G;
	private float B;
	private ArrayList<PwPin> terminales;
	
	public PwLine(float _l, float _r, float _x, float _g, float _b)
	{
		super(PwPin.class.getName());
		this.length= _l;
		this.R= _r;
		this.X= _x;
		this.G= _g;
		this.B= _b;
	}

	/**
	 * @return the length
	 */
	public float get_Length() {
		return length;
	}

	/**
	 * @return the r
	 */
	public float get_R() {
		return R;
	}

	/**
	 * @return the x
	 */
	public float get_X() {
		return X;
	}

	/**
	 * @return the g
	 */
	public float get_G() {
		return G;
	}

	/**
	 * @return the b
	 */
	public float get_B() {
		return B;
	}

	/**
	 * @return the terminales
	 */
	public ArrayList<PwPin> getTerminales() {
		return terminales;
	}

	/**
	 * @param terminales the terminales to set
	 */
	public void addTerminales(PwPin _terminal) {
		this.terminales.add(_terminal);
	}
	
	
}
