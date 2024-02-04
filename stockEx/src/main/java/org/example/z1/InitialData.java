package org.example.z1;

import org.example.grpc.Company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

public class InitialData {
    static String[][] CompanyData =
            {
                    {"AAPL", "Apple Inc. Common Stock", "193.89","193.89", "2.33", "2023-02-02"},
                    {"AAT", "American Assets Trust Inc. Common Stock", "23.52", "23.52", "0.52", "2024-01-23"},
                    {"AAU", "Almaden Minerals Ltd. Common Shares", "0.14", "0.14","0", "2024-01-23"},
                    {"AB", "AllianceBernstein Holding L.P. Units", "33.65","33.65", "0.08", "2024-01-23"},
                    {"ABAT", "American Battery Technology Company Common Stock", "2.85","2.85", "0.245", "2024-01-23"},
                    {"ABBV", "AbbVie Inc. Common Stock", "165.39","165.39", "0.62", "2024-01-23"},
                    {"ABCB", "Ameris Bancorp Common Stock", "53.09","53.09", "1.55", "2024-01-23"},
                    {"ABCL", "AbCellera Biologics Inc. Common Shares", "5.51","5.51", "0.27", "2024-01-23"},
                    {"ABEO", "Abeona Therapeutics Inc. Common Stock", "5.08", "5.08","0.08", "2024-01-23"},
                    {"ABEV", "Ambev S.A. American Depositary Shares", "2.65","2.65", "-0.05", "2024-01-23"},
                    {"ABG", "Asbury Automotive Group Inc Common Stock", "207.57","207.57", "3.11", "2024-01-23"},
                    {"ABIO", "ARCA biopharma Inc. Common Stock", "1.61", "1.61","0", "2024-01-23"},
                    {"ABL", "Abacus Life Inc. Class A Common Stock", "12.11", "12.11","0.11", "2024-01-23"},
                    {"ABLLL", "Abacus Life Inc. 9.875% Fixed Rate Senior Notes due 2028", "25.39","25.39", "0.03", "2024-01-23"},
                    {"ABLLW", "Abacus Life Inc. Warrant", "0.95","0.95", "-0.035", "2024-01-23"},
                    {"ABLV", "Able View Global Inc. Class B Ordinary Shares", "2.60","2.60", "-0.11", "2024-01-23"},
                    {"ABLVW", "Able View Global Inc. Warrant", "0.04", "0.04","0", "2024-01-23"},
                    {"ABM", "ABM Industries Incorporated Common Stock", "42.41","42.41", "0.67", "2024-01-23"},
                    {"ABNB", "Airbnb Inc. Class A Common Stock", "142.01", "142.01","2.08", "2024-01-23"}

            };
    /*public static Client[] initClients(){
        Client[] clients = new Client[5];
        Random random = new Random();

        for (int i = 0; i < clients.length; i++) {
            // Create a new client instance with an initial amount of money
            double initialAmount = 5000 + (1100000 - 5000) * random.nextDouble();
            Client client = new Client(initialAmount);

            // Add the initialized client to the array
            clients[i] = client;
        }

        return clients;

    }*/



    static String dateFormat = "yyyy-MM-dd";


    public static Company[] initCompany() {

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);


        Company[] cs = new Company[CompanyData.length];
        int i =0;
        try {
            for (String[] dataCompany : CompanyData) {
                Company c = Company.newBuilder()
                        .setSymbol(dataCompany[0])
                        .setCompanyName(dataCompany[1])
                        .setPriceNow(Double.parseDouble(dataCompany[2]))
                        .setPriceMorning(Double.parseDouble(dataCompany[3]))
                        .setChange(Double.parseDouble(dataCompany[4]))
                        .setDate(parseDate(dataCompany[5], sdf))
                        .build();  // You need to call .build() to create the CompanyStocks object

                cs[i++] = c;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return cs;
    }
    private static com.google.protobuf.Timestamp parseDate(String dateStr, SimpleDateFormat sdf) throws ParseException {
        long millis = sdf.parse(dateStr).getTime();
        return com.google.protobuf.Timestamp.newBuilder().setSeconds(millis / 1000).build();
    }
    // Method to convert com.google.protobuf.Timestamp to String

}
