//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.13 at 05:02:37 PM CEST 
//


package cim2model.mapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
 *         &lt;element ref="{}cimAttribute" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{}moAttribute" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="package" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
    "cimAttribute",
    "moAttribute"
})
@XmlRootElement(name = "pwLoadPQMap")
public class PwLoadPQMap {

    @XmlElement(required = true)
    protected List<MapAttribute> mapAttribute;
    @XmlAttribute(name = "cim_name", required = true)
    protected String cimName;
    @XmlAttribute(name = "rfd_id", required = true)
    protected String rfdId;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "package", required = true)
    protected String _package;
    @XmlAttribute(name = "stereotype", required = true)
    protected String stereotype;

    /**
     * Gets the value of the moAttribute property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the moAttribute property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMoAttribute().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MoAttribute }
     * 
     * 
     */
    public List<MapAttribute> getMapAttribute() {
        if (mapAttribute == null) {
        	mapAttribute = new ArrayList<MapAttribute>();
        }
        return this.mapAttribute;
    }

    public MapAttribute getMapAttribute(String _name)
    {
    	Iterator<MapAttribute> atributos= this.mapAttribute.iterator();
    	MapAttribute atributo;
    	do
    		atributo= atributos.next();
    	while (!atributo.equals(_name) && atributos.hasNext());
    		
    	return atributo;
    }
    
    public void setMapAttribute(MapAttribute _old, MapAttribute _new)
    {
    	int index= this.mapAttribute.indexOf(_old);
    	this.mapAttribute.set(index, _new);
    }
    
    
    /**
     * Gets the value of the name property.
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
     * Sets the value of the name property.
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
    public String getRfdId() {
        return rfdId;
    }

    /**
     * Sets the value of the name property.
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

    @Override
    public String toString()
    {
    	return this.stereotype+ " "+ this._package+" "+ this.name+ 
    			" from "+ this.cimName+ " with id "+ this.rfdId;
    }
    
    public boolean equals(String _value)
    {
    	return false;
    }
}
