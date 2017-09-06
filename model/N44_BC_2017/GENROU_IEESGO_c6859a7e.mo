model GENROU_IEESGO_c6859a7e "annotation ()"
	OpenIPSL.Electrical.Connectors.PwPin plantaPin (vi=0,vr=420,ir=-1.6764333333333332,ii=-Infinity) "something here" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENROU sm_3300_4 (S_b=1000,M_b=1100,V_b=420,V_0=420,angle_0=0,P_0=-704.102,Q_0=-29.633,R_a=0,Xl=0.14812,H=6,D=0,S10=0.1089,S12=0.37795,Tpd0=10.8,Tpq0=1,Tppd0=0.05,Tppq0=0.05,Xd=2.42,Xpd=0.23,Xppd=0.16,Xq=2,Xpq=0.4108,Xppq=0) "something here" annotation ();
	OpenIPSL.Electrical.Controls.PSSE.TG.IEESGO tgov_IEESGO (K_1=0,K_2=0.7,K_3=0.43,P_MAX=1,P_MIN=0,T_1=0.01,T_2=0,T_3=0.15,T_4=0.3,T_5=8,T_6=0.4) "something here" annotation ();
equation
	connect(sm_3300_4.SPEED, tgov_IEESGO.SPEED);
	connect(sm_3300_4.PMECH0, tgov_IEESGO.PMECH0);
	connect(sm_3300_4.PMECH, tgov_IEESGO.PMECH);
	connect(sm_3300_4.p, plantaPin);
end GENROU_IEESGO_c6859a7e;