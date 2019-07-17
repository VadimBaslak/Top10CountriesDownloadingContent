package GeneratorIpAndTrafficAndCoutries;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class RecordingCountries {
    private static String randomCountryCode(){
        return "" + (char)(97+Math.random()*26) + (char)(97+Math.random()*26);
    }

    public static void main(String[] args) {
        String filePath = "C:\\dev\\JavaProjects\\Top10CountriesDownloadingContent\\src\\Resources\\RangeIpCountries.txt";

        try {
            BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(filePath, true));

            long rangeLength = 10000;
            long firstPower = 0;
            long secondPower = firstPower + rangeLength;
            long maxNumberIP=((long)Math.pow(2,32)-1);

            for (int NumberCountries = 0; NumberCountries < 100000; NumberCountries++) {
                bufferWriter.write("<" + firstPower + ">" + "-<" + secondPower + "> <" + randomCountryCode() + ">\n");
                firstPower = secondPower + 1;
                secondPower = secondPower + rangeLength;
                if((secondPower>=maxNumberIP)){
                    bufferWriter.write("<" + firstPower + ">" + "-<" + maxNumberIP + "> <" + randomCountryCode() + ">\n");
                    break;
                }
            }


            bufferWriter.close();
        } catch (IOException exception) {
            System.out.println("Input / Output error");
        }

    }

}

