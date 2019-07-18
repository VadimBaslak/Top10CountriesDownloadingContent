package main.java;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class RecordingCountries {
    private static String randomCountryCode(){
        return "" + (char)(97+Math.random()*26) + (char)(97+Math.random()*26);
    }

    public static void main() {
        String filePath = ".\\src\\RangeIpCountries.txt";
        try {
            BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(filePath, false));
            int rangeLength = 10000;
            int firstPower = (int)Math.pow(2,31)+1;
            int secondPower = firstPower + rangeLength;
            int maxNumberIP=(int)Math.pow(2,31);

            for (int NumberCountries = 0; NumberCountries < 1000000; NumberCountries++) {
                bufferWriter.write("<" + firstPower + ">" + "-<" + secondPower + "> <" + randomCountryCode() + ">\n");
                firstPower = secondPower + 1;
                secondPower = secondPower + rangeLength;
                if(secondPower >= (maxNumberIP - rangeLength)){
                    bufferWriter.write("<" + firstPower + ">" + "-<" + maxNumberIP + "> <" + randomCountryCode() + ">\n");
                    break;
                }
            }
            if(secondPower < (maxNumberIP - rangeLength)){
                bufferWriter.write("<" + firstPower + ">" + "-<" + maxNumberIP + "> <" + randomCountryCode() + ">\n");
            }
            bufferWriter.close();
        } catch (IOException exception) {
            System.out.println("Input / Output error");
        }

    }

}

