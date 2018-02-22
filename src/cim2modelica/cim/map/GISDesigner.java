package cim2modelica.cim.map;

import cim2modelica.cim.EQProfileModel;
import cim2modelica.cim.GEProfileModel;
import cim2modelica.utils.ReaderCIM;

/**
 * 
 * @author fran_jo
 *
 */
public class GISDesigner 
{
	ReaderCIM readerGE, readerEQ;
	EQProfileModel profileEQ;
	GEProfileModel profileGE;
	
	public GISDesigner(String _source_EQ_profile, String _source_GE_profile)
	{
		readerGE= new ReaderCIM(_source_GE_profile);
		readerEQ= new ReaderCIM(_source_EQ_profile);
	}
	
	/**
	 * 
	 * @param _substationID
	 * @return
	 */
	public String[] create_coordinatesSubstation(String _substationID)
	{
		String[] coordinates= {"0","1"};
		coordinates= profileGE.gather_Buses_Coordinates(_substationID);
		return coordinates;
	}
	
	public String[] check_substationID_Region(String _substationID)
	{
		String[] info= {"0","1"};
		// info[0]= profileEQ.gather_susbstationRegion(_substationID);
		// info[1]= profileEQ.gather_susbstationID(_substationID);
		return info;
	}
}
	
