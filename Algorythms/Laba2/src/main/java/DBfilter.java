import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

public class DBfilter implements DBFilter {

    private  DBValues DB;
    private RMSValues[] rms;
    private double Xa=0;
    private double Ya=0;
    private double Xb=0;
    private double Yb=0;
    private double Xc=0;
    private double Yc=0;

    private double diffSumA=0;
    private double diffSumB=0;
    private double diffSumC=0;
    private double brakSumA;
    private double brakSumB;
    private double brakSumC;
    private double basis=1;
    private int count=0;

    public void setRms(RMSValues[] rms) {
        this.rms = rms;
    }

    public RMSValues[] getRms() {
        return rms;
    }

    public DBValues getDB() {
        return DB;
    }

    public void setDB(DBValues DB) {
        this.DB = DB;
    }

    //Вычисление дифференциального и тормозного тока
    public void calculate() {
        brakSumA=0;
        brakSumB=0;
        brakSumC=0;
        diffSumA = 0;
        diffSumB = 0;
        diffSumC = 0;
        Xa=0;
        Ya=0;
        Xb=0;
        Yb=0;
        Xc=0;
        Yc=0;

        for (RMSValues i:rms) {

            Xa+=getX(i,"A");
            Ya+=getY(i,"A");
            Xb+=getX(i,"B");
            Yb+=getY(i,"B");
            Xc+=getX(i,"C");
            Yc+=getY(i,"C");
            brakSumA+=i.getPhA()/basis;
            brakSumB+=i.getPhB()/basis;
            brakSumC+=i.getPhC()/basis;
        }

        diffSumA = Math.sqrt(Math.pow(Xa,2)+Math.pow(Ya,2));
        diffSumB = Math.sqrt(Math.pow(Xb,2)+Math.pow(Yb,2));
        diffSumC = Math.sqrt(Math.pow(Xc,2)+Math.pow(Yc,2));

        DB.setDiffValueA(diffSumA/basis);
        DB.setDiffValueB(diffSumB/basis);
        DB.setDiffValueC(diffSumC/basis);

        DB.setBrakingValueA(0.5*brakSumA);
        DB.setBrakingValueB(0.5*brakSumB);
        DB.setBrakingValueC(0.5*brakSumC);

    }

    public void showGraph(int series) {
        Charts.addAnalogData(0,series, DB.getDiffValueA());
        Charts.addAnalogData(1,series, DB.getDiffValueB());
        Charts.addAnalogData(2,series, DB.getDiffValueC());
        Charts.addAnalogData(0,series+1, DB.getBrakingValueA());
        Charts.addAnalogData(1,series+1, DB.getBrakingValueB());
        Charts.addAnalogData(2,series+1, DB.getBrakingValueC());
    }
    //Получение координаты x вектора
    private double getX(RMSValues rms, String phase) {
        double x=0;
        if (phase.equals("A")) {
            x = rms.getPhA() * Math.cos(Math.toRadians(rms.getAngleA()));

        } else if (phase.equals("B")) {
            x = rms.getPhB() * Math.cos(Math.toRadians(rms.getAngleB()));
        } else if (phase.equals("C")) {
            x = rms.getPhC() * Math.cos(Math.toRadians(rms.getAngleC()));
        }
        return x;
    }
    //Получение координаты y вектора
    private double getY(RMSValues rms, String phase) {
        double y = 0;
        if (phase.equals("A")) {
            y = rms.getPhA() * Math.sin(Math.toRadians(rms.getAngleA()));
        } else if (phase.equals("B")) {
            y = rms.getPhB() * Math.sin(Math.toRadians(rms.getAngleB()));

        } else if (phase.equals("C")) {
            y = rms.getPhC() * Math.sin(Math.toRadians(rms.getAngleC()));
        }
        return y;
    }

}
