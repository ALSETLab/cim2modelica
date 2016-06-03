package cim2model.modelica.ipsl.machines;

import cim2model.modelica.MOClass;

public class GENROU extends MOClass
{
	//TODO como attributos, los inputs y outputs del componente
	/* Inputs */
	double PMECH;
	double EFD;
	/* Outputs */
	double ANGLE;
	double SPEED;
	double PMECH0;
	double PELEC;
	double ETERM;
	double EFD0;
	double ISORCE;
	double XADIFD;
	
	public GENROU(String _name) {
		super(_name);
	}

	//TODO metodo para establecer default connections, a utilizar cuando no haya controladores
	public String default_connectionPMECH(){
		String code= "";
		StringBuilder pencil= new StringBuilder();
		
		pencil.append("connect(");
		pencil.append(this.name);
		pencil.append(".");
		pencil.append(this.PMECH);
		pencil.append(", ");
		pencil.append(this.name);
		pencil.append(".");
		pencil.append(this.PMECH0);
		code= pencil.toString();
		pencil= null;
		
		return code;
	}
	public String default_connectionEFD(){
		String code= "";
		StringBuilder pencil= new StringBuilder();
		
		pencil.append("connect(");
		pencil.append(this.name);
		pencil.append(".");
		pencil.append(this.EFD);
		pencil.append(", ");
		pencil.append(this.name);
		pencil.append(".");
		pencil.append(this.EFD0);
		code= pencil.toString();
		pencil= null;
		
		return code;
	}
	//TODO metod para establecer connections con controladores
	
	//classe planta?? si, classe planta con configuraciones de maquina y controlador
	//la classe planta creará las equaciones connect respectivas. Y ModelBuilder las 
	//gestiona, creando esas conexiones y guardandolas en la estructura
	//ModelDesigner tiene que mapear el tipo de maquina y los tipos de controladores de la maquina
}
