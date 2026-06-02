package com.chatapp.server;
 
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
 
public class ChatServer {
 

    private static List<ClientHandler> clients = new CopyOnWriteArrayList<>();
 
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Chat Server Started on Port 5000...");
 
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getRemoteSocketAddress());
 
                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler);
                clientHandler.start();
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }
 
    
    public static void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("Client removed. Active clients: " + clients.size());
    }
}