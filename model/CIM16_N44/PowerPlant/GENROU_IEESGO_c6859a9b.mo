within CIM16_N44.PowerPlant;
model GENROU_IEESGO_c6859a9b "annotation ()"
	OpenIPSL.Interfaces.PwPin plantaPin"automatically generated comment" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENROU sm_3359_2 (S_b=1000,M_b=1350,V_b=420,V_0=420,angle_0=-0.7323,P_0=-698.949,Q_0=-269.075,R_a=0.000001,Xl=0.14531,H=4.82,D=0,S10=0.1089,S12=0.37795,Tpd0=4.75,Tpq0=1,Tppd0=0.05,Tppq0=0.05,Xd=2.13,Xpd=0.31,Xppd=0.1937,Xq=2.03,Xpq=0.403,Xppq=0.000001) "automatically generated comment" annotation (Placement(transformation(extent={{30,-40},{50,-20}})));
	OpenIPSL.Electrical.Controls.PSSE.TG.IEESGO tgov_IEESGO (K_1=0,K_2=0.7,K_3=0.43,P_MAX=1,P_MIN=0,T_1=0.01,T_2=0,T_3=0.15,T_4=0.3,T_5=8,T_6=0.4) "automatically generated comment" annotation (Placement(transformation(extent={{30,-40},{50,-20}})));
	Modelica.Blocks.Sources.Constant const (k=0) "automatically generated comment" annotation (Placement(transformation(extent={{30,-40},{50,-20}})));
equation
	connect(sm_3359_2.EFD0, sm_3359_2.EFD);
	connect(sm_3359_2.SPEED, tgov_IEESGO.SPEED);
	connect(sm_3359_2.PMECH0, tgov_IEESGO.PMECH0);
	connect(sm_3359_2.PMECH, tgov_IEESGO.PMECH);
	connect(sm_3359_2.p, plantaPin);
end GENROU_IEESGO_c6859a9b;