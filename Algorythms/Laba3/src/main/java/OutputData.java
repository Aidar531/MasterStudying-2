public class OutputData {

    public void tripPO(boolean flag) {
        Charts.addDiscreteData(0,flag);
    }
    public void trip(boolean trip, int stupen) {
        if (stupen == 1)  Charts.addDiscreteData(1, trip);
        else if (stupen == 2) Charts.addDiscreteData(2, trip);
    }
}
