package com.virtualcave.exercise.service;

import com.virtualcave.exercise.entities.RateEntity;
import com.virtualcave.exercise.model.CreateRateRequest;
import com.virtualcave.exercise.model.Currency;
import com.virtualcave.exercise.model.RateModel;
import com.virtualcave.exercise.repository.RateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class RateServiceImplTest {

    @InjectMocks
    private RateServiceImpl rateService;

    @Mock
    private RateRepository rateRepository;

    @Mock
    private CurrencyServiceIml currencyService;

    @Test
    void createRateTest() {

        int id = 123;
        CreateRateRequest request = new CreateRateRequest();
        request.setBrandId(1);
        request.setPrice(2);
        request.setProductId(3);
        request.setCurrencyCode("EUR");
        Date endDate = Date.from(Instant.now());
        Date startDate = Date.from(Instant.now());
        request.setEndDate(endDate);
        request.setStartDate(startDate);

        RateEntity savedRate = new RateEntity();
        savedRate.setId(id);

        when(rateRepository.save(any(RateEntity.class))).thenReturn(savedRate);

        RateModel createdRate = rateService.createRate(request);

        assertEquals(id, createdRate.getId());
    }

    @Test
    void getByIdSuccessTest() {
        int id = 123;
        RateEntity savedRate = new RateEntity();
        savedRate.setCurrencyCode("EUR");
        savedRate.setId(id);
        savedRate.setPrice(1200);
        Currency c = new Currency();
        c.setCode("EUR");
        c.setDecimals(2);
        c.setSymbol("€");

        when(rateRepository.findById(id)).thenReturn(Optional.of(savedRate));
        when(currencyService.getCurrencyByCode("EUR")).thenReturn(c);
        RateModel readRate = rateService.getById(id);

        assertEquals("12,00", readRate.getPrice());
        assertEquals(id, readRate.getId());

    }

    @Test
    void getByIdNotFoundTest() {

        int id = 123;
        RateEntity savedRate = new RateEntity();
        savedRate.setCurrencyCode("EUR");
        savedRate.setId(id);

        when(rateRepository.findById(id)).thenReturn(Optional.empty());
        Exception ex = assertThrows(NoSuchElementException.class, () -> rateService.getById(id));

        assertEquals("No rate found with this id!", ex.getMessage());

        verify(currencyService, never()).getCurrencyByCode(anyString());

    }

    @Test
    void updatePriceSuccessTest() {
        int id = 123;
        int price = 1234;

        rateService.updatePrice(id, price);

        verify(rateRepository).updatePrice(id, price);
    }

    @Test
    void deleteSuccessTest() {
        int id = 123;

        rateService.deleteById(id);

        verify(rateRepository).deleteById(id);
    }

    @Test
    void getWithFiltersSuccessTest() throws ParseException {
        RateEntity savedRate = new RateEntity();
        savedRate.setCurrencyCode("EUR");
        savedRate.setId(123);
        savedRate.setPrice(1200);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = Date.from(df.parse("2025-02-19").toInstant());
        int productId = 2;
        int brandId = 3;
        Currency c = new Currency();
        c.setCode("EUR");
        c.setDecimals(2);
        c.setSymbol("€");

        when(rateRepository.findByStartDateAfterAndEndDateBeforeAndProductIdAndBrandId(date, date, productId, brandId))
                .thenReturn(Optional.of(savedRate));
        when(currencyService.getCurrencyByCode("EUR")).thenReturn(c);
        RateModel readRate = rateService.getByDateProductAndBrand(date, productId, brandId);

        assertEquals(123, readRate.getId());
        assertEquals("12,00", readRate.getPrice());
    }

}
