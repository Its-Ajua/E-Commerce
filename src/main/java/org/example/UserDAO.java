package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDAO {
    public void addUser(User user) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?,?)")) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
        }
    }

    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username =?")) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(rs.getString("username"), rs.getString("password"));
                    user.setId(rs.getLong("id"));
                    if (user.getPassword().equals(password)) {
                        return user;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        }
    }
}