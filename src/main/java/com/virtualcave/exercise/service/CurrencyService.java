package com.virtualcave.exercise.service;

import com.virtualcave.exercise.model.Currency;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CurrencyService {

    List<Currency> getCurrencies();

    Currency getCurrencyByCode(String code);
}
