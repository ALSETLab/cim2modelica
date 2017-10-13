model GENSAL_HYGOV_c6859a27 "annotation ()"
	OpenIPSL.Interfaces.PwPin plantaPin"automatically generated comment" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENSAL sm_3245_1 (S_b=1000,M_b=950,V_b=420,V_0=420,angle_0=-4.2395,P_0=-200.27,Q_0=24.521,R_a=0,Xl=0.11538,H=3.3,D=0,S10=0.10239,S12=0.2742,Tpd0=5,Tppd0=0.06,Tppq0=0.1,Xd=0.75,Xpd=0.25,Xppd=0.15385,Xq=0.5,Xppq=0) "automatically generated comment" annotation ();
	OpenIPSL.Electrical.Controls.PSSE.TG.HYGOV tgov_HYGOV (R=0.06,r=0.4,T_r=5,T_f=0.05,T_g=0.2,VELM=0.1,G_MAX=1,G_MIN=0,T_w=1,A_t=1.01,D_turb=0.5,q_NL=0.1) "automatically generated comment" annotation ();
	Modelica.Blocks.Sources.Constant const (k=0) "automatically generated comment" annotation ();
equation
	connect(sm_3245_1.EFD0, sm_3245_1.EFD);
	connect(sm_3245_1.SPEED, tgov_HYGOV.SPEED);
	connect(sm_3245_1.PMECH0, tgov_HYGOV.PMECH0);
	connect(sm_3245_1.PMECH, tgov_HYGOV.PMECH);
	connect(sm_3245_1.p, plantaPin);
end GENSAL_HYGOV_c6859a27;