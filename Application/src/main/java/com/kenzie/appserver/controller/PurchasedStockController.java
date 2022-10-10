package com.kenzie.appserver.controller;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.controller.model.PurchaseStockRequest;
import com.kenzie.appserver.controller.model.PurchasedStockResponse;
import com.kenzie.appserver.service.PortfolioService;
import com.kenzie.appserver.service.PurchaseStockService;
import com.kenzie.appserver.service.StockService;
import com.kenzie.appserver.service.model.Portfolio;
import com.kenzie.appserver.service.model.Stock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.InsufficientResourcesException;
import java.net.URI;
import java.util.HashMap;

@RestController
@RequestMapping("/purchasedstocks")
public class PurchasedStockController {

    private PurchaseStockService purchaseStockService;
    private StockService stockService;
    private PortfolioService portfolioService;
    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping
    public ResponseEntity<PurchasedStockResponse> purchaseStock(
            @RequestBody PurchaseStockRequest purchasedStockRequest) throws InsufficientResourcesException {
        String name = stockService.getStockNameBySymbol(purchasedStockRequest.getStockSymbol());

        Stock stock = new Stock(purchasedStockRequest.getStockSymbol(),
                name,
                purchasedStockRequest.getPurchasePrice(),
                purchasedStockRequest.getShares(),
                purchasedStockRequest.getPurchaseDate());
        //purchaseStockService.purchaseStock(purchasedStockRequest.getUserId(), stock);

        PurchasedStockResponse purchasedStockResponse = new PurchasedStockResponse();
        purchasedStockResponse.setUserId(purchasedStockRequest.getUserId());
        purchasedStockResponse.setSymbol(stock.getSymbol());
        purchasedStockResponse.setName(name);
        purchasedStockResponse.setPurchasePrice(stock.getPurchasePrice());
        purchasedStockResponse.setShares(stock.getQuantity());
        purchasedStockResponse.setTotalPaid((stock.getPurchasePrice()*stock.getQuantity()));
        purchasedStockResponse.setPurchaseDate(stock.getPurchaseDate());
        purchasedStockResponse.setOrderedDate(purchasedStockRequest.getOrderDate());

        HashMap<String, AttributeValue> keyToGet = new HashMap<String, AttributeValue>();
        keyToGet.put("id", new AttributeValue(purchasedStockRequest.getUserId()));
        keyToGet.put("symbol", new AttributeValue(purchasedStockRequest.getStockSymbol()));
        keyToGet.put("purchasePrice", new AttributeValue().withN(Double.toString(purchasedStockRequest.getPurchasePrice())));
        keyToGet.put("quantity", new AttributeValue().withN(Integer.toString(purchasedStockRequest.getShares())));
        keyToGet.put("purchaseDate", new AttributeValue(purchasedStockRequest.getPurchaseDate()));
        client.putItem("Portfolio", keyToGet);

        return ResponseEntity.created(URI.create("/purchasedstocks/" + purchasedStockResponse.getUserId())).body(purchasedStockResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Portfolio> getPortfolioByUserId(@PathVariable("userId") String userId) {
        Portfolio portfolio = new Portfolio();
        portfolio.setUserId(userId);
        portfolio.setFunds(10000.00);
        portfolio.addStock(new Stock("AAPL", "Apple", 100.00, 10, "2020-01-01"));

        return ResponseEntity.ok(portfolio);
//        DynamoDBMapper mapper = new DynamoDBMapper(client);
//        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
//        PaginatedScanList<Stock> orderList = mapper.scan(Stock.class, scanExpression);
//        return ResponseEntity.ok(orderList);
    }
}
