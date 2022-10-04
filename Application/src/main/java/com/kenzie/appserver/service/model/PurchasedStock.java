package com.kenzie.appserver.service.model;

import java.time.ZonedDateTime;

public class PurchasedStock {

    private String userId;

    public Stock stock;
    public ZonedDateTime orderDate;

    public PurchasedStock(String userId, Stock stock, ZonedDateTime orderDate) {
        this.userId = userId;
        this.stock = stock;
        this.orderDate = orderDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUser(String userId) {
        this.userId = userId;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
