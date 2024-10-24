package com.example.InventoryManagement.DTO.Kafka;

import com.example.InventoryManagement.Enums.Action;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ProductActionDto implements Serializable {
    private Long id;
    private Action action;
}
