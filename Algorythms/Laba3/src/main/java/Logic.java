public class Logic {
    public double tblk = 0; // счетчик времени для разрешающего сишгнала блокировки
    public double t1 = 0; // счетчик времени для первой ступени
    public double t2 = 0; // счетчик времени для второй ступени
    public boolean flag1=true;
    public boolean flag2=true;
    public boolean lastState = false;
    public boolean now;
    public boolean start = false;
    public ImpedanceData imp;
    public BlockingElement blk;

    public Logic(ImpedanceData imp, BlockingElement blk) {
        this.imp = imp;
        this.blk = blk;
    }

    public OutputData od = new OutputData();
    public OperationChar oc1 = new OperationChar(10, 81.67, 76.11, 7); // характеристика срабатывания первой ступени
    public OperationChar oc2 = new OperationChar(10, 81.67, 134.09, 28); // характеристика срабатывания второй ступени
    private int count = 0;

    public void process() {
        count++;
//        System.out.println(lastState);
        now = blk.getBlkState();
        if (now && !lastState) {
            start = true;
        } else if (start) {
            tblk += 0.00025;
        }
        // после срабатывания разрешающего сигнала блокировки ввод ступеней на 1 с
        if (tblk < 1) {
            //Проверка попадания в область 1 ступени
            if ((oc1.calculate(imp.getZ_phA_x(), imp.getZ_phA_y())) |
                    (oc1.calculate(imp.getZ_phB_x(), imp.getZ_phB_y())) |
                    (oc1.calculate(imp.getZ_phC_x(), imp.getZ_phC_y()))) {
                t1 += 0.00025;
                t2 += 0.00025;
                if ((t1 > 0.02) && flag1) {
                    flag1 = false;
                    od.trip1(true);
                }
                if ((t2 > 0.5) && flag2) {
                    flag2 = false;
                    od.trip2(true);
                }
            //Проверка попадания в область 2 ступени
            } else if ((oc2.calculate(imp.getZ_phA_x(), imp.getZ_phA_y())) |
                    (oc2.calculate(imp.getZ_phB_x(), imp.getZ_phB_y())) |
                    (oc2.calculate(imp.getZ_phC_x(), imp.getZ_phC_y()))) {
                t2 += 0.00025;
                if ((t2 > 0.5 && flag2)) {
                    flag2 = false;
                    od.trip2(true);
                }
            }
            else {
                t1 = 0;
                t2 = 0;
                start = false;
                od.trip1(false);
                od.trip2(false);
            }
        } else {
            tblk = 0;
            t1 = 0;
            t2 = 0;
            start = false;
            od.trip1(false);
            od.trip2(false);
        }
        lastState = now;
    }
}
