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
<li>Select option <i>Package required libraries into generated JAR</i></li>
</p>
<p>
<h5>Using the tool:</h5>
Create a working folder and download:
<li> The .jar file under the <i>dist</i> folder
<li>Place a <i>res</i> folder containing two sub folders:
<ul>Folder <i>map</i> containing the mapping rules for populating the CIM values into the Modelica component instances of he model</ul>
<ul>Folder <i>network</i> containing the CIM files of the network model</ul>
</li>
The <b>cim2modelica.jar</b> contains the necessary libraries to run the tool.

</p>
<p>
<li>Execute the .jar file in command line:
<ul><b>java -jar <i>./folder_CIM_model</i> <i>model_given_name</i> </b></ul>
</li>
<p> The <i>model_given_name</i> is the name for the resulting Modelica model. And, make sure that within the <i>./folder_CIM_model</i> there are the following CIM profiles’ files:</p>
<li><i>CIM_model_EQ.xml</i> - equipment profile cim file</li>
<li><i>CIM_model_TP.xml</i> - topology profile cim file</li>
<li><i>CIM_model_SV.xml</i> - state variable profile cim file</li>
<li><i>CIM_model_DY.xml</i> - dynamics profile cim file</li>

</p>
