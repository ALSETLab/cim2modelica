model GENSAL_HYGOV_c6859b28 "annotation ()"
	OpenIPSL.Electrical.Connectors.PwPin plantaPin (vi=12.1098,vr=303,ir=-0.8691603758414235,ii=2.2160747038763757) "something here" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENSAL sm_5600_4 (S_b=1000,M_b=1650,V_b=300,V_0=303,angle_0=12.1098,P_0=-236.38,Q_0=-296.033,R_a=0,Xl=0.21,H=3.5,D=0,S10=0.1,S12=0.3,Tpd0=7.85,Tppd0=0.05,Tppq0=0.15,Xd=1,Xpd=0.38,Xppd=0.28,Xq=0.51325,Xppq=0) "something here" annotation ();
	OpenIPSL.Electrical.Controls.PSSE.TG.HYGOV tgov_HYGOV (R=0.06,r=0.3,T_r=5,T_f=0.05,T_g=0.2,VELM=0.2,G_MAX=1,G_MIN=0,T_w=1,A_t=1.1,D_turb=0.5,q_NL=0.1) "something here" annotation ();
equation
	connect(sm_5600_4.SPEED, tgov_HYGOV.SPEED);
	connect(sm_5600_4.PMECH0, tgov_HYGOV.PMECH0);
	connect(sm_5600_4.PMECH, tgov_HYGOV.PMECH);
	connect(sm_5600_4.p, plantaPin);
end GENSAL_HYGOV_c6859b28;