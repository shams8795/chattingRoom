package com.example.ged;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public Server() {
        try {
            // Initialize the server socket to listen on port 5050
            serverSocket = new ServerSocket(5050);
            System.out.println("Server started on port 5050");

            // Wait for a client connection
            clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            // Setup input and output streams for communication
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            // Continuous loop to handle messages from the client
            String messageFromClient;
            while ((messageFromClient = reader.readLine()) != null) {
                System.out.println("Client: " + messageFromClient);

                // Respond to the client
                String response = "Server: " + messageFromClient;
                writer.write(response);
                writer.newLine();
                writer.flush();

                System.out.println("Message Received and Response Sent");
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Ensure resources are properly closed when the server is done
            closeResources();
        }
    }

    private void closeResources() {
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
            System.out.println("Server resources closed.");
        } catch (IOException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
