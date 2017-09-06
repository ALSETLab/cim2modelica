model GENROU_8a6b546a "annotation ()"
	OpenIPSL.Electrical.Connectors.PwPin plantaPin (vi=-17.1919,vr=20.2,ir=399.4616621651249,ii=-10.040127741185191) "something here" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENROU sm_4_1 (S_b=100,M_b=900,V_b=20,V_0=20.2,angle_0=-17.1919,P_0=-700,Q_0=-202.046,R_a=0,Xl=0.2,H=6.5,D=0.02,S10=0.186,S12=0.802,Tpd0=8,Tpq0=0.4,Tppd0=0.03,Tppq0=0.05,Xd=1.8,Xpd=0.3,Xppd=0.25,Xq=1.7,Xpq=0.55,Xppq=0) "something here" annotation ();
equation
	connect(sm_4_1.PMECH0, sm_4_1.PMECH);
	connect(sm_4_1.EFD0, sm_4_1.EFD);
	connect(sm_4_1.p, plantaPin);
end GENROU_8a6b546a;