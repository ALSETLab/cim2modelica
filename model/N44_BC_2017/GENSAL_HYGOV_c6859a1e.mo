model GENSAL_HYGOV_c6859a1e "annotation ()"
	OpenIPSL.Electrical.Connectors.PwPin plantaPin (vi=6.571,vr=420,ir=-0.7856498779984312,ii=0.5514755942424439) "something here" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENSAL sm_3115_5 (S_b=1000,M_b=1100,V_b=420,V_0=420,angle_0=6.571,P_0=-316.4,Q_0=65.747,R_a=0,Xl=0.1108,H=4.741,D=0,S10=0.1024,S12=0.2742,Tpd0=7.57,Tppd0=0.045,Tppq0=0.1,Xd=0.946,Xpd=0.29,Xppd=0.23,Xq=0.565,Xppq=0) "something here" annotation ();
	OpenIPSL.Electrical.Controls.PSSE.TG.HYGOV tgov_HYGOV (R=0.06,r=0.4,T_r=5,T_f=0.05,T_g=0.2,VELM=0.1,G_MAX=1,G_MIN=0,T_w=1,A_t=1.0577,D_turb=0.5,q_NL=0.1) "something here" annotation ();
equation
	connect(sm_3115_5.SPEED, tgov_HYGOV.SPEED);
	connect(sm_3115_5.PMECH0, tgov_HYGOV.PMECH0);
	connect(sm_3115_5.PMECH, tgov_HYGOV.PMECH);
	connect(sm_3115_5.p, plantaPin);
end GENSAL_HYGOV_c6859a1e;