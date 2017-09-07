<li>TODO: brief description</li>
<li>TODO: dist folder, executable .jar
<ul>1. How is created in Eclipse; </ul>
<ul>2. how to execute in command line </ul>
<ul>3. how to execute in sw prototype (py)</ul>
</li>

<p>
<h6>For developing:</h6>
<li>Download the entire project in a desired folder</li>
<li>Open Eclipse</li>
<li>On the project explorer, right click and select Import> Existing project into workspace</li>
<li>check project properties... tbc</li>
</p>
<p>
to create a new jar file with eclipse
tbc
</p>
<p>
<h6>Using the tool:</h6>
Create a working folder and download:
<li> The .jar file under the <i>dist</i> folder
<li>Create a <i>res</i> folder containing two sub folders:
<ul>folder <i>map</i> containing the mapping rules for populating the CIM values into the Modelica component instances of he model</ul>
<ul>folder <i>network</i> containing the CIM files of the network model</ul>
</li>
<i>(You can get those folders from the repository and... make sure you have the java 1.6 or later installed</i>
<li>Execute the .jar file in command line:
<ul><b>java -jar EQ.xml TP.xml SV.xml DY.xml model-name cim-schema </b></ul>
</li>

<li><i>EQ.xml</i> - equipment profile cim file</li>
<li><i>TP.xml</i> - topology profile cim file</li>
<li><i>SV.xml</i> - state variable profile cim file</li>
<li><i>DY.xml</i> - dynamics profile cim file</li>
<li><i>model-name</i> - name for the resulting Modelica model</li>
<li><i>cim-schema</i> - xml schema supported by CIM (current schema to use: http://iec.ch/TC57/2013/CIM-schema-cim16#)</li>
</p>
