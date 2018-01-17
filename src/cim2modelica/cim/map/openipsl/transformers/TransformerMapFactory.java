package cim2model.cim.map.openipsl.transformers;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class TransformerMapFactory 
{
	private TransformerMapFactory(){}
	
	private static class SingletonHelper{
		private static final TransformerMapFactory INSTANCE= new TransformerMapFactory();
	}
	
	public static TransformerMapFactory getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	/**
	 * 
	 * @param _xmlmap
	 * @return
	 */
	public TwoWindingTransformerMap twtXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;
		
		try{
			context = JAXBContext.newInstance(TwoWindingTransformerMap.class);
	        un = context.createUnmarshaller();
	        TwoWindingTransformerMap map = (TwoWindingTransformerMap) un.unmarshal(new File(_xmlmap));
	        return map;
        } 
        catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
