package com.virtualcave.exercise.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRateRequest {

    private int price;

    private int brandId;

    private int productId;

    private String currencyCode;

    private Date endDate;

    private Date startDate;

}
