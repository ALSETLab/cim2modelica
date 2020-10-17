# CIM-2-mod: A CIM to Modelica Model Transformation Tool

## About:


## Getting Started: Running the Executable
The executable an resoruces to run the tool can be found under the `./dist` folder.

Create a working folder where to download and place the required files for testing, following this steps:
  - Copy inside the working folder the `cim2modelica.jar` file (It contains the necessary libraries to run the code)
  - Copy the `./res/` folder and its content, into the working folder. Make sure that the `./res/` folder contains the following files: 
    - The folder `./res/map/`, which contains the mapping rules. 
    - The folder `./res/network/` containing the network models' CIM files.
  - Using the tool in command line, open a console terminal and go to the working directory where you have placed the cim2modelica.jar file. Use the command: `java –jar cim2modelica.jar –d <model_name> <relativePathFolderCIMFiles>` where:
      - Option `–d` indicates that the input parameter of the `.jar` file is the folder relative path that contains the CIM profile files.
      - `<model_name>` indicates the name for the resulting Modelica model.
      - The `<relativePathFolderCIMFiles>` indicates the folder where the the CIM profiles’ files are stored, i.e. `./res/network/cim_model/`. The folder must contain the following profiles:
	a. `xxx_EQ.xml` - equipment profile CIM file.
	b. `xxx_TP.xml` - topology profile CIM file.
	c. `xxx_SV.xml` - state variable profile CIM file
	d. `xxx_DY.xml` - dynamics profile CIM file
      - Example: `java -jar ./cim2modelica.jar -d defaultNetwork ./res/network/Kundur_2Area_ES_2017/`
  - Use the option `–p` to indicate the relative path of the profile files individually, in the following order: `java –jar cim2modelica.jar –p <model_name> <relativePath/xxx_EQ.xml> <relativePath/xxx_TP.xml> <relativePath/xxx_SV.xml> <relativePath/xxx_DY.xml>`
    - Example: `java -jar ./cim2modelica.jar -p defaultNetwork ./res/network/Kundur_2Area_ES_2017/Benchmark_4ger_SEXS_ESDC1A_2017_EQ.xml ./res/network/Kundur_2Area_ES_2017/Benchmark_4ger_SEXS_ESDC1A_2017_TP.xml ./res/network/Kundur_2Area_ES_2017/Benchmark_4ger_SEXS_ESDC1A_2017_SV.xml ./res/network/Kundur_2Area_ES_2017/Benchmark_4ger_SEXS_ESDC1A_2017_DY.xml`

# In Memoriam:

