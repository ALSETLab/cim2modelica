model GENROU_8a6b5455 "annotation ()"
	OpenIPSL.Electrical.Connectors.PwPin plantaPin (vi=10.3059,vr=20.2,ir=54.462094709036975,ii=15.053303627808406) "something here" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENROU sm_2_1 (S_b=100,M_b=900,V_b=20,V_0=20.2,angle_0=10.3059,P_0=-700,Q_0=-234.581,R_a=0,Xl=0.2,H=6.5,D=0.02,S10=0.186,S12=0.802,Tpd0=8,Tpq0=0.4,Tppd0=0.03,Tppq0=0.05,Xd=1.8,Xpd=0.3,Xppd=0.25,Xq=1.7,Xpq=0.55,Xppq=0) "something here" annotation ();
equation
	connect(sm_2_1.PMECH0, sm_2_1.PMECH);
	connect(sm_2_1.EFD0, sm_2_1.EFD);
	connect(sm_2_1.p, plantaPin);
end GENROU_8a6b5455;