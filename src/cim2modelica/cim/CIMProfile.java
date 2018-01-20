package cim2modelica.cim;

import java.util.HashMap;
import java.util.Map;

import org.apache.jena.rdf.model.Model;

import cim2modelica.utils.ReaderCIM;

public abstract class CIMProfile {
	private static final String xmlns_cim = "http://iec.ch/TC57/2013/CIM-schema-cim16#";
	protected Model rdfModel;
	protected Map<String, Object> attribute;

	public CIMProfile(String _source_profile) {
		ReaderCIM reader_profile = new ReaderCIM(_source_profile);
		this.rdfModel = reader_profile.read_profile(xmlns_cim);
		attribute = new HashMap<String, Object>();
		reader_profile = null;
	}

	public void clearAttributes() {
		this.attribute.clear();
	}

}
