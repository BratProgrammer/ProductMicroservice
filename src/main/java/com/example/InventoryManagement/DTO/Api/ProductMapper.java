package com.example.InventoryManagement.DTO.Api;

import com.example.InventoryManagement.Entities.Product;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductDto productDto);

    ProductDto toDto(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductDto productDto, @MappingTarget Product product);
}