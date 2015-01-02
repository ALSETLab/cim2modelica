package cim2model.mapping;

public class FactoryMapping 
{
	public static Mapping loadMapping(String _language)
	{
		Mapping map= null;
		
		switch (_language)
		{
		case "modelica":
			map= new ModMapping();
		}
		
		return map;
	}
}
