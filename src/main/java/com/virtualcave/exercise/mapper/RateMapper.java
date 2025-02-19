package com.virtualcave.exercise.mapper;

import com.virtualcave.exercise.entities.RateEntity;
import com.virtualcave.exercise.model.CreateRateRequest;
import com.virtualcave.exercise.model.RateModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface RateMapper {

    RateMapper INSTANCE = Mappers.getMapper(RateMapper.class);

    RateEntity fromCreateRequestToEntity(CreateRateRequest request);

    RateModel fromEntityToModel(RateEntity entity);

    RateEntity fromModelToEntity(RateModel model);
}
