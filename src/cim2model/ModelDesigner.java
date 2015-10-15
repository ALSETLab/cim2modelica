package cim2model;

import java.util.ArrayList;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import cim2model.io.CIMReaderJENA;
import cim2model.model.cim.CIMModel;

/**
 * Read mapping files and create appropriate objects ComponentMap, 
 * Get corresponding values from CIM model, into objects ComponentMap
 * Save objects ComponentMap in memory
 * @author fran_jo
 *
 */
public class ModelDesigner 
{
	ArrayList<Object> modelmap;
	Map<Resource, RDFNode> components;
	CIMReaderJENA cimReader;
	CIMModel cim;
	
	public ModelDesigner()
	{
		
	}
	
	public void read_CIMModel(String _cimSource)
	{
		cimReader= new CIMReaderJENA(_cimSource);
		cim = new CIMModel(cimReader.readModel());
		//???
		components = cim.gatherComponents();
	}
	
	public void write_ModelicaMap()
	{
		//1st, Terminal
		//2nd, Componenent associated to that/those Terminal
	}
	
	public ArrayList<Object> get_ModelMap()
	{
		return this.modelmap;
	}
}
