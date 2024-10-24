package com.example.InventoryManagement.DTO.Api;

import com.example.InventoryManagement.Entities.Product;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Product}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {
    private Long id;
    @Min(0)
    private int cost;
    private String description;
}