package com.learn.usbank.external;

import com.learn.usbank.dto.ExchangeRateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceTest {

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(exchangeRateService, "exchangeUrl", "http://localhost:8080/test");
    }

    @Test
    void testRetrieveExchangeRate() {
        ExchangeRateResponse response = new ExchangeRateResponse();
        Map<String, Double> rates = new HashMap<>();
        rates.put("INR", 83.50);
        response.setRates(rates);
        ResponseEntity<ExchangeRateResponse> mockResponse =
                new ResponseEntity<>(response, HttpStatus.OK);
        when(restTemplate.exchange(
                eq("http://localhost:8080/test"),
                eq(HttpMethod.GET),
                ArgumentMatchers.<HttpEntity<String>>any(),
                eq(ExchangeRateResponse.class),
                ArgumentMatchers.<Map<String, String>>any())
        ).thenReturn(mockResponse);
        double actualRate = exchangeRateService.retrieveExchangeRate("USD", "INR");
        assertEquals(83.50, actualRate);
    }
}