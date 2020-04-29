public class MiddleValue implements Filter {
    private double bufferA[] = new double[81];
    private double bufferB[] = new double[81];
    private double bufferC[] = new double[81]; // буффер для
    private SampleValue sv;
    private double k = 1.11 / 80;
    private double sumA = 0;
    private double sumB = 0;
    private double sumC = 0;
    private int count = 0;
    private RMSValues rms;

    public SampleValue getSv() {
        return sv;
    }

    public void setSv(SampleValue sv) {
        this.sv = sv;
    }

    public RMSValues getRms() {
        return rms;
    }

    public void setRms(RMSValues rms) {
        this.rms = rms;
    }

    public void calculate() {
//        sumA += Math.abs(sv.getPhA()) - bufferA[count];
//        bufferA[count] = Math.abs(sv.getPhA());
//        if (count++ >= 80) count = 0;
        sumA = sum(sumA,sv.getPhA(),bufferA,count);
        sumB = sum(sumB,sv.getPhB(),bufferB,count);
        sumC = sum(sumC,sv.getPhC(),bufferC,count);
        if (count++ >= 80) count = 0;
        rms.setPhA(sumA * k);
        rms.setPhB(sumB * k);
        rms.setPhC(sumC * k);
        Charts.addAnalogData(0,2,rms.getPhA());
        Charts.addAnalogData(1,2,rms.getPhB());
        Charts.addAnalogData(2,2,rms.getPhC());
        rms.setTime(sv.getTime());
    }
    private double sum(double summa,double ph, double[] buffer, int count) {
        summa += Math.abs(ph) - buffer[count];
        buffer[count] = Math.abs(ph);
                return summa;

    }


}
