public class ImpedanceData {
    private double Z_phA_x;
    private double Z_phA_y;
    private double Z_phB_x;
    private double Z_phB_y;
    private double Z_phC_x;
    private double Z_phC_y;

    public double getZ_phA_x() {
        return Z_phA_x;
    }

    public void setZ_phA_x(double z_phA_x) {
        Z_phA_x = z_phA_x;
    }

    public double getZ_phA_y() {
        return Z_phA_y;
    }

    public void setZ_phA_y(double z_phA_y) {
        Z_phA_y = z_phA_y;
    }

    public double getZ_phB_x() {
        return Z_phB_x;
    }

    public void setZ_phB_x(double z_phB_x) {
        Z_phB_x = z_phB_x;
    }

    public double getZ_phB_y() {
        return Z_phB_y;
    }

    public void setZ_phB_y(double z_phB_y) {
        Z_phB_y = z_phB_y;
    }

    public double getZ_phC_x() {
        return Z_phC_x;
    }

    public void setZ_phC_x(double z_phC_x) {
        Z_phC_x = z_phC_x;
    }

    public double getZ_phC_y() {
        return Z_phC_y;
    }

    public void setZ_phC_y(double z_phC_y) {
        Z_phC_y = z_phC_y;
    }

    public double getZ(String phase) {
        double Z=0;
        if ("A".equals(phase)) {
            Z = Math.sqrt(Math.pow(Z_phA_x, 2) + Math.pow(Z_phA_y, 2));
        } else if ("B".equals(phase)) {
            Z = Math.sqrt(Math.pow(Z_phB_x, 2) + Math.pow(Z_phB_y, 2));
        } else if ("C".equals(phase)) {
            Z = Math.sqrt(Math.pow(Z_phC_x, 2) + Math.pow(Z_phC_y, 2));
        }
        return Z;
    }
}
