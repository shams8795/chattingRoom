package com.example.ged;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ChatApp extends Application {
    private Client client;
    private VBox messagesBox;
    private BorderPane app;
    private Stage stage;
    private String currentUsername;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        this.client = new Client();
        this.client.setMessageReceiver(this::displayReceivedMessage);
        showHomeScreen();
    }

    private void showHomeScreen() {
        VBox homeScreen = new VBox(20);
        homeScreen.setAlignment(Pos.CENTER);
        homeScreen.setPadding(new Insets(20));
        homeScreen.setStyle("-fx-background-color: #FFB6C1;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setMaxWidth(200);

        Button enterButton = new Button("Enter");

        enterButton.setOnAction(e -> {
            String username = usernameField.getText();
            if (username != null && !username.isEmpty()) {
                currentUsername = username;
                client.setUsername(username);
                showChatScreen();
            }
        });

        homeScreen.getChildren().addAll(usernameField, enterButton);

        Scene homeScene = new Scene(homeScreen, 500, 400);
        stage.setScene(homeScene);
        stage.setTitle("BSR ChatApp - Home");
        stage.show();
    }

    private void showChatScreen() {
        BorderPane chatPane = new BorderPane();
        TextField writingField = new TextField();

        messagesBox = new VBox(10);
        messagesBox.setPadding(new Insets(10));
        ScrollPane scrollPane = new ScrollPane(messagesBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #FFB6C1; -fx-border-color: #000000;");

        Button homeButton = new Button("Home");
        Button sendButton = new Button("Send");
        Button mediaButton = new Button("Media");

        sendButton.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #000000;");
        mediaButton.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #000000;");
        writingField.setStyle("-fx-border-color: #000000;");

        HBox inputArea = new HBox(10, writingField, sendButton, mediaButton);
        inputArea.setPadding(new Insets(10));
        inputArea.setStyle("-fx-border-color: #000000; -fx-background-color: #FFFFFF;");

        sendButton.setOnAction(e -> {
            String message = writingField.getText();
            if (message != null && !message.isEmpty()) {
                client.sendMessage(message);
                displayMessage("Me: " + message, "#FFB6C1");
                writingField.clear();
            }
        });

        writingField.setOnAction(e -> {
            String message = writingField.getText();
            if (message != null && !message.isEmpty()) {
                client.sendMessage(message);
                displayMessage("Me: " + message, "#FFB6C1");
                writingField.clear();
            }
        });

        mediaButton.setOnAction(e -> handleMediaSending());

        homeButton.setOnAction(e -> showHomeScreen());

        chatPane.setCenter(scrollPane);
        chatPane.setBottom(inputArea);

        app = new BorderPane();
        app.setCenter(chatPane);
        app.setTop(homeButton);

        Scene chatScene = new Scene(app, 500, 400, Color.LIGHTPINK);
        stage.setScene(chatScene);
        stage.setTitle("BSR ChatApp");
        stage.show();
    }

    private void handleMediaSending() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Media");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                client.sendFile(file);
                displayMessage("Sent media: " + file.getName(), "#FFB6C1");
            } catch (IOException ex) {
                ex.printStackTrace();
                displayMessage("Failed to send media: " + ex.getMessage(), "#FFB6C1");
            }
        }
    }

    private void displayMessage(String message, String color) {
        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-background-color: " + color + "; "
                + "-fx-border-color: #000000; "
                + "-fx-border-radius: 10px; "
                + "-fx-background-radius: 10px; "
                + "-fx-padding: 5px;");
        messagesBox.getChildren().add(messageLabel);
    }

    private void displayReceivedMessage(String username, String message) {
        Label messageLabel = new Label(username + ": " + message);
        messageLabel.setStyle("-fx-background-color: #D3D3D3; -fx-border-color: #000000; -fx-padding: 5px;");
        messagesBox.getChildren().add(messageLabel);
    }

    public static void main(String[] args) {
        launch();
    }
}
