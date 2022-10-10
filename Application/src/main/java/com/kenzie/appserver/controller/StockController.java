package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.SearchStockResponse;
import com.kenzie.appserver.controller.model.StockResponse;
import com.kenzie.appserver.service.StockService;
import com.kenzie.appserver.service.model.Stock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stocks")
public class StockController {
    private StockService stockService = new StockService();

    @GetMapping("/{symbol}")
    public ResponseEntity<SearchStockResponse> getStocksBySymbol(@PathVariable("symbol") String symbol) {
        StockResponse stockResponse = stockService.getStocksBySymbol(symbol);

        //If there is no stock, then return a 204
        if (stockResponse == null) {
            return ResponseEntity.notFound().build();
        }
        //Otherwise, convert into searchStockResponse and return it
        SearchStockResponse searchStockResponse = createSearchStockResponse(stockResponse, symbol);

        return ResponseEntity.ok(searchStockResponse);
    }

    private ZonedDateTime convertDate(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(string, formatter);

        return date.atStartOfDay(ZoneId.systemDefault());
    }

    private SearchStockResponse createSearchStockResponse(StockResponse stockResponse, String symbol) {
        List<Stock> stocks = new ArrayList<>();
        String name = stockService.getStockNameBySymbol(symbol);

        Map<String, HashMap<String, String>> stocksByDay = stockResponse.getStocksByDay();

        for(Map.Entry<String, HashMap<String, String>> day : stocksByDay.entrySet()) {
            Stock stock = new Stock(symbol, name,
                    Double.valueOf(day.getValue().get("4. close")),
                    (day.getKey()));
            stocks.add(stock);
        }

        SearchStockResponse searchStockResponse = new SearchStockResponse();
        searchStockResponse.setSymbol(symbol);
        searchStockResponse.setName(name);
        searchStockResponse.setStocks(stocks.stream().limit(30).collect(Collectors.toList()));

        return searchStockResponse;
    }
}
