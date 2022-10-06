package com.kenzie.appserver.service;

import com.kenzie.appserver.service.model.Portfolio;
import com.kenzie.appserver.service.model.PurchasedStock;
import com.kenzie.appserver.service.model.Stock;
import org.springframework.stereotype.Service;

import javax.naming.InsufficientResourcesException;
import java.time.ZonedDateTime;

@Service
public class PurchaseStockService {

    private PortfolioService portfolioService;

    public PurchaseStockService(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    public PurchasedStock purchaseStock(String userId, Stock stock) throws InsufficientResourcesException {
        double transactionCost = stock.getQuantity()* stock.getPurchasePrice();
        Portfolio portfolio = portfolioService.findPortfolioByUserId(userId);

        if (portfolio.getFunds() > transactionCost) {
            portfolioService.updatePortfolio(portfolio, stock);
        } else {
            throw new InsufficientResourcesException("Not enough available funds.");
        }

        return new PurchasedStock(userId, stock, ZonedDateTime.now());
    }

}
