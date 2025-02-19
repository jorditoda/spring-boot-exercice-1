package com.virtualcave.exercise.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateModel {

    private int id;

    private String price;

    private int brandId;

    private int productId;

    private String currencyCode;

    private String currencySymbol;

    private Date endDate;

    private Date startDate;

}
