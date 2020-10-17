![alt text](https://github.com/ALSETLab/cim2modelica/blob/master/docs/img/CIM2mod_logo.png)
## About:
New requirement on power systems analysis tools consider information exchange for both steady-state and system dynamics information. New European regulations on information exchange power system dynamic simulations now require coordinating TSOs operations under different scenarios, some of which require to assess the dynamic behavior of power systems under a vast array of contingencies. As a mean to comply with these regulations and to advance the state-of-the-art, this work describes the software architecture of a Model-To-Model (M2M) transformation tool to create power system dynamic models using Modelica components by linking it to data from the Common Information Model (CIM). This software architecture is conceived to combine the CIM standard language with the Modelica standardized language, and to provide a Free/Libre Open Source Software (FLOSS) CIM-compliant unambiguous power system modeling solution considering both steady-state and dynamic model representations of the electrical grid.

## IN MEMORIAM - by Luigi Vanfretti:
This repository is dedicated to the memory of the main developer, Francisco Jose Gomez Lopez, who passed on September 21st, 2019. The first [Public Release](https://github.com/ALSETLab/cim2modelica/releases/tag/1.0) was made to accompany the SoftwareX manuscript in ([3](https://doi.org/10.1016/j.softx.2019.01.013)), and development continued until Apr 01, 2019, while Francisco was preparing his [PhD Thesis](https://ecse.rpi.edu/~vanfrl/documents/phdthesis/2019_Francisco_PhD_Thesis.pdf) and working on another article related to his work on software requirements for interoperable power system tools which you can find [here](https://ecse.rpi.edu/~vanfrl/documents/publications/journal/J072_Fco_SWReq_MnS4CPS.pdf).

After Francisco's passing, it took a while to get his final journal paper through the review process and to update this repository. I had the intention to update it shortly after the first anniversary of his passing, so to add the information you read above. If you are curious about Francisco's wonderful life and spirit, you are welcome to read this [small tribute](https://ecse.rpi.edu/~vanfrl/documents/other/2019_10_10_FranciscoJoseLopezGomez_In-Memoriam.pdf).

## Read More and Cite this Work:
If you use or are inspired by this work, please cite the following papers.

The design of the mapping was first proposed in ([1](https://ieeexplore.ieee.org/document/8231176)), and then was further explained with a proof of concept of methodology for a model-to-model transformation workflow described in ([2](http://dx.doi.org/10.1109/PESGM.2015.7286434)).

The main paper documenting this repository ([3](https://doi.org/10.1016/j.softx.2019.01.013)) and providing further information has been published as open access in the SoftwareX journal, you can obtain a copy [here](http://www.sciencedirect.com/science/article/pii/S2352711018300554).

Please cite as follows:
> (1) Gómez FJ, Vanfretti L, Olsen SH. Binding CIM and Modelica for consistent power system dynamic model exchange and simulation. In: 2015 IEEE power & energy society general meeting, Denver, CO; 2015. http://dx.doi.org/10.1109/PESGM.2015.7286434.
  - Download the authors copy [here](https://www.researchgate.net/publication/304604502_Binding_CIM_and_modelica_for_consistent_power_system_dynamic_model_exchange_and_simulation).

> (2) Gómez FJ, Vanfretti L, Olsen SH. CIM-compliant power system dynamic model-to-model transformation and Modelica simulation, IEEE Transactions on Industrial Informatics, PP (99) 1-1. http://dx.doi.org/10.1109/TII.2017.2785439.
  - Download the authors' copy [here](https://ecse.rpi.edu/~vanfrl/documents/publications/journal/J053_CIM2Modelica_Theory.pdf).
  
> (3) Francisco J. Gómez, Luigi Vanfretti, Miguel Aguilera, Svein H. Olsen, "CIM-2-mod: A CIM to modelica mapping and model-2-model transformation engine," SoftwareX, Volume 9, 2019, Pages 161-167, ISSN 2352-7110, https://doi.org/10.1016/j.softx.2019.01.013.
  - Download an open access copy [here](http://www.sciencedirect.com/science/article/pii/S2352711018300554).

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

## Documentation:
  - [User's Guide](https://github.com/ALSETLab/cim2modelica/blob/master/docs/users_guide.md)
  - [Developer's Guide](https://github.com/ALSETLab/cim2modelica/blob/master/docs/developers_guide.md)

## License: No Warranty
(c) Francisco Jose Gomez Lopez and Luigi Vanfretti

This software package is free software and the use is completely at your own risk; it can be redistributed and/or modified under the terms of the GNU Public License version 3.
