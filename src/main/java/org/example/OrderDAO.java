package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;

public class OrderDAO {
    /**public List<Order> getOrders(User user) throws SQLException {
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
                    order.setTimestamp(rs.getString("timestamp").toLocalDateTime());
                    order.setEstimatedDeliveryTime(Duration.between(order.getTimestamp(), rs.getString("estimated_delivery_time").toLocalDateTime()));
                    orders.add(order);
                }
                return orders;
            }
        }
    }*/

    public void addOrder(Order order) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (user_id, total, timestamp_p, estimateddeliverytime) VALUES (?, ?, ?, ?)");

        stmt.setLong(1, order.getUserId());
        stmt.setDouble(2, order.getTotal());

        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
        stmt.setTimestamp(3, currentTimestamp); // set current timestamp

        LocalDateTime estimatedDeliveryTime = currentDateTime.plusDays(5);
        Timestamp estimatedDeliveryTimestamp = Timestamp.valueOf(estimatedDeliveryTime);
        stmt.setTimestamp(4, estimatedDeliveryTimestamp); // set estimated delivery time 5 days from now

        stmt.executeUpdate();

        stmt.close();
    }
}