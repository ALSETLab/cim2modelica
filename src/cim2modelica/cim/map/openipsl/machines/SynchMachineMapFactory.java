package cim2modelica.cim.map.openipsl.machines;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class SynchMachineMapFactory 
{
	private SynchMachineMapFactory(){}
	
	private static class SingletonHelper{
		private static final SynchMachineMapFactory INSTANCE= new SynchMachineMapFactory();
	}
	
	public static SynchMachineMapFactory getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public GENROUMap genrouXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(GENROUMap.class);
	        un = context.createUnmarshaller();
	        GENROUMap map = (GENROUMap) un.unmarshal(new File(_xmlmap));
	        return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	/**
	 * 
	 * @param _xmlmap
	 * @return
	 */
	public GENSALMap gensalXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(GENSALMap.class);
	        un = context.createUnmarshaller();
	        GENSALMap map = (GENSALMap) un.unmarshal(new File(_xmlmap));
	        return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	/**
	 * 
	 * @param _xmlmap
	 * @return
	 */
	public GENROEMap genroeXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(GENROEMap.class);
	        un = context.createUnmarshaller();
	        GENROEMap map = (GENROEMap) un.unmarshal(new File(_xmlmap));
	        return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
