package cim2model.io;
import java.io.File;
import java.io.InputStream;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;


public class CIMReaderJENA {
	
	Model model;
	InputStream in;
	
	public CIMReaderJENA(String path, String file)
	{
		//open CIM/XML file
		model = ModelFactory.createDefaultModel();
		File xmlFile = new File(path +"/"+ file);
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

	public Model readModel()
	{
		// read the RDF/XML file
//		model.read(in, "RDF/XML");
		model.read(in, "http://iec.ch/TC57/2012/CIM-schema-cim16#");
		
		return model;
	}
	
	public void writeModel(String _destiny)
	{
		this.model.write(System.out, null, "TURTLE");
	}
	
	public void writeModel(Model _rdfModel, String _destiny)
	{
		_rdfModel.write(System.out, null, "TURTLE");
	}
		
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
