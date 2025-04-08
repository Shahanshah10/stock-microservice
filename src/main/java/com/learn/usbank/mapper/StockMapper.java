package com.learn.usbank.mapper;


import com.learn.usbank.entity.Stock;
import com.learn.usbank.external.ExchangeRateService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.learn.usbank.constant.StackConstant.DEFAULT_ZONE_ID;
import static com.learn.usbank.constant.StackConstant.INR;

@Mapper(componentModel = "spring", uses = ExchangeRateService.class)
public abstract class StockMapper {

    @Autowired
    protected ExchangeRateService exchangeRateService;

    double convertIntoRupees(double price, String from) {
        return INR.equalsIgnoreCase(from) ? price : price * exchangeRateService.retrieveExchangeRate(from, INR);
    }

    String lastUpdatedDate() {
        return LocalDateTime.now(ZoneId.of(DEFAULT_ZONE_ID)).toString();
    }

    public double retrieveRate(String to) {
        return exchangeRateService.retrieveExchangeRate(INR, to);
    }

    @Mapping(target = "currency", constant = INR)
    @Mapping(target = "price", expression = "java(convertIntoRupees(stock.getPrice(), stock.getCurrency()))")
    @Mapping(target = "openPrice", expression = "java(convertIntoRupees(stock.getOpenPrice(), stock.getCurrency()))")
    @Mapping(target = "closePrice", expression = "java(convertIntoRupees(stock.getClosePrice(), stock.getCurrency()))")
    @Mapping(target = "active", source = "active", defaultValue = "true")
    @Mapping(target = "lastUpdatedDate", expression = "java(lastUpdatedDate())")
    public abstract Stock toMapEntityStock(com.learn.usbank.openapi.model.Stock stock);

    public abstract com.learn.usbank.openapi.model.Stock toMapApiStock(Stock stock);

    public abstract List<com.learn.usbank.openapi.model.Stock> toMapStockList(List<Stock> stocks);

}
