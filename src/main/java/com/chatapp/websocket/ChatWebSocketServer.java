package com.chatapp.websocket;
 
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
 
import java.net.InetSocketAddress;
import java.util.Collection;
 
public class ChatWebSocketServer extends WebSocketServer {
 
    public ChatWebSocketServer(int port) {
        super(new InetSocketAddress(port));
    }
 
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New client connected: " + conn.getRemoteSocketAddress());
    }
 
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Client disconnected: " + conn.getRemoteSocketAddress());
    }
 
    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received from client: " + message);
 
        
        Collection<WebSocket> clients = getConnections();
        for (WebSocket client : clients) {
            if (client != conn) {               // <-- only broadcast to OTHER clients
                client.send(message);
            }
        }
    }
 
    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("WebSocket error on " + (conn != null ? conn.getRemoteSocketAddress() : "server") + ":");
        ex.printStackTrace();
    }
 
    @Override
    public void onStart() {
        System.out.println("WebSocket Server Started on port 8080!");
    }
 
    public static void main(String[] args) {
        int port = 8080;
        ChatWebSocketServer server = new ChatWebSocketServer(port);
        server.start();
        System.out.println("Server running on ws://localhost:" + port);
    }
}