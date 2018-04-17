package cim2modelica.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.xml.sax.SAXParseException;

import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Driver;
import com.sun.tools.xjc.XJCListener;

public class MappingStructureGenerator {

	public static void main(String[] args)
			throws BadCommandLineException, IOException {
		final String targetDir = "./src";
		Path path = Paths.get(targetDir);
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
		Driver.run(new String[]{"-d", targetDir, "-p", args[0], args[1]},
				new XJCListener() {

			@Override
			public void error(SAXParseException e) {
				printError(e, "ERROR");
			}

			@Override
			public void fatalError(SAXParseException e) {
				printError(e, "FATAL");
			}

			@Override
			public void warning(SAXParseException e) {
				printError(e, "WARN");
			}

			@Override
			public void info(SAXParseException e) {
				printError(e, "INFO");
			}

			private void printError(SAXParseException e, String level) {
				System.err.printf("%s: SAX Parse exception", level);
				e.printStackTrace();
			}
		});
	}
}
