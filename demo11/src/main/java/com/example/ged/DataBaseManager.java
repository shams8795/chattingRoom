package com.example.ged;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseManager {

    private Connection connection;

    public DataBaseManager() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void saveMessage(String username, String message) throws SQLException {
        String sql = "INSERT INTO messages (username, message) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, message);
            statement.executeUpdate();
        }
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
