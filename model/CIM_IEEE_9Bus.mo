model cim_ieee_9bus "something here"
  inner OpenIPSL.Electrical.SystemBase SysData(S_b= 100, fn= 60);
  OpenIPSL.Electrical.Branches.PwLine Ln46(
    R=0.02,
    X=0.09,
    G=0,
    B=0.08) "something here" annotation ();
  OpenIPSL.Electrical.Buses.Bus BUS4 "something here" annotation ();
  OpenIPSL.Electrical.Buses.Bus BUS2 "something here" annotation ();
  OpenIPSL.Electrical.Buses.Bus BUS7 "something here" annotation ();
  OpenIPSL.Electrical.Buses.Bus BUS6 "something here" annotation ();
  OpenIPSL.Electrical.Loads.PSSE.Load Ld8(
    angle_0=-91.63,
    V_0=1.01,
    P_0=100,
    Q_0=35) "something here" annotation ();
  OpenIPSL.Electrical.Buses.Bus BUS8 "something here" annotation ();
  OpenIPSL.Electrical.Branches.PwLine Ln57(
    R=0.03,
    X=0.16,
    G=0,
    B=0.15) "something here" annotation ();
  OpenIPSL.Electrical.Buses.Bus BUS5 "something here" annotation ();
  OpenIPSL.Electrical.Loads.PSSE.Load Ld6(
    angle_0=-97.1,V_0=1.01, P_0=90, Q_0=30) "auto-generated comment";
  OpenIPSL.Electrical.Branches.PwLine Ln45(
    R=0.01,
    X=0.09,
    G=0,
    B=0.09) "something here" annotation ();
  OpenIPSL.Electrical.Loads.PSSE.Load Ld5(
    angle_0=-97.2,
    V_0=0.99,
    P_0=125,
    Q_0=50) "something here" annotation ();
  OpenIPSL.Electrical.Branches.PwLine Ln89(
    R=0.01,
    X=0.1,
    G=0,
    B=0.11) "something here" annotation ();
  OpenIPSL.Electrical.Buses.Bus BUS9 "something here" annotation ();
  OpenIPSL.Electrical.Branches.PSSE.TwoWindingTransformer T3(
    R=0,
    X=0.06,
    G=0,
    B=0,
    ANG1=0,
    S_n=100,
    CW=1,
    CZ=1,
    t2=1,
    VB2=13.8,
    VNOM2=13.8,
    t1=1,
    VB1=230,
    VNOM1=230) "something here" annotation ();
  OpenIPSL.Electrical.Branches.PSSE.TwoWindingTransformer T1(
    R=0,
    X=0.06,
    G=0,
    B=0,
    ANG1=0,
    S_n=100,
    CW=1,
    CZ=1,
    t2=1,
    VB2=230,
    VNOM2=230,
    t1=1,
    VB1=16.5,
    VNOM1=16.5) "something here" annotation ();
  OpenIPSL.Electrical.Branches.PSSE.TwoWindingTransformer T2(
    R=0,
    X=0.06,
    G=0,
    B=0,
    ANG1=0,
    S_n=100,
    CW=1,
    CZ=1,
    t1=1,
    VB1=18,
    VNOM1=18,
    t2=1,
    VB2=230,
    VNOM2=230) "something here" annotation ();
  OpenIPSL.Electrical.Buses.Bus BUS3 "something here" annotation ();
  OpenIPSL.Electrical.Buses.Bus BUS1 "something here" annotation ();
  OpenIPSL.Electrical.Branches.PwLine Ln78(
    R=0.01,
    X=0.07,
    G=0,
    B=0.08) "something here" annotation ();
  OpenIPSL.Electrical.Branches.PwLine Ln69(
  R=0.04, X=0.17, G=0, B=0.18) "auto-generated comment"  annotation ();
  OpenIPSL.Electrical.Machines.PSSE.GENROU Gn2(
    S_b=100,
    M_b=320,
    V_b=18,
    V_0=1.03,
    angle_0=-82.52,
    P_0=177.21,
    Q_0=12.98,
    R_a=0,
    Xl=0.1,
    H=2.32,
    D=0.67,
    S10=1.01,
    S12=1.02,
    Tpd0=6,
    Tpq0=0.54,
    Tppd0=0.05,
    Tppq0=0.05,
    Xd=1.72,
    Xpd=0.23,
    Xppd=0.21,
    Xq=1.66,
    Xpq=0.37,
    Xppq=0.21) "something here" annotation ();
  OpenIPSL.Electrical.Machines.PSSE.GENSAL Gn1(S_b=100, 
  M_b=275, 
  V_b=16.5, 
  V_0=1.04, 
  angle_0=-94.34, 
  P_0=52.79, 
  Q_0=27.51, 
  R_a=0, 
  Xl=0.06, 
  H=9.55, 
  D=1.6, 
  S10=1.01, 
  S12=1.02, 
  Tpd0=8.96, 
  Tppd0=0.05,
  Tppq0=0.05, 
  Xd=0.36, 
  Xpd=0.15, 
  Xppd=0.1, 
  Xq=0.24, 
  Xppq=0.1) "auto-generated comment" annotation ();
  OpenIPSL.Electrical.Machines.PSSE.GENROU Gn3(
    S_b=100,
    M_b=300,
    V_b=13,
    V_0=1.03,
    angle_0=-87.72,
    P_0=89.56,
    Q_0=-12.14,
    R_a=0,
    Xl=0.15,
    H=3.35,
    D=0.47,
    S10=1.01,
    S12=1.02,
    Tpd0=5.89,
    Tpq0=0.6,
    Tppd0=0.05,
    Tppq0=0.05,
    Xd=1.68,
    Xpd=0.23,
    Xppd=0.21,
    Xq=1.61,
    Xpq=0.32,
    Xppq=0.21) "something here" annotation ();
  OpenIPSL.Electrical.Events.PwFault faultAtBus8(
    R=0.1,
    t1=2,
    t2=2.2,
    X=0.01) "manually implemented, for simulation of dynamic behaviour";
