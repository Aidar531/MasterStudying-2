
public class Main {
    public static void main(String[] args) {
        Charts.createAnalogChart("Ток в фазе A",0);
        Charts.createAnalogChart("Ток в фазе B",1);
        Charts.createAnalogChart("Ток в фазе C",2);
        Charts.createDiscreteChart("Срабатывание защиты", 0);

        Charts.addSeries("Фаза А", 0,0);
        Charts.addSeries("Фаза B", 1,0);
        Charts.addSeries("Фаза C", 2,0);
        Charts.addSeries("setting", 0,1);
        Charts.addSeries("setting", 1,1);
        Charts.addSeries("setting", 2,1);
        Charts.addSeries("RMS A", 0,2);
        Charts.addSeries("RMS B", 1,2);
        Charts.addSeries("RMS C", 2,2);

        InputData inD = new InputData();
        inD.start();
    }
}
