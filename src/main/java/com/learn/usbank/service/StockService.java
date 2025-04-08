package com.learn.usbank.service;

import com.learn.usbank.exception.StockException;
import com.learn.usbank.exception.StockNotFoundException;
import com.learn.usbank.mapper.StockMapper;
import com.learn.usbank.openapi.model.Stock;
import com.learn.usbank.repository.StockRepository;
import com.learn.usbank.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.learn.usbank.constant.StackConstant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    private final StockMapper stockMapper;
    private final StockRepository stockRepository;

    public Stock addStock(Stock stock) {
        try {
            com.learn.usbank.entity.Stock saveStock = stockRepository.save(stockMapper.toMapEntityStock(stock));
            return stockMapper.toMapApiStock(saveStock);
        } catch (Exception exception) {
            log.error(STOCK_EXCEPTION_MESSAGE_PLACEHOLDER, exception);
            throw new StockException(STOCK_EXCEPTION_MESSAGE + exception.getMessage());
        }
    }

    public Stock retrieveStock(String symbol) {
        Optional<com.learn.usbank.entity.Stock> stockOptional = stockRepository.findById(symbol);
        return stockOptional.map(stockMapper::toMapApiStock).orElseThrow(() -> new StockNotFoundException(STOCK_NOT_FOUND_MESSAGE + symbol));
    }

    public String removeStock(String symbol) {
        if (stockRepository.findById(symbol).isPresent()) {
            stockRepository.deleteById(symbol);
            return SUCCESSFULLY_DELETED_MESSAGE;
        }
        throw new StockNotFoundException(STOCK_NOT_FOUND_MESSAGE + symbol);
    }

    public Stock updateStock(String symbol, Stock updatedStock) {
        Optional<com.learn.usbank.entity.Stock> stockOptional = stockRepository.findById(symbol);
        if (stockOptional.isEmpty()) {
            throw new StockNotFoundException(STOCK_NOT_FOUND_MESSAGE + symbol);
        }
        com.learn.usbank.entity.Stock mapEntityStock = stockMapper.toMapEntityStock(updatedStock);
        com.learn.usbank.entity.Stock stock = stockOptional.get();
        MappingUtils.updateTargetFieldWithSourceField(mapEntityStock, stock);
        return stockMapper.toMapApiStock(stockRepository.save(stock));
    }

    public List<Stock> retrieveStocks(String currency) {
        List<com.learn.usbank.entity.Stock> stockList = stockRepository.findAll();
        if (StringUtils.isBlank(currency) || INR.equalsIgnoreCase(currency)) {
            return stockList.stream().map(stockMapper::toMapApiStock).collect(Collectors.toList());
        }
        return stockList.stream().map(stock -> {
            Stock mapped = stockMapper.toMapApiStock(stock);
            double rate = stockMapper.retrieveRate(currency);
            mapped.setPrice(mapped.getPrice() * rate);
            mapped.setOpenPrice(mapped.getOpenPrice() * rate);
            mapped.setClosePrice(mapped.getClosePrice() * rate);
            mapped.setCurrency(currency);
            return mapped;
        }).toList();
    }
}
