model GENSAL_HYGOV_c6859bf4 "annotation ()"
	OpenIPSL.Electrical.Connectors.PwPin plantaPin (vi=8.9692,vr=420,ir=1.1932430504383085,ii=-3.1316096543141514) "something here" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENSAL sm_7100_3 (S_b=1000,M_b=1000,V_b=420,V_0=420,angle_0=8.9692,P_0=-450.047,Q_0=-578.697,R_a=0,Xl=0.11538,H=3.2,D=0,S10=0.10239,S12=0.2742,Tpd0=5,Tppd0=0.06,Tppq0=0.1,Xd=0.75,Xpd=0.25,Xppd=0.15385,Xq=0.5,Xppq=0) "something here" annotation ();
	OpenIPSL.Electrical.Controls.PSSE.TG.HYGOV tgov_HYGOV (R=0.06,r=0.4,T_r=5,T_f=0.05,T_g=0.2,VELM=0.1,G_MAX=1,G_MIN=0,T_w=1,A_t=1.01,D_turb=0.5,q_NL=0.1) "something here" annotation ();
equation
	connect(sm_7100_3.SPEED, tgov_HYGOV.SPEED);
	connect(sm_7100_3.PMECH0, tgov_HYGOV.PMECH0);
	connect(sm_7100_3.PMECH, tgov_HYGOV.PMECH);
	connect(sm_7100_3.p, plantaPin);
end GENSAL_HYGOV_c6859bf4;