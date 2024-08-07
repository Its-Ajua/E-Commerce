package org.example;

import java.time.Duration;
import java.time.LocalDateTime;

public class Order {
    private long id;
    private long userId;
    private double total;
    private LocalDateTime timestamp_p;
    private Duration estimatedDeliveryTime;


    public Order() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getTimestamp() {
        return timestamp_p;
    }

    public void setTimestamp(LocalDateTime timestamp_p) {
        this.timestamp_p = timestamp_p;
    }

    public Duration getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(Duration estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }
}