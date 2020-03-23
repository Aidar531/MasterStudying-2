import java.io.*;

public class InputData {

    private File comtrCgf, comtrDat;
    private BufferedReader br;
    private String line ;
    private String[] lineData;
    private double k1[], k2[];

    private String comtrName = "/PhABC20";
    private String path = "/home/aidar/Загрузки/Rjvnhtql";
    private String cfgName = path + comtrName + ".cfg";
    private String datName = path + comtrName + ".dat";

    private SampleValue sv = new SampleValue();

    public InputData() {
        comtrCgf = new File(cfgName);
        comtrDat = new File(datName);
    }
    private RMSValues rms = new RMSValues();
    private Filter filter = new MiddleValue();
    private Logic logic = new Logic();

    public void start() {

        filter.setSv(sv);
        filter.setRms(rms);
        logic.setRms(rms);

        try {
            br = new BufferedReader(new FileReader(comtrCgf));
            int lineNumber = 0, count =0, numberData =100;

            while((line = br.readLine()) != null) {
                System.out.println(line);
                lineNumber++;
                if (lineNumber ==2) {
                    numberData = Integer.parseInt(line.split(",")[1].replaceAll("A",""));
                    k1 = new double[numberData];
                    k2 = new double[numberData];
//                    System.out.println("Number Data: "+numberData);
                }

                if (lineNumber>2 && lineNumber<numberData+3) {
                    k1[count] = Double.parseDouble(line.split(",")[5]);
                    k2[count] = Double.parseDouble(line.split(",")[6]);
                    count++;
                }
            }

            br = new BufferedReader(new FileReader(comtrDat));
            double time = 0;

            while ((line = br.readLine()) !=null){
                count++;
                lineData = line.split(",");
                sv.setPhA(Double.parseDouble(lineData[2])*k1[0] + k2[0]);
                sv.setPhB(Double.parseDouble(lineData[3])*k1[1] + k2[1]);
                sv.setPhC(Double.parseDouble(lineData[4])*k1[2] + k2[2]);
                sv.setTime(time+=0.001);
                logic.process();
                Charts.addAnalogData(0,0,sv.getPhA());
            }





        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
