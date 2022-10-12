package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.PurchaseStockRequest;
import com.kenzie.appserver.controller.model.PurchasedStockResponse;
import com.kenzie.appserver.repositories.PortfolioRepository;
import com.kenzie.appserver.repositories.model.PortfolioRecord;
import com.kenzie.appserver.service.PurchaseStockService;
import com.kenzie.appserver.service.model.PurchasedStock;
import com.kenzie.appserver.service.model.Stock;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.naming.InsufficientResourcesException;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class PurchasedStockControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private PurchaseStockService purchaseStockService;

    private PortfolioRepository portfolioRepository;

    private final MockNeat mockNeat = MockNeat.threadLocal();
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        portfolioRepository = mock(PortfolioRepository.class);

    }

    @Test
    public void purchaseStock() throws Exception {
        //GIVEN
        Stock stock = new Stock("amzn",
                mockNeat.strings().get(),
                110.00,
                ZonedDateTime.now());

        PurchaseStockRequest purchaseStockRequest = new PurchaseStockRequest();
        purchaseStockRequest.setUserId(mockNeat.strings().get());
        purchaseStockRequest.setStockSymbol(stock.getSymbol());
        purchaseStockRequest.setPurchasePrice(stock.getPurchasePrice());
        purchaseStockRequest.setShares(3);
        purchaseStockRequest.setPurchaseDate(stock.getPurchaseDate());
        purchaseStockRequest.setOrderDate(ZonedDateTime.now());

        PortfolioRecord portfolioRecord = new PortfolioRecord();
        portfolioRecord.setUserId(purchaseStockRequest.getUserId());

        when(portfolioRepository.findByUserId(any())).thenReturn(portfolioRecord);

        PurchasedStock purchasedStock = purchaseStockService.purchaseStock(purchaseStockRequest.getUserId(), stock);

        //WHEN
        ResultActions actions = mvc.perform(post("/purchasedstocks")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(purchaseStockRequest)))
        //THEN
                    .andExpect(status().isCreated());
        String responseBody = actions.andReturn().getResponse().getContentAsString();
        PurchasedStockResponse purchasedStockResponse = mapper.readValue(responseBody, PurchasedStockResponse.class);
        assertThat(purchasedStockResponse.getUserId())
                .isEqualTo(purchasedStock.getUserId());
        assertThat(purchasedStockResponse.getName())
                .isEqualTo(stock.getName());
        assertThat(purchasedStockResponse.getPurchasePrice())
                .isEqualTo(stock.getPurchasePrice());
    }

    @Test
    public void purchaseStock_insufficientFunds_throwsInsufficientResourcesException() throws Exception {
        //GIVEN
        Stock stock = new Stock("amzn",
                mockNeat.strings().get(),
                110000.00,
                ZonedDateTime.now());

        PurchaseStockRequest purchaseStockRequest = new PurchaseStockRequest();
        purchaseStockRequest.setUserId(mockNeat.strings().get());
        purchaseStockRequest.setStockSymbol(stock.getSymbol());
        purchaseStockRequest.setPurchasePrice(stock.getPurchasePrice());
        purchaseStockRequest.setShares(3);
        purchaseStockRequest.setPurchaseDate(stock.getPurchaseDate());
        purchaseStockRequest.setOrderDate(ZonedDateTime.now());

        PortfolioRecord portfolioRecord = new PortfolioRecord();
        portfolioRecord.setUserId(purchaseStockRequest.getUserId());

        when(portfolioRepository.findByUserId(any())).thenReturn(portfolioRecord);

        purchaseStockService.purchaseStock(purchaseStockRequest.getUserId(), stock);

        //WHEN
        mvc.perform(post("/purchasedstocks", purchaseStockRequest)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(purchaseStockRequest)))
                //THEN
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof
                        InsufficientResourcesException))
                .andExpect(result -> assertEquals("Not enough available funds.",
                        result.getResolvedException().getMessage()));
    }
}
