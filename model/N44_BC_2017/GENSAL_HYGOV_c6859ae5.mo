model GENSAL_HYGOV_c6859ae5 "annotation ()"
	OpenIPSL.Electrical.Connectors.PwPin plantaPin (vi=43.807,vr=300,ir=-2.4486359354574607,ii=3.8953389394175915) "something here" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENSAL sm_5300_5 (S_b=1000,M_b=1200,V_b=300,V_0=300,angle_0=43.807,P_0=-723.333,Q_0=-203.805,R_a=0,Xl=0.2,H=3.5,D=0,S10=0.1,S12=0.3,Tpd0=6.4,Tppd0=0.05,Tppq0=0.15,Xd=1.14,Xpd=0.34,Xppd=0.26,Xq=0.84,Xppq=0) "something here" annotation ();
	OpenIPSL.Electrical.Controls.PSSE.TG.HYGOV tgov_HYGOV (R=0.06,r=0.4,T_r=5,T_f=0.05,T_g=0.2,VELM=0.2,G_MAX=1,G_MIN=0,T_w=1,A_t=1.1,D_turb=0.5,q_NL=0.1) "something here" annotation ();
equation
	connect(sm_5300_5.SPEED, tgov_HYGOV.SPEED);
	connect(sm_5300_5.PMECH0, tgov_HYGOV.PMECH0);
	connect(sm_5300_5.PMECH, tgov_HYGOV.PMECH);
	connect(sm_5300_5.p, plantaPin);
end GENSAL_HYGOV_c6859ae5;