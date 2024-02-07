package org.example.z1;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.example.grpc.*;

import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StockExServer {
    private static ConcurrentHashMap<Client, Company> trackList = new ConcurrentHashMap<Client, Company>();//ko sta prati, jedan unos je jedan klijent jedna kompanija
    private static ConcurrentHashMap<Client, Socket> clientsMap = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Socket, Client> clientsMap2 = new ConcurrentHashMap<>();
    private List<Client> clientsList = new ArrayList<>();
    private static List<String> activeClients = new ArrayList<>();//trenutno aktivni
    private static ConcurrentHashMap<String, List<Stock>> clientsStockMap = new ConcurrentHashMap<>();


    private static List<StockChange> changeList = new ArrayList<>();//treba da se menja vremednom


    public static Company[] companies = InitialData.initCompany();//nema promene ovoga

    public StockExServer() {
        for (Company company : companies){
            StockChange stockChange = new StockChange(company);
            changeList.add(stockChange);
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        StockExServer stockExchangeServer = new StockExServer();
        StockExServer.StocksService stockExService = new StockExServer.StocksService();
        for (StockChange stockChange : stockExchangeServer.changeList) {
            double oldPrice = stockChange.getOldPrice();
            double newPrice = stockChange.getComp().getPriceMorning(); // You might want to change this depending on where you update the company prices

                System.out.println("company" + stockChange.getComp().getSymbol());
                // Update the old price to the new price


        }
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
       // List<Stock> stocks = new ArrayList<>();
        //stocks.add(new Stock("AAU", 50 , 50));
        //clientsStockMap.put("andjela", stocks);



        new Thread(stockExService::startSocketServer).start();
        server.awaitTermination();
    }
    static class StocksService extends StocksServiceGrpc.StocksServiceImplBase {

        private final ConcurrentHashMap<String, BuyOrder> buyMap = new ConcurrentHashMap<String, BuyOrder>();//ove traze da l neko hoce, od najvise, ove ja da prodam
        private final ConcurrentHashMap<String, SellOrder> sellMap = new ConcurrentHashMap<String, SellOrder>();//ove se daju na kupovinu, od najnize, ove ja da kupim

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
                    if (clientMessage.startsWith("register") && parts[0].equals("register") && parts.length == 2) {
                        System.out.print("register message in");
                        handleRegistration(clientMessage, writer, clientSocket);



                    } else if (!clientsMap2.containsKey(clientSocket)) {
                        writer.println("Not registered yet!");
                        continue;
                    } else if (clientMessage.startsWith("track")) {
                        System.out.print("track message in");
                        handleTracking(clientMessage, writer, clientSocket);


                    } else {
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
                    handleSocketClient(clientSocket);
                    while (true) {
                        if (clientSocket.isClosed()) {
                            String id = clientsMap2.get(clientSocket).getID();
                            System.out.println("Client disconnected: " + clientSocket + " User: " +id);
                            activeClients.remove(id);
                            clientsMap2.remove(id);
                            break;
                        }

                        Thread.sleep(1000);

                    }
                }
            } catch (IOException e) {
                // Handle client disconnection
                //handleClientDisconnection(clientSocket);
                e.printStackTrace();
            }  catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

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
                        assignRandomStocks(username);


                        clientsMap.put(novi, clientS);
                        clientsMap2.put(clientS, novi);
                        //System.out.println("ime klijekta" + novi.getID() +", soket : " + clientS);
                        activeClients.add(username);
                        writer.println("Registration successful. Welcome, " + username + "!");
                        //getOwnedStocksByTcp(clientS);
                        giveStocks(username);
                        System.out.println("Gave stocks");




                    }
                } else {
                    writer.println("Invalid 'register' message format: " + clientMessage);
                }
            }

        }

        private void giveStocks(String client) {

            List<Stock> clientStock = new ArrayList<>();
            for(Company company : companies){
                String name = company.getSymbol();
                Stock stock = new Stock(name, 100, 100);
                clientStock.add(stock);//za sve kompanije po 100, svakome

            }



            clientsStockMap.put(client, clientStock);

        }

        private void getOwnedStocksByTcp(Socket clientSocket) {
            // Get the client associated with the provided socket
            Client client = clientsMap2.get(clientSocket);

            // Check if the client is registered
            if (client != null) {
                // Retrieve the client's stock list from the clientsStockMap
                List<Stock> clientStocks = clientsStockMap.getOrDefault(client.getID(), new ArrayList<>());

                try (PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {
                    // Send the client's stock list over TCP
                    for (Stock stock : clientStocks) {
                        String stockInfo = String.format("Stock: Symbol=%s, Quantity=%d, Price=%.2f",
                                stock.getSymbol(), stock.getQuantity(), stock.getPrice());
                        writer.println(stockInfo);
                    }
                    // Notify the client that the response is complete
                    writer.println("Stocks update complete.");
                } catch (IOException e) {
                    // Handle the exception (e.g., log it) if there's an issue sending the message
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error: Client not found for the provided socket.");
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
        private void assignRandomStocks(String client) {
            List<Stock> randomStocks = generateRandomStocks();
            clientsStockMap.put(client, randomStocks);
        }
        private List<Stock> generateRandomStocks() {
            List<Stock> stocks = new ArrayList<>();
            Random random = new Random();

            // Generate a random number of stocks for simplicity
            int numStocks = random.nextInt(3) + 1; // Random number between 1 and 5

            for (int i = 0; i < numStocks; i++) {
                // Get a random company from the existing list
                Company randomCompany = getRandomCompany();

                // Generate random quantity and price
                int quantity = random.nextInt(100) + 1; // Random quantity between 1 and 100
                double price = random.nextDouble() * 100; // Random price between 0 and 100

                // Create a Stock instance and add it to the list
                Stock stock = new Stock(randomCompany.getSymbol(), quantity, price);
                stocks.add(stock);
            }

            return stocks;
        }

        private Company getRandomCompany() {
            Random random = new Random();
            int randomIndex = random.nextInt(companies.length);
            return companies[randomIndex];
        }

        @Override
        public void ask(StockRequest request, StreamObserver<SellOrder> responseObserver) {
            System.out.print("ask went through");
            String symbol = request.getSymbol();
            int quantity = request.getQuantity();
            boolean stockFound = false;
            for(Company com : companies){
                if(com.getSymbol().equals(symbol)){
                    stockFound = true;
                    break;
                }
            }
            if(stockFound) {


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

            }else{
                responseObserver.onNext(null);
                responseObserver.onCompleted();
            }

        }

        @Override
        public void bid(StockRequest request, StreamObserver<BuyOrder> responseObserver) {//od najvise
            System.out.print("bid went through");
            String symbol = request.getSymbol();
            int quantity = request.getQuantity();

            boolean stockFound = false;
            for(Company com : companies){
                if(com.getSymbol().equals(symbol)){
                    stockFound = true;
                    break;
                }
            }
            if(stockFound) {




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


        }else{
                responseObserver.onNext(null);
                responseObserver.onCompleted();

            }
            }


        @Override
        public void order(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
            System.out.print("order went through");
            String symbol = request.getSymbol();
            int quantity = request.getQuantity();
            double price = request.getPrice();
            String clientID = request.getIdClient();


            boolean stockFound = false;
            for(Company com : companies){
                if(com.getSymbol().equals(symbol)){
                    stockFound = true;
                    break;
                }
            }
            if(stockFound) {

                if (isClientInMap(clientID)) {


                    boolean ask = request.getAsk();//ako je true, onda je ask, ako je false onda je bid
                    if (ask == false) {//znaci bid je
                        //find me all the ask offers for this stock
                        List<SellOrder> askOrders = new ArrayList<>();
                        sellMap.forEach((key, sellOrder) -> {
                            if (sellOrder.getSymbol().equals(symbol)) {
                                askOrders.add(sellOrder);
                            }
                        });
                        List<SellOrder> filteredAskOrders = new ArrayList<>();
                        for (SellOrder order : askOrders) {
                            if (order.getPrice() <= price && order.getQuantity() >= quantity) {
                                filteredAskOrders.add(order);
                            }
                        }
                        if (filteredAskOrders.isEmpty()) {
                            BuyOrder newBuyOrder = BuyOrder.newBuilder()
                                    .setSymbol(symbol)
                                    .setPrice(price)
                                    .setQuantity(quantity)
                                    .setClientID(clientID)
                                    .build();

                            buyMap.put(clientID, newBuyOrder);
                            responseObserver.onNext(OrderResponse.newBuilder().setResponse("Bid order processed successfully, no transaction yet").build());
                            responseObserver.onCompleted();

                        }else{
                            //izaberi najbolju i izvrsi transakciju, kao i skini ask koji si kupio, kao i dodaj sebi da posedujes ovo
                            //i posalji svima, promeni cenu
                            SellOrder bestAskOffer = null;
                            double minPrice = Double.MAX_VALUE;
                            for (SellOrder order : filteredAskOrders) {
                                if (order.getPrice() < minPrice) {
                                    minPrice = order.getPrice();
                                    bestAskOffer = order;
                                }
                            }
                            updateAsk(bestAskOffer, quantity);
                            responseObserver.onNext(OrderResponse.newBuilder().setResponse("Bid order processed successfully, transaction ok").build());
                            responseObserver.onCompleted();

                            if (clientID != null) {
                                // Retrieve the client's stock list
                                List<Stock> clientStocks = clientsStockMap.getOrDefault(clientID, new ArrayList<>());

                                // Add the new stock to the list
                                clientStocks.add(new Stock(bestAskOffer.getSymbol(), quantity, bestAskOffer.getPrice()));

                                // Update the client's stock list in the clientsStockMap
                                clientsStockMap.put(clientID, clientStocks);
                                writeToFile(clientID, bestAskOffer.getClientID(),bestAskOffer.getSymbol(), bestAskOffer.getQuantity(), bestAskOffer.getPrice());
                                //changePrice(bestAskOffer.getSymbol(), bestAskOffer.getQuantity(), bestAskOffer.getPrice());
                                //notify(bestAskOffer.getSymbol(), bestAskOffer.getQuantity(), bestAskOffer.getPrice());
                            } else {
                                System.out.println("Error: Client is null while trying to add stock.");
                            }
                        }




                    } else {
                        //check if i have stocks to sell

                        List<Stock> clientStocks = clientsStockMap.get(clientID);
                        boolean ok = false;
                        if (clientStocks != null) {
                            int totalQuantity = 0;

                            for (Stock stock : clientStocks) {
                                if (stock.getSymbol().equals(symbol)) {
                                    totalQuantity += stock.getQuantity();
                                }
                            }

                            if( totalQuantity >= quantity) {
                                ok = true;

                                //skini ih sa liste, posto ce se ili dodati u ask ili prodati
                                for (Stock stock : clientStocks) {
                                    if (stock.getSymbol().equals(symbol)) {
                                        stock.setQuantity(stock.getQuantity() - quantity);//promena u listi cuvanja

                                    }
                                }
                            }

                        }
                        if(ok) {


                            //find me all the bid offers for this stock
                            List<BuyOrder> bidOrders = new ArrayList<>();
                            buyMap.forEach((key, buyOrder) -> {
                                if (buyOrder.getSymbol().equals(symbol) && buyOrder.getQuantity() >= quantity) {//da ima barem isto koliko ja zelim da prodam, pa cu da oduzimam od bid-a, koliko jos zeli da kupi nakon ove prodaje
                                    bidOrders.add(buyOrder);
                                }
                            });
                            List<BuyOrder> filteredBidOrders = new ArrayList<>();

                            for (BuyOrder buyOrder : bidOrders) {
                                if (buyOrder.getPrice() >= price) {
                                    filteredBidOrders.add(buyOrder);
                                }
                            }
                            if (filteredBidOrders.isEmpty()) {
                                SellOrder newSellOrder = SellOrder.newBuilder()
                                        .setSymbol(symbol)
                                        .setPrice(price)
                                        .setQuantity(quantity)
                                        .setClientID(clientID)
                                        .build();

                                sellMap.put(clientID, newSellOrder);
                                //moram prvo da ih posedujem da bi ih prodala
                                responseObserver.onNext(OrderResponse.newBuilder().setResponse("Ask order processed successfully, no transaction yet").build());
                                responseObserver.onCompleted();


                            } else {
                                //izaberi najbolju i izvrsi transakciju
                                BuyOrder bestBidOffer = null;
                                double maxPrice = Double.MIN_VALUE;

                                for (BuyOrder buyOrder : bidOrders) {
                                    if (buyOrder.getPrice() > maxPrice) {
                                        maxPrice = buyOrder.getPrice();
                                        bestBidOffer = buyOrder;
                                    }
                                }
                                //bestBidOffer je najbolja, vec sam skinula onome ko je posedovao, samo dodaj onome ko je kupio, i posalji update svima koji prate

                                updateBid(bestBidOffer, quantity);//ovaj je kupio i koliko je kupio
                                //changePrice(bestBidOffer.getSymbol(), bestBidOffer.getQuantity(), bestBidOffer.getPrice());
                                //notify(bestBidOffer.getSymbol(),bestBidOffer.getQuantity(), bestBidOffer.getPrice());
                                writeToFile(clientID, bestBidOffer.getClientID(),bestBidOffer.getSymbol(),quantity, bestBidOffer.getPrice());
                                responseObserver.onNext(OrderResponse.newBuilder().setResponse("Sell order processed successfully, transaction ok").build());
                                responseObserver.onCompleted();



                            }
                        }else{
                            String orderResult = "Cannot make sell orders when you dont have enough stocks";
                            OrderResponse response = OrderResponse.newBuilder()
                                    .setResponse(orderResult)
                                    .build();

                            // Send the response to the client
                            responseObserver.onNext(response);
                            responseObserver.onCompleted();


                        }

                    }
                } else {
                    String orderResult = "Cannot make orders when not registered";
                    OrderResponse response = OrderResponse.newBuilder()
                            .setResponse(orderResult)
                            .build();

                    // Send the response to the client
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                }
            }else{
                String orderResult = "Stock symbol not valid";
                OrderResponse response = OrderResponse.newBuilder()
                        .setResponse(orderResult)
                        .build();

                // Send the response to the client
                responseObserver.onNext(response);
                responseObserver.onCompleted();

            }






        }



        private void writeToFile(String client1ID, String sellerID, String symbol, int quantity, double price) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))) {
                // Formatiranje transakcije i upisivanje u fajl
                String formattedTransaction = String.format(
                        "Symbol: %s, Price: %f, Quantity: %d, Buyer: %s, Seller: %s%n",
                        symbol,
                        price,
                        quantity,
                        client1ID,
                        sellerID

                );

                writer.write(formattedTransaction);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        private boolean isClientInMap(String clientID) {
            for (Client client : clientsMap2.values()) {
                if (client.getID().equals(clientID)) {
                    // Client with the specified ID found in the map
                    return true;
                }
            }
            // Client with the specified ID not found in the map
            return false;
        }

        private void updateAsk(SellOrder bestAskOffer, int quantity) {
            for (Map.Entry<String, SellOrder> entry : sellMap.entrySet()) {
                SellOrder existingSellOrder = entry.getValue();

                // Check if the BuyOrder objects match
                if (existingSellOrder.equals(bestAskOffer)) {
                    // Check if the quantities match
                    if (existingSellOrder.getQuantity() == quantity) {
                        // If the quantities match, remove the BuyOrder from the buyMap
                        sellMap.remove(entry.getKey());
                        changePrice(bestAskOffer.getSymbol(), bestAskOffer.getPrice());
                        broadcastTheTransaction(bestAskOffer.getSymbol(), quantity, bestAskOffer.getPrice());
                    } else if (existingSellOrder.getQuantity() > quantity) {
                        // If the existing quantity is greater, create a new BuyOrder with updated quantity
                        SellOrder updatedSellOrder = existingSellOrder.toBuilder()
                                .setQuantity(existingSellOrder.getQuantity() - quantity)
                                .build();

                        // Replace the existing entry in the buyMap with the updated BuyOrder
                        sellMap.put(entry.getKey(), updatedSellOrder);
                        changePrice(bestAskOffer.getSymbol(), bestAskOffer.getPrice());
                        broadcastTheTransaction(bestAskOffer.getSymbol(), quantity, bestAskOffer.getPrice());
                    } else {
                        // Handle the case where the existing quantity is less than the sold quantity
                        System.out.println("Error: Buy quantity exceeds existing sell quantity!");
                    }

                    // Perform any additional processing or notification as needed
                    return; // Exit the loop since the BuyOrder was found and processed
                }

            }

            // Handle the case where the specified BuyOrder is not found in the buyMap
            System.out.println("Error: SellOrder not found in sellMap!");
        }

        private void updateBid(BuyOrder bestBidOffer, int quantity) {
            // Iterate through the buyMap entries to find the matching BuyOrder
            for (Map.Entry<String, BuyOrder> entry : buyMap.entrySet()) {
                BuyOrder existingBuyOrder = entry.getValue();

                // Check if the BuyOrder objects match
                if (existingBuyOrder.equals(bestBidOffer)) {
                    // Check if the quantities match
                    if (existingBuyOrder.getQuantity() == quantity) {
                        // If the quantities match, remove the BuyOrder from the buyMap
                        buyMap.remove(entry.getKey());
                        changePrice(bestBidOffer.getSymbol(),  bestBidOffer.getPrice());
                        broadcastTheTransaction(bestBidOffer.getSymbol(), quantity, bestBidOffer.getPrice());
                    } else if (existingBuyOrder.getQuantity() > quantity) {
                        // If the existing quantity is greater, create a new BuyOrder with updated quantity
                        BuyOrder updatedBuyOrder = existingBuyOrder.toBuilder()
                                .setQuantity(existingBuyOrder.getQuantity() - quantity)
                                .build();

                        // Replace the existing entry in the buyMap with the updated BuyOrder
                        buyMap.put(entry.getKey(), updatedBuyOrder);
                        changePrice(bestBidOffer.getSymbol(), bestBidOffer.getPrice());
                        broadcastTheTransaction(bestBidOffer.getSymbol(), quantity, bestBidOffer.getPrice());
                    } else {
                        // Handle the case where the existing quantity is less than the sold quantity
                        System.out.println("Error: Sold quantity exceeds existing buy quantity!");
                    }

                    // Perform any additional processing or notification as needed
                    return; // Exit the loop since the BuyOrder was found and processed
                }

            }

            // Handle the case where the specified BuyOrder is not found in the buyMap
            System.out.println("Error: BuyOrder not found in buyMap!");
        }

        private void changePrice(String symbol, double newPrice) {
            // Find the corresponding StockChange
            StockChange stockChange = findStockChangeBySymbol(symbol);
            if (stockChange != null) {
                // Update the price in the StockChange object
               double old= stockChange.getNewPrice();
               stockChange.setPriceOld(old);
                stockChange.setPriceNew(newPrice);

            } else {
                // Handle case where StockChange for the symbol is not found
                System.out.println("StockChange for symbol " + symbol + " not found.");
            }
        }
        private StockChange findStockChangeBySymbol(String symbol) {
            for (StockChange stockChange : changeList) {
                if (stockChange.getComp().getSymbol().equals(symbol)) {
                    return stockChange;
                }
            }
            return null; // If no StockChange is found for the given symbol
        }




        /*private void broadcastTheTransaction(String symbol, int quantity, double price) {
            //za sad samo nek salje sale: symbol, quantity, price
            List<Client> tracking = new ArrayList<>();

            for (Company company : trackList.values()) {
                if (company.getSymbol().equals(symbol)) {
                    // The current company matches the target company
                    // Now find the corresponding client
                    for (Map.Entry<Client, Company> entry : trackList.entrySet()) {
                        if (entry.getValue().equals(company)) {
                            tracking.add(entry.getKey());
                        }
                    }
                }
            }
            StockChange sc = findStockChangeBySymbol(symbol);



            for (Client client : tracking) {
                Socket clientSocket = clientsMap.get(client);


                if (clientSocket != null) {
                    try {
                        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

                        // Formatiranje vremena u format dd.MM.yyyy.-hh'h' mm'm'
                        String currentTime = java.time.LocalDateTime.now().toString().replace("T", "-").substring(0, 16);

                        // Pronalaženje prethodne cene i izračunavanje promene

                        Company company = sc.getComp();
                        double change = ((sc.getNewPrice() - sc.getOldPrice()) / sc.getOldPrice()) * 100;
                        String changeSign = change >= 0 ? "+" : "-";
                        String changeArrow = change >= 0 ? "↑" : "↓";
                        String changeColor = change >= 0 ? "\u001B[32m" : "\u001B[31m";
                        String resetColor = "\u001B[0m";


                        String notification = String.format("Company: %-5s, Time: %s New price: %.2f Change: %s%s %.2f%% %s %.2f%s",
                                symbol,
                                currentTime,
                                sc.getNewPrice(),
                                changeColor,
                                changeSign,
                                Math.abs(change),
                                changeArrow,
                                sc.getOldPrice(),
                                resetColor);







                        writer.println(notification);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }*/
        private void broadcastTheTransaction(String symbol, int quantity, double price) {
            // Fetch the stock change for the given symbol
            StockChange sc = findStockChangeBySymbol(symbol);
            if (sc == null) {
                // Handle case when stock change is not found
                return;
            }

            // Fetch the company for the symbol
            Company company = sc.getComp();

            // Find clients tracking the company
            List<Client> tracking = new ArrayList<>();
            for (Map.Entry<Client, Company> entry : trackList.entrySet()) {
                if (entry.getValue().equals(company)) {
                    tracking.add(entry.getKey());
                }
            }

            // Send notifications to each tracking client
            for (Client client : tracking) {
                Socket clientSocket = clientsMap.get(client);
                if (clientSocket != null) {
                    try {
                        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

                        // Formatiranje vremena u format dd.MM.yyyy.-hh'h' mm'm'
                        String currentTime = java.time.LocalDateTime.now().toString().replace("T", "-").substring(0, 16);

                        // Pronalaženje prethodne cene i izračunavanje promene


                        double change = ((sc.getNewPrice() - sc.getOldPrice()) / sc.getOldPrice()) * 100;
                        String changeSign = change >= 0 ? "+" : "-";
                        String changeArrow = change >= 0 ? "↑" : "↓";
                        String changeColor = change >= 0 ? "\u001B[32m" : "\u001B[31m";
                        String resetColor = "\u001B[0m";


                        String notification = String.format("Company: %-5s, Time: %s New price: %.2f Change: %s%s %.2f%% %s %.2f%s",
                                symbol,
                                currentTime,
                                sc.getNewPrice(),
                                changeColor,
                                changeSign,
                                Math.abs(change),
                                changeArrow,
                                sc.getOldPrice(),
                                resetColor);







                        writer.println(notification);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
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

    /*private void startSocketServer() {
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

    }*/

    /*private void handleSocketClient(Socket clientSocket) {
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
                if (clientMessage.startsWith("register") && parts[0].equals("register") && parts.length == 2) {
                    handleRegistration(clientMessage, writer, clientSocket);


                } else if (!clientsMap2.containsKey(clientSocket)) {
                    writer.println("Not registered yet!");
                    continue;
                } else if (clientMessage.startsWith("track")) {
                    handleTracking(clientMessage, writer, clientSocket);


                } else {
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
                }
                // Process client request if needed
                while (true) {
                    if (clientSocket.isClosed()) {
                        String id = clientsMap2.get(clientSocket).getID();
                        System.out.println("Client disconnected: " + clientSocket + " User: " +id);
                        activeClients.remove(id);
                        clientsMap2.remove(id);
                        break;
                    }

                    Thread.sleep(1000);

                }
            }
        } catch (IOException e) {
            // Handle client disconnection
            //handleClientDisconnection(clientSocket);
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
*//*
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
                    assignRandomStocks(username);


                    clientsMap.put(novi, clientS);
                    clientsMap2.put(clientS, novi);
                    //System.out.println("ime klijekta" + novi.getID() +", soket : " + clientS);
                    activeClients.add(username);
                    writer.println("Registration successful. Welcome, " + username + "!");
                    //getOwnedStocksByTcp(clientS);



                }
            } else {
                writer.println("Invalid 'register' message format: " + clientMessage);
            }
        }

    }
    private void getOwnedStocksByTcp(Socket clientSocket) {
        // Get the client associated with the provided socket
        Client client = clientsMap2.get(clientSocket);

        // Check if the client is registered
        if (client != null) {
            // Retrieve the client's stock list from the clientsStockMap
            List<Stock> clientStocks = clientsStockMap.getOrDefault(client.getID(), new ArrayList<>());

            try (PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {
                // Send the client's stock list over TCP
                for (Stock stock : clientStocks) {
                    String stockInfo = String.format("Stock: Symbol=%s, Quantity=%d, Price=%.2f",
                            stock.getSymbol(), stock.getQuantity(), stock.getPrice());
                    writer.println(stockInfo);
                }
                // Notify the client that the response is complete
                //writer.println("Stocks update complete.");
            } catch (IOException e) {
                // Handle the exception (e.g., log it) if there's an issue sending the message
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: Client not found for the provided socket.");
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
    }*//*

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
    private void assignRandomStocks(String client) {
        List<Stock> randomStocks = generateRandomStocks();
        clientsStockMap.put(client, randomStocks);
    }
    private List<Stock> generateRandomStocks() {
        List<Stock> stocks = new ArrayList<>();
        Random random = new Random();

        // Generate a random number of stocks for simplicity
        int numStocks = random.nextInt(5) + 1; // Random number between 1 and 5

        for (int i = 0; i < numStocks; i++) {
            // Get a random company from the existing list
            Company randomCompany = getRandomCompany();

            // Generate random quantity and price
            int quantity = random.nextInt(100) + 1; // Random quantity between 1 and 100
            double price = random.nextDouble() * 100; // Random price between 0 and 100

            // Create a Stock instance and add it to the list
            Stock stock = new Stock(randomCompany.getSymbol(), quantity, price);
            stocks.add(stock);
        }

        return stocks;
    }

    private Company getRandomCompany() {
        Random random = new Random();
        int randomIndex = random.nextInt(companies.length);
        return companies[randomIndex];
    }*/
}
