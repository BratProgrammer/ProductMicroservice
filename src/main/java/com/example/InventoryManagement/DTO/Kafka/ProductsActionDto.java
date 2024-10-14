package com.example.InventoryManagement.DTO.Kafka;

import com.example.InventoryManagement.Enums.Action;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ProductsActionDto {

    List<Long> ids;
    Action action;

}
