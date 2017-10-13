model GENROU_ESDC1A_3b7c8513 "annotation ()"
	OpenIPSL.Interfaces.PwPin plantaPin"automatically generated comment" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENROU sm_4_1 (S_b=100,M_b=900,V_b=20,V_0=20.2,angle_0=-17.1919,P_0=-700,Q_0=-202.046,R_a=0,Xl=0.2,H=6.5,D=0.02,S10=0.186,S12=0.802,Tpd0=8,Tpq0=0.4,Tppd0=0.03,Tppq0=0.05,Xd=1.8,Xpd=0.3,Xppd=0.25,Xq=1.7,Xpq=0.55,Xppq=0) "automatically generated comment" annotation ();
	OpenIPSL.Electrical.Controls.PSSE.ES.ESDC1A es_ESDC1A (T_R=0,K_A=20,T_A=0.055,T_B=1,T_C=1,K_E=-0.072,T_E=0.36,K_F=0.125,T_F1=1.8,E_1=2,E_2=1,S_EE_1=0.0481,S_EE_2=0.0164,V_RMIN=-4,V_RMAX=4) "automatically generated comment" annotation ();
	Modelica.Blocks.Sources.Constant const (k=0) "automatically generated comment" annotation ();
equation
	connect(sm_4_1.EFD0, es_ESDC1A.EFD0);
	connect(sm_4_1.ETERM, es_ESDC1A.ECOMP);
	connect(es_ESDC1A.EFD, sm_4_1.EFD);
	connect(sm_4_1.PMECH0, sm_4_1.PMECH);
	connect(sm_4_1.p, plantaPin);
	connect(const.y, es_ESDC1A.VOTHSG);
	connect(const.y, es_ESDC1A.VUEL);
	connect(const.y, es_ESDC1A.VOEL);
	connect(const.y, es_ESDC1A.XADIFD);
end GENROU_ESDC1A_3b7c8513;