package com.virtualcave.exercise.controller;

import com.virtualcave.exercise.model.CreateRateRequest;
import com.virtualcave.exercise.model.RateModel;
import com.virtualcave.exercise.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Date;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rates")
public class RateController {

    private RateService service;

    @PostMapping("")
    public ResponseEntity<RateModel> createRate(@RequestBody CreateRateRequest rate) {
        return ResponseEntity.ok().body(service.createRate(rate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RateModel> getById(@PathVariable int id) {
        return ResponseEntity.ok().body(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePrice(@PathVariable int id, @RequestParam(value = "price") int price) {
        service.updatePrice(id, price);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable int id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<RateModel> getByDateProductAndBrand(@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                                              @RequestParam(value = "productId") int productId,
                                                              @RequestParam(value = "brandId") int brandId) {
        return ResponseEntity.ok().body(service.getByDateProductAndBrand(date, productId, brandId));
    }

}
