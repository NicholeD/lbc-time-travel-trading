package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.StockRepository;
import com.kenzie.appserver.repositories.model.StockRecord;
import com.kenzie.appserver.service.model.Stock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) { this.stockRepository = stockRepository;}

    public List<Stock> findBySymbol(String symbol) {
        List<Stock> stocks = new ArrayList<>();

        Iterable<StockRecord> stockIterator = stockRepository.find100BySymbol(symbol);

        for (int i = 0; i < 30; i++) {
            for(StockRecord record : stockIterator) {
                stocks.add(new Stock(record.getSymbol(),
                        record.getName(),
                        record.getPurchasePrice(),
                        1,
                        record.getPurchaseDate()));
            }
        }

        return stocks;
    }
}
