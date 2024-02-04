package org.example.z1;

import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.grpc.Company;
import org.example.grpc.Empty;
import org.example.grpc.StocksServiceGrpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class StockExClient {
    private Socket tcpSocket;
    private String client= "";
    private static int nextClientID = 1;
    StocksServiceGrpc.StocksServiceBlockingStub blockingStub;
    private ManagedChannel grpcChannel = null;

    public StockExClient(ManagedChannel channel) {


        blockingStub = StocksServiceGrpc.newBlockingStub(channel);
        this.grpcChannel = grpcChannel;
    }

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8091)
                .usePlaintext()
                .build();

        StockExClient client = new StockExClient(channel);

        StocksServiceGrpc.StocksServiceBlockingStub blockingStub = StocksServiceGrpc.newBlockingStub(channel);
        StocksServiceGrpc.StocksServiceStub asyncStub = StocksServiceGrpc.newStub(channel);

        try (Socket socket = new Socket("localhost", 7998)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            Thread serverListener = new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = reader.readLine()) != null) {
                        System.out.println(serverMessage);
                        if(serverMessage.startsWith("Registration successful.")){

                            String[] words = serverMessage.split(" ");

                            // Get the last word (assuming the username is the last word)
                            String lastWord = words[words.length - 1];
                            lastWord = lastWord.substring(0, lastWord.length() - 1);
                            client.client = lastWord;
                            System.out.print(lastWord);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            serverListener.start();

            // Start a separate thread to listen for user input
            Thread userInputThread = new Thread(() -> handleUserInput(writer));
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
            channel.shutdown();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void handleUserInput(PrintWriter writer) {
        try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
            String userInput;
            boolean running = true;

            while (running) {
                //System.out.println("Enter a command:");
                userInput = stdIn.readLine();

                if (userInput == null || "BYE".equalsIgnoreCase(userInput)) {
                    running = false;
                } else if(userInput.startsWith("track") || userInput.startsWith("register")){
                    writer.println(userInput);



                    Thread.sleep(100);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
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


