import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class InputData {
    private File comtrCgf, comtrDat;
    private BufferedReader br;
    private String line;
    private String[] lineData;
    private double[] k1;
    private double[] k2;
    private SampleValue[] SVs;
    private RMSValues[] RMSs_1;
    private RMSValues[] RMSs_5;
    private FilterFur[] FILTER_1;
    private FilterFur[] FILTER_5;
    private DBValues DB = new DBValues();


//    private String comtrName = "/KzAB";//ABC в конце линии
    private String comtrName = "/KzB";//ABC в конце линии
//    private String comtrName = "/Vkl";//ABC в конце линии
    private String path = "/home/aidar/Рабочий стол/MasterStudying#2/Algorythms/Data/DPB/5 sections";


    private String cfgName = path + comtrName + ".cfg";
    private String datName = path + comtrName + ".dat";



    public InputData() {
        comtrCgf = new File(cfgName);
        comtrDat = new File(datName);
    }


    private DBFilter dbFilter = new DBfilter();
    private Logic logic = new Logic();

    public void start() {

        try {
            br = new BufferedReader(new FileReader(comtrCgf));
            int lineNumber = 0, count = 0, numberData = 100, cntID=0;
            //Начало считывания файла конфигурационного
            while ((line = br.readLine()) != null) {
//                System.out.println(line);
                lineNumber++;
//                Считывание сколько аналоговых каналов и создание массива нужного размера под коэфициенты
                if (lineNumber == 2) {
                    numberData = Integer.parseInt(line.split(",")[1].replaceAll("A", ""));
                    k1 = new double[numberData];
                    k2 = new double[numberData];
                    SVs = new SampleValue[numberData/3];
                    RMSs_1 = new RMSValues[numberData/3];
                    RMSs_5 = new RMSValues[numberData/3];
                    FILTER_1 = new FilterFur[numberData/3];
                    FILTER_5 = new FilterFur[numberData/3];
//                    System.out.println("Количество аналоговых сигналов: " + numberData);
                }
//              Заполнение массивов коэфициентов
                if (lineNumber > 2 && lineNumber < numberData + 3) {
                    k1[count] = Double.parseDouble(line.split(",")[5]);
                    k2[count] = Double.parseDouble(line.split(",")[6]);
                    count++;
//                  Задание IDшников, тобишь номеров шин
                    if (count%3 == 0) {
                        SampleValue sv = new SampleValue();
                        RMSValues rms_1 = new RMSValues();
                        RMSValues rms_5 = new RMSValues();
                        FilterFur furrieFilter_1 = new FurrieFilter(50,20);
                        FilterFur furrieFilter_5 = new FurrieFilter(250,20);
                        rms_1.setID(line.split(",")[1].substring(2));
                        rms_5.setID(line.split(",")[1].substring(2));
                        sv.setID(rms_1.getID());
                        SVs[cntID] = sv;
                        RMSs_1[cntID] = rms_1;
                        RMSs_5[cntID] = rms_5;
                        FILTER_1[cntID] = furrieFilter_1;
                        FILTER_5[cntID] = furrieFilter_5;
                        cntID++;
                    }
                }

            }

            dbFilter.setRms(RMSs_1);
            dbFilter.setDB(DB);
            logic.setBlockingRMS(RMSs_5);
            logic.setDB(DB);

            //Начало считывания файла с данными
            br = new BufferedReader(new FileReader(comtrDat));
            double time = 0;
            count=0;
            SampleValue currentSV ;
            RMSValues currentRMS_1 ;
            RMSValues currentRMS_5 ;
            FilterFur currentFILTER_1;
            FilterFur currentFILTER_5;
            while ((line = br.readLine()) != null) {
                lineData = line.split(",");
                count = 0;
                for (String i:lineData) {
                    if (count % 3 == 0 && count != 0 && count < 18) {
                        currentSV = SVs[count / 3 - 1];
                        currentRMS_1 = RMSs_1[count / 3 - 1];
                        currentRMS_5 = RMSs_5[count / 3 - 1];
                        currentFILTER_1 = FILTER_1[count / 3 - 1];
                        currentFILTER_5 = FILTER_5[count / 3 - 1];
                        currentSV.setPhA(Double.parseDouble(lineData[count - 1]) * k1[count - 3] + k2[count -3]);
                        currentSV.setPhB(Double.parseDouble(lineData[count]) * k1[count- 2] + k2[count -2]);
                        currentSV.setPhC(Double.parseDouble(lineData[count + 1]) * k1[count -1] + k2[count -1]);
                        time=currentSV.getTime();
                        currentSV.setTime(time += 0.001);
                        currentRMS_1.setTime(time);
                        //Рассчет 1 гармоники
                        currentFILTER_1.setSv(currentSV);
                        currentFILTER_1.setRms(currentRMS_1);
                        currentFILTER_1.calculate();
                        //Рассчет 5 гармоники
                        currentFILTER_5.setSv(currentSV);
                        currentFILTER_5.setRms(currentRMS_5);
                        currentFILTER_5.calculate();
                    }
                    count++;
                }
                dbFilter.setRms(RMSs_1);
                dbFilter.calculate();
                // Вывод графиков
                Charts.addAnalogData(0, 0, SVs[3].getPhA());
                Charts.addAnalogData(1, 0, SVs[3].getPhB());
                Charts.addAnalogData(2, 0, SVs[3].getPhC());
                FILTER_1[0].showGraph(2);
                FILTER_5[0].showGraph(1);
                logic.process();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

