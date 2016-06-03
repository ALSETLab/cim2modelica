package cim2model.cim.map.ipsl.transformers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import cim2model.cim.map.AttributeMap;


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
 *         &lt;element ref="{}mapAttribute" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="PowerTransformer" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="RatioTapChanger" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="Terminal" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="cim_name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="package" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="rfd_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="stereotype" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "mapAttribute"
})
@XmlRootElement(name = "twoWindingTransformerMap")
public class TwoWindingTransformerMap {

    @XmlElement(required = true)
    protected List<AttributeMap> mapAttribute;
    @XmlAttribute(name = "PowerTransformer", required = true)
    protected String powerTransformer;
    @XmlAttribute(name = "RatioTapChanger", required = true)
    protected String ratioTapChanger;
    @XmlAttribute(name = "Terminal", required = true)
    protected String terminal;
    @XmlAttribute(name = "cim_name", required = true)
    protected String cimName;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "package", required = true)
    protected String _package;
    @XmlAttribute(name = "rfd_id", required = true)
    protected String rfdId;
    @XmlAttribute(name = "stereotype", required = true)
    protected String stereotype;

    /**
     * Gets the value of the mapAttribute property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mapAttribute property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMapAttribute().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeMap }
     * 
     * 
     */
    public List<AttributeMap> getMapAttribute() {
        if (mapAttribute == null) {
            mapAttribute = new ArrayList<AttributeMap>();
        }
        return this.mapAttribute;
    }

    /**
     * Gets the value of the powerTransformer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPowerTransformer() {
        return powerTransformer;
    }

    /**
     * Sets the value of the powerTransformer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPowerTransformer(String value) {
        this.powerTransformer = value;
    }

    /**
     * Gets the value of the ratioTapChanger property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatioTapChanger() {
        return ratioTapChanger;
    }

    /**
     * Sets the value of the ratioTapChanger property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatioTapChanger(String value) {
        this.ratioTapChanger = value;
    }

    /**
     * Gets the value of the terminal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerminal() {
        return terminal;
    }

    /**
     * Sets the value of the terminal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerminal(String value) {
        this.terminal = value;
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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the package property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackage() {
        return _package;
    }

    /**
     * Sets the value of the package property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackage(String value) {
        this._package = value;
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

    /**
     * Gets the value of the stereotype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStereotype() {
        return stereotype;
    }

    /**
     * Sets the value of the stereotype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStereotype(String value) {
        this.stereotype = value;
    }

}
