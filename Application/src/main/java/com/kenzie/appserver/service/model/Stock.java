package com.kenzie.appserver.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.time.ZonedDateTime;

@DynamoDBTable(tableName = "Portfolio")
public class Stock {

    @DynamoDBRangeKey(attributeName = "symbol")
    private final String symbol;

    @DynamoDBHashKey(attributeName = "id")
    private final String name;

    @DynamoDBAttribute(attributeName = "purchasePrice")
    private final double purchasePrice;

    @DynamoDBAttribute(attributeName = "quantity")
    private final int quantity;

    @DynamoDBAttribute(attributeName = "purchaseDate")
    private final String purchaseDate;

    public Stock(String symbol, String name, double purchasePrice, int quantity, String purchaseDate) {
        this.symbol = symbol;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
    }

    public Stock(String symbol, String name, double purchasePrice, String purchaseDate) {
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

    public int getQuantity() {
        return quantity;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }
}
