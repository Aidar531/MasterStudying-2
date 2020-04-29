
public class Main {
    public static void main(String[] args) {
        Charts.createAnalogChart("Ток в фазе A",0);
        Charts.createAnalogChart("Ток в фазе B",1);
        Charts.createAnalogChart("Ток в фазе C",2);
        Charts.createDiscreteChart("Срабатывание защиты", 0);
        Charts.addSeries("Фаза А", 0,0);
        Charts.addSeries("Фаза B", 1,0);
        Charts.addSeries("Фаза C", 2,0);
        Charts.addSeries("5гармоника", 0,1);//
        Charts.addSeries("5гаромника", 1,1);// Составляющая 5 гармоники
        Charts.addSeries("5гармоника", 2,1);//
        Charts.addSeries("RMS A", 0,2);//
        Charts.addSeries("RMS B", 1,2);//Действующее значение
        Charts.addSeries("RMS C", 2,2);//
        Charts.addSeries("ДиффА", 0,3);//
        Charts.addSeries("ДиффВ", 1,3);//Дифференциальный ток
        Charts.addSeries("ДиффС", 2,3);//
        Charts.addSeries("тормознойА", 0,4);//
        Charts.addSeries("ТормознойВ", 1,4);// Тормозной ток
        Charts.addSeries("ТормознойС", 2,4);//
        InputData inD = new InputData();
        inD.start();
    }
}
