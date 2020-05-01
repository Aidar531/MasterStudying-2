import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;

public class BlockingElement {
    public RMSValues rms_i;
    public RMSValues rms_u;
    public double dt=0.00025;
    public double blockingSetting; // уставка срабатывания по производной

    public boolean isBlkState() {
        return blkState;
    }

    public boolean blkState = false;

    public Double[] lastValue_I = {0.0,0.0,0.0};
    public Double[] lastValue_U = {0.0,0.0,0.0};
    public Double[] der_I = new Double[3];
    public Double[] der_U = new Double[3];
    private double dI=-1000000;
    private double dU=-1000000;

    public BlockingElement(RMSValues rms_i, RMSValues rms_u, double blockingSetting) {
        this.rms_i = rms_i;
        this.rms_u = rms_u;
        this.blockingSetting = blockingSetting;
    }

    public boolean getBlkState() {

        // расчет производной по току

        der_I[0]=Math.abs(rms_i.getPh("A")-lastValue_I[0])/dt;
        der_I[1]=Math.abs(rms_i.getPh("B")-lastValue_I[1])/dt;
        der_I[2]=Math.abs(rms_i.getPh("C")-lastValue_I[2])/dt;

//        der_U[0]=Math.abs(rms_u.getPh("A")-lastValue_U[0])/dt;
//        der_U[1]=Math.abs(rms_u.getPh("B")-lastValue_U[1])/dt;
//        der_U[2]=Math.abs(rms_u.getPh("C")-lastValue_U[2])/dt;

//        lastValue_U[0] = rms_u.getPh("A"); lastValue_U[1] = rms_u.getPh("B"); lastValue_U[2] = rms_u.getPh("C");
        lastValue_I[0] = rms_i.getPh("A"); lastValue_I[1] = rms_i.getPh("B"); lastValue_I[2] = rms_i.getPh("C");

        //Проверка условий срабатывания
        blkState = (der_I[0] > blockingSetting)
                | (der_I[1] > blockingSetting) |
                (der_I[2] > blockingSetting);
        return blkState;
    }
}
