package com.virtualcave.exercise.service;

import com.virtualcave.exercise.model.CreateRateRequest;
import com.virtualcave.exercise.model.RateModel;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface RateService {

    RateModel createRate(CreateRateRequest request);

    RateModel getById(int id);

    void updatePrice(int id, int price);

    void deleteById(int id);

    RateModel getByDateProductAndBrand(Date date, int productId, int brandId);
}
