package com.example.InventoryManagement.Services;

import com.example.InventoryManagement.DTO.Kafka.ProductActionDto;
import com.example.InventoryManagement.Entities.Product;
import com.example.InventoryManagement.Repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.InventoryManagement.Enums.Action.CREATE;


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final CacheManager cacheManager;

    private final KafkaTemplate<String, ProductActionDto> kafkaTemplate;

    @Cacheable(value = "products", key = "#product.id")
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Cacheable(value = "productsByIds", key = "#ids != null ? #ids.toString() : 'empty'")
    public List<Product> findAllById(List<Long> ids) {
        return productRepository.findAllById(ids);
    }

    @CachePut(value = "products", key = "#product.id")
    public Product save(Product product) {
        productRepository.save(product);
        kafkaTemplate.send("product_updated", new ProductActionDto(product.getId(), CREATE));
        return product;
    }

    @CachePut(value = "products", key = "#product.id")
    public Product patch(Product product) {
        productRepository.save(product);
        return product;
    }

    @CacheEvict(value = "products", key = "#product.id")
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @CacheEvict(value = "productsByIds", key = "#ids != null ? #ids.toString() : 'empty'")
    public void deleteAllById(List<Long> ids) {
        productRepository.deleteAllById(ids);
    }

    public List<Product> saveAll(Iterable<Product> products) {

        List<Product> savedProducts = productRepository.saveAll(products);

        for (Product product : savedProducts) {
            cacheManager.getCache("products").put(product.getId(), product);
        }

        return savedProducts;
    }
}
