package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM orders");
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
            Order order = new Order();
            order.setId(rs.getLong("id"));
            order.setUserId(rs.getLong("user_id"));
            order.setTotal(rs.getDouble("total"));
            orders.add(order);
        }

        rs.close();
        stmt.close();
    } finally {
            DatabaseConnection.closeConnection();
        }

        return orders;
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