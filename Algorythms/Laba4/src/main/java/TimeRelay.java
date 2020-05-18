
public class TimeRelay {
    private double setting;
    private boolean operation = false;

    public TimeRelay(double setting) {
        this.setting = setting;
    }

    private double t1; //Счетчик времени для срабавтывания
    private double t2; // Cчетчик времени для сбрасывания t1
    private double deltaT = 0.00025; // шаг времени


    public boolean isOperation(boolean flag) {
        if (flag) {
            t1+=deltaT;
            t2 =0;
        }
        else t2+=deltaT;

        if (t2>0.03) {
            t2 = 0;
            t1=0;
        }
        operation = (t1 > setting);

        return operation;
    }

}
