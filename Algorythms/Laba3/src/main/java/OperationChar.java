public class OperationChar {
    private boolean flag=false;
    private double tanPhi1; //
    private double tanPhi2; //
    private double tanPhi3; //
    private double tanPhi4; //
    private double X0;      // Значения точек и параметров характеристики срабатывания
    private double X1;      //
    private double X2;      //
    private double X3;      //
    private double Xust;    //
    private double Rust;    //
    public OperationChar(double phi1, double phi2, double xust, double rust) {
        Xust = xust;
        Rust = rust;

        this.tanPhi1 = Math.tan(Math.toRadians(phi1));
        this.tanPhi2 = Math.tan(Math.toRadians(phi2));
        this.tanPhi3 = tanPhi2;
        this.tanPhi4 = Math.tan(Math.toRadians(65));

        X0 = Rust * tanPhi2 / (tanPhi2 + tanPhi1);
        X1 = Rust + (Xust) / (tanPhi2);
        X2 = (Xust) / (tanPhi2) - Rust;
        X3 = -(Rust * tanPhi2) / (tanPhi2 + tanPhi4);
    }
    //проверка на срабатывание по тормозной характеристике
    public boolean calculate(double Z_X, double Z_Y) {

        // Проверка попадания в область срабтывания

        if (Z_X > X3 && Z_X < X2) {
            if ((Z_Y < tanPhi2 * (Z_X + Rust)) && (Z_Y > -tanPhi4 * Z_X)) {
                flag = true;
            } else flag = false;
        }
        if (Z_X > X2 && Z_X < 0) {
            if ((Z_Y < Xust) && (Z_Y >  -tanPhi4 * Z_X)) {
                flag = true;
            } else flag = false;
        }
        if (Z_X > 0 && Z_X < X0) {
            if ((Z_Y < Xust) && (Z_Y > - tanPhi1 * Z_X)) {
                flag = true;
            } else flag = false;
        }
        if (Z_X > X0 && Z_X < X1) {
            if ((Z_Y < Xust) && (Z_Y >  tanPhi2 * (Z_X-Rust))) {
                flag = true;
            } else flag = false;
        }

        return flag;
    }
}
