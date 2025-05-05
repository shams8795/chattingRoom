package com.example.ged;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

public class Client {

    private String username;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private MessageReceiver messageReceiver;
    private DataBaseManager databaseManager;

    public Client() {
        try {
            socket = new Socket("localhost", 5050);
            System.out.println("Connected to the server on port 5050");

            // Verify that socket is connected
            if (socket != null && !socket.isClosed()) {
                try {
                    // Initialize BufferedReader and BufferedWriter
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    // Check if initialization was successful
                    if (bufferedReader != null && bufferedWriter != null) {
                        System.out.println("BufferedReader and BufferedWriter initialized successfully.");
                    } else {
                        System.out.println("Failed to initialize BufferedReader or BufferedWriter.");
                    }
                } catch (IOException e) {
                    System.out.println("Error initializing input/output streams: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("Socket is not connected.");
            }

            // Initialize database manager
            databaseManager = new DataBaseManager();

            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = bufferedReader.readLine()) != null) {
                        if (messageReceiver != null) {
                            messageReceiver.receiveMessage("Server", serverMessage);
                        }
                        // Save incoming messages to the database
                        if (username != null && !username.isEmpty()) {
                            databaseManager.saveMessage(username, serverMessage);
                        }
                    }
                } catch (IOException | SQLException e) {
                    System.out.println("Error receiving messages: " + e.getMessage());
                }
            }).start();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();
                sendMessage(message);

                if (message.equalsIgnoreCase("Exit")) {
                    break;
                }
            }

            scanner.close();
            closeConnection();
        } catch (IOException e) {
            System.out.println("Error on the client side: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (bufferedWriter == null) {
            System.out.println("BufferedWriter is null. Cannot send message.");
            return;
        }

        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            // Save outgoing messages to the database
            if (username != null && !username.isEmpty()) {
                databaseManager.saveMessage(username, message);
            }
        } catch (IOException | SQLException e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }

    public void sendFile(File file) throws IOException {
        // Implement file sending functionality here
        System.out.println("File sending functionality not implemented.");
    }

    public void setMessageReceiver(MessageReceiver receiver) {
        this.messageReceiver = receiver;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void closeConnection() {
        try {
            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (socket != null) socket.close();
            if (databaseManager != null) databaseManager.close();
            System.out.println("Client connection closed.");
        } catch (IOException | SQLException e) {
            System.out.println("Error closing client resources: " + e.getMessage());
        }
    }

    public interface MessageReceiver {
        void receiveMessage(String username, String message);
    }
}
