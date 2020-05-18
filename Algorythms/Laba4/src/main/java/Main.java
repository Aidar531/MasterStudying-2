
public class Main {
    public static void main(String[] args) {
        Charts.createAnalogChart("Ток в фазе A",0);
        Charts.createAnalogChart("Ток в фазе B",1);
        Charts.createAnalogChart("Ток в фазе C",2);
        Charts.createAnalogChart("Нулевая последовательность",3);
        Charts.createDiscreteChart("Срабатывание 1 ступени", 0);
        Charts.createDiscreteChart("Срабатывание 2 ступени", 1);
        Charts.createDiscreteChart("Срабатывание 3 ступени", 2);
        Charts.createDiscreteChart("Срабатывание 4 ступени", 3);
        Charts.createDiscreteChart("Срабатывание 5 ступени", 4);
        Charts.addSeries("Фаза А", 0,0);
        Charts.addSeries("Фаза B", 1,0);
        Charts.addSeries("Фаза C", 2,0);

        Charts.addSeries("Нулевая последовательность", 3,0);

        Charts.addSeries("RMS A", 0,1);//
        Charts.addSeries("RMS B", 1,1);//Действующее значение
        Charts.addSeries("RMS C", 2,1);//

        InputData inD = new InputData();
        inD.start();
    }
}
