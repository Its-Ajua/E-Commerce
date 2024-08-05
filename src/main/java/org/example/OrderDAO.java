package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public List<Order> getOrders(User user) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM orders WHERE user_id = ?")) {
            stmt.setLong(1, user.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                List<Order> orders = new ArrayList<>();
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getLong("id"));
                    order.setUserId(rs.getLong("user_id"));
                    order.setTotal(rs.getDouble("total"));
                    orders.add(order);
                }
                return orders;
            }
        }
    }

    public void addOrder(Order order) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (user_id, total) VALUES (?, ?)");
        stmt.setLong(1, order.getUserId());
        stmt.setDouble(2, order.getTotal());
        stmt.executeUpdate();

        stmt.close();

    }
}