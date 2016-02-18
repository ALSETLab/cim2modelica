//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.21 at 06:27:04 PM CEST 
//

package cim2model.mapping.modelica;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /* MACHINES */
    /**
     * Create an instance of {@link GENROUMap }
     * 
     */
    public GENROUMap createGENROUMap() {
        return new GENROUMap();
    }
    /**
     * Create an instance of {@link GENROUMap }
     * 
     */
    public GENSALMap createGENSALMap() {
        return new GENSALMap();
    }
    /* ### */
    
    /**
     * Create an instance of {@link PwPinMap }
     * 
     */
    public PwPinMap createPwPinMap() {
        return new PwPinMap();
    }
    
    /**
     * Create an instance of {@link PwLineMap }
     * 
     */
    public PwLineMap createPwLineMap() {
        return new PwLineMap();
    }

    /**
     * Create an instance of {@link PwBusMap }
     * 
     */
    public PwBusMap createPwBusMap() {
        return new PwBusMap();
    }
    
    /**
     * Create an instance of {@link PwBusMap }
     * 
     */
    public BusExt2Map createBusExt2Map() {
        return new BusExt2Map();
    }
    
    /**
     * Create an instance of {@link PwLoadPQMap }
     * 
     */
    public PwLoadPQMap createPwLoadPQMap() {
        return new PwLoadPQMap();
    }
    
    /**
     * Create an instance of {@link PwLoadPQMap }
     * 
     */
    public ConstantLoadMap createConstantLoadMap() {
        return new ConstantLoadMap();
    }
    
    /**
     * Create an instance of {@link TwoWindingTransformerMap }
     * 
     */
    public TwoWindingTransformerMap createTwoWindingTransformerMap() {
        return new TwoWindingTransformerMap();
    }
    
    /**
     * Create an instance of {@link Ieeet1Map }
     * 
     */
    public Ieeet1Map createIeeet1Map() {
        return new Ieeet1Map();
    }
    
    /**
     * Create an instance of {@link MapAttribute }
     * 
     */
    public MapAttribute createMapAttribute() {
        return new MapAttribute();
    }
    
    /**
     * Create an instance of {@link MapEquation }
     * 
     */
    public MapEquation createMapEquation() {
        return new MapEquation();
    }

    /**
     * Create an instance of {@link ComponentAttribute }
     * 
     */
    public ComponentAttribute createComponentAttribute() {
        return new ComponentAttribute();
    }

    /**
     * Create an instance of {@link MathOperation }
     * 
     */
    public MathOperation createMathOperation() {
        return new MathOperation();
    }

    /**
     * Create an instance of {@link MathWord }
     * 
     */
    public MathWord createMathWord() {
        return new MathWord();
    }

}
