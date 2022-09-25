package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockResponse {

    @JsonProperty("Meta Data")
    private List<String> metaData;

    @JsonProperty("Time Series (Daily)")
    private Map<String, HashMap<String, String>> stocksByDay;

    public List<String> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<String> metaData) {
        this.metaData = metaData;
    }

    public Map<String, HashMap<String, String>> getStocksByDay() {
        return stocksByDay;
    }

    public void setStocksByDay(Map<String, HashMap<String, String>> stocksByDay) {
        this.stocksByDay = stocksByDay;
    }
}
