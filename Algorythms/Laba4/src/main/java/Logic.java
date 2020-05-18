public class Logic {
    private RNM rnm;
    private SeqValues seq;
    private ProtectionStageRelay stupen1;
    private ProtectionStageRelay stupen2;
    private ProtectionStageRelay stupen3;
    private ProtectionStageRelay stupen4;
    private ProtectionStageRelay stupen5;
    private TimeRelay tm1;
    private TimeRelay tm2;
    private TimeRelay tm3;
    private TimeRelay tm4;
    private TimeRelay tm5;

    private OutputData od = new OutputData();


    public Logic(RNM rnm, SeqValues seq) {
        this.rnm = rnm;
        this.seq = seq;
        this.stupen1 = new ProtectionStageRelay(0.65, seq);
        this.stupen2 = new ProtectionStageRelay(0.5, seq);
        this.stupen3 = new ProtectionStageRelay(0.338, seq);
        this.stupen4 = new ProtectionStageRelay(0.195, seq);
        this.stupen5 = new ProtectionStageRelay(0.08, seq);
        this.tm1 = new TimeRelay(0.05);
        this.tm2 = new TimeRelay(0.5);
        this.tm3 = new TimeRelay(0.5);
        this.tm4 = new TimeRelay(0.8);
        this.tm5 = new TimeRelay(2);
    }

    public void process() {
        System.out.println("Прямое: " + rnm.isForward());
        System.out.println("Обратное: " + rnm.isBackward());
//        System.out.println("Stup1 " + stupen1.isOperation());
//        System.out.println("Stup2 " + stupen2.isOperation());
//        System.out.println("Stup3 " + stupen3.isOperation());
//        System.out.println("Stup4 " + stupen4.isOperation());
//        System.out.println("Stup5 " + stupen5.isOperation());

        if (stupen1.isOperation()) od.trip1(tm1.isOperation(stupen1.isOperation()));
        else od.trip1(false);

        if (rnm.isForward()) {
            if (stupen2.isOperation()) {
                System.out.println("22222");
                od.trip2(tm2.isOperation(stupen2.isOperation()));
            } else od.trip2(false);
        } else od.trip2(false);

        if (stupen3.isOperation()) {
            System.out.println("33333");
            if (!rnm.isBackward()) od.trip3(tm3.isOperation(stupen3.isOperation()));
        } else od.trip3(false);

        if (rnm.isForward()) {
            if (stupen4.isOperation()) {
                od.trip4(tm4.isOperation(stupen4.isOperation()));
            } else od.trip4(false);
        } else od.trip4(false);

        if (stupen5.isOperation()) od.trip5(tm5.isOperation(stupen5.isOperation()));
        else od.trip5(false);


    }
}
