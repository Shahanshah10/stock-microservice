package com.learn.usbank.resolver;

import com.learn.usbank.openapi.model.Stock;
import com.learn.usbank.service.StockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockResolverTest {

    @InjectMocks
    private StockResolver stockResolver;
    @Mock
    private StockService stockService;

    @Test
    void retrieveStocks() {
        when(stockService.retrieveStocks(anyString()))
                .thenReturn(mockApiStocks());
        List<Stock> stocks = stockResolver.retrieveStocks("INR");
        assertEquals(1,stocks.size());
    }

    private List<Stock> mockApiStocks() {
        Stock stock = new Stock();
        stock.setSymbol("ALPHA");
        stock.setPrice(12.11);
        stock.setOpenPrice(13.33);
        stock.setClosePrice(12.33);
        return List.of(stock);
    }
}