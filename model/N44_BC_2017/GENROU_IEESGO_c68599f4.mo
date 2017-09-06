model GENROU_IEESGO_c68599f4 "annotation ()"
	OpenIPSL.Electrical.Connectors.PwPin plantaPin (vi=-2.8801,vr=420,ir=0.9149931316546118,ii=1.5912479997545186) "something here" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENROU sm_3000_2 (S_b=1000,M_b=1300,V_b=420,V_0=420,angle_0=-2.8801,P_0=-371.233,Q_0=-172.777,R_a=0,Xl=0.16875,H=5.97,D=0,S10=0.1089,S12=0.37795,Tpd0=5,Tpq0=1,Tppd0=0.05,Tppq0=0.05,Xd=2.22,Xpd=0.36,Xppd=0.225,Xq=2.13,Xpq=0.468,Xppq=0) "something here" annotation ();
	OpenIPSL.Electrical.Controls.PSSE.TG.IEESGO tgov_IEESGO (K_1=0,K_2=0.7,K_3=0.43,P_MAX=1,P_MIN=0,T_1=0.01,T_2=0,T_3=0.15,T_4=0.3,T_5=8,T_6=0.4) "something here" annotation ();
equation
	connect(sm_3000_2.SPEED, tgov_IEESGO.SPEED);
	connect(sm_3000_2.PMECH0, tgov_IEESGO.PMECH0);
	connect(sm_3000_2.PMECH, tgov_IEESGO.PMECH);
	connect(sm_3000_2.p, plantaPin);
end GENROU_IEESGO_c68599f4;