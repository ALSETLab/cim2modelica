package cim2modelica.cim.map.openipsl.loads;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class LoadMapFactory {
	private LoadMapFactory() {
	}

	private static class SingletonHelper {
		private static final LoadMapFactory INSTANCE = new LoadMapFactory();
	}

	public static LoadMapFactory getInstance() {
		return SingletonHelper.INSTANCE;
	}

	/**
	 * 
	 * @param _xmlmap
	 * @return
	 */
	public LoadMap loadXMLToObject(String _xmlmap) {
		JAXBContext context;
		Unmarshaller un;

		try {
			context = JAXBContext.newInstance(LoadMap.class);
			un = context.createUnmarshaller();
			LoadMap map = (LoadMap) un.unmarshal(new File(_xmlmap));
			return map;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
}
