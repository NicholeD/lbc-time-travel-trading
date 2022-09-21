package com.kenzie.appserver.service.model;

import java.time.ZonedDateTime;

public class Stock {
    private final String symbol;
    private final String name;
    private final double purchasePrice;
    private final double quantity;
    private final ZonedDateTime purchaseDate;

    public Stock(String symbol, String name, double purchasePrice, double quantity, ZonedDateTime purchaseDate) {
        this.symbol = symbol;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public ZonedDateTime getPurchaseDate() {
        return purchaseDate;
    }
}
