package com.kenzie.appserver.service.model;

public class User {
    private final String username;
    private final String userId;
    private Portfolio portfolio;

    public User(String id, String name, Portfolio portfolio) {
        this.userId = id;
        this.username = name;
        this.portfolio = portfolio;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void addStock(Stock stock) {
        portfolio.addStock(stock);
    }

    public void removeStock(Stock stock) {
        portfolio.removeStock(stock);
    }
}
