package cim2model.model.modelica;

import java.util.Map;
import java.util.AbstractMap;

/**
 * Implementation of a tuple, to store pair of MOClass that are connected to each other
 * @author fragom
 *
 */
public class MOConnect 
{
	private Map.Entry<String, String> conexio;
	public final String id_component_u, pin_component_u;
	public final String id_component_y, pin_component_y;
    
    public MOConnect(String _u, String _pin_u, String _y, String _pin_y) 
    {
      this.id_component_u = _u;
      this.pin_component_u = _pin_u;
      this.id_component_y = _y;
      this.pin_component_y = _pin_y;
      this.conexio= new AbstractMap.SimpleEntry<String, String>(
    			_u+ "."+ _pin_u, _y+ "."+ _pin_y);
    }
    
    public String get_connection_U(){
    	return this.conexio.getKey();
    }
    
    public String get_connection_Y(){
    	return this.conexio.getValue();
    }
    
    /**
     * 
     * @return
     */
    public String to_ModelicaEquation()
    {
    	String code= "";
		StringBuilder pencil= new StringBuilder();
		
		pencil.append("connect(");
		pencil.append(this.id_component_u);
		pencil.append(".");
		pencil.append(this.pin_component_u);
		pencil.append(", ");
		pencil.append(this.id_component_y);
		pencil.append(".");
		pencil.append(this.pin_component_y);
		pencil.append(");"); pencil.append("\n");
		
		code= pencil.toString();
		pencil= null;
		
		return code;
    }
}
