package cim2model.io;
import java.io.File;
import java.io.InputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.util.FileManager;

/**
 * 
 * @author fragom
 *
 */
public class CIMReaderJENA {
	
	Model model;
	InputStream in;
	
	/**
	 * 
	 * @param path
	 * @param file
	 */
	public CIMReaderJENA(String _source_CIM_profile)
	{
		//open CIM/XML file
		model = ModelFactory.createDefaultModel();
		File xmlFile = new File(_source_CIM_profile);
		System.out.println(xmlFile.getAbsolutePath());
		try
		{
			// use the FileManager to find the input file
			in = FileManager.get().open(xmlFile.getAbsolutePath());
		}
		catch (IllegalArgumentException iaex) {
			System.out.println("iaex");
			System.out.println(iaex.getMessage());
		}
	}

	/**
	 * 
	 * @return
	 */
	public Model readModel()
	{
		model.read(in, "http://iec.ch/TC57/2009/CIM-schema-cim14#");
		
		return model;
	}
	
	/**
	 * 
	 * @param _destiny
	 */
	public void writeModel(String _destiny)
	{
		this.model.write(System.out, null, "TURTLE");
	}
	
	/**
	 * 
	 * @param _rdfModel
	 * @param _destiny
	 */
	public void writeModel(Model _rdfModel, String _destiny)
	{
		_rdfModel.write(System.out, null, "TURTLE");
	}
	
	/**
	 * 
	 */
	public void retrieveResource()
	{
//		final Resource s = model.getResource("file:///C:/Users/fragom/PhD_CIM/JAVA/edu.smartslab.cim2modelica/RDF/XML#_a8ae1d40-1e1f-11e4-a682-080027008896");
		final StmtIterator stmtiter = model.listStatements();
		while( stmtiter.hasNext() ) {
			Statement stmt= stmtiter.next();
//		    System.out.println(stmtiter.next());
		    Resource s = stmt.getSubject();
            Resource p = stmt.getPredicate();
            RDFNode o = stmt.getObject();
            
            if ( s.isURIResource() ) {
                System.out.println("1.URI : "+ s.getLocalName());
            } else if ( s.isAnon() ) {
                System.out.println("1.blank: "+ s.getLocalName());
            }
            
            if ( p.isURIResource() ) 
                System.out.println("2.URI: "+ p.getLocalName());
            
            if ( o.isURIResource() ) {
                System.out.println("3.URI: "+ o.toString());
            } else if ( o.isAnon() ) {
                System.out.println("3.blank: "+ o.getClass().toString());
            } else if ( o.isLiteral() ) {
                System.out.println("3.literal: "+ o.getClass().toString());
            }
		}
	}
}
