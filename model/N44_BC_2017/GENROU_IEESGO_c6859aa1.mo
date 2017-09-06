model GENROU_IEESGO_c6859aa1 "annotation ()"
	OpenIPSL.Electrical.Connectors.PwPin plantaPin (vi=-0.7323,vr=420,ir=-2.237866905871952,ii=0.9582294790769365) "something here" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENROU sm_3359_3 (S_b=1000,M_b=1350,V_b=420,V_0=420,angle_0=-0.7323,P_0=-698.949,Q_0=-269.075,R_a=0,Xl=0.14531,H=4.82,D=0,S10=0.1089,S12=0.37795,Tpd0=4.75,Tpq0=1,Tppd0=0.05,Tppq0=0.05,Xd=2.13,Xpd=0.31,Xppd=0.1937,Xq=2.03,Xpq=0.403,Xppq=0) "something here" annotation ();
	OpenIPSL.Electrical.Controls.PSSE.TG.IEESGO tgov_IEESGO (K_1=0,K_2=0.7,K_3=0.43,P_MAX=1,P_MIN=0,T_1=0.01,T_2=0,T_3=0.15,T_4=0.3,T_5=8,T_6=0.4) "something here" annotation ();
equation
	connect(sm_3359_3.SPEED, tgov_IEESGO.SPEED);
	connect(sm_3359_3.PMECH0, tgov_IEESGO.PMECH0);
	connect(sm_3359_3.PMECH, tgov_IEESGO.PMECH);
	connect(sm_3359_3.p, plantaPin);
end GENROU_IEESGO_c6859aa1;