package cim2model.utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.xml.sax.SAXParseException;

import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Driver;
import com.sun.tools.xjc.XJCListener;

public class MapStructureGenerator {

	public static void main(String[] args)
			throws BadCommandLineException, IOException {
		final String targetDir = "src";
		final String targetPackage = "cim2model.cim";
		final String xsdFile = args[0];
        Path path = Paths.get(targetDir);
        if(!Files.exists(path)) {
            Files.createDirectories(path);
        }
		Driver.run(new String[]{"-d", targetDir, "-p", targetPackage,
				xsdFile}, new XJCListener() {
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
