<h3> CIM to Modelica Model Transformation Tool</h3>
<p>
<h4> Working spaces setup: </h4>
Create a working folder where to download and place the required files for <br>
testing, following this steps:
<ol>
<li>Copy inside the working folder the <b>cim2modelica.jar</b> file (It contains <br>
the necessary libraries to run the code)</li>
<li>Copy the <b>./res/</b> folder and its content, into the working folder. Make <br>
sure that the <i>./res/</i> folder contains the following files:</li>
<ul>
<li>The folder <b>./res/map/</b>, which contains the mapping rules.</li>
<li>The folder <b>./res/network/</b> containing the network models' CIM files </li>
</ul>
</ol>
</p>
<p>
<h4>Using the tool in command line:</h4>
Open a console terminal and go to the working directory where you have <br>
placed the cim2modelica.jar file. Use the command: <br>
<b>java –jar cim2modelica.jar –d &lt;model_name&gt; &lt;relativePathFolderCIMFiles&gt;</b><br>
where:
<ol>
<li>Option –d indicates that the input parameter of the .jar file is the <br>
folder relative path that contains the CIM profile files.</li>
<li>&lt;model_name&gt; - name for the resulting Modelica model.</li>
<li>The &lt;relativePathFolderCIMFiles&gt; indicates the folder where the <br>
the CIM profiles’ files are stored, i.e. ./res/network/cim_model/. The folder <br>
must contain the following profiles:</li>
<ul>
	<li><i>xxx_EQ.xml</i> - equipment profile CIM file.</li>
	<li><i>xxx_TP.xml</i> - topology profile CIM file.</li>
	<li><i>xxx_SV.xml</i> - state variable profile CIM file</li>
	<li><i>xxx_DY.xml</i> - dynamics profile CIM file</li>
</ul>
</ol>
</p>
<p>
Use the option –p to indicate the relative path of the profile files <br>
individually, in the following order: <br>
<b>java –jar cim2modelica.jar –p &lt;model_name&gt; &lt;relativePath/xxx_EQ.xml&gt; <br>
&lt;relativePath/xxx_TP.xml&gt; &lt;relativePath/xxx_SV.xml&gt; &lt;relativePath/xxx_DY.xml&gt;</b>
</p>
