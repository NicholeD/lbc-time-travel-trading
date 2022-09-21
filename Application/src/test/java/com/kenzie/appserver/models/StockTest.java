package com.kenzie.appserver.models;

import com.kenzie.appserver.service.model.Stock;
import org.junit.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StockTest {
    @Test
    public void stock_canCreateStock() {
        //GIVEN
        //WHEN
        Stock stock = new Stock("TEST", "testing stock", 1.0, 3, ZonedDateTime.now());
        //THEN
        assertNotNull(stock);
    }

    @Test
    public void stock_canGetMethods() {
        //GIVEN
        Stock stock = new Stock("TEST", "testing stock", 1.0, 3, ZonedDateTime.now());
        //WHEN
        String symbol = stock.getSymbol();
        String name = stock.getName();
        double purchasePrice = stock.getPurchasePrice();
        double quantity = stock.getQuantity();
        ZonedDateTime purchaseDate = stock.getPurchaseDate();
        //THEN
        assertEquals(symbol, "TEST", "Symbol does not match, check getSymbol method");
        assertEquals(name, "testing stock", "Name does not match, check getName method");
        assertEquals(purchasePrice, 1.0, "Purchase price does not match, check getPurchasePrice method");
        assertEquals(quantity, 3, "Quantity does not match, check getQuantity method");
        assertNotNull(purchaseDate, "Purchase date is null, check getPurchaseDate method");
    }
}
