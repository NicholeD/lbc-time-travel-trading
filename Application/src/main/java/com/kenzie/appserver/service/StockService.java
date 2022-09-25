package com.kenzie.appserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.controller.model.StockResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@Service
public class StockService {
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public StockResponse getStocksBySymbol(String symbol) {
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&apikey=5DA1NYHTSAKVQ99Z&symbol=" + symbol;
        StockResponse stockResponse = restTemplate.getForObject(url, StockResponse.class);

        return stockResponse;
    }

    @GetMapping("/Name")
    public String getStockNameBySymbol(String symbol) {
        String url = "https://www.alphavantage.co/query?function=OVERVIEW&apikey=5DA1NYHTSAKVQ99Z&symbol=" + symbol;

        try {
            Map<String, String> map = mapper.readValue(url, Map.class);
            return map.get("Name");
        } catch (IOException e) {
            return e.getMessage();
        }
    }

}
