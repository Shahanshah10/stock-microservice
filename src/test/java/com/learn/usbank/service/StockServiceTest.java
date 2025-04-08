package com.learn.usbank.service;

import com.learn.usbank.exception.StockException;
import com.learn.usbank.exception.StockNotFoundException;
import com.learn.usbank.mapper.StockMapper;
import com.learn.usbank.openapi.model.Stock;
import com.learn.usbank.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @InjectMocks
    private StockService stockService;
    @Mock
    private StockMapper stockMapper;
    @Mock
    private StockRepository stockRepository;

    @Test
    void addStock() {
        when(stockRepository.save(any())).thenReturn(mockEntityStock());
        when(stockMapper.toMapEntityStock(any())).thenReturn(mockEntityStock());
        when(stockMapper.toMapApiStock(any())).thenReturn(mockApiStock());
        Stock stock = stockService.addStock(mockApiStock());
        assertEquals("ALPHA", stock.getSymbol());
    }

    @Test
    void addStockException() {
        when(stockRepository.save(any())).thenThrow(new RuntimeException("Failed"));
        StockException stockException = assertThrows(StockException.class, () -> stockService.addStock(mockApiStock()));
        assertEquals("Exception occurred while saving Stock :Failed", stockException.getMessage());
    }

    @Test
    void retrieveStock() {
        when(stockRepository.findById(any())).thenReturn(Optional.of(mockEntityStock()));
        when(stockMapper.toMapApiStock(any())).thenReturn(mockApiStock());
        Stock stock = stockService.retrieveStock("ALPHA");
        assertEquals("ALPHA", stock.getSymbol());
    }

    @Test
    void retrieveStockException() {
        when(stockRepository.findById(any())).thenReturn(Optional.of(mockEntityStock()));
        Exception exception = assertThrows(StockNotFoundException.class, () -> stockService.retrieveStock("ALPHA"));
        assertEquals("No stock is available for the given symbol : ALPHA", exception.getMessage());
    }

    @Test
    void removeStock() {
        when(stockRepository.findById(any())).thenReturn(Optional.of(mockEntityStock()));
        String response = stockService.removeStock("ALPHA");
        assertEquals("Stock has been successfully deleted from the system.", response);
    }

    @Test
    void removeStockException() {
        when(stockRepository.findById(any())).thenReturn(Optional.empty());
        Exception exception = assertThrows(StockNotFoundException.class, () -> stockService.removeStock("ALPHA"));
        assertEquals("No stock is available for the given symbol : ALPHA", exception.getMessage());
    }

    @Test
    void updateStock() {
        when(stockRepository.findById(anyString())).thenReturn(Optional.of(mockEntityStock()));
        when(stockMapper.toMapEntityStock(any())).thenReturn(mockEntityStock());
        when(stockMapper.toMapApiStock(any())).thenReturn(mockApiStock());
        Stock stock = stockService.updateStock("ALPHA", mockApiUpdatedStock());
        assertEquals(12.11, stock.getPrice());
    }

    @Test
    void updateStockNotFound() {
        when(stockRepository.findById(anyString())).thenReturn(Optional.empty());
        Exception exception = assertThrows(StockNotFoundException.class,
                () -> stockService.updateStock("ALPHA", mockApiUpdatedStock())
        );
        assertEquals("No stock is available for the given symbol : ALPHA", exception.getMessage());
    }

    @Test
    void retrieveStocksINR() {
        when(stockRepository.findAll()).thenReturn(List.of(mockEntityStock()));
        List<Stock> stocks = stockService.retrieveStocks("INR");
        assertEquals(1,stocks.size());
    }

    @Test
    void retrieveStocksEmpty() {
        when(stockRepository.findAll()).thenReturn(List.of(mockEntityStock()));
        List<Stock> stocks = stockService.retrieveStocks("");
        assertEquals(1,stocks.size());
    }

    @Test
    void retrieveStocks() {
        when(stockRepository.findAll()).thenReturn(List.of(mockEntityStock()));
        when(stockMapper.toMapApiStock(any())).thenReturn(mockApiStock());
        when(stockMapper.retrieveRate(anyString())).thenReturn(90.91);
        List<Stock> stocks = stockService.retrieveStocks("USD");
        assertEquals(1,stocks.size());
    }


    private Stock mockApiStock() {
        Stock stock = new Stock();
        stock.setSymbol("ALPHA");
        stock.setPrice(12.11);
        stock.setOpenPrice(13.33);
        stock.setClosePrice(12.33);
        return stock;
    }

    private Stock mockApiUpdatedStock() {
        Stock stock = new Stock();
        stock.setSymbol("ALPHA");
        stock.setPrice(12.15);
        stock.setOpenPrice(13.37);
        stock.setClosePrice(12.38);
        return stock;
    }

    private com.learn.usbank.entity.Stock mockEntityStock() {
        com.learn.usbank.entity.Stock stock = new com.learn.usbank.entity.Stock();
        stock.setSymbol("ALPHA");
        stock.setPrice(12.11);
        stock.setOpenPrice(13.33);
        stock.setClosePrice(12.33);

        return stock;
    }
}