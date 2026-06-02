package com.chatapp;

import com.chatapp.database.DBConnection;

public class App {
    public static void main(String[] args) {
        DBConnection.getConnection();
    }
}
