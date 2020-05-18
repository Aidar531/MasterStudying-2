public class RMSValues {

    private double phA_x = 0;
    private double phA_y = 0;
    private double phB_x = 0;
    private double phB_y = 0;
    private double phC_x = 0;
    private double phC_y = 0;

    private double time = 0;

    public double getPhA_x() {
        return phA_x;
    }

    public void setPhA_x(double phA_x) {
        this.phA_x = phA_x;
    }

    public double getPhA_y() {
        return phA_y;
    }

    public void setPhA_y(double phA_y) {
        this.phA_y = phA_y;
    }

    public double getPhB_x() {
        return phB_x;
    }

    public void setPhB_x(double phB_x) {
        this.phB_x = phB_x;
    }

    public double getPhB_y() {
        return phB_y;
    }

    public void setPhB_y(double phB_y) {
        this.phB_y = phB_y;
    }

    public double getPhC_x() {
        return phC_x;
    }

    public void setPhC_x(double phC_x) {
        this.phC_x = phC_x;
    }

    public double getPhC_y() {
        return phC_y;
    }

    public void setPhC_y(double phC_y) {
        this.phC_y = phC_y;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
    public double getPh(String phase) {
        double A=0;
        if ("A".equals(phase)) {
            A = Math.sqrt(Math.pow(phA_x, 2) + Math.pow(phA_y, 2));
        } else if ("B".equals(phase)) {
            A = Math.sqrt(Math.pow(phB_x, 2) + Math.pow(phB_y, 2));
        } else if ("C".equals(phase)) {
            A = Math.sqrt(Math.pow(phC_x, 2) + Math.pow(phC_y, 2));
        }
        return A;
    }
}