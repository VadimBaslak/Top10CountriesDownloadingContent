package main.java;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//test
public class IpConverterInt {
    //the lower limit of the interval -> country code
    private static Map<Integer, String> rangeIpCountries = null;
    //country code -> traffic volume
    private static Map<String, Integer> countriesTraffic = null;
    //lower and upper limit of the interval
    private static List<Integer> listRangeIp = null;

    public static void main() {

        String filePathRangeIpCountries  = ".\\src\\RangeIpCountries.txt";
        String filePathIPAndTraffic = ".\\src\\IpAndTraffic.txt";
        /*
        Reading a text file of IP-address ranges given to different countries
        Filling rangeIpCountries and listRangeIP
        Sorting listRangeIP
        */
        try {
            String strLine;
            rangeIpCountries = new HashMap<>();
            listRangeIp = new ArrayList<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePathRangeIpCountries)));
            while ((strLine = br.readLine()) != null) {
                int leftLimit = getLeftLimit(strLine);
                int rightLimit = getRightLimit(strLine);
                listRangeIp.add(leftLimit);
                listRangeIp.add(rightLimit);
                rangeIpCountries.put(leftLimit,getCountriesCode(strLine));
            }
            listRangeIp.sort(Integer::compareTo);
        } catch (IOException e) {
            System.out.println("Output error RangeIpCountries");
        }

        /*
        Reading a text file with the amount of downloaded traffic.
        Identifying the country to which the IP address belongs.
        Filling countriesTraffic
        Output Top-10 countries with maximum traffic
        */
        try {
            String strLine;
            countriesTraffic = new HashMap<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePathIPAndTraffic)));
            int sizeListRageIP = listRangeIp.size();
            while ((strLine = br.readLine()) != null) {
                String ipString = strLine.substring(0, 15);
                String trafficString = strLine.substring(16);
                int traffic = Integer.parseInt(trafficString);
                int ipNumber = ipConverterInt(ipString);
                int lowerBound = 0;
                addIpCountriesTraffic(ipNumber, traffic, lowerBound, sizeListRageIP);
            }
            viewTop10CountriesDownloadingContent();

        } catch (IOException e) {
            System.out.println("Output error IpAndTraffic");
        }
    }

    private static void addIpCountriesTraffic(int ipNumber, int traffic, int lowerBound, int upperBound){
        int medium;
        while (true){
            medium = (lowerBound + upperBound)/2;
            if (listRangeIp.get(medium) == ipNumber){
                if(rangeIpCountries.containsKey(ipNumber)){
                    countriesTraffic.put(rangeIpCountries.get(listRangeIp.get(medium)),
                            countriesTraffic.getOrDefault(rangeIpCountries.get(listRangeIp.get(medium)), 0) + traffic);
                    break;
                } else {
                    countriesTraffic.put(rangeIpCountries.get(listRangeIp.get(medium-1)),
                            countriesTraffic.getOrDefault(rangeIpCountries.get(listRangeIp.get(medium-1)), 0) + traffic);
                    break;
                }
            } else if (lowerBound > upperBound){
                if(medium%2==1){
                    countriesTraffic.put(rangeIpCountries.get(listRangeIp.get(medium-1)),
                        countriesTraffic.getOrDefault(rangeIpCountries.get(listRangeIp.get(medium-1)), 0) + traffic);
                    break;

                } else {
                    countriesTraffic.put(rangeIpCountries.get(listRangeIp.get(medium)),
                            countriesTraffic.getOrDefault(rangeIpCountries.get(listRangeIp.get(medium)), 0) + traffic);
                    break;
                }
            }
            else {
                if(listRangeIp.get(medium) < ipNumber){
                    lowerBound = medium + 1;
                }
                else upperBound = medium - 1;
            }

        }
    }

    private static void viewTop10CountriesDownloadingContent(){
        countriesTraffic.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(10)
                .forEach(System.out::println);
    }

    private static int getLeftLimit(String strLine){
        String leftLimitStr = strLine.substring(strLine.indexOf("<") + 1, strLine.indexOf(">-"));
        return Integer.parseInt(leftLimitStr);
    }

    private static int getRightLimit(String strLine){
        String RightLimitStr = strLine.substring(strLine.indexOf("-<") + 1, strLine.indexOf("> "));
        return Integer.parseInt(RightLimitStr.substring(1));
    }

    private static String getCountriesCode(String strLine){
        return strLine.substring(strLine.indexOf(" <") + 2, strLine.indexOf(" <") + 4);
    }

    private static int ipConverterInt(String strLineIP){
        InetAddress i;
        int intRepresentation = 0;
        try {
            i = InetAddress.getByName(strLineIP);
            intRepresentation= ByteBuffer.wrap(i.getAddress()).getInt();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("Incorrect Ip-address");
        }
        return intRepresentation;
    }
}
