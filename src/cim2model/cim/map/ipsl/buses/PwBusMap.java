package cim2model.cim.map.ipsl.buses;

import javax.xml.bind.annotation.XmlRootElement;
import cim2model.cim.map.ComponentMap;


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
 *         &lt;element ref="{}attributeMap" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="_package" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="stereotype" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "pwBusMap")
public class PwBusMap extends ComponentMap{

    @Override
    public String toString()
    {
    	return this.stereotype+ " "+ this._package+" "+ this.name+ 
    			" from "+ this.cimName+ " with id "+ this.rdfId;
    }
    
    public boolean equals(String _value)
    {
    	return false;
    }
}
