package cim2model.mapping;

public interface Mapping 
{
	public void loadMapping(String _xmlSource);
	public void loadCIMModel(String _rdfSource);
	public int createModel(String _language);
}
