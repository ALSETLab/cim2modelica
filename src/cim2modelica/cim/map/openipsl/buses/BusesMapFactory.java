package cim2modelica.cim.map.openipsl.buses;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class BusesMapFactory 
{
	private BusesMapFactory(){}
	
	private static class SingletonHelper{
		private static final BusesMapFactory INSTANCE= new BusesMapFactory();
	}
	
	public static BusesMapFactory getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	/**
	 * 
	 * @param _xmlmap
	 * @return
	 */
	public PwBusMap pwbusXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(PwBusMap.class);
	        un = context.createUnmarshaller();
	        PwBusMap map = (PwBusMap) un.unmarshal(new File(_xmlmap));
	        return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
