import javax.xml.bind.Marshaller;

public class SequencesFilter {
    private double A0_x = 0;
    private double A1_x = 0;
    private double A2_x = 0;
    private double A0_y = 0;
    private double A1_y = 0;
    private double A2_y = 0;



    private RMSValues rms;
    private SeqValues seq;

    public SequencesFilter(RMSValues rms, SeqValues seq) {
        this.rms = rms;
        this.seq = seq;
    }

    public void calcSeq0() {
        A0_x = 1.0/3.0 * (rms.getPhA_x() + rms.getPhB_x() + rms.getPhC_x());
        A0_y = 1.0/3.0 * (rms.getPhA_y() + rms.getPhB_y() + rms.getPhC_y());
        seq.setSeq0(Math.sqrt(Math.pow(A0_x, 2) + Math.pow(A0_y, 2)));
        calcAngle0();
    }
    private void calcAngle0() {
        double angle=Math.toDegrees(Math.atan(A0_y/A0_x));
        if (A0_x<0 && A0_y>0) {
            angle = 180+angle;
        }
        else if (A0_y<0 && A0_x<0) angle = -180+angle;
        seq.setAngleSeq0(angle);
    }
    public void calcSeq2() {
        double a_y = Math.sqrt(3)/2;
        A2_x = 1.0/3.0 * (rms.getPhA_x() - 0.5 * rms.getPhB_x() - a_y * rms.getPhB_y() - 0.5 * rms.getPhC_x() + a_y * rms.getPhC_y());
        A2_y = 1.0/3.0 * (rms.getPhA_y() - 0.5 * rms.getPhB_y() + a_y * rms.getPhB_x() - 0.5 * rms.getPhC_y() - a_y * rms.getPhC_x());
        seq.setSeq2(Math.sqrt(Math.pow(A2_x, 2) + Math.pow(A2_y, 2)));
        calcAngle2();
    }
    private void calcAngle2() {
        double angle=Math.toDegrees(Math.atan(A2_y/A2_x));
        if (A2_x<0 && A2_y>0) {
            angle = 180+angle;
        }
        else if (A2_y<0 && A2_x<0) angle = -180+angle;
        seq.setAngleSeq2(angle);
    }
    public void calcSeq1() {
        double a_y = Math.sqrt(3)/2;
        A1_x = 1.0/3.0 * (rms.getPhA_x() - 0.5 * rms.getPhC_x() - a_y * rms.getPhC_y() - 0.5 * rms.getPhB_x() + a_y * rms.getPhB_y());
        A1_y = 1.0/3.0 * (rms.getPhA_y() - 0.5 * rms.getPhC_y() + a_y * rms.getPhC_x() - 0.5 * rms.getPhB_y() - a_y * rms.getPhB_x());
        seq.setSeq1(Math.sqrt(Math.pow(A1_x, 2) + Math.pow(A1_y, 2)));
        calcAngle1();
    }
    private void calcAngle1() {
        double angle=Math.toDegrees(Math.atan(A1_y/A1_x));
        if (A1_x<0 && A1_y>0) {
            angle = 180+angle;
        }
        else if (A1_y<0 && A1_x<0) angle = -180+angle;
        seq.setAngleSeq1(angle);
    }
    
}
