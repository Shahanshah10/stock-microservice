package com.learn.usbank.controller;

import com.learn.usbank.config.StockExceptionHandler;
import com.learn.usbank.dto.ErrorResponse;
import com.learn.usbank.exception.StockException;
import com.learn.usbank.exception.StockNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class StockExceptionHandlerTest {

    @InjectMocks
    private StockExceptionHandler stockExceptionHandler;

    @Test
    void handleStockAlreadyExistsException() {
        ResponseEntity<ErrorResponse> response = stockExceptionHandler.handleStockAlreadyExistsException(new StockException("Bad Request"));
        assertAll(()->{
            assertEquals(400, response.getStatusCode().value());
            assertEquals("Bad Request", Objects.requireNonNull(response.getBody()).message());
        });
    }

    @Test
    void handleStockNotFoundException() {
        ResponseEntity<ErrorResponse> response = stockExceptionHandler.handleStockNotFoundException(new StockNotFoundException("Stock Not Found"));
        assertAll(()->{
            assertEquals(404, response.getStatusCode().value());
            assertEquals("Stock Not Found", Objects.requireNonNull(response.getBody()).message());
        });
    }

    @Test
    void handleException() {
        ResponseEntity<ErrorResponse> response = stockExceptionHandler.handleException(new StockNotFoundException("Stock Not Found"));
        assertAll(()->{
            assertEquals(500, response.getStatusCode().value());
            assertEquals("Stock Not Found", Objects.requireNonNull(response.getBody()).message());
        });
    }
}