package cim2model.model.modelica;

/**
 * Implementation of a tuple, to store pair of MOClass that are connected to each other
 * @author fragom
 *
 */
public class MOConnect 
{
	public final MOClass component_u;
    public final MOClass component_y;
    
    public MOConnect(MOClass _u, MOClass _y) 
    {
      super();
      this.component_u = _u;
      this.component_y = _y;
    }
    
    @Override
    public String toString() 
    { //TODO: arreglar
      return String.format("(%s, %s)", component_u, component_y);
    }
    
    //TODO: add method to_Modelica()
}
