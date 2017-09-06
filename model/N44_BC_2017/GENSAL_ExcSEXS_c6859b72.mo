model GENSAL_ExcSEXS_c6859b72 "annotation ()"
	OpenIPSL.Electrical.Connectors.PwPin plantaPin (vi=-15.4611,vr=300,ir=0.6918063463869328,ii=3.3926366665026433) "something here" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENSAL sm_6500_2 (S_b=1000,M_b=1100,V_b=300,V_0=300,angle_0=-15.4611,P_0=-201.25,Q_0=-248.711,R_a=0,Xl=0.13514,H=3.558,D=0,S10=0.1,S12=0.3,Tpd0=5.4855,Tppd0=0.05,Tppq0=0.15,Xd=1.0679,Xpd=0.23865,Xppd=0.15802,Xq=0.642,Xppq=0) "something here" annotation ();
	OpenIPSL.Electrical.Controls.CGMES.ES.ExcSEXS es_SEXS (T_AT_B=0.05,T_B=100,K=200,T_E=0.5,K_C=1,T_C=0,E_MIN=0,E_MAX=4,EFD_MIN=9999,EFD_MAX=-9999) "something here" annotation ();
	OpenIPSL.Electrical.Controls.PSSE.TG.HYGOV tgov_HYGOV (R=0.06,r=0.4,T_r=5,T_f=0.05,T_g=0.2,VELM=0.2,G_MAX=1,G_MIN=0,T_w=1,A_t=1.1,D_turb=0.5,q_NL=0.1) "something here" annotation ();
equation
	connect(sm_6500_2.EFD0, es_SEXS.EFD0);
	connect(sm_6500_2.ETERM, es_SEXS.ECOMP);
	connect(es_SEXS.EFD, sm_6500_2.EFD);
	connect(sm_6500_2.p, plantaPin);
end GENSAL_ExcSEXS_c6859b72;