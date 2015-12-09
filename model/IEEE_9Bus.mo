model IEEE_9Bus "annotation"
  PowerSystems.Electrical.Machines.PSSE.GENSAL.GENSAL Gn1(eterm = 17.15999984741211, anglev0 = 0, pelec = -71.66100311279297, qelec = -27.048999786376953, Mbase = 275, Sbase = 100, Ra = 0, Xl = 0.05999999865889549, Tpd0 = 8.960000038146973, Tppd0 = 0.05000000074505806, Tppq0 = 0.05000000074505806, H = 9.550000190734863, D = 1.600000023841858, Xd = 0.36149999499320984, Xq = 0.23999999463558197, Xpd = 0.15080000460147858, Xppd = 0.10000000149011612, Xppq = 0.10000000149011612, s10 = 1.0099999904632568, s12 = 1.0199999809265137) "annotation";
  PowerSystems.Electrical.Buses.Bus BUS1 "annotation";
  PowerSystems.Electrical.Branches.PwLine Ln45(R = 0.009999999776482582, X = 0.08500000089406967, G = 0, B = 0.08799999952316284) "annotation";
  PowerSystems.Electrical.Buses.Bus BUS4 "annotation";
  PowerSystems.Electrical.Buses.Bus BUS6 "annotation";
  PowerSystems.Electrical.Branches.PwLine Ln78(R = 0.008500000461935997, X = 0.07199999690055847, G = 0, B = 0.07450000196695328) "annotation";
  PowerSystems.Electrical.Buses.Bus BUS7 "annotation";
  PowerSystems.Electrical.Branches.PSSE.TwoWindingTransformer T2(R = 0, X = 0.0575999990105629, G = 0, B = 0, PrimaryOnFromSide = false, t2 = 1, t1 = 1) "annotation";
  PowerSystems.Electrical.Branches.PwLine Ln69(R = 0.039000000804662704, X = 0.17000000178813934, G = 0, B = 0.17900000512599945) "annotation";
  PowerSystems.Electrical.Buses.Bus BUS2 "annotation";
  PowerSystems.Electrical.Loads.PwLoadPQ Ld8(P = 1, Q = 1, Vo_real = 0, Vo_img = 0) "annotation";
  PowerSystems.Electrical.Buses.Bus BUS8 "annotation";
  PowerSystems.Electrical.Branches.PSSE.TwoWindingTransformer T3(R = 0, X = 0.0625, G = 0, B = 0, PrimaryOnFromSide = false, t2 = 1, t1 = 1) "annotation";
  PowerSystems.Electrical.Loads.PwLoadPQ Ld5(P = 1, Q = 1, Vo_real = 0, Vo_img = 0) "annotation";
  PowerSystems.Electrical.Buses.Bus BUS5 "annotation";
  PowerSystems.Electrical.Branches.PwLine Ln57(R = 0.03200000151991844, X = 0.16099999845027924, G = 0, B = 0.15299999713897705) "annotation";
  PowerSystems.Electrical.Branches.PwLine Ln89(R = 0.011900000274181366, X = 0.10080000013113022, G = 0, B = 0.10450000315904617) "annotation";
  PowerSystems.Electrical.Buses.Bus BUS9 "annotation";
  PowerSystems.Electrical.Buses.Bus BUS3 "annotation";
  PowerSystems.Electrical.Branches.PwLine Ln46(R = 0.017000000923871994, X = 0.09200000017881393, G = 0, B = 0.07900000363588333) "annotation";
  PowerSystems.Electrical.Branches.PSSE.TwoWindingTransformer T1(R = 0, X = 0.05860000103712082, G = 0, B = 0, PrimaryOnFromSide = false, t2 = 1, t1 = 1) "annotation";
  PowerSystems.Electrical.Loads.PwLoadPQ Ld6(P = 1, Q = 1, Vo_real = 0, Vo_img = 0) "annotation";
  PowerSystems.Electrical.Machines.PSSE.GENSAL.GENSAL Gn3(eterm = 14.144999504089355, anglev0 = 4.664299964904785, pelec = -85, qelec = 10.859000205993652, Mbase = 300, Sbase = 100, Ra = 0, Xl = 0.15360000729560852, Tpd0 = 5.889999866485596, Tppd0 = 0.05000000074505806, Tppq0 = 0.05000000074505806, H = 2.3499999046325684, D = 0.4699999988079071, Xd = 1.6799999475479126, Xq = 1.6100000143051147, Xpd = 0.2320999950170517, Xppd = 0.20999999344348907, Xppq = 0, s10 = 1.0099999904632568, s12 = 1.0199999809265137) "annotation";
  PowerSystems.Electrical.Machines.PSSE.GENSAL.GENSAL Gn2(eterm = 18.44999885559082, anglev0 = 9.279600143432617, pelec = -163, qelec = -6.65500020980835, Mbase = 320, Sbase = 100, Ra = 0, Xl = 0.10000000149011612, Tpd0 = 6, Tppd0 = 0.05000000074505806, Tppq0 = 0.05000000074505806, H = 3.3299999237060547, D = 0.6700000166893005, Xd = 1.7200000286102295, Xq = 1.659999966621399, Xpd = 0.23000000417232513, Xppd = 0.20999999344348907, Xppq = 0.20999999344348907, s10 = 1.0099999904632568, s12 = 1.0199999809265137) "annotation";
equation
  connect(Gn1.p, BUS1.p);
  connect(Ln45.p, BUS4.p);
  connect(Ln78.p, BUS7.p);
  connect(T2.n, BUS7.p);
  connect(T2.p, BUS2.p);
  connect(Ln69.p, BUS6.p);
  connect(Ld8.p, BUS8.p);
  connect(T3.n, BUS3.p);
  connect(T1.n, BUS4.p);
  connect(Ld5.p, BUS5.p);
  connect(Ln57.p, BUS5.p);
  connect(Ln89.n, BUS9.p);
  connect(Ln57.n, BUS7.p);
  connect(Ln45.n, BUS5.p);
  connect(T3.p, BUS9.p);
  connect(Ln46.p, BUS4.p);
  connect(Ln89.p, BUS8.p);
  connect(T1.p, BUS1.p);
  connect(Ld6.p, BUS6.p);
  connect(Ln69.n, BUS9.p);
  connect(Ln78.n, BUS8.p);
  connect(Gn3.p, BUS3.p);
  connect(Gn2.p, BUS2.p);
  connect(Ln46.n, BUS6.p);
  //connect added manually, to replace the configBus models
  //Generator 1 at Bus 1
  connect(Gn1.PMECH0, Gn1.PMECH);
  connect(Gn1.EFD0, Gn1.EFD);
  //Generator 2 at Bus 2
  connect(Gn2.PMECH0, Gn2.PMECH);
  connect(Gn2.EFD0, Gn2.EFD);
  //Generator 3 at Bus 3
  connect(Gn3.PMECH0, Gn3.PMECH);
  connect(Gn3.EFD0, Gn3.EFD);
end IEEE_9Bus;