equation
  connect(Ln46.p, BUS4.p);
  connect(T2.n, BUS7.p);
  connect(T2.p, BUS2.p);
  connect(Ld8.p, BUS8.p);
  connect(Ln57.p, BUS5.p);
  connect(Ld6.p, BUS6.p);
  connect(Ln45.n, BUS5.p);
  connect(Ld5.p, BUS5.p);
  connect(Ln89.n, BUS9.p);
  connect(T3.n, BUS3.p);
  connect(T1.n, BUS4.p);
  connect(Ln89.p, BUS8.p);
  connect(Ln45.p, BUS4.p);
  connect(Ln57.n, BUS7.p);
  connect(T1.p, BUS1.p);
  connect(Ln78.n, BUS8.p);
  connect(Ln46.n, BUS6.p);
  connect(Ln69.p, BUS6.p);
  connect(Ln69.n, BUS9.p);
  connect(Ln78.p, BUS7.p);
  connect(T3.p, BUS9.p);
  connect(Gn1.p, BUS1.p);
  connect(Gn2.p, BUS2.p);
  connect(Gn3.p, BUS3.p);
  connect(Gn2.PMECH0, Gn2.PMECH);
  connect(Gn2.EFD0, Gn2.EFD);
  connect(Gn1.PMECH0, Gn1.PMECH);
  connect(Gn1.EFD0, Gn1.EFD);
  connect(Gn3.PMECH0, Gn3.PMECH);
  connect(Gn3.EFD0, Gn3.EFD);
  //manually implemented, for simulation purposes
  connect(faultAtBus8.p, BUS8.p);
  annotation (uses(Modelica(version="3.2.1")));
end cim_ieee_9bus;
