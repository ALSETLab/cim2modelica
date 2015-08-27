package cim2model;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import cim2model.mapping.*;

public class joke_JAXB {

	private static PwLineMap pwlineXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(PwLineMap.class);
	        un = context.createUnmarshaller();
	        PwLineMap map = (PwLineMap) un.unmarshal(new File(_xmlmap));
	        return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	private static PwPinMap pwpinXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try {
    		context = JAXBContext.newInstance(PwPinMap.class);
    		un = context.createUnmarshaller();
            PwPinMap map = (PwPinMap) un.unmarshal(new File(_xmlmap));
            return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static void main(String[] args) 
	{	
		//TODO: Automatizar la creación del mapping, 1. xml, 2. dtd, 3. xsd
		//TODO: Automatizar el proceso de crear las classes mediante JAXB/xjc
		PwLineMap line= pwlineXMLToObject("./res/cim_iteslalibrary_pwline.xml");
		System.out.println(line.toString());
		PwPinMap pin= pwpinXMLToObject("./res/cim_iteslalibrary_pwpin.xml");
		System.out.println(pin.toString());
	}
}
