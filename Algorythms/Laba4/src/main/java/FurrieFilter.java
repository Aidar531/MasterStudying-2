public class FurrieFilter implements FilterFur {
    private double[] bufferFxA;
    private double[] bufferFyA;
    private double[] bufferFxB;
    private double[] bufferFyB;
    private double[] bufferFxC;
    private double[] bufferFyC;


    private int numberOfPoints;
    private int frequency;
    private double[] SIN;
    private double[] COS;

    private double Kf;

    private double FxA = 0;
    private double FyA = 0;
    private double FxB = 0;
    private double FyB = 0;
    private double FxC = 0;
    private double FyC = 0;




    public FurrieFilter(int frequency, int numberOfPoints) {
        this.frequency = frequency;
        this.Kf = 2/(numberOfPoints*Math.sqrt(2));
        this.numberOfPoints = numberOfPoints*50/frequency;


        bufferFxA = new double[this.numberOfPoints];bufferFyA = new double[this.numberOfPoints];
        bufferFxB = new double[this.numberOfPoints];bufferFyB = new double[this.numberOfPoints];
        bufferFxC = new double[this.numberOfPoints];bufferFyC = new double[this.numberOfPoints];


        SIN = new double[this.numberOfPoints];
        COS = new double[this.numberOfPoints];
        for (int i = 0; i < this.numberOfPoints; i++) {
            SIN[i] = Math.sin(2 * Math.PI * i / this.numberOfPoints);
            COS[i] = Math.cos(2 * Math.PI * i / this.numberOfPoints);
        }
    }

    private int count = 0;

    private SampleValue sv;
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
        FxA = getFx(FxA,sv.getPhA(), bufferFxA,count);
        FyA = getFy(FyA,sv.getPhA(), bufferFyA,count);
        FxB = getFx(FxB,sv.getPhB(), bufferFxB,count);
        FyB = getFy(FyB,sv.getPhB(), bufferFyB,count);
        FxC = getFx(FxC,sv.getPhC(), bufferFxC,count);
        FyC = getFy(FyC,sv.getPhC(), bufferFyC,count);
//        System.out.println(FxA);
//        System.out.println(FyA);

        rms.setPhA_x(Kf*FxA);
        rms.setPhA_y(Kf*FyA);
        rms.setPhB_x(Kf*FxB);
        rms.setPhB_y(Kf*FyB);
        rms.setPhC_x(Kf*FxC);
        rms.setPhC_y(Kf*FyC);

        if (count++ >= numberOfPoints-1) count = 0;

    }
    //Вывод графика
    public void showGraph(int series) {
        Charts.addAnalogData(0,series, getRMSValue(FxA,FyA));
        Charts.addAnalogData(1,series, getRMSValue(FxB,FyB));
        Charts.addAnalogData(2,series, getRMSValue(FxC,FyC));
    }

    //Выделение синусной составляющей
    private double getFx(double fx, double ph, double[] buffer , int count) {
        fx += SIN[count] * ph - buffer[count];
        buffer[count] = SIN[count] * ph;
        return fx;
    }
    //Выделение косинусной составляющей
    private double getFy(double fy, double ph, double[] buffer , int count) {
        fy += COS[count] * ph - buffer[count];
        buffer[count] = COS[count] * ph;
        return fy;
    }
    //Получение действующего значения
    private double getRMSValue(double fx,double fy) {
            return Kf*Math.sqrt(Math.pow(fx, 2) + Math.pow(fy, 2));
        }
    //Получение угла
    private double getAngle(double fx,double fy) {
        double angle=Math.toDegrees(Math.atan(fy/fx));
        if (fx<0 && fy>0) {
            angle = 180+angle;
        }
        else if (fy<0 && fx<0) angle = -180+angle;
        return angle;
    }
}
