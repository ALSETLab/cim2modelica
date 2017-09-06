model GENROU_ExcSEXS_8a6b544c "annotation ()"
	OpenIPSL.Electrical.Connectors.PwPin plantaPin (vi=20.0702,vr=20.6,ir=-99.05696466240433,ii=-9.560877453783137) "something here" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENROU sm_1_1 (S_b=100,M_b=900,V_b=20,V_0=20.6,angle_0=20.0702,P_0=-700,Q_0=-185.003,R_a=0,Xl=0.2,H=6.5,D=0.02,S10=0.186,S12=0.802,Tpd0=8,Tpq0=0.4,Tppd0=0.03,Tppq0=0.05,Xd=1.8,Xpd=0.3,Xppd=0.25,Xq=1.7,Xpq=0.55,Xppq=0) "something here" annotation ();
	OpenIPSL.Electrical.Controls.CGMES.ES.ExcSEXS es_SEXS (T_AT_B=1,T_B=1,K=200,T_E=0.1,K_C=1,T_C=0,E_MIN=0,E_MAX=4,EFD_MIN=9999,EFD_MAX=-9999) "something here" annotation ();
equation
	connect(sm_1_1.EFD0, es_SEXS.EFD0);
	connect(sm_1_1.ETERM, es_SEXS.ECOMP);
	connect(es_SEXS.EFD, sm_1_1.EFD);
	connect(sm_1_1.p, plantaPin);
end GENROU_ExcSEXS_8a6b544c;