package com.virtualcave.exercise.service;

import com.virtualcave.exercise.entities.RateEntity;
import com.virtualcave.exercise.mapper.RateMapper;
import com.virtualcave.exercise.model.CreateRateRequest;
import com.virtualcave.exercise.model.Currency;
import com.virtualcave.exercise.model.RateModel;
import com.virtualcave.exercise.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;

    private final CurrencyService currencyService;

    private final RateMapper rateMapper = RateMapper.INSTANCE;

    @Override
    public RateModel createRate(CreateRateRequest request) {

        var entity = rateRepository.save(rateMapper.fromCreateRequestToEntity(request));

        return rateMapper.fromEntityToModel(entity);
    }

    @Override
    public RateModel getById(int id) {
        var entity = rateRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("No rate found with this id!"));

        Currency c = callToCurrencyService(entity.getCurrencyCode());
        return updatePriceFromRate(entity, c);
    }


    @Override
    public void updatePrice(int id, int price) {
        rateRepository.updatePrice(id, price);
    }

    @Override
    public void deleteById(int id) {
        rateRepository.deleteById(id);
    }

    @Override
    public RateModel getByDateProductAndBrand(Date date, int productId, int brandId) {
        var entity = rateRepository.findByStartDateAfterAndEndDateBeforeAndProductIdAndBrandId(date, date, productId, brandId)
                .orElseThrow(() ->
                        new NoSuchElementException("No rate found with this values!"));

        Currency c = callToCurrencyService(entity.getCurrencyCode());
        return updatePriceFromRate(entity, c);
    }

    private Currency callToCurrencyService(String code) {
        return currencyService.getCurrencyByCode(code);
    }

    private RateModel updatePriceFromRate(RateEntity entity, Currency c) {
        var rate = rateMapper.fromEntityToModel(entity);
        rate.setCurrencySymbol(c.getSymbol());
        double value = entity.getPrice() / Math.pow(10, c.getDecimals());
        rate.setPrice(String.format("%." + c.getDecimals() + "f", value));
        return rate;
    }
}
