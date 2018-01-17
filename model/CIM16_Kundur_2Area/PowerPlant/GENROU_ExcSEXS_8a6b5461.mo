within CIM16_Kundur_2Area.PowerPlant;
model GENROU_ExcSEXS_8a6b5461 "annotation ()"
	OpenIPSL.Interfaces.PwPin plantaPin"automatically generated comment" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENROU sm_3_1 (S_b=100,M_b=900,V_b=20,V_0=20.6,angle_0=-7,P_0=-719.091,Q_0=-175.997,R_a=0.000001,Xl=0.2,H=6.175,D=0,S10=0.186,S12=0.802,Tpd0=8,Tpq0=0.4,Tppd0=0.03,Tppq0=0.05,Xd=1.8,Xpd=0.3,Xppd=0.25,Xq=1.7,Xpq=0.55,Xppq=0.000001) "automatically generated comment" annotation (Placement(transformation(extent={{30,-40},{50,-20}})));
	OpenIPSL.Electrical.Controls.CGMES.ES.ExcSEXS es_SEXS (T_AT_B=0.1,T_B=10,K=200,T_E=0.1,K_C=1,T_C=0,E_MIN=0,E_MAX=4,EFD_MIN=9999,EFD_MAX=-9999) "automatically generated comment" annotation (Placement(transformation(extent={{30,-40},{50,-20}})));
	Modelica.Blocks.Sources.Constant const (k=0) "automatically generated comment" annotation (Placement(transformation(extent={{30,-40},{50,-20}})));
equation
	connect(sm_3_1.EFD0, es_SEXS.EFD0);
	connect(sm_3_1.ETERM, es_SEXS.ECOMP);
	connect(es_SEXS.EFD, sm_3_1.EFD);
	connect(sm_3_1.PMECH0, sm_3_1.PMECH);
	connect(sm_3_1.p, plantaPin);
	connect(const.y, es_SEXS.VOTHSG);
	connect(const.y, es_SEXS.VUEL);
	connect(const.y, es_SEXS.VOEL);
	connect(const.y, es_SEXS.XADIFD);
end GENROU_ExcSEXS_8a6b5461;