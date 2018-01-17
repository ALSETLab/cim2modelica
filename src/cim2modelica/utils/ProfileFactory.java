package cim2modelica.utils;

import cim2modelica.cim.CIMProfile;
import cim2modelica.cim.CIMProfileType;
import cim2modelica.cim.DYProfileModel;
import cim2modelica.cim.EQProfileModel;
import cim2modelica.cim.SVProfileModel;
import cim2modelica.cim.TPProfileModel;

public class ProfileFactory {
	public CIMProfile getProfile(String _source_Profile,
			CIMProfileType _whichProfile) {
		if (_whichProfile == null) {
			return null;
		}
		if (_whichProfile.equals(CIMProfileType.EQUIPMENT)) {
			return new EQProfileModel(_source_Profile);
		} else if (_whichProfile.equals(CIMProfileType.TOPOLOGY)) {
			return new TPProfileModel(_source_Profile);
		} else if (_whichProfile.equals(CIMProfileType.STATE_VARIABLE)) {
			return new SVProfileModel(_source_Profile);
		} else if (_whichProfile.equals(CIMProfileType.DYNAMICS)) {
			return new DYProfileModel(_source_Profile);
		}

		return null;
	}
}
