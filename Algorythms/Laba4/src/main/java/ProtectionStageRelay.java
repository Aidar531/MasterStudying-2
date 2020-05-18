public class ProtectionStageRelay {
    private double setting;
    private SeqValues seq;
    private boolean operation;

    public ProtectionStageRelay(double setting, SeqValues seq) {
        this.setting = setting;
        this.seq = seq;
    }

    public boolean isOperation() {
        operation = (seq.getSeq0()>setting);
        return operation;
    }
}
