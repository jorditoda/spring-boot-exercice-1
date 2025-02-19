package com.virtualcave.exercise.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_RATE")
public class RateEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    int id;

    @Column(name = "BRAND_ID")
    int brandId;

    @Column(name = "PRODUCT_ID")
    int productId;

    @Column(name = "START_DATE")
    Date startDate;

    @Column(name = "END_DATE")
    Date endDate;

    @Column(name = "PRICE")
    int price;

    @Column(name = "CURRENCY_CODE")
    String currencyCode;

}

