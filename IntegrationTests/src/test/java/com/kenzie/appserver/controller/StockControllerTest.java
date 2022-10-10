package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.SearchStockResponse;
import com.kenzie.appserver.service.StockService;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class StockControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private StockService stockService;

    private final MockNeat mockNeat = MockNeat.threadLocal();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getStocksBySymbol_ReturnsStocks() throws Exception {
        //GIVEN
        String symbol = "amzn";

        //WHEN
        mvc.perform(get("/stocks/{symbol}", symbol)
                .accept(MediaType.APPLICATION_JSON))
        //THEN
                .andExpect(jsonPath("symbol")
                        .value(symbol))
                        .andExpect(status().isOk());
    }
    
}
