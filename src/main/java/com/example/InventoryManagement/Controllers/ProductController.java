package com.example.InventoryManagement.Controllers;

import com.example.InventoryManagement.DTO.ProductDto;
import com.example.InventoryManagement.DTO.ProductMapper;
import com.example.InventoryManagement.Entities.Product;
import com.example.InventoryManagement.Services.ProductService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/rest/admin-ui/products")
@RequiredArgsConstructor
public class ProductController {


    private final ObjectMapper objectMapper;

    private final ProductMapper productMapper;

    private final ProductService productService;

    @GetMapping
    public Page<Product> getList(Pageable pageable) {
        return productService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);

        if (productOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id));
        }

        return productMapper.toDto(productOptional.get());
    }

    @GetMapping("/by-ids")
    public List<Product> getMany(@RequestParam List<Long> ids) {
        return productService.findAllById(ids);
    }

    @PostMapping
    public ProductDto create(@RequestBody @Valid ProductDto productDto) {
        return productMapper.toDto(productService.save(productMapper.toEntity(productDto)));
    }

    @PatchMapping(path = {"/{id}"})
    public Product patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
        Product product = productService.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        objectMapper.readerForUpdating(product).readValue(patchNode);

        return productService.patch(product);
    }

    @PatchMapping
    public List<Long> patchMany(@RequestParam List<Long> ids, @RequestBody JsonNode patchNode) throws IOException {
        Collection<Product> products = productService.findAllById(ids);

        for (Product product : products) {
            objectMapper.readerForUpdating(product).readValue(patchNode);
        }

        List<Product> resultProducts = productService.saveAll(products);
        List<Long> ids1 = resultProducts.stream()
                .map(Product::getId)
                .toList();
        return ids1;
    }

    @DeleteMapping("/{id}")
    public Product delete(@PathVariable Long id) {
        Product product = productService.findById(id).orElse(null);
        if (product != null) {
            productService.delete(product);
        }
        return product;
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        productService.deleteAllById(ids);
    }
}
