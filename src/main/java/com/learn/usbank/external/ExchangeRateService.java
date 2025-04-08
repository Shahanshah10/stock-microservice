package com.learn.usbank.external;

import com.learn.usbank.dto.ExchangeRateResponse;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ExchangeRateService {

    private final String exchangeUrl;
    private final RestTemplate restTemplate;

    public ExchangeRateService(@Value("${exchange-service.url}") String exchangeUrl, RestTemplate restTemplate) {
        this.exchangeUrl = exchangeUrl;
        this.restTemplate = restTemplate;
    }

    public double retrieveExchangeRate(String from, String to) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        Map<String, String> params = new HashMap<>();
        params.put("from", from);
        params.put("to", to);
        ResponseEntity<ExchangeRateResponse> response = restTemplate.exchange(
                exchangeUrl,
                HttpMethod.GET,
                entity,
                ExchangeRateResponse.class,
                params
        );
        return Objects.nonNull(response.getBody()) && MapUtils.isNotEmpty(response.getBody().getRates())
                ? response.getBody().getRates().get(to) : 83.02;
    }
}
