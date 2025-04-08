package com.learn.usbank.dto;

import lombok.Data;
import java.util.Map;

@Data
public class ExchangeRateResponse {

    private int amount;
    private String base;
    private String date;
    private Map<String, Double> rates;
}
