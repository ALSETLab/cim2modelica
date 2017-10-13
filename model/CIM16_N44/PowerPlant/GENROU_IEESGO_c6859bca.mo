model GENROU_IEESGO_c6859bca "annotation ()"
	OpenIPSL.Interfaces.PwPin plantaPin"automatically generated comment" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENROU sm_7000_6 (S_b=1000,M_b=1278,V_b=420,V_0=420,angle_0=11.5945,P_0=-707.429,Q_0=-51.637,R_a=0,Xl=0.16875,H=5.5,D=0,S10=0.1089,S12=0.37795,Tpd0=10,Tpq0=1,Tppd0=0.05,Tppq0=0.05,Xd=2.22,Xpd=0.36,Xppd=0.225,Xq=2.13,Xpq=0.468,Xppq=0) "automatically generated comment" annotation ();
	OpenIPSL.Electrical.Controls.PSSE.TG.IEESGO tgov_IEESGO (K_1=0,K_2=0.7,K_3=0.43,P_MAX=1,P_MIN=0,T_1=0.01,T_2=0,T_3=0.15,T_4=0.3,T_5=8,T_6=0.4) "automatically generated comment" annotation ();
	Modelica.Blocks.Sources.Constant const (k=0) "automatically generated comment" annotation ();
equation
	connect(sm_7000_6.EFD0, sm_7000_6.EFD);
	connect(sm_7000_6.SPEED, tgov_IEESGO.SPEED);
	connect(sm_7000_6.PMECH0, tgov_IEESGO.PMECH0);
	connect(sm_7000_6.PMECH, tgov_IEESGO.PMECH);
	connect(sm_7000_6.p, plantaPin);
end GENROU_IEESGO_c6859bca;