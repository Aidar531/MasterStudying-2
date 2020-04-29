public class Logic {
    private double tripPoint = 10;
    private RMSValues rms;
    private OutputData od = new OutputData();
    public RMSValues getRms() {
        return rms;
    }

    public void setRms(RMSValues rms) {
        this.rms = rms;
    }

    public void process() {
        if (rms.getPhA() > tripPoint) od.trip(true); else od.trip(false);
    }


}
