package cim2model.model;

import org.jdom2.Element;

public interface Mapping 
{
	public void loadMapping(String _rdfSource);
	public void saveMapping();
	public Element getCIMAttributeMap();
	public void setCIMAttributeMap(String _cimAtt, String _value, String _moAtt, String _type, String _datatype);
	public int generateCode(String _language);
}
