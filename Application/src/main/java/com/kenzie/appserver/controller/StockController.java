package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.StockResponse;
import com.kenzie.appserver.service.StockService;
import com.kenzie.appserver.service.model.Stock;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping
public class StockController {
    private StockService stockService;

    @GetMapping
    public List<Stock> getStocks(String symbol) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Stock> last30Days = new ArrayList<>();

        StockResponse stockResponse = stockService.getStocksBySymbol(symbol);
        String name = stockService.getStockNameBySymbol(symbol);

        Map<String, HashMap<String, String>> stocksByDay = stockResponse.getStocksByDay();

        //Iterating over returned nested Map from api call to only return the last 30 days of Stock objects
        for (int i = 0; i < 30; i++) {
            for(Map.Entry<String, HashMap<String, String>> day : stocksByDay.entrySet()) {
                    Stock stock = new Stock(symbol, name,
                            Double.valueOf(day.getValue().get("4. close")),
                            1,
                            ZonedDateTime.parse(day.getKey(), formatter));
                    last30Days.add(stock);
            }
        }

        return last30Days;
        }
}


