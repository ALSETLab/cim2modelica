//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.04 at 01:19:27 PM CET 
//


package cim2model.cim.map.ipsl.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import cim2model.cim.map.ipsl.buses.TopologicalNodeMap;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{}svPowerFlowMap"/&gt;
 *         &lt;element ref="{}topologicalNodeMap"/&gt;
 *         &lt;element ref="{}conductingEquipmentMap"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="cim_mRID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="cim_name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="mo_package" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="mo_stereotype" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="rfd_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "svPowerFlowMap",
    "topologicalNodeMap",
    "conductingEquipmentMap"
})
@XmlRootElement(name = "terminalMap")
public class TerminalMap {

    @XmlElement(required = true)
    protected SvPowerFlowMap svPowerFlowMap;
    @XmlElement(required = true)
    protected TopologicalNodeMap topologicalNodeMap;
    @XmlElement(required = true)
    protected ConductingEquipmentMap conductingEquipmentMap;
    @XmlAttribute(name = "cim_mRID", required = true)
    protected String cimMRID;
    @XmlAttribute(name = "cim_name", required = true)
    protected String cimName;
    @XmlAttribute(name = "mo_package", required = true)
    protected String moPackage;
    @XmlAttribute(name = "mo_stereotype", required = true)
    protected String moStereotype;
    @XmlAttribute(name = "rfd_id", required = true)
    protected String rfdId;

    /**
     * Gets the value of the svPowerFlowMap property.
     * 
     * @return
     *     possible object is
     *     {@link SvPowerFlowMap }
     *     
     */
    public SvPowerFlowMap getSvPowerFlowMap() {
        return svPowerFlowMap;
    }

    /**
     * Sets the value of the svPowerFlowMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link SvPowerFlowMap }
     *     
     */
    public void setSvPowerFlowMap(SvPowerFlowMap value) {
        this.svPowerFlowMap = value;
    }

    /**
     * Gets the value of the topologicalNodeMap property.
     * 
     * @return
     *     possible object is
     *     {@link TopologicalNodeMap }
     *     
     */
    public TopologicalNodeMap getTopologicalNodeMap() {
        return topologicalNodeMap;
    }

    /**
     * Sets the value of the topologicalNodeMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link TopologicalNodeMap }
     *     
     */
    public void setTopologicalNodeMap(TopologicalNodeMap value) {
        this.topologicalNodeMap = value;
    }

    /**
     * Gets the value of the conductingEquipmentMap property.
     * 
     * @return
     *     possible object is
     *     {@link ConductingEquipmentMap }
     *     
     */
    public ConductingEquipmentMap getConductingEquipmentMap() {
        return conductingEquipmentMap;
    }

    /**
     * Sets the value of the conductingEquipmentMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConductingEquipmentMap }
     *     
     */
    public void setConductingEquipmentMap(ConductingEquipmentMap value) {
        this.conductingEquipmentMap = value;
    }

    /**
     * Gets the value of the cimMRID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCimMRID() {
        return cimMRID;
    }

    /**
     * Sets the value of the cimMRID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCimMRID(String value) {
        this.cimMRID = value;
    }

    /**
     * Gets the value of the cimName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCimName() {
        return cimName;
    }

    /**
     * Sets the value of the cimName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCimName(String value) {
        this.cimName = value;
    }

    /**
     * Gets the value of the moPackage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMoPackage() {
        return moPackage;
    }

    /**
     * Sets the value of the moPackage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMoPackage(String value) {
        this.moPackage = value;
    }

    /**
     * Gets the value of the moStereotype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMoStereotype() {
        return moStereotype;
    }

    /**
     * Sets the value of the moStereotype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMoStereotype(String value) {
        this.moStereotype = value;
    }

    /**
     * Gets the value of the rfdId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRfdId() {
        return rfdId;
    }

    /**
     * Sets the value of the rfdId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRfdId(String value) {
        this.rfdId = value;
    }
    
    @Override
    public String toString()
    {
    	String code= "";
		StringBuilder pencil= new StringBuilder();
		
		pencil.append(this.rfdId);
		pencil.append(" ");
		pencil.append(this.cimName);
		pencil.append("\n\t");
		pencil.append(this.conductingEquipmentMap.getRfdId());
		pencil.append(" - ");
		pencil.append(this.conductingEquipmentMap.getCimName());
		pencil.append("\n\t");
		pencil.append(this.topologicalNodeMap.getRfdId());
		pencil.append(" - ");
		pencil.append(this.topologicalNodeMap.getCimName());
		pencil.append(") ");
		code= pencil.toString();
		
		return code;
    }

}