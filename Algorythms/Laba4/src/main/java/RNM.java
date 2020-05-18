public class RNM {
    private boolean isForward = false;
    private boolean isBackward = true;
    private double PhiMCH = 140;

    private SeqValues Seq_I;
    private SeqValues Seq_U;

    public RNM(SeqValues seq_I, SeqValues seq_U) {
        Seq_I = seq_I;
        Seq_U = seq_U;
    }

    public boolean isForward() {

        return isForward;
    }

    public boolean isBackward() {
        return isBackward;
    }

    public void calculateDirection() {
        double angle = Seq_U.getAngleSeq0() - Seq_I.getAngleSeq0();

        if (angle < 0) {
            angle =  360 + angle;
        }
        System.out.println("Угол "+angle);
        isForward = !(((angle) <= (90 + PhiMCH)) || (((angle) >= 360 - (90 - PhiMCH))));

        isBackward = !(((angle) >= (90 + PhiMCH)) && ((angle) <= 360 - (90 - PhiMCH)));
    }
}
