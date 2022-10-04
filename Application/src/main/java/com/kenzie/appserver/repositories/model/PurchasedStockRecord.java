package com.kenzie.appserver.repositories.model;

import java.time.ZonedDateTime;
import java.util.Objects;

public class PurchasedStockRecord {

    private String userId;
    private String symbol;
    private String name;
    private double purchasePrice;
    private double shares;
    private double totalPaid;
    private ZonedDateTime purchaseDate;

    private ZonedDateTime orderedDate;

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getShares() { return shares; }

    public void setShares(double shares) { this.shares = shares; }

    public double getTotalPaid() { return totalPaid; }

    public void setTotalPaid(double totalPaid) { this.totalPaid = totalPaid; }

    public ZonedDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(ZonedDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public ZonedDateTime getOrderedDate() { return orderedDate; }

    public void setOrderedDate(ZonedDateTime orderedDate) { this.orderedDate = orderedDate; }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PurchasedStockRecord purchasedStockRecord = (PurchasedStockRecord) o;
        return Objects.equals(symbol, purchasedStockRecord.symbol);
    }
}
