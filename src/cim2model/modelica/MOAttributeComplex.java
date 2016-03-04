package cim2model.modelica;

/**
 * Generic class to implement the declaration of a Modelica Parameter.
 * @author fragom
 *
 */
public class MOAttributeComplex extends MOAttribute
{
	private String re;
	private String im;
	
	public MOAttributeComplex() {
		super();
		this.re= "0";
		this.im= "0";
	}
	/**
	 * @return the re
	 */
	public String get_Real() {
		return re;
	}
	/**
	 * @param paramtype the re to set
	 */
	public void set_Real(String _re) {
		this.re = _re;
	}
	/**
	 * @param paramtype the re to set
	 */
	public void set_Real(double _re) {
		this.re = String.valueOf(_re);
	}
	/**
	 * @return the im
	 */
	public String get_Imaginary() {
		return im;
	}
	/**
	 * @param paramtype the im to set
	 */
	public void set_Imaginary(String _im) {
		this.im= _im;
	}
	/**
	 * @param paramtype the im to set
	 */
	public void set_Imaginary(double _im) {
		this.im= String.valueOf(_im);
	}
	
	@Override
	public String to_Modelica()
	{
		String code= "";
		StringBuilder pencil= new StringBuilder();
		
		if (this.flow) {
			pencil.append("flow ");
		}
		pencil.append(this.variability); pencil.append(" ");
		pencil.append(this.datatype); pencil.append(" ");
		pencil.append(this.name); 
		pencil.append("(re= "); pencil.append(re); pencil.append(",");
		pencil.append("im= "); pencil.append(im); pencil.append(") ");
		pencil.append('"'+ this.annotation+ '"');
		pencil.append(";");
		code= pencil.toString();
		
		return code;
	}
}
