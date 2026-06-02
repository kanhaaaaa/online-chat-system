package com.chatapp.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {

        try (
            Socket socket      = new Socket("localhost", 5000);
            Scanner scanner    = new Scanner(System.in);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()))
        ) {
            System.out.println("Connected to Chat Server on port 5000!");

            
            Thread receiveThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = reader.readLine()) != null) {
                        System.out.println("[Received]: " + serverMessage);
                    }
                } catch (IOException e) {
                    
                    if (!socket.isClosed()) {
                        System.out.println("Connection lost: " + e.getMessage());
                    }
                }
                
                System.out.println("Server disconnected. You may close this window.");
            });

            
            receiveThread.setDaemon(true);
            receiveThread.start();

            System.out.println("Type a message and press Enter. Type 'exit' to quit.");
            while (scanner.hasNextLine()) {
                String message = scanner.nextLine();
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Disconnecting...");
                    break;
                }
                if (!message.isBlank()) {
                    writer.println(message);
                }
            }

        } catch (ConnectException e) {
            System.out.println("Could not connect — is ChatServer running on port 5000?");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}