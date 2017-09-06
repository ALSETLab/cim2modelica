package cim2model.cim.map;

import java.util.Map;

import org.apache.jena.rdf.model.Resource;

import cim2model.cim.EQProfileModel;
import cim2model.cim.GEProfileModel;
import cim2model.io.ReaderCIM;

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
	
	public Map<Resource, String> load_EQ_profile(String _xmlns_cim)
	{
		profileEQ = new EQProfileModel(readerEQ.read_profile(_xmlns_cim));
		Map<Resource, String> classesEQ = profileEQ.gather_SubstationResource();
		
		return classesEQ;
	}
	
	public void load_GE_profile(String _xmlns_cim)
	{
		profileGE = new GEProfileModel(readerGE.read_profile(_xmlns_cim));
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
	
