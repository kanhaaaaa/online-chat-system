package com.chatapp.server;
 
import java.io.*;
import java.net.*;
 
public class ClientHandler extends Thread {
 
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
 
    public ClientHandler(Socket socket) {
        this.socket = socket;
 
        try {
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public void run() {
        try {
            String message;
 
            while ((message = reader.readLine()) != null) {
                System.out.println("Client says: " + message);
                ChatServer.broadcast(message, this);
            }
 
        } catch (Exception e) {
            System.out.println("Client disconnected: " + socket.getRemoteSocketAddress());
        } finally {
            
            ChatServer.removeClient(this);
            try { socket.close(); } catch (Exception ignored) {}
        }
    }
 
    public void sendMessage(String message) {
        writer.println(message);
    }
}