public class Logic {
    public double tblk = 0;
    public boolean lastState = false;
    public boolean start = false;
    public ImpedanceData imp;
    public BlockingElement blk;

    public Logic(ImpedanceData imp, BlockingElement blk) {
        this.imp = imp;
        this.blk = blk;
    }

    public OutputData od = new OutputData();
    public OperationChar oc1 = new OperationChar(10,81.67,76.11,5);
    public OperationChar oc2 = new OperationChar(10,81.67,134.09,20);
    private int count = 0;

    public void process() {
        count++;
        if (blk.getBlkState() && !lastState) {
            od.tripPO(true);
            System.out.println("TRIPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
            start = true;
        }
        System.out.println();
        if (start) {
            tblk+=0.00025;
        }
        if (tblk<0.5) {
            if ((oc1.calculate(imp.getZ_phA_x(), imp.getZ_phA_y())) |
                    (oc1.calculate(imp.getZ_phB_x(), imp.getZ_phB_y())) |
                    (oc1.calculate(imp.getZ_phC_x(), imp.getZ_phC_y()))) {
                od.trip(true, 1);
                od.trip(true, 2);
            } else if ((oc2.calculate(imp.getZ_phA_x(), imp.getZ_phA_y())) |
                    (oc2.calculate(imp.getZ_phB_x(), imp.getZ_phB_y())) |
                    (oc2.calculate(imp.getZ_phC_x(), imp.getZ_phC_y()))) {
                od.trip(true, 2);
            }
            else {
                od.trip(false, 1);
                od.trip(false, 2);
            }
        } else {
            start = false;
            od.tripPO(false);
            od.trip(false,1);
            od.trip(false,2);
        }
        lastState = blk.isBlkState();
    }
}
