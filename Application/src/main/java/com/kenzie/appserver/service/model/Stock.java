package com.kenzie.appserver.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.time.ZonedDateTime;

@DynamoDBTable(tableName = "Portfolio")
public class Stock {

    @DynamoDBHashKey(attributeName = "symbol")
    private final String symbol;

    @DynamoDBRangeKey(attributeName = "stockId")
    private final String name;

    @DynamoDBAttribute(attributeName = "purchasePrice")
    private final double purchasePrice;

    @DynamoDBAttribute(attributeName = "quantity")
    private final double quantity;

    @DynamoDBAttribute(attributeName = "purchaseDate")
    private final ZonedDateTime purchaseDate;

    public Stock(String symbol, String name, double purchasePrice, double quantity, ZonedDateTime purchaseDate) {
        this.symbol = symbol;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
    }

    public Stock(String symbol, String name, double purchasePrice, ZonedDateTime purchaseDate) {
        this.symbol = symbol;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.quantity = 1;
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
