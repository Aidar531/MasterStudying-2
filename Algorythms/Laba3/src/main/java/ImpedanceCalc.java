public class ImpedanceCalc {
    private RMSValues rms_i;
    private RMSValues rms_u;
    private ImpedanceData imp;

    private double k0x;
    private double k0y;

    public ImpedanceCalc(RMSValues rms_i, RMSValues rms_u, ImpedanceData imp, double k0x, double k0y) {
        this.rms_i = rms_i;
        this.rms_u = rms_u;
        this.imp = imp;
        this.k0x = k0x;
        this.k0y = k0y;
    }

    public void calculate() {

        imp.setZ_phA_x(getZ_X(rms_i.getPhA_x(), rms_i.getPhA_y(), rms_u.getPhA_x(), rms_u.getPhA_y()));
        imp.setZ_phA_y(getZ_Y(rms_i.getPhA_x(), rms_i.getPhA_y(), rms_u.getPhA_x(), rms_u.getPhA_y()));

        imp.setZ_phB_x(getZ_X(rms_i.getPhB_x(), rms_i.getPhB_y(), rms_u.getPhB_x(), rms_u.getPhB_y()));
        imp.setZ_phB_y(getZ_Y(rms_i.getPhB_x(), rms_i.getPhB_y(), rms_u.getPhB_x(), rms_u.getPhB_y()));

        imp.setZ_phC_x(getZ_X(rms_i.getPhA_x(), rms_i.getPhA_y(), rms_u.getPhA_x(), rms_u.getPhA_y()));
        imp.setZ_phC_y(getZ_Y(rms_i.getPhA_x(), rms_i.getPhA_y(), rms_u.getPhA_x(), rms_u.getPhA_y()));

    }

    public double getZ_X(double i_ph_x, double i_ph_y, double u_ph_x, double u_ph_y) {
        double X = i_ph_x + k0x * rms_i.getPh0_x() - k0y * rms_i.getPh0_y();
        double Y = i_ph_y + k0x * rms_i.getPh0_y() + k0y * rms_i.getPh0_x();
        double K = 1 / (Math.pow(X, 2) + Math.pow(Y, 2));
        return K * (u_ph_x * X + u_ph_y * Y);
    }

    public double getZ_Y(double i_ph_x, double i_ph_y, double u_ph_x, double u_ph_y) {
        double X = i_ph_x + k0x * rms_i.getPh0_x() - k0y * rms_i.getPh0_y();
        double Y = i_ph_y + k0x * rms_i.getPh0_y() + k0y * rms_i.getPh0_x();
        double K = 1 / (Math.pow(X, 2) + Math.pow(Y, 2));
        return K * (u_ph_y * X - u_ph_x * Y);
    }

}
