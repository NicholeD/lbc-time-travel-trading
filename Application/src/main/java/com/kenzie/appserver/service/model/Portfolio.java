package com.kenzie.appserver.service.model;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    private Double funds;
    private List<Stock> stocks;

    public Portfolio() {
        this.funds = 100000.0;
        this.stocks = new ArrayList<>();
    }

    public Double getFunds() {
        return funds;
    }

    public void setFunds(Double funds) {
        this.funds = funds;
    }

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