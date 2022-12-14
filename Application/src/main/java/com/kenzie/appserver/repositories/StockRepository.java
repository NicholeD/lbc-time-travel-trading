package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.StockRecord;
import com.kenzie.appserver.service.model.Stock;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
@EnableScan
public interface StockRepository extends CrudRepository<StockRecord, String> {
    List<StockRecord> findStockBySymbol(String symbol);
}
