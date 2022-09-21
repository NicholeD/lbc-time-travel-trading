package com.kenzie.appserver.models;

import com.kenzie.appserver.service.model.Portfolio;
import com.kenzie.appserver.service.model.Stock;
import org.junit.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PortfolioTest {
    @Test
    public void portfolio_canCreatePortfolio() {
        //GIVEN
        //WHEN
        Portfolio portfolio = new Portfolio();
        //THEN
        assertNotNull(portfolio);
    }

    @Test
    public void portfolio_canGetFunds() {
        //GIVEN
        Portfolio portfolio = new Portfolio();
        //WHEN
        Double funds = portfolio.getFunds();
        //THEN
        assertNotNull(funds);
    }

    @Test
    public void portfolio_canAddStocks() {
        //GIVEN
        Portfolio portfolio = new Portfolio();
        Stock stock = new Stock("TEST", "testing stock", 1.0, 3, ZonedDateTime.now());
        //WHEN
        portfolio.addStock(stock);
        //THEN
        assertTrue(portfolio.getStocks().contains(stock), "Stocks list does not contain stock, Check addStock method");
    }

    @Test
    public void portfolio_canRemoveStocks() {
        //GIVEN
        Portfolio portfolio = new Portfolio();
        Stock stock = new Stock("TEST", "testing stock", 1.0, 3, ZonedDateTime.now());
        portfolio.addStock(stock);
        //WHEN
        portfolio.removeStock(stock);
        //THEN
        assertFalse(portfolio.getStocks().contains(stock), "Stocks list still contains stock, Check removeStock method");
    }
}
