within CIM16_N44.PowerPlant;
model GENSAL_ExcSEXS_c6859b3d "annotation ()"
	OpenIPSL.Interfaces.PwPin plantaPin"automatically generated comment" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENSAL sm_6000_3 (S_b=1000,M_b=680,V_b=300,V_0=301.5,angle_0=24.0686,P_0=-429.844,Q_0=-15.552,R_a=0.000001,Xl=0.2,H=3.5,D=0,S10=0.1,S12=0.3,Tpd0=9.7,Tppd0=0.05,Tppq0=0.15,Xd=1.28,Xpd=0.37,Xppd=0.28,Xq=0.94,Xppq=0.000001) "automatically generated comment" annotation (Placement(transformation(extent={{30,-40},{50,-20}})));
	OpenIPSL.Electrical.Controls.CGMES.ES.ExcSEXS es_SEXS (T_AT_B=1,T_B=0.1,K=20,T_E=0.1,K_C=1,T_C=0.000001,E_MIN=-4,E_MAX=4,EFD_MIN=-9999,EFD_MAX=9999) "automatically generated comment" annotation (Placement(transformation(extent={{30,-40},{50,-20}})));
	OpenIPSL.Electrical.Controls.PSSE.TG.HYGOV tgov_HYGOV (R=0.06,r=0.3,T_r=5,T_f=0.05,T_g=0.2,VELM=0.2,G_MAX=1,G_MIN=0,T_w=1,A_t=1.1,D_turb=0.5,q_NL=0.1) "automatically generated comment" annotation (Placement(transformation(extent={{30,-40},{50,-20}})));
	Modelica.Blocks.Sources.Constant const (k=0) "automatically generated comment" annotation (Placement(transformation(extent={{30,-40},{50,-20}})));
equation
	connect(sm_6000_3.EFD0, es_SEXS.EFD0);
	connect(sm_6000_3.ETERM, es_SEXS.ECOMP);
	connect(es_SEXS.EFD, sm_6000_3.EFD);
	connect(sm_6000_3.SPEED, tgov_HYGOV.SPEED);
	connect(sm_6000_3.PMECH0, tgov_HYGOV.PMECH0);
	connect(sm_6000_3.PMECH, tgov_HYGOV.PMECH);
	connect(sm_6000_3.p, plantaPin);
	connect(const.y, es_SEXS.VOTHSG);
	connect(const.y, es_SEXS.VUEL);
	connect(const.y, es_SEXS.VOEL);
	connect(const.y, es_SEXS.XADIFD);
end GENSAL_ExcSEXS_c6859b3d;