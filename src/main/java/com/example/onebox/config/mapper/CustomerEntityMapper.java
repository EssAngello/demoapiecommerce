package com.example.onebox.config.mapper;

import com.example.onebox.customer.model.CustomerDto;
import com.example.onebox.customer.model.CustomerEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerEntityMapper {
    CustomerEntity toEntity(CustomerDto customerDto);

    CustomerDto toDto(CustomerEntity customerEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "audit", ignore = true)
    CustomerEntity partialUpdate(CustomerDto customerDto, @MappingTarget CustomerEntity customerEntity);
}
