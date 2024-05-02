package com.example.onebox.config.mapper;

import com.example.onebox.product.model.ProductDto;
import com.example.onebox.product.model.ProductEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductEntityMapper {
    ProductEntity toEntity(ProductDto productDto);

    ProductDto toDto(ProductEntity productEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "audit", ignore = true)
    ProductEntity partialUpdate(ProductDto productDto, @MappingTarget ProductEntity productEntity);
}
