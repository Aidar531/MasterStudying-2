public class FurrieFilter implements FilterFur {
    private double[] bufferFxA;
    private double[] bufferFyA;
    private double[] bufferFxB;
    private double[] bufferFyB;
    private double[] bufferFxC;
    private double[] bufferFyC;
    private double[] bufferFx0;
    private double[] bufferFy0;

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
    private double Fx0 = 0;
    private double Fy0 = 0;

    public double getFxA() {
        return FxA;
    }

    public double getFyA() {
        return FyA;
    }

    public double getFxB() {
        return FxB;
    }

    public double getFyB() {
        return FyB;
    }

    public double getFxC() {
        return FxC;
    }

    public double getFyC() {
        return FyC;
    }

    public double getFx0() {
        return Fx0;
    }

    public double getFy0() {
        return Fy0;
    }

    public FurrieFilter(int frequency, int numberOfPoints) {
        this.frequency = frequency;
        this.Kf = 2/(numberOfPoints*Math.sqrt(2));
        this.numberOfPoints = numberOfPoints*50/frequency;


        bufferFxA = new double[this.numberOfPoints];bufferFyA = new double[this.numberOfPoints];
        bufferFxB = new double[this.numberOfPoints];bufferFyB = new double[this.numberOfPoints];
        bufferFxC = new double[this.numberOfPoints];bufferFyC = new double[this.numberOfPoints];
        bufferFx0 = new double[this.numberOfPoints];bufferFy0 = new double[this.numberOfPoints];

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
        Fx0 = getFx(Fx0,sv.getPh0(), bufferFx0,count);
        Fy0 = getFy(Fy0,sv.getPh0(), bufferFy0,count);

        rms.setPhA_x(FxA);
        rms.setPhA_y(FyA);
        rms.setPhB_x(FxB);
        rms.setPhB_y(FyB);
        rms.setPhC_x(FxC);
        rms.setPhC_x(FyC);
        rms.setPh0_x(Fx0);
        rms.setPh0_y(Fy0);

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
    //Получение децйствубщего значения
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
