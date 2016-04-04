model CIM_Kundur "annotation()"
	iPSL.Electrical.Branches.PwLine Ln3(R=0.05289999768137932,X=0.5290000438690186,G=0,B=0.000007599243417644175) "annotation()";
	iPSL.Electrical.Buses.Bus BUS02"annotation()";
	iPSL.Electrical.Buses.Bus BUS03"annotation()";
	iPSL.Electrical.Branches.PSSE.TwoWindingTransformer T1(R=0,X=8.816666603088379,G=0,B=0,ANG1=0.1,S_n=900,CW=1,CZ=1,t1=1,VNOM1=230,VB1=253.54049682617188,t2=1,VNOM2=20,VB2=22.082801818847656) "something here" annotation();
	iPSL.Electrical.Buses.Bus BUS04"annotation()";
	iPSL.Electrical.Branches.PwLine Ln1(R=0.05289999768137932,X=0.05289999768137932,G=0,B=0.000007599243417644175) "annotation()";
	iPSL.Electrical.Buses.Bus BUS01"annotation()";
	iPSL.Electrical.Machines.PSSE.GENROE.GENROE G1(S_b=0,M_b=900,V_b=0,V_0=22.082801818847656,angle_0=8.982099533081055,P_0=0,Q_0=0,R_a=0.0020000000949949026,Xl=0.11999999731779099,H=4,D=0,S10=0.10000000149011612,S12=0.5,Tpd0=5,Tpq0=0.8999999761581421,Tppd0=0.05000000074505806,Tppq0=0.10000000149011612,Xd=1.409999966621399,Xpd=0.30000001192092896,Xppd=0.20000000298023224,Xq=1.350000023841858,Xpq=0.6000000238418579,Xppq=0.20000000298023224) "something here" annotation();
	iPSL.Electrical.Machines.PSSE.GENCLS.GENCLS Gslack(S_b=0,M_b=900,V_b=0,V_0=230,angle_0=0,P_0=0,Q_0=0,R_a=0,H=0,D=0,X_d=0) "something here" annotation();
	iPSL.Electrical.Branches.PwLine Ln2(R=0.05289999768137932,X=0.528947114944458,G=0,B=0.000007599243417644175) "annotation()";
	iPSL.Electrical.Loads.PSSE.Load Ld1(angle_0=8.550200462341309,V_0=253.54049682617188,P_0=0,Q_0=0) "something here" annotation();
equation
	connect(Ln3.p, BUS02.p);
	connect(Ln3.n, BUS03.p);
	connect(T1.p, BUS02.p);
	connect(Ln1.p, BUS03.p);
	connect(T1.n, BUS01.p);
	connect(G1.p, BUS01.p);
	connect(Gslack.p, BUS04.p);
	connect(Ln2.n, BUS04.p);
	connect(Ln2.p, BUS02.p);
	connect(Ln1.n, BUS04.p);
	connect(Ld1.p, BUS02.p);
end CIM_Kundur;