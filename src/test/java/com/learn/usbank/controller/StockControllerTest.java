package com.learn.usbank.controller;

import com.learn.usbank.openapi.model.Stock;
import com.learn.usbank.service.StockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StockControllerTest {

    @InjectMocks
    private StockController stockController;
    @Mock
    private StockService stockService;

    @Test
    void getRequest() {
        Optional<NativeWebRequest> request = stockController.getRequest();
        assertNotNull(request);
    }

    @Test
    void addStock() {
        when(stockService.addStock(any())).thenReturn(mockStock());
        ResponseEntity<Stock> stockResponseEntity = stockController.addStock(mockStock());
        assertEquals(201, stockResponseEntity.getStatusCode().value());
    }

    @Test
    void removeStock() {
        when(stockService.removeStock(anyString())).thenReturn("Successfully Stock deleted!!");
        ResponseEntity<String> responseEntity = stockController.removeStock("ALPHA");
        assertAll(() -> {
            assertEquals(200, responseEntity.getStatusCode().value());
            assertEquals("Successfully Stock deleted!!", responseEntity.getBody());
        });
    }

    @Test
    void retrieveStock() {
        when(stockService.retrieveStock(anyString())).thenReturn(mockStock());
        ResponseEntity<Stock> stockResponseEntity = stockController.retrieveStock("ALPHA");
        assertAll(() -> {
            assertEquals(200, stockResponseEntity.getStatusCode().value());
            assertEquals("ALPHA", Objects.requireNonNull(stockResponseEntity.getBody()).getSymbol());
        });
    }

    @Test
    void updateStock() {
        when(stockService.updateStock(anyString(), any())).thenReturn(mockStock());
        ResponseEntity<Stock> stockResponseEntity = stockController.updateStock("ALPHA", mockStock());
        assertAll(() -> {
            assertEquals(200, stockResponseEntity.getStatusCode().value());
            assertEquals("ALPHA", Objects.requireNonNull(stockResponseEntity.getBody()).getSymbol());
        });
    }

    @Test
    void retrieveStocks() {
        when(stockService.retrieveStocks(anyString())).thenReturn(List.of(mockStock()));
        ResponseEntity<List<Stock>> stockResponseEntity = stockController.retrieveStocks("ALPHA");
        assertAll(() -> {
            assertEquals(200, stockResponseEntity.getStatusCode().value());
            assertEquals("ALPHA", Objects.requireNonNull(stockResponseEntity.getBody()).get(0).getSymbol());
        });
    }

    private Stock mockStock() {
        Stock stock = new Stock();
        stock.setSymbol("ALPHA");
        return stock;
    }
}
