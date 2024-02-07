package org.example.z1;

import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.example.grpc.*;

import javax.ejb.SessionSynchronization;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class StockExClient extends StocksServiceGrpc.StocksServiceImplBase{
    private Socket tcpSocket;
    private String client= "";
    //private static int nextClientID = 1;
    StocksServiceGrpc.StocksServiceBlockingStub blockingStub;
    private ManagedChannel grpcChannel = null;


    public StockExClient(ManagedChannel channel) {


        blockingStub = StocksServiceGrpc.newBlockingStub(channel);

        this.grpcChannel = channel;
        try {
            tcpSocket = new Socket("localhost", 7998);
        } catch (IOException e) {
            throw new RuntimeException("Error creating socket", e);
        }
    }

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8091)
                .usePlaintext()
                .build();

        StockExClient client = new StockExClient(channel);
        Scanner scanner = new Scanner(System.in);


        //StocksServiceGrpc.StocksServiceBlockingStub blockingStub = StocksServiceGrpc.newBlockingStub(channel);
        //StocksServiceGrpc.StocksServiceStub asyncStub = StocksServiceGrpc.newStub(channel);

        try /*(Socket socket = new Socket("localhost", 7998))*/ {
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(client.tcpSocket.getInputStream()));
            PrintWriter writer1 = new PrintWriter(client.tcpSocket.getOutputStream(), true);

            Thread serverListener = new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = reader1.readLine()) != null) {
                        System.out.println(serverMessage);
                        if(serverMessage.startsWith("Registration successful.")){

                            String[] words = serverMessage.split(" ");

                            // Get the last word (assuming the username is the last word)
                            String lastWord = words[words.length - 1];
                            lastWord = lastWord.substring(0, lastWord.length() - 1);
                            client.client = lastWord;
                           // System.out.print(lastWord);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            serverListener.start();

            // Start a separate thread to listen for user input
            Thread userInputThread = new Thread(() -> {
                while(true) {
                    handleUserInput(writer1, scanner, client, client.blockingStub);
                }
            });
            userInputThread.start();

            // Continue with other tasks if needed
            System.out.println("Primer soket poziva");
            //makeSocketCall(writer);

            try {
                userInputThread.join();
                serverListener.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Shutdown the channel when done
            client.grpcChannel.shutdown();
            //client.tcpSocket.close();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void handleUserInput(PrintWriter writer, Scanner scanner,StockExClient client, StocksServiceGrpc.StocksServiceBlockingStub blockingStub ) {

            
            
                //System.out.println("Enter a command:");
            String userInput = scanner.nextLine();
                String[] parts = userInput.split(" ");

                 if(userInput.startsWith("track") || userInput.startsWith("register")){
                    writer.println(userInput);
                    /*if(userInput.startsWith("track")){
                        System.out.println("track order in");
                    }*/




                }else if(userInput.startsWith("bid")){
                    if(parts.length == 3){
                        String symbol = parts[1];
                        int quantity = Integer.parseInt(parts[2]);
                        client.seeBid(symbol, quantity, client);



                    }else{
                        System.out.print("bid has 3 parameters");
                    }




                }else if(userInput.startsWith("ask")){
                    if(parts.length == 3){
                        String symbol = parts[1];
                        int quantity = Integer.parseInt(parts[2]);
                        client.seeAsk(symbol, quantity, client);



                    }else{
                        System.out.print("ask has 3 parameters");
                    }


                }else if(userInput.startsWith("order")){
                    if(parts.length == 6){
                        String symbol = parts[1];
                        double price = Double.parseDouble(parts[3]);
                        int quantity = Integer.parseInt(parts[2]);
                        String idClient = parts[4];
                        boolean ask = Boolean.parseBoolean(parts[5]);//ako je false onda je bid

                        client.order(symbol,quantity,price,  idClient, ask,  client);



                    }else{
                        System.out.print("order has 6 parameters");
                    }




                }else if(userInput.equals("getAllCompanies")){

                     client.seeGetAllCompanies(client);

                 }

            }

    private void seeGetAllCompanies(StockExClient client) {
        try{
            Empty empty = Empty.newBuilder().build();

            Iterator<Company> companyStream = client.blockingStub.getAllCompanies(empty);
            while (companyStream.hasNext()) {
                Company company = companyStream.next();
                String dateFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                System.out.println("Simbol: " + company.getSymbol() + ", ime kompanije: " + company.getCompanyName() + "cena: "
                        + company.getPriceNow() + ", promena: " + company.getChange() + ", datum: " + formatDate(company.getDate(), sdf));


            }}catch(StatusRuntimeException e){
            System.out.println("Error during getAllCompanies: " + e.getStatus());


        }
    }
    private void seeAsk(String symbol, int quantity, StockExClient client) {
		// TODO Auto-generated method stub
             try{
                 StockRequest stockRequest = StockRequest.newBuilder()
                         .setSymbol(symbol)

                         .setQuantity(quantity)
                         .build();
                 Iterator<SellOrder> sellResponse = client.blockingStub.ask(stockRequest);
                 /*for (Iterator<SellOrder> it = sellResponse; it.hasNext(); ) {
                     SellOrder sellO = it.next();
                     System.out.println(sellO.getSymbol() + " " + sellO.getQuantity() + " " + sellO.getPrice());


                 }*/
                 while (sellResponse.hasNext()) {
                     SellOrder sellO = sellResponse.next();
                     // Print each SellOrder response
                     System.out.println(sellO.getSymbol() + " " + sellO.getQuantity() + " " + sellO.getPrice());
                 }
             }catch(StatusRuntimeException e){
                 System.out.println("Error during ask: " + e.getStatus());


             }
		
	}

		private void seeBid(String symbol, int quantity, StockExClient client) {
		// TODO Auto-generated method stub
            try{
                StockRequest stockRequest = StockRequest.newBuilder()
                        .setSymbol(symbol)

                        .setQuantity(quantity)
                        .build();
                Iterator<BuyOrder> buyResponse = client.blockingStub.bid(stockRequest);
                for (Iterator<BuyOrder> it = buyResponse; it.hasNext(); ) {
                    BuyOrder buyO = it.next();
                    System.out.println(buyO.getSymbol() + " " + buyO.getQuantity() + " " + buyO.getPrice());


                }
            }catch(StatusRuntimeException e){
                System.out.println("Error during ask: " + e.getStatus());


            }

		
	}

		/*catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }*/
        
    

    private void order(String symbol,  int quantity,double price, String idClient, boolean ask, StockExClient client) {
        try{
            OrderRequest orderRequest = OrderRequest.newBuilder()
                    .setSymbol(symbol)
                    .setPrice(price)
                    .setQuantity(quantity)
                    .setIdClient(idClient)
                    .setAsk(ask)
                    .build();
            OrderResponse orderResponse = client.blockingStub.order(orderRequest);
            System.out.print(orderResponse.getResponse());
        }catch(StatusRuntimeException e){
            System.out.println("Error during order: " + e.getStatus());


        }
    }

/*
    private void seeAsk(String symbol, int quantity, StockExClient client) {
        try{

        }catch(StatusRuntimeException e){


        }
    }

    private void seeBid(String symbol, int quantity, StockExClient client) {
        try{

        }catch(StatusRuntimeException e){


        }
    }
*/
    public static void ispisKopmanije(Company cs) {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        System.out.println("Simbol: " + cs.getSymbol() + ", ime kompanije: " + cs.getCompanyName() + "cena: " + cs.getPriceNow() + ", promena: " + cs.getChange() + ", datum: " + formatDate(cs.getDate(), sdf));

    }


    private static String formatDate(Timestamp timestamp, SimpleDateFormat sdf) {

        long seconds = timestamp.getSeconds();
        Date date = new Date(seconds * 1000);
        return sdf.format(date);
    }

   /* private static void makeSocketCall() {
        try (Socket socket = new Socket("localhost", 7998);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Send a message to the server
            //writer.println("update");

            try (
                    BufferedReader stdIn = new BufferedReader(
                            new InputStreamReader(System.in))    // Za čitanje sa standardnog ulaza - tastature!
            ) {

                String userInput;
                boolean running = true;

                while (running) {
                    System.out.println("Unesi komandu");
                    userInput = stdIn.readLine();
                    if (userInput == null || "BYE".equalsIgnoreCase(userInput)) // userInput - tekst koji je unet sa tastature!
                    {
                        running = false;
                    } else if (userInput.startsWith("register")) {
                        writer.println(userInput); // Send registration request to the server

                        // Read the server's response
                        String serverResponse = reader.readLine();
                        System.out.println("Server says: " + serverResponse);

                        // Check if registration was successful before allowing other requests
                        if ("Client registered successfully.".equals(serverResponse)) {
                            System.out.println("Unesi koje kompanije zelis da pratis");
                            userInput = stdIn.readLine();
                            if (userInput.startsWith("track")) {
                                String[] parts = userInput.split(" ", 12);
                                if (parts.length >= 2) {
                                    String track = parts[0];
                                    String saljem = track;
                                    for (int i = 1; parts.length > i; i++) {
                                        String symbol = parts[i];
                                        saljem = saljem + " " + symbol;


                                    }

                                    writer.println(saljem);


                                } else {
                                    System.out.println("track command not ok");
                                }
                            } else {
                                System.out.println("Registration failed. Please try again.");
                            }
                        }

                    }

                    // Read the server's response

                    String serverResponse;
                    while ((serverResponse = reader.readLine()) != null) {
                        System.out.println("server kaze: " + serverResponse);
                    }
                }
            }catch(IOException e){
                    e.printStackTrace();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }*/
    }


