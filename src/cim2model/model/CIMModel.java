package cim2model.model;

import java.util.HashMap;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class CIMModel {
	
//	private String id;
	private Map<String, Object> attribute;
	private Map<Resource, RDFNode> component;
	private Model rdfModel;

	public CIMModel()
	{
//		id= "";
		attribute= new HashMap<String, Object>();
		component= new HashMap<Resource, RDFNode>();
	}
	public CIMModel(Model _model)
	{
		this.rdfModel= _model;
//		id= "";
		attribute= new HashMap<String, Object>();
		component= new HashMap<Resource, RDFNode>();
	}
	
	public Map<Resource,RDFNode> gatherComponents()
	{
		final StmtIterator stmtiter = this.rdfModel.listStatements();
		while( stmtiter.hasNext() ) 
		{
			Statement stmt= stmtiter.next();
		    Resource s = stmt.getSubject();
            Resource p = stmt.getPredicate();
            RDFNode o = stmt.getObject();
            //p as "type" means that the statment is refering to the component
            if (p.isURIResource() && p.getLocalName().equals("type"))
            {
            	this.component.put(s, o);
//            	System.out.println("Subject : "+ s.getLocalName());
//            	System.out.println("Predicate : "+ p.getLocalName());
//            	System.out.print("Object : "+ o.toString());
//            	String [] componentName= o.toString().split("#");
//            	System.out.println(" : "+ componentName[1]);
            }
		}
		
		return this.component;
		//post: Hashtable with cim id of the class (key) and the rdf name of the cim component (value)
	}
	
	public Map<String,Object> retrieveAttributes(Resource _subject)
	{
		System.out.println("Attributes");
		System.out.println(_subject.toString());
		StmtIterator statements= _subject.listProperties();
		while( statements.hasNext() ) 
		{
		    Statement stmt= statements.next();
			System.out.println("Statement -> "+ stmt);
			System.out.println("Predicate -> "+ stmt.getPredicate());
			System.out.println("Attribute -> "+ stmt.getPredicate().getLocalName()); //name of the variable
//			System.out.println("Value -> "+ stmt.getBag()); //value of the variable as String
			System.out.println("Value -> "+ stmt.getAlt()); //value of the variable as String
			this.attribute.put(stmt.getPredicate().getLocalName(), stmt.getAlt());
		}
		
		return this.attribute;
	}
}
