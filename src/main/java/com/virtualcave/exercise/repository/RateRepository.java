package com.virtualcave.exercise.repository;

import com.virtualcave.exercise.entities.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface RateRepository extends JpaRepository<RateEntity, Integer> {
    @Modifying
    @Query("update RateEntity r set r.price = :price where r.id = :id")
    void updatePrice(@Param(value = "id") long id, @Param(value = "price") int price);


    Optional<RateEntity> findByStartDateAfterAndEndDateBeforeAndProductIdAndBrandId(Date startDate, Date endDate, int productId, int brandId);

}
