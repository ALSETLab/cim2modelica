package cim2modelica.cim.map;

import java.util.ArrayList;

import cim2modelica.cim.CIMProfile;
import cim2modelica.cim.DLProfileModel;

/**
 * Read mapping files and create appropriate objects ComponentMap, Get
 * corresponding values from CIM model into objects ComponentMap, Save objects
 * ComponentMap in memory
 * 
 * @author fran_jo
 *
 */
public class ModelCADDesigner extends ModelDesigner {
    static final String CIMns = "http://iec.ch/TC57/2013/CIM-schema-cim16#";
    ArrayList<ConnectionMap> connections;
    DLProfileModel profile_DL;

    public ModelCADDesigner(CIMProfile _profileEQ, CIMProfile _profileTP, CIMProfile _profileSV,
	    CIMProfile _profileDY, CIMProfile _profileDL) {
	super(_profileEQ, _profileTP, _profileSV, _profileDY);
	this.profile_DL = (DLProfileModel) _profileDL;
	this.connections = new ArrayList<ConnectionMap>();
    }

    public void load_DL_profile() {
	profile_DL.gather_Objects();
	profile_DL.gather_ObjectPoints();
    }
}
