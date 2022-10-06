package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.PortfolioRepository;
import com.kenzie.appserver.repositories.model.PortfolioRecord;
import com.kenzie.appserver.service.model.Portfolio;
import com.kenzie.appserver.service.model.Stock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PortfolioServiceTest {

    private PortfolioRepository portfolioRepository;
    private PortfolioService portfolioService;

    @BeforeEach
    void setup() {
        portfolioRepository = mock(PortfolioRepository.class);
        portfolioService = new PortfolioService(portfolioRepository);
    }

    @Test
    void findPortfolioByUserId() {
        //GIVEN
        String userId = "abc123";
        Stock stock = new Stock("symbol", "name", 123.00, 1.0, ZonedDateTime.now());
        PortfolioRecord portfolioRecord = new PortfolioRecord();
        portfolioRecord.setUserId(userId);
        portfolioRecord.addStock(stock);

        when(portfolioRepository.findByUserId(any())).thenReturn(portfolioRecord);

        //WHEN
        Portfolio portfolio = portfolioService.findPortfolioByUserId(userId);

        //THEN
        Assertions.assertEquals(portfolio.getStocks(), portfolioRecord.getStocks());
    }

    @Test
    void updatePortfolio() {
        //GIVEN


        //WHEN


        //THEN

    }
}
