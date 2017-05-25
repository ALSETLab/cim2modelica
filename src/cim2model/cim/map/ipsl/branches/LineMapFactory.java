package cim2model.cim.map.ipsl.branches;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class LineMapFactory 
{
	private LineMapFactory(){}
	
	private static class SingletonHelper{
		private static final LineMapFactory INSTANCE= new LineMapFactory();
	}
	
	public static LineMapFactory getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	/**
	 * 
	 * @param _xmlmap
	 * @return
	 */
	public PwLineMap pwlineXMLToObject(String _xmlmap) {
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
}
