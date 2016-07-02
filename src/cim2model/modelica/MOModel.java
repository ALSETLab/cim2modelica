package cim2model.modelica;

/**
 * Generic class which contains general attributes to create a Modelica class
 * @author fragom
 *
 */
public abstract class MOModel 
{
	//for internal identification only
	private String rfdId;
	protected String name;
	protected String stereotype;
	protected String pakage;
	protected String comment;
	protected String annotation;
	
	public MOModel(String _name, String _stereotype)
	{
		this.name= _name;
		this.stereotype= _stereotype;
		this.pakage= "package";
		this.comment= "something here";
		this.annotation= "annotation ()";
		this.rfdId= "";
	}
	
	/**
	 * @return the rfdId
	 */
	public String get_RfdId() {
		return rfdId;
	}

	/**
	 * @param rfdId the rfdId to set
	 */
	public void set_RfdId(String rfdId) {
		this.rfdId = rfdId;
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
}
