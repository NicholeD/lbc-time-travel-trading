package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchasedStockResponse {

    @JsonProperty("userId")
    private String userId;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("name")
    private String name;
    @JsonProperty("purchasePrice")
    private double purchasePrice;
    @JsonProperty("shares")
    private int shares;
    @JsonProperty("totalPaid")
    private double totalPaid;
    @JsonProperty("purchaseDate")
    private String purchaseDate;
    @JsonProperty("orderDate")
    private String orderedDate;

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

    public int getShares() { return shares; }

    public void setShares(int shares) { this.shares = shares; }

    public double getTotalPaid() { return totalPaid; }

    public void setTotalPaid(double totalPaid) { this.totalPaid = totalPaid; }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getOrderedDate() { return orderedDate; }

    public void setOrderedDate(String orderedDate) { this.orderedDate = orderedDate; }

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
        PurchasedStockResponse purchasedStockResponse = (PurchasedStockResponse) o;
        return Objects.equals(symbol, purchasedStockResponse.symbol);
    }
}
