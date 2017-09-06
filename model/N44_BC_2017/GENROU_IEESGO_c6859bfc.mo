model GENROU_IEESGO_c6859bfc "annotation ()"
	OpenIPSL.Electrical.Connectors.PwPin plantaPin (vi=-3.9983,vr=428.4,ir=0.6035284677884248,ii=-0.48629185670892394) "something here" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENROU sm_8500_1 (S_b=1000,M_b=1300,V_b=420,V_0=428.4,angle_0=-3.9983,P_0=-169.333,Q_0=-157.431,R_a=0,Xl=0.14812,H=7,D=0,S10=0.1089,S12=0.37795,Tpd0=10,Tpq0=1,Tppd0=0.05,Tppq0=0.05,Xd=2.42,Xpd=0.23,Xppd=0.17062,Xq=2,Xpq=0.4108,Xppq=0) "something here" annotation ();
	OpenIPSL.Electrical.Controls.PSSE.TG.IEESGO tgov_IEESGO (K_1=0,K_2=0.7,K_3=0.43,P_MAX=1,P_MIN=0,T_1=0.01,T_2=0,T_3=0.15,T_4=0.3,T_5=8,T_6=0.4) "something here" annotation ();
equation
	connect(sm_8500_1.SPEED, tgov_IEESGO.SPEED);
	connect(sm_8500_1.PMECH0, tgov_IEESGO.PMECH0);
	connect(sm_8500_1.PMECH, tgov_IEESGO.PMECH);
	connect(sm_8500_1.p, plantaPin);
end GENROU_IEESGO_c6859bfc;