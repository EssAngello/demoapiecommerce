package com.example.onebox.config.mapper;

import com.example.onebox.cart.model.CartDto;
import com.example.onebox.cart.model.CartEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartEntityMapper {
    CartEntity toEntity(CartDto cartDto);

    CartDto toDto(CartEntity cartEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "audit", ignore = true)
    CartEntity partialUpdate(CartDto cartDto, @MappingTarget CartEntity cartEntity);
}
