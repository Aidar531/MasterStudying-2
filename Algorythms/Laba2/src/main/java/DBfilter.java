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
//        System.out.println("-----------------");
//        System.out.println("Time: " + rms[0].getTime());
        for (RMSValues i:rms) {
//            System.out.println("Шина: " + i.getID());
//            System.out.println("Value of A: " + i.getPhA());
//            System.out.println("Phase of A: " + i.getAngleA());
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

        DB.setBrakingValueA(brakSumA);
        DB.setBrakingValueB(brakSumB);
        DB.setBrakingValueC(brakSumC);

//        System.out.println(DB.getDiffValueA());
//        System.out.println(DB.getBrakingValueA());
    }
    private double getY(RMSValues rms, String phase) {
        double x=0;
        if ("A".equals(phase)) {
            x = rms.getPhA() * Math.cos(Math.toRadians(rms.getAngleA()));
        } else if ("B".equals(phase)) {
            x = rms.getPhB() * Math.cos(Math.toRadians(rms.getAngleB()));
        } else if ("C".equals(phase)) {
            x = rms.getPhC() * Math.cos(Math.toRadians(rms.getAngleC()));
        }
        return x;
    }
    private double getX(RMSValues rms, String phase) {
        double y = 0;
        if ("A".equals(phase)) {
            y = rms.getPhA() * Math.sin(Math.toRadians(rms.getAngleA()));
        } else if ("B".equals(phase)) {
            y = rms.getPhB() * Math.sin(Math.toRadians(rms.getAngleB()));
        } else if ("C".equals(phase)) {
            y = rms.getPhC() * Math.sin(Math.toRadians(rms.getAngleC()));
        }
        return y;
    }

}
