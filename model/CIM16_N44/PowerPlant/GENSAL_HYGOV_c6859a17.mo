model GENSAL_HYGOV_c6859a17 "annotation ()"
	OpenIPSL.Interfaces.PwPin plantaPin"automatically generated comment" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENSAL sm_3115_4 (S_b=1000,M_b=1100,V_b=420,V_0=420,angle_0=6.571,P_0=-316.4,Q_0=65.747,R_a=0,Xl=0.11077,H=4.741,D=0,S10=0.10239,S12=0.2742,Tpd0=7.57,Tppd0=0.045,Tppq0=0.1,Xd=0.946,Xpd=0.29,Xppd=0.23,Xq=0.565,Xppq=0) "automatically generated comment" annotation ();
	OpenIPSL.Electrical.Controls.PSSE.TG.HYGOV tgov_HYGOV (R=0.06,r=0.4,T_r=5,T_f=0.05,T_g=0.2,VELM=0.1,G_MAX=1,G_MIN=0,T_w=1,A_t=1.0577,D_turb=0.5,q_NL=0.1) "automatically generated comment" annotation ();
	Modelica.Blocks.Sources.Constant const (k=0) "automatically generated comment" annotation ();
equation
	connect(sm_3115_4.EFD0, sm_3115_4.EFD);
	connect(sm_3115_4.SPEED, tgov_HYGOV.SPEED);
	connect(sm_3115_4.PMECH0, tgov_HYGOV.PMECH0);
	connect(sm_3115_4.PMECH, tgov_HYGOV.PMECH);
	connect(sm_3115_4.p, plantaPin);
end GENSAL_HYGOV_c6859a17;