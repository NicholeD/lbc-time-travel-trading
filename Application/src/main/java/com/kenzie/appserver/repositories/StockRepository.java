package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.StockRecord;
import com.kenzie.appserver.service.model.Stock;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StockRepository extends CrudRepository<Stock, String> {
    List<StockRecord> find100BySymbol(String symbol);
}
