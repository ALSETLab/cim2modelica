package cim2model.utils;

import cim2model.cim.CIMProfile;
import cim2model.cim.CIMProfileType;
import cim2model.cim.DYProfileModel;
import cim2model.cim.EQProfileModel;
import cim2model.cim.SVProfileModel;
import cim2model.cim.TPProfileModel;

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
