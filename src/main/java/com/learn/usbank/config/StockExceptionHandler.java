package com.learn.usbank.config;

import com.learn.usbank.dto.ErrorResponse;
import com.learn.usbank.exception.StockException;
import com.learn.usbank.exception.StockNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.learn.usbank.config")
public class StockExceptionHandler {

    @ExceptionHandler(value = StockException.class)
    public ResponseEntity<ErrorResponse> handleStockAlreadyExistsException(StockException stockException) {
        log.error("{}:{}", HttpStatus.BAD_REQUEST, stockException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder().statusCode(HttpStatus.BAD_REQUEST.value())
                        .message(stockException.getMessage()).build());
    }

    @ExceptionHandler(value = StockNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStockNotFoundException(StockNotFoundException stockNotFoundException) {
        log.error("{}:{}", HttpStatus.NOT_FOUND, stockNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder().statusCode(HttpStatus.NOT_FOUND.value())
                        .message(stockNotFoundException.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }

}