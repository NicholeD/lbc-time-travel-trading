package com.kenzie.appserver.service;

import com.kenzie.appserver.service.model.Portfolio;
import com.kenzie.appserver.service.model.Stock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.InsufficientResourcesException;
import java.time.ZonedDateTime;

import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PurchasedStockServiceTest {

    private PortfolioRepository portfolioRepository;
    private PortfolioService portfolioService;
    private PurchaseStockService purchaseStockService;

    @BeforeEach
    void setup() {
        this.portfolioRepository = mock(PortfolioRepository.class);
        this.portfolioService = new PortfolioService(portfolioRepository);
        this.purchaseStockService = new PurchaseStockService(portfolioService);

    }

    @Test
    void purchaseStock() throws InsufficientResourcesException {
        //GIVEN
        String userId = randomUUID().toString();
        Stock stock = new Stock("symbol", "name", 123.00, 1.00, ZonedDateTime.now());

        PurchasedStock purchasedStock = new PurchasedStock(userId, stock, ZonedDateTime.now());
        Portfolio portfolio = new Portfolio();
        portfolio.setUserId(userId);
        double newFunds = portfolio.getFunds() - (stock.getPurchasePrice() * stock.getQuantity());

        PortfolioRecord portfolioRecord = new PortfolioRecord();
        portfolioRecord.setFunds(portfolio.getFunds());
        portfolioRecord.setUserId(userId);
        when(portfolioRepository.findByUserId(any())).thenReturn(portfolioRecord);

        //WHEN
        PurchasedStock purchasedStock2 = purchaseStockService.purchaseStock(userId, stock);

        //THEN
        Assertions.assertEquals(purchasedStock.getStock(), purchasedStock2.getStock());
        Assertions.assertNotNull(purchasedStock2.getStock());
    }
}
