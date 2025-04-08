package com.learn.usbank.mapper;

import com.learn.usbank.external.ExchangeRateService;
import com.learn.usbank.openapi.model.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockMapperTest {

    private ExchangeRateService exchangeRateService;
    private StockMapper stockMapper;

    @BeforeEach
    void init() {
        exchangeRateService = mock(ExchangeRateService.class);
        stockMapper = Mappers.getMapper(StockMapper.class);
        stockMapper.exchangeRateService = exchangeRateService;
    }

    @Test
    void testToMapEntityStock() {
        Stock apiStock = new Stock();
        apiStock.setStockId(BigDecimal.valueOf(1L));
        apiStock.setSymbol("AAPL");
        apiStock.setCompanyName("Apple Inc.");
        apiStock.setCurrency("USD");
        apiStock.setPrice(100.0);
        apiStock.setOpenPrice(90.0);
        apiStock.setClosePrice(95.0);
        apiStock.setActive(true);
        when(exchangeRateService.retrieveExchangeRate("USD", "INR")).thenReturn(80.0);
        com.learn.usbank.entity.Stock stock = stockMapper.toMapEntityStock(apiStock);
        assertAll(() -> {
            assertThat(stock).isNotNull();
            assertThat(stock.getPrice()).isEqualTo(8000.0);
            verify(exchangeRateService, times(3)).retrieveExchangeRate("USD", "INR");
        });
    }
}
