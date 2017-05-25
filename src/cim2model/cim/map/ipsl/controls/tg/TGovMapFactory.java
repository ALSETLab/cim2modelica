package cim2model.cim.map.ipsl.controls.tg;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class TGovMapFactory 
{
	private TGovMapFactory(){}
	
	private static class SingletonHelper{
		private static final TGovMapFactory INSTANCE= new TGovMapFactory();
	}
	
	public static TGovMapFactory getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	/**
	 * 
	 * @param _xmlmap
	 * @return
	 */
	public IEESGOMap ieesgoXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(IEESGOMap.class);
	        un = context.createUnmarshaller();
	        IEESGOMap map = (IEESGOMap) un.unmarshal(new File(_xmlmap));
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
	public HYGOVMap hygovXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(HYGOVMap.class);
	        un = context.createUnmarshaller();
	        HYGOVMap map = (HYGOVMap) un.unmarshal(new File(_xmlmap));
	        return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
