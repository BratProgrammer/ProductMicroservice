package com.example.InventoryManagement.Repositories;

import com.example.InventoryManagement.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    boolean existsById(Long aLong);
}