package org.example.z1;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.example.grpc.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class StockExServer {
    private ConcurrentHashMap<Client, Company> trackList = new ConcurrentHashMap<Client, Company>();//ko sta prati, jedan unos je jedan klijent jedna kompanija
    private ConcurrentHashMap<Client, Socket> clientsMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Socket, Client> clientsMap2 = new ConcurrentHashMap<>();
    private List<Client> clientsList = new ArrayList<>();
    private List<String> activeClients = new ArrayList<>();//trenutno aktivni






    public static Company[] companies = InitialData.initCompany();//nema promene ovoga

    public static void main(String[] args) throws IOException, InterruptedException {
        StockExServer stockExchangeServer = new StockExServer();
        Server server = ServerBuilder
                .forPort(8091)
                .addService(new StocksService())
                .build();
        try {
            server.start();
            System.out.println("gRPC server started on port 8090");
        } catch (IOException e) {
            e.printStackTrace();
        }




        new Thread(() -> stockExchangeServer.startSocketServer()).start();
        server.awaitTermination();
    }
    static class StocksService extends StocksServiceGrpc.StocksServiceImplBase {

        private final ConcurrentHashMap<String, BuyOrder> buyMap = new ConcurrentHashMap<String, BuyOrder>();//ove traze da l neko hoce, od najvise, ove ja da prodam
        private final ConcurrentHashMap<String, SellOrder> sellMap = new ConcurrentHashMap<String, SellOrder>();//ove se daju na kupovinu, od najnize, ove ja da kupim

        @Override
        public void ask(StockRequest request, StreamObserver<SellOrder> responseObserver) {
            String symbol = request.getSymbol();
            int quantity = request.getQuantity();

            List<SellOrder> relevantSellOrders = new ArrayList<>();
            sellMap.forEach((key, sellOrder) -> {
                if (sellOrder.getSymbol().equals(symbol)) {
                    relevantSellOrders.add(sellOrder);
                }
            });

            // If there are no relevant sell orders, return null
            if (relevantSellOrders.isEmpty()) {
                responseObserver.onNext(null);
                responseObserver.onCompleted();
                return;
            }

            // Sort the SellOrder list by price in ascending order
            relevantSellOrders.sort(Comparator.comparingDouble(SellOrder::getPrice));

            // Send the specified quantity of SellOrder instances to the client
            int count = 0;
            for (SellOrder sellOrder : relevantSellOrders) {
                if (count < quantity) {
                    responseObserver.onNext(sellOrder);
                    count++;
                } else {
                    break; // Stop once the specified quantity is reached
                }
            }

            // Notify the client that the response is complete
            responseObserver.onCompleted();



        }

        @Override
        public void bid(StockRequest request, StreamObserver<BuyOrder> responseObserver) {//od najvise
            String symbol = request.getSymbol();
            int quantity = request.getQuantity();

            List<BuyOrder> relevantBuyOrders = new ArrayList<>();
            buyMap.forEach((key, buyOrder) -> {
                if (buyOrder.getSymbol().equals(symbol)) {
                    relevantBuyOrders.add(buyOrder);
                }
            });
            if (relevantBuyOrders.isEmpty()) {
                responseObserver.onNext(null);
                responseObserver.onCompleted();
                return;
            }
            relevantBuyOrders.sort(Comparator.comparingDouble(BuyOrder::getPrice).reversed());

            // Send the specified quantity of BuyOrder instances to the client
            int count = 0;
            for (BuyOrder buyOrder : relevantBuyOrders) {
                if (count < quantity) {
                    responseObserver.onNext(buyOrder);
                    count++;
                } else {
                    break; // Stop once the specified quantity is reached
                }
            }

            // Notify the client that the response is complete
            responseObserver.onCompleted();





        }

        @Override
        public void order(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
            String symbol = request.getSymbol();
            int quantity = request.getQuantity();
            String clientID = request.getIdClient();
            boolean ask = request.getAsk();//ako je true, onda je ask, ako je false onda je bid






        }

        @Override
        public void getAllCompanies(Empty request, StreamObserver<Company> responseObserver) {


            // Iterate through the list of companies and send each one to the client
            for (Company company : companies) {
                responseObserver.onNext(company);
            }

            // Notify the client that the response is complete
            responseObserver.onCompleted();
            System.out.print("Poslao sam sve");
        }




    }

    private void startSocketServer() {
        try (ServerSocket serverSocket = new ServerSocket(7998)) {
            System.out.println("Socket server started on port 7999");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getPort());

                // Handle the socket client in a new thread
                new Thread(() -> handleSocketClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handleSocketClient(Socket clientSocket) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // Implement the logic to handle the socket client
            // This method should read and process client requests
            // You can use BufferedReader and PrintWriter for communication
            String clientMessage;
            while ((clientMessage = reader.readLine()) != null) {
                String[] parts = clientMessage.split(" ");
                if(clientMessage.startsWith("register") && parts[0].equals("register")&& parts.length==2){
                    handleRegistration(clientMessage, writer, clientSocket);



                }else if(!clientsMap2.containsKey(clientSocket)){
                    writer.println("Not registered yet!");
                    continue;
                }
                else if(clientMessage.startsWith("track")){
                    handleTracking(clientMessage, writer, clientSocket);


                }else{
                    writer.println("Not a valid call");
                }

               /* String[] parts = clientMessage.split(" ");
                if (parts.length == 2) {
                    String companySymbol = parts[1];
                    if (isPairAlreadyTracked(String.valueOf(clientSocket.getPort()), companySymbol)) {
                        System.out.println("Client and company symbol pair is already tracked.");
                    } else {
                        // Add the pair to the trackList
                        trackList.put(String.valueOf(clientSocket.getPort()), findCompanyStock(companySymbol));
                        System.out.println("Tracking company: " + companySymbol);
                        writer.println("tracking");
                    }
                } else {
                    System.out.println("Invalid 'track' message format: " + clientMessage);
                }*/


                /*if(clientMessage.equals("update")){
                    writer.println("Hello from the server!");

                    // Simulate periodic stock updates
                    sendTcpStockUpdates(writer);
                }*/
                // Process client request if needed

            }
        } catch (IOException e) {
            // Handle client disconnection
            handleClientDisconnection(clientSocket);
            e.printStackTrace();
        }

    }

    private void handleClientDisconnection(Socket clientSocket) {

    }

    private void handleTracking(String clientMessage, PrintWriter writer, Socket clientS) {
        // Example clientMessage: "track AAPL GOOGL MSFT"

        // Split the client message into parts
        String[] parts = clientMessage.split(" ");

        // Check if the message has the correct format
        if (parts.length >= 2) {
            // Extract the client from the socket
            Client client = clientsMap2.get(clientS);

            // Extract the symbols from the message starting from index 1
            for (int i = 1; i < parts.length; i++) {
                String companySymbol = parts[i];

                // Find the corresponding company based on the symbol
                Company company = findCompanyStock(companySymbol);

                // Check if the company is valid
                if (company != null) {
                    // Add the pair to the trackList
                    trackList.put(client, company);

                    writer.println("Tracking company: " + companySymbol);
                } else {
                    // If the company is not valid, inform the client
                    writer.println("Invalid company symbol: " + companySymbol);
                }
            }
        } else {
            // If the message format is incorrect, inform the client
            writer.println("Invalid 'track' message format: " + clientMessage);
        }
    }

    private void handleRegistration(String clientMessage, PrintWriter writer, Socket clientS) {
        if (clientsMap2.containsKey(clientS)) {
            String existingUsername = clientsMap2.get(clientS).getID();
            writer.println("You are already registered as: " + existingUsername);
        } else {
            // If not registered, proceed with registration
            String[] parts = clientMessage.split(" ");
            if (parts.length == 2) {
                String username = parts[1];

                // Check if the username is already registered
                if (activeClients.contains(username)) {
                    writer.println("Username already registered. Choose a different username.");
                } else {
                    // Register the client with the provided username and socket
                    Client novi = new Client(username);

                    clientsMap.put(novi, clientS);
                    clientsMap2.put(clientS, novi);
                    //System.out.println("ime klijekta" + novi.getID() +", soket : " + clientS);
                    activeClients.add(username);
                    writer.println("Registration successful. Welcome, " + username + "!");
                }
            } else {
                writer.println("Invalid 'register' message format: " + clientMessage);
            }
        }

    }

    private Company findCompanyStock(String companySymbol) {
        Company comS = new Company();
        for(Company cs:companies){
            if(cs.getSymbol().equals(companySymbol)) {
                comS = cs;
                break;
            }
        }
        return comS;
    }

    private boolean isPairAlreadyTracked(String clientSocket, String companySymbol) {
        return trackList.containsKey(clientSocket) && trackList.get(clientSocket).equals(companySymbol);
    }
    private void sendTcpStockUpdates(PrintWriter writer) {
        // Implement the logic to send periodic stock updates
        // This method can be called periodically to simulate real-time updates
        for (int i = 0; i < 5; i++) {
            // Send a new stock update to the client
            writer.println("Stock Update: " + i);

            try {
                Thread.sleep(1000); // Sleep for 1 second (simulating updates every second)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
