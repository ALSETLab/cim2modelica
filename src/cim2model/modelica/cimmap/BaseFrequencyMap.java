//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.02 at 03:09:53 PM CET 
//


package cim2model.modelica.cimmap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{}modelVariableMap"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="cim_mRID" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="cim_name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="mo_package" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="mo_stereotype" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "modelVariableMap"
})
@XmlRootElement(name = "baseFrequencyMap")
public class BaseFrequencyMap {

    @XmlElement(required = true)
    protected ModelVariableMap modelVariableMap;
    @XmlAttribute(name = "cim_mRID", required = true)
    protected String cimMRID;
    @XmlAttribute(name = "cim_name", required = true)
    protected String cimName;
    @XmlAttribute(name = "mo_package", required = true)
    protected String moPackage;
    @XmlAttribute(name = "mo_stereotype", required = true)
    protected String moStereotype;

    /**
     * Gets the value of the modelVariableMap property.
     * 
     * @return
     *     possible object is
     *     {@link ModelVariableMap }
     *     
     */
    public ModelVariableMap getModelVariableMap() {
        return modelVariableMap;
    }

    /**
     * Sets the value of the modelVariableMap property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModelVariableMap }
     *     
     */
    public void setModelVariableMap(ModelVariableMap value) {
        this.modelVariableMap = value;
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

}
