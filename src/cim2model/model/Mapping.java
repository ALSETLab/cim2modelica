package cim2model.model;

import java.util.ArrayList;

public interface Mapping 
{
	public void addComponent(String _name);
	public void addAttribute(String _moAtt, String _cimAtt, String _type, String _datatype);
	public int generateCode(String _language);
}
