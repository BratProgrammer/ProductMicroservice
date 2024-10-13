package com.example.InventoryManagement.Services;

import com.example.InventoryManagement.Entities.Product;
import com.example.InventoryManagement.Repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public List<Product> findAllById(List<Long> ids) {
        return productRepository.findAllById(ids);
    }

    public Product save(Product product) {
        productRepository.save(product);
        return product;
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }

    public void deleteAllById(List<Long> ids) {
        productRepository.deleteAllById(ids);
    }

    public List<Product> saveAll(Iterable<Product> products) {
        return productRepository.saveAll(products);
    }
}
