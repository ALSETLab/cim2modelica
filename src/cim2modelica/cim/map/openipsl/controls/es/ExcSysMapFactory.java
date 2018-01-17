package cim2model.cim.map.openipsl.controls.es;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class ExcSysMapFactory 
{
	private ExcSysMapFactory(){}
	
	private static class SingletonHelper{
		private static final ExcSysMapFactory INSTANCE= new ExcSysMapFactory();
	}
	
	public static ExcSysMapFactory getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	/**
	 * 
	 * @param _xmlmap
	 * @return
	 */
	public ESDC1AMap esdc1aXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(ESDC1AMap.class);
	        un = context.createUnmarshaller();
	        ESDC1AMap map = (ESDC1AMap) un.unmarshal(new File(_xmlmap));
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
	public ExcSEXSMap excSexsXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(ExcSEXSMap.class);
	        un = context.createUnmarshaller();
	        ExcSEXSMap map = (ExcSEXSMap) un.unmarshal(new File(_xmlmap));
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
	public ESST1AMap esst1aXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;

		try {
			context = JAXBContext.newInstance(ESST1AMap.class);
			un = context.createUnmarshaller();
			ESST1AMap map = (ESST1AMap) un.unmarshal(new File(_xmlmap));
			return map;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
}
