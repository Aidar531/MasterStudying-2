import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class InputData {
    private final File comtrCgf;
    private final File comtrDat;
    private double[] k1;
    private double[] k2;

    public InputData() {
        String comtrName = "/KZ2";
        String path = "/home/aidar/Рабочий стол/MasterStudying#2/Algorythms/Data/Laba3";

        String cfgName = path + comtrName + ".cfg";
        comtrCgf = new File(cfgName);

        String datName = path + comtrName + ".dat";
        comtrDat = new File(datName);
    }


    public void start() {
        SampleValue currentSV = new SampleValue();
        SampleValue voltageSV = new SampleValue();
        RMSValues RMS_I = new RMSValues();
        RMSValues RMS_U = new RMSValues();
        FilterFur FILTER_I = new FurrieFilter(50, 80);
        FilterFur FILTER_U = new FurrieFilter(50, 80);
        ImpedanceData IMPData = new ImpedanceData();
        ImpedanceCalc impedance = new ImpedanceCalc(RMS_I, RMS_U, IMPData, 3.126, 2.5);
        BlockingElement blk = new BlockingElement(RMS_I, RMS_U, 2000);
        Logic logic = new Logic(IMPData, blk);
        Godograph godograph = new Godograph(IMPData);
        godograph.drawSetting();

        try {
            BufferedReader br = new BufferedReader(new FileReader(comtrCgf));
            int lineNumber = 0, count = 0, numberData = 100;
            //Начало считывания файла конфигурационного
            String line;
            while ((line = br.readLine()) != null) {
//                System.out.println(line);
                lineNumber++;
//                Считывание сколько аналоговых каналов и создание массива нужного размера под коэфициенты
                if (lineNumber == 2) {
                    numberData = Integer.parseInt(line.split(",")[1].replaceAll("A", ""));
                    k1 = new double[numberData];
                    k2 = new double[numberData];

                }
//              Заполнение массивов коэфициентов
                if (lineNumber > 2 && lineNumber < numberData + 3) {
                    k1[count] = Double.parseDouble(line.split(",")[5]);
                    k2[count] = Double.parseDouble(line.split(",")[6]);
                    count++;
                }
            }

            //Начало считывания файла с данными
            br = new BufferedReader(new FileReader(comtrDat));
            double time = 0;
            count = 0;
            while ((line = br.readLine()) != null) {
                String[] lineData = line.split(",");
                //Считывание .dat файла
//                    System.out.println(count);
//                    System.out.println(i);
                voltageSV.setPhA(Double.parseDouble(lineData[2]) * k1[0] + k2[0]);
                voltageSV.setPhB(Double.parseDouble(lineData[3]) * k1[1] + k2[1]);
                voltageSV.setPhC(Double.parseDouble(lineData[4]) * k1[2] + k2[2]);
                voltageSV.calculatePh0();

                currentSV.setPhA(Double.parseDouble(lineData[5]) * k1[3] + k2[3]);
                currentSV.setPhB(Double.parseDouble(lineData[6]) * k1[4] + k2[4]);
                currentSV.setPhC(Double.parseDouble(lineData[7]) * k1[5] + k2[5]);
                currentSV.calculatePh0();

                //Рассчет 1 гармоники
                FILTER_I.setSv(currentSV);
                FILTER_I.setRms(RMS_I);
                FILTER_I.calculate();
                FILTER_U.setSv(voltageSV);
                FILTER_U.setRms(RMS_U);
                FILTER_U.calculate();

                impedance.calculate();
                godograph.showGodograph();

//                    System.out.println("Z_A_X = " + IMPData.getZ_phA_x());
//                    System.out.println("Z_A_Y = " + IMPData.getZ_phA_y());
//
                    Charts.addAnalogData(0, 1, IMPData.getZ("A"));
                    Charts.addAnalogData(1, 1, IMPData.getZ("B"));
                    Charts.addAnalogData(2, 1, IMPData.getZ("C"));


                // Вывод графиков

                Charts.addAnalogData(0, 0, currentSV.getPhA()*100);
                Charts.addAnalogData(1, 0, currentSV.getPhB()*100);
                Charts.addAnalogData(2, 0, currentSV.getPhC()*100);
//                FILTER_I.showGraph(1);

                logic.process();
                count++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

