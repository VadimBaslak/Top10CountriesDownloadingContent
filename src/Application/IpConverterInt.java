package Application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//test
public class IpConverterInt {
    //the lower limit of the interval -> country code
    private static Map<Long, String> rangeIpCountries = null;
    //country code -> traffic volume
    private static Map<String, Long> countriesTraffic = null;
    //lower and upper limit of the interval
    private static List<Long> listRangeIp = null;

    public static void main(String[] args) {

        String filePathRangeIpCountries  = "C:\\dev\\JavaProjects\\Top10CountriesDownloadingContent\\src\\Resources\\RangeIpCountries.txt";
        String filePathIPAndTraffic = "C:\\dev\\JavaProjects\\Top10CountriesDownloadingContent\\src\\Resources\\IpAndTraffic.txt";
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
                long leftLimit = getLeftLimit(strLine);
                long rightLimit = getRightLimit(strLine);
                listRangeIp.add(leftLimit);
                listRangeIp.add(rightLimit);
                rangeIpCountries.put(leftLimit,getCountriesCode(strLine));
            }
            listRangeIp.sort(Long::compareTo);
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
                long traffic = Integer.parseInt(trafficString);
                long ipNumber = ipConverterLong(ipString);
                int lowerBound = 0;
                addIpCountriesTraffic(ipNumber, traffic, lowerBound, sizeListRageIP);
            }
            viewTop10CountriesDownloadingContent();

        } catch (IOException e) {
            System.out.println("Output error IpAndTraffic");
        }
    }

    private static void addIpCountriesTraffic(long ipNumber, long traffic, int lowerBound, int upperBound){
        int medium;
        while (true){
            medium = (lowerBound + upperBound)/2;
            if (listRangeIp.get(medium) == ipNumber){
                if(rangeIpCountries.containsKey(ipNumber)){
                    countriesTraffic.put(rangeIpCountries.get(listRangeIp.get(medium)),
                            countriesTraffic.getOrDefault(rangeIpCountries.get(listRangeIp.get(medium)), 0L) + traffic);
                    break;
                } else {
                    countriesTraffic.put(rangeIpCountries.get(listRangeIp.get(medium-1)),
                            countriesTraffic.getOrDefault(rangeIpCountries.get(listRangeIp.get(medium-1)), 0L) + traffic);
                    break;
                }
            } else if (lowerBound > upperBound){
                if(medium%2==1){
                    countriesTraffic.put(rangeIpCountries.get(listRangeIp.get(medium-1)),
                        countriesTraffic.getOrDefault(rangeIpCountries.get(listRangeIp.get(medium-1)), 0L) + traffic);
                    break;

                } else {
                    countriesTraffic.put(rangeIpCountries.get(listRangeIp.get(medium)),
                            countriesTraffic.getOrDefault(rangeIpCountries.get(listRangeIp.get(medium)), 0L) + traffic);
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
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()).limit(10)
                .forEach(System.out::println);
    }

    private static long getLeftLimit(String strLine){
        String leftLimitStr = strLine.substring(strLine.indexOf("<") + 1, strLine.indexOf(">-"));
        return Long.parseLong(leftLimitStr);
    }

    private static long getRightLimit(String strLine){
        String RightLimitStr = strLine.substring(strLine.indexOf("-<") + 1, strLine.indexOf("> "));
        return Long.parseLong(RightLimitStr.substring(1));
    }

    private static String getCountriesCode(String strLine){
        return strLine.substring(strLine.indexOf(" <") + 2, strLine.indexOf(" <") + 4);
    }

    private static long ipConverterLong(String strLineIP){
        long[] ip = new long[4];
        long ipNumbers = 0;
        String[] parts = strLineIP.split("\\.");
        for (int i = 0; i < 4; i++) {
            ip[i] = Long.parseLong(parts[i]);
        }
        for (int i = 0; i < 4; i++) {
            ipNumbers += ip[i] << (24 - (8 * i));
        }
        return ipNumbers;
    }
}
