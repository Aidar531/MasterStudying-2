import org.jfree.data.json.JSONUtils;

public class Godograph {
    public ImpedanceData IMPData;
    private int count =0;
    public Double[] settingX1 = new Double[402];
    public Double[] settingY1= new Double[402];
    public Double[] settingX2 = new Double[1099];
    public Double[] settingY2= new Double[1099];

    public double tanPhi1, tanPhi2, tanPhi3, tanPhi4, Xust, Rust;
    public double X0, X1, X2, X3;

    public Godograph(ImpedanceData IMPData) {
        this.IMPData = IMPData;

    }

    public void drawSetting() {

        ChartsXY.createAnalogChart("XR", 0);
        ChartsXY.addSeries("setting1", 0, 0);
        ChartsXY.addSeries("setting2", 0, 1);
        ChartsXY.addSeries("Фаза А", 0, 2);
        ChartsXY.addSeries("Фаза B", 0, 3);
        ChartsXY.addSeries("Фаза С", 0, 4);
        System.out.println("setting1");
        createCharacteristic(settingX1,settingY1,10,81.67,76.11,5);
        System.out.println("setting2");
        createCharacteristic(settingX2,settingY2,10,81.67,134.09,20);

        for (int i=0;i<settingX1.length;i++) ChartsXY.addAnalogData(0, 0, settingX1[i], settingY1[i]);

        for (int i=0;i<settingX2.length;i++) ChartsXY.addAnalogData(0, 1, settingX2[i], settingY2[i]);
    }

    public void showGodograph() {
        if (count >80 ) {
            ChartsXY.addAnalogData(0, 2, IMPData.getZ_phA_x(), IMPData.getZ_phA_y());
            ChartsXY.addAnalogData(0, 3, IMPData.getZ_phB_x(), IMPData.getZ_phB_y());
            ChartsXY.addAnalogData(0, 4, IMPData.getZ_phC_x(), IMPData.getZ_phC_y());
        }
        count ++;
    }

    public void createCharacteristic(Double[] settingX,Double[] settingY,double phi1, double phi2,double Xust, double Rust) {
        tanPhi1 = Math.tan(Math.toRadians(phi1));
        tanPhi2 = Math.tan(Math.toRadians(phi2));
        tanPhi3 = tanPhi2;
        tanPhi4 = Math.tan(Math.toRadians(65));

        X0 = Rust *tanPhi2 / (tanPhi2 + tanPhi1);
        X1 = Rust  + (Xust) / (tanPhi2);
        X2 = (Xust) / (tanPhi2) - Rust;
        X3 = - (Rust *tanPhi2) / (tanPhi2 + tanPhi4);

        System.out.println("X0= "+ X0);
        System.out.println("X1= "+ X1);
        System.out.println("X2= "+ X2);

        int count = 0;
        for (double i = 0; i <= X0; i += 0.1) {
            settingX[count] = i;
            settingY[count] = -tanPhi1 * i;
            count++;
        }
        for (double i = X0; i <= X1; i += 0.1) {
            settingX[count] = i;
            settingY[count] = tanPhi2 * (i - Rust);
            count++;
        }
        for (double i = X2; i <= X1; i += 0.1) {
            settingX[count] = i;
            settingY[count] = Xust;
            count++;
        }
        for (double i = X3; i <= X2; i += 0.1) {
            settingX[count] = i;
            settingY[count] = tanPhi2*(i+Rust);
            count++;
        }

        for (double i = X3; i <= 0; i += 0.1) {
            settingX[count] = i;
            settingY[count] = -tanPhi4 * i;
            count++;
        }
        System.out.println(count);

    }
}
