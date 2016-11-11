model cim_smib_genroe_esdc1a "something here"
	OpenIPSL.Electrical.Branches.PwLine Ln3(R=0.05289999768137932,X=0.5290000438690186,G=0,B=0.000007599243417644175) "something here" annotation ();
	OpenIPSL.Electrical.Buses.Bus BUS02 "something here" annotation ();
	OpenIPSL.Electrical.Buses.Bus BUS03 "something here" annotation ();
	OpenIPSL.Electrical.Branches.PSSE.TwoWindingTransformer T1 (R=0,X=8.816666603088379,G=0,B=0,ANG1=0.1,S_n=900,CW=1,CZ=1,t1=1,VB1=230,VNOM1=230,t2=1,VB2=20,VNOM2=20) "something here" annotation ();
	OpenIPSL.Electrical.Buses.Bus BUS04 "something here" annotation ();
	OpenIPSL.Electrical.Branches.PwLine Ln1(R=0.05289999768137932,X=0.05289999768137932,G=0,B=0.000007599243417644175) "something here" annotation ();
	OpenIPSL.Electrical.Buses.Bus BUS01 "something here" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENCLS Gslack (S_b=0,M_b=900,V_b=0,V_0=230,angle_0=0,P_0=0,Q_0=0,R_a=0,H=0,D=0,X_d=0) "something here" annotation ();
	OpenIPSL.Electrical.Branches.PwLine Ln2(R=0.05289999768137932,X=0.528947114944458,G=0,B=0.000007599243417644175) "something here" annotation ();
	OpenIPSL.Electrical.Loads.PSSE.Load Ld1 (angle_0=8.550200462341309,V_0=253.54049682617188,P_0=49.944000244140625,Q_0=9.977999687194824) "something here" annotation ();
	OpenIPSL.Electrical.Machines.PSSE.GENROE G1 (S_b=0,M_b=900,V_b=0,V_0=22.082801818847656,angle_0=8.982099533081055,P_0=-55.055999755859375,Q_0=-12.069999694824219,R_a=0.0020000000949949026,Xl=0.11999999731779099,H=4,D=0,S10=0.10000000149011612,S12=0.5,Tpd0=5,Tpq0=0.8999999761581421,Tppd0=0.05000000074505806,Tppq0=0.10000000149011612,Xd=1.409999966621399,Xpd=0.30000001192092896,Xppd=0.20000000298023224,Xq=1.350000023841858,Xpq=0.6000000238418579,Xppq=0.20000000298023224) "something here" annotation ();
	OpenIPSL.Electrical.Controls.PSSE.ES.ESDC1A ExcSysG1 (T_R=0,K_A=75,T_A=0.05000000074505806,T_B=1,T_C=1,K_E=0,T_E=0.5,K_F=0.07000000029802322,T_F1=1,E_1=2.4700000286102295,E_2=4.5,S_EE_1=0.03500000014901161,S_EE_2=0.4699999988079071,V_RMIN=-3.9000000953674316,V_RMAX=0) "something here" annotation ();
	OpenIPSL.Electrical.Events.PwFault pwFault(t1=2, t2=2.2, R=0, X=0.01) "manually implemented, for simulation of dynamic behaviour";
equation
	connect(Ln3.p, BUS02.p);
	connect(Ln3.n, BUS03.p);
	connect(T1.p, BUS02.p);
	connect(Ln1.p, BUS03.p);
	connect(T1.n, BUS01.p);
	connect(Gslack.p, BUS04.p);
	connect(Ln2.n, BUS04.p);
	connect(Ln2.p, BUS02.p);
	connect(Ln1.n, BUS04.p);
	connect(Ld1.p, BUS02.p);
	connect(G1.EFD0, ExcSysG1.EFD0);
	connect(G1.ETERM, ExcSysG1.ECOMP);
	connect(ExcSysG1.EFD, G1.EFD);
	connect(G1.PMECH0, G1.PMECH);
	// added manually, for dynamic behaviour response
  	connect(pwFault.p, BUS03.p);
end cim_smib_genroe_esdc1a;