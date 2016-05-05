package cim2model.ipsl.connectors;

import cim2model.ipsl.cimmap.MapAttribute;

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
	
//	try 
//	{ 
//		double voltage= Double.valueOf(mapTerminal.getMapAttribute("vr").getContent());
//		double apower= Double.valueOf(mapTerminal.getMapAttribute("P").getContent());
//		double angle= Double.valueOf(mapTerminal.getMapAttribute("vi").getContent());
//		double current= apower/(voltage*Math.cos(angle));
//		newmapAtt= new MapAttribute();
//		newmapAtt.setCimName("ir");
//		newmapAtt.setMoName("ir");
//		newmapAtt.setContent(Double.toString(current));
//		newmapAtt.setDatatype("Real");
//		newmapAtt.setVariability("none");
//		newmapAtt.setVisibility("public");
//		newmapAtt.setFlow("true");
//		mapTerminal.setMapAttribute(newmapAtt);
//		newmapAtt= new MapAttribute();
//		newmapAtt.setCimName("ii");
//		newmapAtt.setMoName("ii");
//		newmapAtt.setContent(Double.toString(current));
//		newmapAtt.setDatatype("Real");
//		newmapAtt.setVariability("none");
//		newmapAtt.setVisibility("public");
//		newmapAtt.setFlow("true");
//		mapTerminal.setMapAttribute(newmapAtt);
//	}
//	catch (NumberFormatException nfe)
//	{
//		System.err.println(nfe.getLocalizedMessage());
//	}
//	catch (NullPointerException npe)
//	{
//		System.out.println("This Terminal does not have power flow associated!");
//		System.err.println(npe.getLocalizedMessage());
//	}
}
