package com.learn.usbank.controller;

import com.learn.usbank.openapi.api.StockApi;
import com.learn.usbank.openapi.model.Stock;
import com.learn.usbank.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

import static com.learn.usbank.constant.StackConstant.BASIC_URL;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BASIC_URL)
public class StockController implements StockApi {

    private final StockService stockService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return StockApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Stock> addStock(Stock stock) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(stockService.addStock(stock));
    }

    @Override
    public ResponseEntity<String> removeStock(String symbol) {
        return ResponseEntity.ok(stockService.removeStock(symbol));
    }

    @Override
    public ResponseEntity<Stock> retrieveStock(String symbol) {
        return ResponseEntity.ok(stockService.retrieveStock(symbol));
    }

    @Override
    public ResponseEntity<Stock> updateStock(String symbol, Stock updatedStock) {
        return ResponseEntity.ok(stockService.updateStock(symbol, updatedStock));
    }

    @Override
    public ResponseEntity<List<Stock>> retrieveStocks(String currency) {
        return ResponseEntity.ok(stockService.retrieveStocks(currency));
    }
}
