package com.example.InventoryManagement.DTO.Kafka;

import com.example.InventoryManagement.Enums.Action;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductActionDto {
    Long id;
    Action action;
}
