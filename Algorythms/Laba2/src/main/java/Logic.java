public class Logic {
    private double brakSetting = 100; // уставка начала торможения
    private double diffSetting = 5 ; // уставка Дo
    private double blockingSetting = 0.4;
    private double k=0.13; // наклон характеристики срабатывания
    private double tm = 0;
    private DBValues DB;
    private RMSValues[] blockingRMS;
    private OutputData od = new OutputData();
    private int count = 0;

    public void setBlockingRMS(RMSValues[] blockingRMS) {
        this.blockingRMS = blockingRMS;
    }

    public void setDB(DBValues DB) {
        this.DB = DB;
    }

    /*
    TODO
      Рассчитать уставки защиты
      далее склорее всего придется сделать другой ток возврата, чтобы не дребезжало
    */
    public void process() {
        count++;
//        System.out.println(count);
//        Charts.addAnalogData(0,1,tripPoint);
//        Charts.addAnalogData(1,1,tripPoint);
//        Charts.addAnalogData(2,1,tripPoint);
        if (!isBlocked(blockingRMS)) {
            if ((brakingChar(DB.getDiffValueA(), DB.getBrakingValueB())) |
                    (brakingChar(DB.getDiffValueB(), DB.getBrakingValueB())) |
                    (brakingChar(DB.getDiffValueC(), DB.getBrakingValueC()))) {
                od.trip(true);
                if (count > 7999) od.trip(false);
            } else od.trip(false);
//            System.out.println("Защита не работает");
        } else od.trip(false);;
//            System.out.println("BLOCK!!!!!!!!!!!!!");
    }


    private boolean brakingChar(double diff, double brak) {
        boolean flag=false;
        if (brak> brakSetting) {
            if (diff > k*(brak- brakSetting) + diffSetting) flag =true;
        }
        else if (diff>diffSetting) flag=true;
            return flag;
    }
    private boolean isBlocked(RMSValues[] rms){
        boolean flag=false;
        for (RMSValues i:rms) {
            if ((i.getPhA()>blockingSetting)|
                    (i.getPhB()>blockingSetting)|
                    (i.getPhC()>blockingSetting)) {
                flag= true;
            }
        }
        return flag;
    }


}
