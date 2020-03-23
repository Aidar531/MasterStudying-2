public class MiddleValue implements Filter{
    private double buffer[] = new double[20];
    private SampleValue sv;
    private double k = 1.11/20;
    private double sum = 0;
    private int count =0;
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
        sum += Math.abs(sv.getPhA()) - buffer[count];
        buffer[count] = Math.abs(sv.getPhA());
        if (count++>=20) count = 0;
        rms.setPhA(sum*k);
        rms.setTime(sv.getTime());
    }


}
