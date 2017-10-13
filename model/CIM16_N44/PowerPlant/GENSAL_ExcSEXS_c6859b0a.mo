model GENSAL_ExcSEXS_c6859b0a "annotation ()"
	OpenIPSL.Interfaces.PwPin plantaPin"automatically generated comment" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENSAL sm_5500_2 (S_b=1000,M_b=1450,V_b=300,V_0=301.2,angle_0=13.6204,P_0=-259.503,Q_0=49.584,R_a=0,Xl=0.1619,H=3,D=0,S10=0.1,S12=0.3,Tpd0=7.198,Tppd0=0.05,Tppq0=0.15,Xd=1.2364,Xpd=0.3741,Xppd=0.2283,Xq=0.6557,Xppq=0) "automatically generated comment" annotation ();
	OpenIPSL.Electrical.Controls.CGMES.ES.ExcSEXS es_SEXS (T_AT_B=0.05,T_B=100,K=200,T_E=0.5,K_C=1,T_C=0,E_MIN=0,E_MAX=4,EFD_MIN=9999,EFD_MAX=-9999) "automatically generated comment" annotation ();
	OpenIPSL.Electrical.Controls.PSSE.TG.HYGOV tgov_HYGOV (R=0.06,r=0.4,T_r=5,T_f=0.05,T_g=0.2,VELM=0.2,G_MAX=1,G_MIN=0,T_w=1,A_t=1.1,D_turb=0.5,q_NL=0.1) "automatically generated comment" annotation ();
	Modelica.Blocks.Sources.Constant const (k=0) "automatically generated comment" annotation ();
equation
	connect(sm_5500_2.EFD0, es_SEXS.EFD0);
	connect(sm_5500_2.ETERM, es_SEXS.ECOMP);
	connect(es_SEXS.EFD, sm_5500_2.EFD);
	connect(sm_5500_2.SPEED, tgov_HYGOV.SPEED);
	connect(sm_5500_2.PMECH0, tgov_HYGOV.PMECH0);
	connect(sm_5500_2.PMECH, tgov_HYGOV.PMECH);
	connect(sm_5500_2.p, plantaPin);
	connect(const.y, es_SEXS.VOTHSG);
	connect(const.y, es_SEXS.VUEL);
	connect(const.y, es_SEXS.VOEL);
	connect(const.y, es_SEXS.XADIFD);
end GENSAL_ExcSEXS_c6859b0a;