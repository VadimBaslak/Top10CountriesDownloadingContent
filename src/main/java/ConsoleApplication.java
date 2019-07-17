package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleApplication {
    public static void main(String[] args) {
        System.out.println("Hello, colleague! \n" +
                "To generate IP-addresses and their traffic, enter: RecIP, \n" +
                "(if you have a list of IP-addresses with traffic in a format \n" +
                "xxx.xxx.xxx.xxx \\t <traffic>, add it to the folder src)\n" +
                "To create IP-addresses ranges for different countries, enter: RecCountries,\n" +
                "(if you have a list in a format <integer>-<integer> <country code>)\n" +
                "To display Top countries by traffic load, enter: traffic\n" +
                "To exit, enter: esc");
        while(true){
            String choice = "esc";
            System.out.print("Enter: ");
            try {
                choice = getString();
            } catch (IOException e) {
                System.out.println("Invalid input");
            }
            switch (choice){
                case "RecIP":
                    RecordingIp.main();
                    System.out.println("Were generated IP-addresses and their traffic");
                    break;
                case "RecCountries":
                    RecordingCountries.main();
                    System.out.println("Were generated intervals of addresses and their countries");
                    break;
                case "traffic":
                    System.out.println("Top-10 countries and their traffic:");
                    IpConverterInt.main();
                    break;
                case "esc":
                    System.out.print("Bye-Bye!");
                    return;
                default:
                    System.out.println("There is no such command");
            }
        }
    }
    public static String getString() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        return s;
    }
}
