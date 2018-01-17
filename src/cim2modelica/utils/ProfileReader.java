package cim2modelica.utils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Class used to retrieve the different CIM profiles from a given folder
 * 
 * @author fran_jo
 *
 */
public class ProfileReader {
	private String cimFolder;
	private String _source_EQ_profile;
	private String _source_TP_profile;
	private String _source_SV_profile;
	private String _source_DY_profile;
	private String _source_DL_profile;

	public ProfileReader(String _currentFolder) {
		this.cimFolder = _currentFolder;
	}

	/**
	 * @return the _source_EQ_profile
	 */
	public String get_source_EQ_profile() {
		return _source_EQ_profile;
	}

	/**
	 * @return the _source_TP_profile
	 */
	public String get_source_TP_profile() {
		return _source_TP_profile;
	}

	/**
	 * @return the _source_SV_profile
	 */
	public String get_source_SV_profile() {
		return _source_SV_profile;
	}

	/**
	 * @return the _source_DY_profile
	 */
	public String get_source_DY_profile() {
		return _source_DY_profile;
	}

	/**
	 * @return the _source_DL_profile
	 */
	public String get_source_DL_profile() {
		return _source_DL_profile;
	}

	private void match_Files(File[] _matchingFiles) {
		for (File filemu : _matchingFiles) {
			if (filemu.getName().contains("EQ"))
				_source_EQ_profile = filemu.getPath();
			if (filemu.getName().contains("TP"))
				_source_TP_profile = filemu.getPath();
			if (filemu.getName().contains("SV"))
				_source_SV_profile = filemu.getPath();
			if (filemu.getName().contains("DY"))
				_source_DY_profile = filemu.getPath();
			if (filemu.getName().contains("DL"))
				_source_DL_profile = filemu.getPath();
		}
	}
	/**
	 * 
	 */
	public void read_Directory() {
		File f = new File(this.cimFolder);
		File[] matchingFiles = f.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}
		});
		this.match_Files(matchingFiles);
	}
}
