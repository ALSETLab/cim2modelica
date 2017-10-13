model GENROU_703939f9 "annotation ()"
	OpenIPSL.Interfaces.PwPin plantaPin"automatically generated comment" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENROU sm_2_G2 (S_b=100,M_b=320,V_b=18,V_0=18.49914,angle_0=22.8822,P_0=-162.73,Q_0=-8.689,R_a=0,Xl=0.1,H=3.33,D=0.67,S10=1.01,S12=1.02,Tpd0=6,Tpq0=0.535,Tppd0=0.05,Tppq0=0.05,Xd=1.72,Xpd=0.23,Xppd=0.21,Xq=1.66,Xpq=0.37,Xppq=0) "automatically generated comment" annotation ();
equation
	connect(sm_2_G2.EFD0, sm_2_G2.EFD);
	connect(sm_2_G2.PMECH0, sm_2_G2.PMECH);
	connect(sm_2_G2.p, plantaPin);
end GENROU_703939f9;