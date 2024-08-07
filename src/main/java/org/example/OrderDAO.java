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
    public void addOrder(Order order) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (user_id, total, timestamp_p, estimateddeliverytime) VALUES (?, ?, ?, ?)");

        stmt.setLong(1, order.getUserId());
        stmt.setDouble(2, order.getTotal());

        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
        stmt.setTimestamp(3, currentTimestamp);

        LocalDateTime estimatedDeliveryTime = currentDateTime.plusDays(5);
        Timestamp estimatedDeliveryTimestamp = Timestamp.valueOf(estimatedDeliveryTime);
        stmt.setTimestamp(4, estimatedDeliveryTimestamp);

        stmt.executeUpdate();

        stmt.close();
    }
}