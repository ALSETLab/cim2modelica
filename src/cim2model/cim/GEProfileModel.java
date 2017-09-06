package cim2model.cim;

import java.util.HashMap;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;

public class GEProfileModel {
	
	private static Resource pointTag;
	private static Resource locationTag;
	private static Property locationResource, equipmentResource;
	private static Property coordX;
	private static Property coordY;
	
	private Map<String, Object[]> coordinates;
	// private Map<Resource, RDFNode> location;
	private Model rdfModel;
	static final String CIMns= "http://iec.ch/TC57/2013/CIM-schema-cim16#";

	/**
	 * 
	 */
	public GEProfileModel()
	{
		pointTag= ResourceFactory.createResource(CIMns+"PositionPoint");
		locationTag= ResourceFactory.createResource(CIMns+"Location");
		locationResource= ResourceFactory.createProperty(CIMns+ "PositionPoint.Location");
		equipmentResource= ResourceFactory.createProperty(CIMns+ "Location.PowerSystemResources");
		coordX= ResourceFactory.createProperty(CIMns+ "PositionPoint.xPosition");
		coordY= ResourceFactory.createProperty(CIMns+ "PositionPoint.yPosition");
		coordinates= new HashMap<String, Object[]>();
		// location= new HashMap<Resource, RDFNode>();
	}
	/**
	 * 
	 * @param _model
	 */
	public GEProfileModel(Model _model)
	{
		this.rdfModel= _model;
		pointTag= ResourceFactory.createResource(CIMns+"PositionPoint");
		locationTag= ResourceFactory.createResource(CIMns+"Location");
		locationResource= ResourceFactory.createProperty(CIMns+ "PositionPoint.Location");
		equipmentResource= ResourceFactory.createProperty(CIMns+ "Location.PowerSystemResources");
		coordX= ResourceFactory.createProperty(CIMns+ "PositionPoint.xPosition");
		coordY= ResourceFactory.createProperty(CIMns+ "PositionPoint.yPosition");
		coordinates= new HashMap<String, Object[]>();
		// location= new HashMap<Resource, RDFNode>();
	}
	
	// private boolean exist_Coordinate(String _uri)
	// {
	// Iterator<String> it= this.coordinates.keySet().iterator();
	// boolean found= false;
	//
	// while (!found && it.hasNext())
	// {
	// found= it.next().equals(_uri);
	// }
	// return found;
	// }
	/**
	 * 
	 * @param _attribute
	 */
	public String[] gather_Buses_Coordinates(String _busID)
	{
		String[] coordinates= {"0","1"};
		boolean foundLocation= false, foundPoint= false;
		Resource tagLocation= null, tagPoint= null;
		final ResIterator itLocation= this.rdfModel.listResourcesWithProperty(RDF.type, locationTag);
		final ResIterator itPoint= this.rdfModel.listResourcesWithProperty(RDF.type, pointTag);
		System.out.println(_busID);
		while (!foundLocation && itLocation.hasNext())
		{
			tagLocation= itLocation.next();
			String tagLocationEquipment= tagLocation.getProperty(equipmentResource).
					getObject().toString().split("#")[1];
			foundLocation= tagLocationEquipment.equals(_busID);
			if (foundLocation)
			{
				String rdfidlocation= tagLocation.getLocalName();
				while (!foundPoint && itPoint.hasNext())
				{
					tagPoint= itPoint.next();
					if (tagPoint.getProperty(locationResource).
							getObject().toString().split("#")[1].equals(rdfidlocation)){
						foundPoint= true;
						coordinates[0]= tagPoint.getProperty(coordX).getLiteral().getValue().toString();
						coordinates[1]= tagPoint.getProperty(coordY).getLiteral().getValue().toString();
					}
				}
			}
			
		}
		return coordinates;
	}
		
	
	public void clearAttributes()
	{
		this.coordinates.clear();
	}
}