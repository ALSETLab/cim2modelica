package cim2model.modelica;

import java.util.ArrayList;

/**
 * Generic class which contains general attributes to create a Modelica class
 * @author fragom
 *
 */
public abstract class MOModel 
{
	//for internal identification only
	protected String rdfid;
	protected String name;
	protected String stereotype;
	protected String pakage;
	protected String comment;
	protected String annotation;
	protected ArrayList<String> coord_eq;
	
	public MOModel(String _name, String _stereotype)
	{
		this.name= _name;
		this.stereotype= _stereotype;
		this.pakage= "package";
		this.comment = "automatically generated comment";
		this.annotation= "annotation ()";
		this.rdfid= "";
		this.coord_eq = new ArrayList<String>();
	}
	
	/**
	 * @return the rfdId
	 */
	public String get_RdfId() {
		return rdfid;
	}

	/**
	 * @param rfdId the rfdId to set
	 */
	public void set_RdfId(String rfdId) {
		this.rdfid = rfdId;
	}
	
	/**
	 * @return the name
	 */
	public String get_Name() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void set_Name(String name) {
		this.name = name;
	}
	/**
	 * @return the stereotype
	 */
	public String get_Stereotype() {
		return stereotype;
	}
	/**
	 * @param stereotype the stereotype to set
	 */
	public void set_Stereotype(String stereotype) {
		this.stereotype = stereotype;
	}
	/**
	 * @return the comment
	 */
	public String get_Comment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void set_Comment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the annotation
	 */
	public String get_Annotation() {
		return annotation;
	}
	/**
	 * @param annotation the annotation to set
	 */
	public void set_Annotation(String annotation) {
		this.annotation = annotation;
	}
	/**
	 * @return the package
	 */
	public String get_Package() {
		return pakage;
	}
	/**
	 * @param pakage the package to set
	 */
	public void set_Package(String pakage) {
		this.pakage = pakage;
	}

	public ArrayList<String> get_Coord() {
		return coord_eq;
	}

	public void set_Coord(String _x, String _y) {
		// this.coord_bus.clear();
		if (_x.substring(_x.length() - 1) == ".")
			_x = _x.substring(0, _x.length() - 1);
		this.coord_eq.add(_x);
		if (_y.substring(_y.length() - 1) == ".")
			_y = _y.substring(0, _y.length() - 1);
		this.coord_eq.add(_y);
	}
}
