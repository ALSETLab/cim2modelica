model GENSAL_HYGOV_c6859b51 "annotation ()"
	OpenIPSL.Interfaces.PwPin plantaPin"automatically generated comment" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENSAL sm_6100_2 (S_b=1000,M_b=1240,V_b=300,V_0=300,angle_0=51.9125,P_0=-628.821,Q_0=-132.332,R_a=0,Xl=0.15,H=3,D=0,S10=0.1,S12=0.3,Tpd0=9.9,Tppd0=0.05,Tppq0=0.15,Xd=1.2,Xpd=0.37,Xppd=0.18,Xq=0.73,Xppq=0) "automatically generated comment" annotation ();
	OpenIPSL.Electrical.Controls.PSSE.TG.HYGOV tgov_HYGOV (R=0.06,r=0.4,T_r=5,T_f=0.05,T_g=0.2,VELM=0.2,G_MAX=1,G_MIN=0,T_w=1,A_t=1.1,D_turb=0.5,q_NL=0.1) "automatically generated comment" annotation ();
	Modelica.Blocks.Sources.Constant const (k=0) "automatically generated comment" annotation ();
equation
	connect(sm_6100_2.EFD0, sm_6100_2.EFD);
	connect(sm_6100_2.SPEED, tgov_HYGOV.SPEED);
	connect(sm_6100_2.PMECH0, tgov_HYGOV.PMECH0);
	connect(sm_6100_2.PMECH, tgov_HYGOV.PMECH);
	connect(sm_6100_2.p, plantaPin);
end GENSAL_HYGOV_c6859b51;