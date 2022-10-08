package com.kenzie.appserver.controller;

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

@RestController
@RequestMapping("/purchasedstocks")
public class PurchasedStockController {

    private PurchaseStockService purchaseStockService;
    private StockService stockService;
    private PortfolioService portfolioService;

    PurchasedStockController(PurchaseStockService purchaseStockService,
                             StockService stockService,
                             PortfolioService portfolioService) {
        this.purchaseStockService = purchaseStockService;
        this.stockService = stockService;
        this.portfolioService = portfolioService;
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

    @GetMapping("/portfolio/{userId}")
    public ResponseEntity<Portfolio> getPortfolioByUserId(@PathVariable("userId") String userId) {
        Portfolio portfolio = portfolioService.findPortfolioByUserId(userId);

        if (portfolio == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(portfolio);
    }
}
