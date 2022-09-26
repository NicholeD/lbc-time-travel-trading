package com.kenzie.appserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.controller.model.StockResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class StockService {

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();


    @GetMapping
    public StockResponse getStocksBySymbol(String symbol) {
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&apikey=5DA1NYHTSAKVQ99Z&symbol=" + symbol;

        StockResponse stockResponse = restTemplate.getForObject(url, StockResponse.class);

        return stockResponse;
    }

    @GetMapping("/Name")
    public String getStockNameBySymbol(String symbol) {
        String url = "https://www.alphavantage.co/query?function=OVERVIEW&apikey=5DA1NYHTSAKVQ99Z&symbol=" + symbol;

        Map<String, String> response = restTemplate.getForObject(url, Map.class);

        return response.get("Name");
    }
}
