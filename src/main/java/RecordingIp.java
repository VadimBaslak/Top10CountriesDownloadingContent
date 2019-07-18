package main.java;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RecordingIp {
    public static void main() {
        String filePath = ".\\src\\IpAndTraffic.txt";
        try {
            BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(filePath, false));
            for (int NumberNewRows = 0; NumberNewRows < 1000000; NumberNewRows++) {
                bufferWriter.write((random(256) + "." + random(256) + "." + random(256) +
                        "." + random(256) + "\t" + random(1000) + "\n"));
            }
            bufferWriter.close();
        } catch (IOException exception) {
            System.out.println("Input / Output error");
        }
    }

    private static String random(int range) {
        int resultInt = (int) (range * Math.random());
        String resultString;
        if (resultInt > 99) {
            resultString = "" + resultInt;
        } else if (resultInt > 9) {
            resultString = "0" + resultInt;
        } else {
            resultString = "00" + resultInt;
        }
        return resultString;
    }
}
