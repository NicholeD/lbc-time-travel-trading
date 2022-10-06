package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.kenzie.appserver.service.model.Stock;

import java.util.ArrayList;
import java.util.List;

@DynamoDBTable(tableName = "Portfolio")
public class PortfolioRecord {
    private String userId;
    private Double funds;
    private List<Stock> stocks;

    public PortfolioRecord() {
        this.funds = 100000.0;
        this.stocks = new ArrayList<>();
    }

    @DynamoDBHashKey(attributeName = "UserId")
    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    @DynamoDBAttribute(attributeName = "Funds")
    public Double getFunds() {
        return funds;
    }

    public void setFunds(Double funds) {
        this.funds = funds;
    }

    @DynamoDBAttribute(attributeName = "Stocks")
    public List<Stock> getStocks() {
        return stocks;
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public void removeStock(Stock stock) {
        this.stocks.remove(stock);
    }

}
