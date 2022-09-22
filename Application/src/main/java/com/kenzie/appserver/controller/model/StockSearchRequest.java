package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class StockSearchRequest {

    @NotEmpty
    @JsonProperty("symbol")
    private String symbol;

    public String getSymbol() { return symbol; }

    public void setSymbol(String symbol) { this.symbol = symbol; }
}
