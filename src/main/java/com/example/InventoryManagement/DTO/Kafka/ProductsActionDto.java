package com.example.InventoryManagement.DTO.Kafka;

import com.example.InventoryManagement.Enums.Action;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductsActionDto implements Serializable {

    private List<Long> ids;
    private Action action;

}
