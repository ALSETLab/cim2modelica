//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.14 at 08:30:25 PM CET 
//


package cim2model.cim.map;

import java.util.ArrayList;
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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}componentAttribute"/>
 *         &lt;element ref="{}mathOperation" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "componentAttribute",
    "mathOperation"
})
@XmlRootElement(name = "mathWord")
public class MathWord {

    @XmlElement(required = true)
    protected ComponentMap componentAttribute;
    @XmlElement(required = true)
    protected List<MathOperation> mathOperation;
    @XmlAttribute(name = "type", required = true)
    protected String type;

    /**
     * Gets the value of the componentAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link ComponentMap }
     *     
     */
    public ComponentMap getComponentAttribute() {
        return componentAttribute;
    }

    /**
     * Sets the value of the componentAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComponentMap }
     *     
     */
    public void setComponentAttribute(ComponentMap value) {
        this.componentAttribute = value;
    }

    /**
     * Gets the value of the mathOperation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mathOperation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMathOperation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MathOperation }
     * 
     * 
     */
    public List<MathOperation> getMathOperation() {
        if (mathOperation == null) {
            mathOperation = new ArrayList<MathOperation>();
        }
        return this.mathOperation;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}