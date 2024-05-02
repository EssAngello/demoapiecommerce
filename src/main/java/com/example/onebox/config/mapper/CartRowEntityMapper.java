package com.example.onebox.config.mapper;

import com.example.onebox.cartrow.model.CartRowDto;
import com.example.onebox.cartrow.model.CartRowEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartRowEntityMapper {
    CartRowEntity toEntity(CartRowDto carRowtDto);

    CartRowDto toDto(CartRowEntity cartRowEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cartId", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "audit", ignore = true)
    CartRowEntity partialUpdate(CartRowDto cartRowDto, @MappingTarget CartRowEntity cartRowEntity);

}
