//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.03 at 04:27:51 PM CET 
//


package cim2model.cim.map.ipsl.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="mo_datatype" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="mo_name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="mo_variability" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="mo_visibility" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "content"
})
@XmlRootElement(name = "bindingVariableMap")
public class BindingVariableMap {

    @XmlValue
    protected String content;
    @XmlAttribute(name = "mo_datatype", required = true)
    protected String moDatatype;
    @XmlAttribute(name = "mo_name", required = true)
    protected String moName;
    @XmlAttribute(name = "mo_variability", required = true)
    protected String moVariability;
    @XmlAttribute(name = "mo_visibility", required = true)
    protected String moVisibility;

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Gets the value of the moDatatype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMoDatatype() {
        return moDatatype;
    }

    /**
     * Sets the value of the moDatatype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMoDatatype(String value) {
        this.moDatatype = value;
    }

    /**
     * Gets the value of the moName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMoName() {
        return moName;
    }

    /**
     * Sets the value of the moName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMoName(String value) {
        this.moName = value;
    }

    /**
     * Gets the value of the moVariability property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMoVariability() {
        return moVariability;
    }

    /**
     * Sets the value of the moVariability property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMoVariability(String value) {
        this.moVariability = value;
    }

    /**
     * Gets the value of the moVisibility property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMoVisibility() {
        return moVisibility;
    }

    /**
     * Sets the value of the moVisibility property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMoVisibility(String value) {
        this.moVisibility = value;
    }

}