import org.jfree.data.json.JSONUtils;

public class Logic {
    private double brakSetting = 1; // уставка начала торможения
    private double diffSetting = 1.98 ; // уставка Дo
    private double blockingSetting = 0.4;//уставка блокировки
    private double k=0.61; // наклон характеристики срабатывания
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

    public void process() {
        count++;
        if (!isBlocked(blockingRMS)) {
            if ((brakingChar(DB.getDiffValueA(), DB.getBrakingValueA())) |
                    (brakingChar(DB.getDiffValueB(), DB.getBrakingValueB())) |
                    (brakingChar(DB.getDiffValueC(), DB.getBrakingValueC()))) {
                od.trip(true);
                if (count>1999) od.trip(false);
            } else od.trip(false);
        } else {od.trip(false);
            System.out.println("BLOCK!!!!!!!!!!!!!");}
    }

    //проверка на срабатывание по тормозной характеристике
    private boolean brakingChar(double diff, double brak) {
        boolean flag=false;

        if (brak> brakSetting) {
            if (diff > k*(brak - brakSetting) + diffSetting) flag =true;
        }
        else if (diff>diffSetting) flag=true;
            return flag;
    }
    //Првоерка на блокировку
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
