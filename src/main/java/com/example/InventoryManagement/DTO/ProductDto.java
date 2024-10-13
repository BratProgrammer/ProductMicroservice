package com.example.InventoryManagement.DTO;

import com.example.InventoryManagement.Entities.Product;
import jakarta.validation.constraints.Min;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Product}
 */
@Value
public class ProductDto implements Serializable {
    Long id;
    @Min(0)
    int quantity;
    @Min(0)
    int cost;
    String description;
}