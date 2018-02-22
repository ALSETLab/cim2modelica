package cim2modelica;

import cim2modelica.cim.map.GISDesigner;

public class CIM2GIS 
{
	private static String xmlns_cim= "http://iec.ch/TC57/2013/CIM-schema-cim16#";
	private static GISDesigner cartografo;
	private static String source_EQ_profile;
	// private static String source_TP_profile;
	// private static String source_SV_profile;
	private static String source_GE_profile;
	
	public static void setUp (String[] args)
	{
		source_EQ_profile= args[0];
		// source_TP_profile= args[1];
		// source_SV_profile= args[2];
		source_GE_profile= args[3];
		cartografo= new GISDesigner(source_EQ_profile, source_GE_profile);
	}
	
	public static void coordinates_network(String _source)
	{//creation of the coordinates file
	// Map<Resource, String> profile_EQ;
	// String substationID, substationName;
	// String[] coordinates;
	//
	// profile_EQ= cartografo.load_EQ_profile(xmlns_cim);
	// cartografo.load_GE_profile(xmlns_cim);
	// for (Resource tag : profile_EQ.keySet())
	// {
	// substationID= tag.getLocalName();
	// substationName= profile_EQ.get(tag);
	// coordinates= cartografo.create_coordinatesSubstation(substationID);
	// System.out.print(substationName+ " - ");
	// System.out.println(coordinates[0]+ " ; "+ coordinates[1]);
	// }
	}
	
	public static void main(String[] args) 
	{
		setUp(args);
		coordinates_network(source_GE_profile);
		
	}
		
}
