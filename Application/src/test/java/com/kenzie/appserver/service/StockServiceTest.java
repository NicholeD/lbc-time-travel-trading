package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.StockResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class StockServiceTest {

//    @Autowired
//    private RestTemplate restTemplate = new RestTemplate();

    private StockService stockService;

    @BeforeEach
    void setup() {
        stockService = new StockService();
    }

    @Test
    void getStockBySymbol() {
        //GIVEN
        String symbol = "IBM";

        //WHEN
        StockResponse stockResponse = stockService.getStocksBySymbol(symbol);

        //THEN
        Assertions.assertNotNull(stockResponse);
        Assertions.assertEquals(stockResponse.getMetaData().get("2. Symbol"), symbol);
    }

    @Test
    void getStockNameBySymbol() {
        //GIVEN
        String symbol = "IBM";
        String name = "International Business Machines";

        //WHEN
       String companyName = stockService.getStockNameBySymbol(symbol);

        //THEN
        Assertions.assertNotNull(companyName);
        Assertions.assertEquals(name, companyName);
    }
}
