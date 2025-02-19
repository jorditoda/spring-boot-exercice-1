package com.virtualcave.exercise.service;

import com.virtualcave.exercise.model.Currency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CurrencyServiceIml implements CurrencyService {

    //Those methods should call currency API using restTemplate or other mechanisms but I have created this way to simplify
    //the logic of mocking the currency api
    public List<Currency> getCurrencies() {
        return List.of(Currency.builder()
                        .code("EUR")
                        .decimals(2)
                        .symbol("€")
                        .build(),
                Currency.builder()
                        .code("USD")
                        .decimals(2)
                        .symbol("$")
                        .build());
    }

    public Currency getCurrencyByCode(String code) {
        if ("USD".equals(code)) {
            return Currency.builder()
                    .code("USD")
                    .decimals(2)
                    .symbol("$")
                    .build();
        }
        return Currency.builder()
                .code("EUR")
                .decimals(2)
                .symbol("€")
                .build();
    }
}
