package cim2model.ipsl.machines;

import cim2model.modelica.MOClass;

public class GENROU extends MOClass 
{
	/* Inputs */
	double PMECH;
	double EFD;
	/* Outputs */
	double ANGLE;
	double SPEED;
	double PMECH0;
	double PELEC;
	double ETERM;
	double EFD0;
	double ISORCE;
	double XADIFD;
	
	public GENROU(String _name) {
		super(_name);
	}

	/**
	 * @return the pMECH
	 */
	public double getPMECH() {
		return PMECH;
	}

	/**
	 * @param pMECH the pMECH to set
	 */
	public void setPMECH(double pMECH) {
		PMECH = pMECH;
	}

	/**
	 * @return the eFD
	 */
	public double getEFD() {
		return EFD;
	}

	/**
	 * @param eFD the eFD to set
	 */
	public void setEFD(double eFD) {
		EFD = eFD;
	}

	/**
	 * @return the aNGLE
	 */
	public double getANGLE() {
		return ANGLE;
	}

	/**
	 * @param aNGLE the aNGLE to set
	 */
	public void setANGLE(double aNGLE) {
		ANGLE = aNGLE;
	}

	/**
	 * @return the sPEED
	 */
	public double getSPEED() {
		return SPEED;
	}

	/**
	 * @param sPEED the sPEED to set
	 */
	public void setSPEED(double sPEED) {
		SPEED = sPEED;
	}

	/**
	 * @return the pMECH0
	 */
	public double getPMECH0() {
		return PMECH0;
	}

	/**
	 * @param pMECH0 the pMECH0 to set
	 */
	public void setPMECH0(double pMECH0) {
		PMECH0 = pMECH0;
	}

	/**
	 * @return the pELEC
	 */
	public double getPELEC() {
		return PELEC;
	}

	/**
	 * @param pELEC the pELEC to set
	 */
	public void setPELEC(double pELEC) {
		PELEC = pELEC;
	}

	/**
	 * @return the eTERM
	 */
	public double getETERM() {
		return ETERM;
	}

	/**
	 * @param eTERM the eTERM to set
	 */
	public void setETERM(double eTERM) {
		ETERM = eTERM;
	}

	/**
	 * @return the eFD0
	 */
	public double getEFD0() {
		return EFD0;
	}

	/**
	 * @param eFD0 the eFD0 to set
	 */
	public void setEFD0(double eFD0) {
		EFD0 = eFD0;
	}

	/**
	 * @return the iSORCE
	 */
	public double getISORCE() {
		return ISORCE;
	}

	/**
	 * @param iSORCE the iSORCE to set
	 */
	public void setISORCE(double iSORCE) {
		ISORCE = iSORCE;
	}

	/**
	 * @return the xADIFD
	 */
	public double getXADIFD() {
		return XADIFD;
	}

	/**
	 * @param xADIFD the xADIFD to set
	 */
	public void setXADIFD(double xADIFD) {
		XADIFD = xADIFD;
	}

	
	public void default_connection() {
		
	}
}
