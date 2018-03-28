within CIM16_Kundur_2Area.PowerPlant;
model GENROU_ESDC1A_8a6b5455 "annotation ()"
	OpenIPSL.Interfaces.PwPin plantaPin"automatically generated comment" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENROU sm_2_1 (S_b=100,M_b=900,V_b=20,V_0=1.011,angle_0=10.3059,P_0=700,Q_0=234.581,R_a=0.0025,Xl=0.2,H=6.5,D=0.02,S10=0.186,S12=0.802,Tpd0=8,Tpq0=0.4,Tppd0=0.03,Tppq0=0.05,Xd=1.8,Xpd=0.3,Xppd=0.25,Xq=1.7,Xpq=0.55,Xppq=0.25) "automatically generated comment" annotation ();
	OpenIPSL.Electrical.Controls.PSSE.ES.ESDC1A es_ESDC1A (T_R=0.05,K_A=75,T_A=0.055,T_B=1,T_C=1,K_E=-0.052927,T_E=0.36,K_F=0.06,T_F1=1.8,E_1=1,E_2=2,S_EE_1=0.0164,S_EE_2=0.0481,V_RMIN=-4,V_RMAX=4) "automatically generated comment" annotation ();
	Modelica.Blocks.Sources.Constant const0 (k=0) "automatically generated comment" annotation ();
	Modelica.Blocks.Sources.Constant const_Inf (k=-Modelica.Constants.inf) "automatically generated comment" annotation ();
equation
	connect(sm_2_1.EFD0, es_ESDC1A.EFD0);
	connect(sm_2_1.ETERM, es_ESDC1A.ECOMP);
	connect(es_ESDC1A.EFD, sm_2_1.EFD);
	connect(sm_2_1.PMECH0, sm_2_1.PMECH);
	connect(sm_2_1.p, plantaPin);
	connect(const0.y, es_ESDC1A.VOTHSG);
	connect(const_Inf.y, es_ESDC1A.VUEL);
	connect(const0.y, es_ESDC1A.VOEL);
end GENROU_ESDC1A_8a6b5455;