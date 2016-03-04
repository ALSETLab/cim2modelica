package cim2model.modelica;

import java.util.Map;
import java.util.AbstractMap;

/**
 * Implementation of a tuple, to store pair of MOClass that are connected to each other
 * @author fragom
 *
 */
public class MOConnectNode 
{
	private Map.Entry<String, String> conexio;
	public final String id_component_u, pin_component_u;
	public final String id_component_y, pin_component_y;
    
    public MOConnectNode(String _u, String _pin_u, String _y, String _pin_y) 
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
     * Method contain a little trick: 
     * to convert name of terminal into p or n, depending on T1 or T2
     * to convert name of terminal from bus, all p
     * @return
     */
    public String to_ModelicaEquation()
    {
    	String code= "";
		StringBuilder pencil= new StringBuilder();
		
		pencil.append("connect(");
		pencil.append(this.id_component_u);
		pencil.append(".");
		if (this.pin_component_u.substring(this.pin_component_u.length()- 2, 
				this.pin_component_u.length()).equals("T1"))
			pencil.append("p");
		else
			pencil.append("n");
//		pencil.append(this.pin_component_u);
		pencil.append(", ");
		pencil.append(this.id_component_y);
		pencil.append(".");
		if (this.id_component_y.substring(0, 3).equals("BUS"))
			pencil.append("p");
		else
			if (this.pin_component_y.substring(this.pin_component_y.length()- 2, 
					this.pin_component_y.length()).equals("T1"))
				pencil.append("p");
			else
				pencil.append("n");
//		pencil.append(this.pin_component_y);
		pencil.append(");"); pencil.append("\n");
		
		code= pencil.toString();
		pencil= null;
		
		return code;
    }
}
