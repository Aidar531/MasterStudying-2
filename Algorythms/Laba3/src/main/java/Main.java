public class Main {
    public static void main(String[] args) {

        Charts.createAnalogChart("Ток в фазе A", 0);
        Charts.createAnalogChart("Ток в фазе B", 1);
        Charts.createAnalogChart("Ток в фазе C", 2);
        Charts.createDiscreteChart("Срабатывание 1 ступени", 0);
        Charts.createDiscreteChart("Срабатывание 2 ступени", 1);
        Charts.addSeries("Фаза А", 0, 0);
        Charts.addSeries("Фаза B", 1, 0);
        Charts.addSeries("Фаза C", 2, 0);
        Charts.addSeries("Z_A", 0, 1);//
        Charts.addSeries("Z_B", 1, 1);//Сопротивление контура
        Charts.addSeries("Z_C", 2, 1);//

        InputData inD = new InputData();
        inD.start();
    }
}
