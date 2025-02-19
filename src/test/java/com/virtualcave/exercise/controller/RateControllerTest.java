package com.virtualcave.exercise.controller;

import com.virtualcave.exercise.exceptions.GlobalExceptionHandler;
import com.virtualcave.exercise.model.CreateRateRequest;
import com.virtualcave.exercise.model.RateModel;
import com.virtualcave.exercise.service.RateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.NoSuchElementException;

import static com.virtualcave.exercise.TestHelper.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class RateControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RateController rateController;

    @Mock
    private RateServiceImpl rateService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(rateController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createRateTest() throws Exception {
        CreateRateRequest request = new CreateRateRequest();
        request.setBrandId(1);
        request.setPrice(2);
        request.setProductId(3);
        request.setCurrencyCode("EUR");
        Date endDate = Date.from(Instant.now());
        Date startDate = Date.from(Instant.now());
        request.setEndDate(endDate);
        request.setStartDate(startDate);
        RateModel rateModel = RateModel.builder()
                .id(123).build();

        when(rateService.createRate(request)).thenReturn(rateModel);
        String jsonRequest = asJsonString(request);

        mockMvc.perform(post("/api/v1/rates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(rateModel)));
    }

    @Test
    void getByIdSuccessTest() throws Exception {
        RateModel rateModel = RateModel.builder()
                .id(123).build();

        when(rateService.getById(123)).thenReturn(rateModel);

        mockMvc.perform(get("/api/v1/rates/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(rateModel)));
    }

    @Test
    void getByIdNotFoundTest() throws Exception {

        when(rateService.getById(123)).thenThrow(new NoSuchElementException("No rate found with this id!"));

        mockMvc.perform(get("/api/v1/rates/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No rate found with this id!"));
    }

    @Test
    void updatePriceSuccessTest() throws Exception {

        mockMvc.perform(put("/api/v1/rates/123")
                        .param("price", "1234")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(rateService).updatePrice(123, 1234);
    }


    @Test
    void deleteSuccessTest() throws Exception {

        mockMvc.perform(delete("/api/v1/rates/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(rateService).deleteById(123);
    }

    @Test
    void getWithFiltersSuccessTest() throws Exception {
        int productId = 2;
        int brandId = 3;
        Date date = Date.from(Instant.now());
        RateModel rateModel = RateModel.builder()
                .id(123)
                .build();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(date);

        when(rateService.getByDateProductAndBrand(Date.from(df.parse(formattedDate).toInstant()), productId, brandId)).thenReturn(rateModel);

        mockMvc.perform(get("/api/v1/rates")
                        .param("date", formattedDate)
                        .param("productId", String.valueOf(productId))
                        .param("brandId", String.valueOf(brandId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(rateModel)));
    }

}
