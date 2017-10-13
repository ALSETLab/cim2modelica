model GENSAL_703939f0 "annotation ()"
	OpenIPSL.Interfaces.PwPin plantaPin"automatically generated comment" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENSAL sm_1_G1 (S_b=100,M_b=275,V_b=16.5,V_0=17.136735,angle_0=13.6627,P_0=-71.417,Q_0=-25.931,R_a=0,Xl=0.06,H=9.55,D=1.6,S10=1.01,S12=1.02,Tpd0=8.96,Tppd0=0.05,Tppq0=0.05,Xd=0.3615,Xpd=0.1508,Xppd=0.1,Xq=0.24,Xppq=0) "automatically generated comment" annotation ();
equation
	connect(sm_1_G1.EFD0, sm_1_G1.EFD);
	connect(sm_1_G1.PMECH0, sm_1_G1.PMECH);
	connect(sm_1_G1.p, plantaPin);
end GENSAL_703939f0;