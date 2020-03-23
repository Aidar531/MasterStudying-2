public class Logic {
    private double tripPoint = 0.33; // уставка защиты
    private double timeSetting = 0.5; // Задерожка срабатывания
    private double tm = 0;
    private RMSValues rms;
    private OutputData od = new OutputData();
    private int count =0;

    public void setRms(RMSValues rms) {
        this.rms = rms;
    }

    public void process() {
        count++;
//        System.out.println(count);
        Charts.addAnalogData(0,1,tripPoint);
        Charts.addAnalogData(1,1,tripPoint);
        Charts.addAnalogData(2,1,tripPoint);
        if ((rms.getPhA() > tripPoint) | (rms.getPhC() > tripPoint) | (rms.getPhB() > tripPoint)) {
            tm += 0.00025;
            if (tm >= timeSetting) {
                od.trip(true);
            }
            if (count >7999) od.trip(false);
        } else {
            tm = 0;
            od.trip(false);
        }
    }


}
