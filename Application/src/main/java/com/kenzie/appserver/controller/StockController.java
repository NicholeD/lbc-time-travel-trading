package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.StockResponse;
import com.kenzie.appserver.repositories.StockRepository;
import com.kenzie.appserver.service.StockService;
import com.kenzie.appserver.service.model.Stock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/symbol")
public class StockController {

    private StockService stockService;

    StockController(StockService stockService) { this.stockService = stockService; }

    @GetMapping("/{symbol}")
    public ResponseEntity<List<StockResponse>> searchStocksBySymbol(@PathVariable("symbol") String symbol) {
        List<Stock> stocks = stockService.findBySymbol(symbol);

        if (stocks == null) {
            return ResponseEntity.notFound().build();
        }

        List<StockResponse> stockResponses = createStockResponse(stocks);
        return ResponseEntity.ok(stockResponses);
    }


    private List<StockResponse> createStockResponse(List<Stock> stocks) {
        List<StockResponse> stockResponses = new ArrayList<>();
        for (Stock stock : stocks) {
            StockResponse stockResponse = new StockResponse();
            stockResponse.setSymbol(stock.getSymbol());
            stockResponse.setName((stock.getName()));
            stockResponse.setPurchasePrice(stock.getPurchasePrice());
            stockResponse.setPurchaseDate(stock.getPurchaseDate());

            stockResponses.add(stockResponse);
        }

        return stockResponses;
    }
}
