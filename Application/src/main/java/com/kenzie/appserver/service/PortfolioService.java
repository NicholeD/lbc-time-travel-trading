package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.PortfolioRepository;
import com.kenzie.appserver.repositories.model.PortfolioRecord;
import com.kenzie.appserver.service.model.Portfolio;
import com.kenzie.appserver.service.model.Stock;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {

    private PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public Portfolio findPortfolioByUserId(String userId) {
        PortfolioRecord portfolioRecord = portfolioRepository.findByUserId(userId);

        Portfolio portfolio = new Portfolio();
        portfolio.setFunds(portfolioRecord.getFunds());

        List<Stock> stocks = portfolioRecord.getStocks();
        for(Stock stock : stocks) {
            portfolio.addStock(stock);
        }

        return portfolio;
    }

    public Portfolio updatePortfolio(Portfolio portfolio, Stock stock) {
        double totalCost = stock.getPurchasePrice() * stock.getQuantity();
        double newFunds = portfolio.getFunds() - totalCost;
        PortfolioRecord portfolioRecord = new PortfolioRecord();
        portfolioRecord.setUserId(portfolio.getUserId());
        portfolioRecord.setFunds(portfolio.getFunds());
        portfolioRecord.addStock(stock);
        portfolioRecord.setFunds(newFunds);

        portfolioRepository.save(portfolioRecord);
        return portfolio;
    }
}
