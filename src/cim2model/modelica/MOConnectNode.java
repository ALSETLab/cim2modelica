package cim2model.modelica;

/**
 * Implementation of a tuple, to store pair of MOClass that are connected to each other
 * @author fragom
 *
 */
public class MOConnectNode 
{
	public final String id_equipment, pin_equipment;
	public final String id_bus, pin_bus;
    
	/**
	 * Connecting equipment with bus/equipment
	 * @param _u
	 * @param _pin_u
	 * @param _y
	 * @param _pin_y
	 */
    public MOConnectNode(String _u, String _pin_u, String _y, String _pin_y) 
    {
      this.id_equipment = _u;
      this.pin_equipment = _pin_u;
      this.id_bus = _y;
      this.pin_bus = _pin_y;
    }
    
    /**
	 * @return the id_component_u
	 */
	public String getId_component_u() {
		return id_equipment;
	}

	/**
	 * @return the pin_component_u
	 */
	public String getPin_component_u() {
		return pin_equipment;
	}

	/**
	 * @return the id_component_y
	 */
	public String getId_component_y() {
		return id_bus;
	}

	/**
	 * @return the pin_component_y
	 */
	public String getPin_component_y() {
		return pin_bus;
	}

	public String to_ModelicaEquation(String isNetwork)
	{
		String code= "";
		if (isNetwork.equals("network"))
			code= this.connect_equipmentNetwork();
		if (isNetwork.equals("plant"))
			code= this.connect_equipmentPlant();
		return code;
	}
	/**
     * Writes connect equation between network equipment, at network level, 
     * Method contain a little trick: 
     * to convert name of terminal into p or n, depending on T1 or T2
     * to convert name of terminal from bus, all p
     * @return
     */
    private String connect_equipmentNetwork()
    {
    	String code= "";
		StringBuilder pencil= new StringBuilder();
		
		pencil.append("connect(");
		pencil.append(this.id_equipment);
		pencil.append(".");
		pencil.append(this.pin_equipment);
//		pencil.append(this.pin_component_u);
		pencil.append(", ");
		pencil.append(this.id_bus);
		pencil.append(".");
		pencil.append(this.pin_bus);
//		if (this.id_component_y.substring(0, 3).equals("BUS"))
//			pencil.append("p");
//		else
//		if (this.pin_component_y.substring(this.pin_component_y.length()- 2, 
//				this.pin_component_y.length()).equals("T1"))
//			pencil.append("p");
//		else
//			pencil.append("n");
//		pencil.append(this.pin_component_y);
		pencil.append(");"); pencil.append("\n");
		
		code= pencil.toString();
		pencil= null;
		
		return code;
    }
    
    /**
     * Used to assemble the plant: connect the machine with the external Pin
     * @return
     */
    private String connect_equipmentPlant()
    {
    	String code= "";
		StringBuilder pencil= new StringBuilder();
		
		pencil.append("connect(");
		pencil.append(this.id_equipment);
		pencil.append(".");
		pencil.append(this.pin_equipment);
		pencil.append(", ");
		if (!this.id_bus.equals("")){
			pencil.append(this.id_bus);
			pencil.append(".");
			pencil.append(this.pin_bus);
		}
		else
			pencil.append(this.pin_bus);
		pencil.append(");"); pencil.append("\n");
		
		code= pencil.toString();
		pencil= null;
		
		return code;
    }
}
