package cim2model.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ModelWriter {

	private static final String packete = "package.mo";
	private static final String packorder = "package.order";
	/**
	 * 
	 * @param _moCode
	 * @param _nameModel
	 * @param _folder
	 */
	public void save_ModelicaFile(String _moCode, String _nameModel,
			String _folder) {
		BufferedWriter writer = null;
		try {
			String nomFitxer = "./model/" + _folder + "/" + _nameModel + ".mo";
			File fitxer = new File(nomFitxer);
			if (!fitxer.getParentFile().exists())
				fitxer.getParentFile().mkdirs();
			writer = new BufferedWriter(new FileWriter(fitxer));
			writer.write(_moCode);
		} catch (IOException _e) {
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException _e) {
			}
		}
	}

	public void pack_ModelicaPackage(String _packageText,
			ArrayList<String> _packOrderText, String _folder) {
		BufferedWriter writer = null;
		try {
			File packeteFile = new File("./model/" + _folder + "/" + packete);
			File packorderFile = new File(
					"./model/" + _folder + "/" + packorder);
			writer = new BufferedWriter(new FileWriter(packeteFile));
			writer.write(_packageText);
			writer.close();
			writer = new BufferedWriter(new FileWriter(packorderFile));
			for (String nameModel : _packOrderText) {
				writer.write(nameModel);
				writer.newLine();
			}

		} catch (IOException _e) {
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException _e) {
			}
		}
	}
	public void pack_ModelicaModel(String _packageText, String _packOrderText,
			String _mainModelName, String _folder) {
		BufferedWriter writer = null;
		try {
			File packeteFile = new File("./model/" + _folder + "/" + packete);
			File packorderFile = new File(
					"./model/" + _folder + "/" + packorder);
			writer = new BufferedWriter(new FileWriter(packeteFile));
			writer.write(_packageText);
			writer.close();
			writer = new BufferedWriter(new FileWriter(packorderFile));
			writer.write(_packOrderText);
			writer.newLine();
			writer.write(_mainModelName);
			writer.newLine();
		} catch (IOException _e) {
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException _e) {
			}
		}
	}
}
