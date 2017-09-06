model GENSAL_ExcSEXS_c6859abb "annotation ()"
	OpenIPSL.Electrical.Connectors.PwPin plantaPin (vi=11.5126,vr=300,ir=-2.3720903443527095,ii=1.6700809282157165) "something here" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENSAL sm_5100_1 (S_b=1000,M_b=1200,V_b=300,V_0=300,angle_0=11.5126,P_0=-351.755,Q_0=-435.537,R_a=0,Xl=0.13405,H=3.9871,D=0,S10=0.1,S12=0.3,Tpd0=4.9629,Tppd0=0.05,Tppq0=0.15,Xd=1.1332,Xpd=0.24302,Xppd=0.15135,Xq=0.68315,Xppq=0) "something here" annotation ();
	OpenIPSL.Electrical.Controls.CGMES.ES.ExcSEXS es_SEXS (T_AT_B=0.05,T_B=100,K=200,T_E=0.5,K_C=1,T_C=0,E_MIN=0,E_MAX=4,EFD_MIN=9999,EFD_MAX=-9999) "something here" annotation ();
	OpenIPSL.Electrical.Controls.PSSE.TG.HYGOV tgov_HYGOV (R=0.06,r=0.4,T_r=5,T_f=0.05,T_g=0.2,VELM=0.2,G_MAX=1,G_MIN=0,T_w=1,A_t=1.1,D_turb=0.5,q_NL=0.1) "something here" annotation ();
equation
	connect(sm_5100_1.EFD0, es_SEXS.EFD0);
	connect(sm_5100_1.ETERM, es_SEXS.ECOMP);
	connect(es_SEXS.EFD, sm_5100_1.EFD);
	connect(sm_5100_1.p, plantaPin);
end GENSAL_ExcSEXS_c6859abb;