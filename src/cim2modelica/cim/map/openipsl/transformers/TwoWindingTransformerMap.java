package cim2modelica.cim.map.openipsl.transformers;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cim2modelica.cim.map.ComponentMap;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *     &lt;extension base="ComponentMap"&gt;
 *       &lt;attribute name="PowerTransformer" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="RatioTapChanger" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="Terminal" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "twoWindingTransformerMap")
public class TwoWindingTransformerMap extends ComponentMap{

    @XmlAttribute(name = "PowerTransformer", required = true)
    protected String powerTransformer;
    @XmlAttribute(name = "RatioTapChanger", required = true)
    protected String ratioTapChanger;
    @XmlElement(name = "Terminal", required = true)
    protected String terminal;

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
     * Gets the value of the Terminal property.
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
     * Sets the value of the Terminal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerminal(String value) {
    	this.terminal = value;
    }
}
