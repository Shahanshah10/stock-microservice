package com.learn.usbank.resolver;

import com.learn.usbank.openapi.model.Stock;
import com.learn.usbank.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StockResolver {

    private final StockService stockService;

    @QueryMapping
    public List<Stock> retrieveStocks(@Argument String currency) {
        return stockService.retrieveStocks(currency);
    }
}
