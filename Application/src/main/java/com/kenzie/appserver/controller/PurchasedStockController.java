package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.PurchaseStockRequest;
import com.kenzie.appserver.controller.model.PurchasedStockResponse;
import com.kenzie.appserver.service.PurchaseStockService;
import com.kenzie.appserver.service.StockService;
import com.kenzie.appserver.service.model.Stock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.InsufficientResourcesException;
import java.net.URI;

@RestController
@RequestMapping("/purchasedstocks")
public class PurchasedStockController {

    private PurchaseStockService purchaseStockService;
    private StockService stockService;

    PurchasedStockController(PurchaseStockService purchaseStockService, StockService stockService) {
        this.purchaseStockService = purchaseStockService;
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<PurchasedStockResponse> purchaseStock(
            @RequestBody PurchaseStockRequest purchasedStockRequest) throws InsufficientResourcesException {
        String name = stockService.getStockNameBySymbol(purchasedStockRequest.getStockSymbol());
        Stock stock = new Stock(purchasedStockRequest.getStockSymbol(),
                name,
                purchasedStockRequest.getPurchasePrice(),
                purchasedStockRequest.getShares(),
                purchasedStockRequest.getPurchaseDate());
        purchaseStockService.purchaseStock(purchasedStockRequest.getUserId(), stock);

        PurchasedStockResponse purchasedStockResponse = new PurchasedStockResponse();
        purchasedStockResponse.setUserId(purchasedStockRequest.getUserId());
        purchasedStockResponse.setSymbol(stock.getSymbol());
        purchasedStockResponse.setName(name);
        purchasedStockResponse.setPurchasePrice(stock.getPurchasePrice());
        purchasedStockResponse.setShares(stock.getQuantity());
        purchasedStockResponse.setTotalPaid((stock.getPurchasePrice()*stock.getQuantity()));
        purchasedStockResponse.setPurchaseDate(stock.getPurchaseDate());
        purchasedStockResponse.setOrderedDate(purchasedStockRequest.getOrderDate());

        return ResponseEntity.created(URI.create("/purchase/" + purchasedStockResponse.getUserId())).body(purchasedStockResponse);
    }
}
