<p>
<h4> CIM to Modelica Model Transformation Tool</h4>
Description

<p>
<h5>For developing:</h5>
<li>Download the entire project in a desired folder</li>
<li>Open Eclipse</li>
<li>On the project explorer, right click and select Import> Existing project into workspace</li>
<li>check project properties... tbc</li>
</p>
<p>
<h6> Create a new distribution .jar file for the tool </h6>
<li>Click on the project root folder and select File> Export...</li>
<li>Select JAVA> Runnable JAR file </li>
<li>Select Launch configuration, which you use to run the code</li>
<li>Give a name to the .jar file</li>
<li>Select option Extract required libraries into generated JAR</li>
</p>
<p>
<h5>Using the tool:</h5>
Create a working folder and download:
<li> The .jar file under the <i>dist</i> folder
<li>Create a <i>res</i> folder containing two sub folders:
<ul>Folder <i>map</i> containing the mapping rules for populating the CIM values into the Modelica component instances of he model</ul>
<ul>Folder <i>network</i> containing the CIM files of the network model</ul>
</li>
<i>(You can get those folders from the repository and... make sure you have the java 1.6 or later installed)</i>
</p>
<p>
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
