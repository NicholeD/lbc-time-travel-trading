package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.PortfolioRepository;
import com.kenzie.appserver.repositories.model.PortfolioRecord;
import com.kenzie.appserver.service.model.Portfolio;
import com.kenzie.appserver.service.model.PurchasedStock;
import com.kenzie.appserver.service.model.Stock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.InsufficientResourcesException;
import java.time.ZonedDateTime;

import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

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
        Stock stock = new Stock("symbol", "name", 123.00, 1, "ZonedDateTime.now()");

        PurchasedStock purchasedStock = new PurchasedStock(userId, stock, ZonedDateTime.now());
        Portfolio portfolio = new Portfolio();
        double newFunds = portfolio.getFunds() - (stock.getPurchasePrice() * stock.getQuantity());

        PortfolioRecord portfolioRecord = new PortfolioRecord();
        portfolioRecord.setFunds(portfolio.getFunds());
        portfolioRecord.setUserId(userId);
        when(portfolioRepository.findByUserId(any())).thenReturn(portfolioRecord);

        //WHEN
        PurchasedStock purchasedStock2 = purchaseStockService.purchaseStock(userId, stock);

        //THEN
        Assertions.assertEquals(purchasedStock2.getStock(), stock);
        Assertions.assertNotNull(purchasedStock2.getStock());
    }

    @Test
    void purchaseStock_InsufficientFunds_throwsException() throws InsufficientResourcesException {
        //GIVEN
        String userId = randomUUID().toString();
        Stock stock = new Stock("symbol", "name", 123.00, 1, "ZonedDateTime.now()");

        PurchasedStock purchasedStock = new PurchasedStock(userId, stock, ZonedDateTime.now());
        Portfolio portfolio = new Portfolio();
        portfolio.setUserId(userId);
        portfolio.setFunds(50.00);

        PortfolioRecord portfolioRecord = new PortfolioRecord();
        portfolioRecord.setFunds(portfolio.getFunds());
        portfolioRecord.setUserId(userId);
        when(portfolioRepository.findByUserId(any())).thenReturn(portfolioRecord);

        //WHEN & THEN
        Assertions.assertThrows(InsufficientResourcesException.class,
                () -> purchaseStockService.purchaseStock(userId, stock));
    }
}
