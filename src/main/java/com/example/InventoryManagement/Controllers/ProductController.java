package com.example.InventoryManagement.Controllers;

import com.example.InventoryManagement.DTO.Api.ProductDto;
import com.example.InventoryManagement.DTO.Api.ProductMapper;
import com.example.InventoryManagement.Entities.Product;
import com.example.InventoryManagement.Services.ProductService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/admin-ui/products")
@RequiredArgsConstructor
public class ProductController {

    private final ObjectMapper objectMapper;

    private final ProductMapper productMapper;

    private final ProductService productService;

    @GetMapping
    public Page<ProductDto> findAll(Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        Page<ProductDto> productDtoPage = products.map(productMapper::toDto);
        return productDtoPage;
    }

    @GetMapping("/all")
    public List<ProductDto> getList() {
        return productService.findAll().stream()
                .map(productMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable("id") Long id) {
        Product product = getProductById(id);
        return productMapper.toDto(product);
    }

    @GetMapping("/by-ids")
    public List<Product> getMany(@RequestParam List<Long> ids) {
        return productService.findAllById(ids);
    }

    @PostMapping
    public ProductDto create(@RequestBody ProductDto productDto) {
        return productMapper.toDto(productService.save(productMapper.toEntity(productDto)));
    }

    @PatchMapping(path = {"/{id}"})
    public Product patch(@PathVariable("id") Long id, @RequestBody JsonNode patchNode) throws IOException {
        Product product = getProductById(id);

        objectMapper.readerForUpdating(product).readValue(patchNode);

        return productService.patch(product);
    }

    @PatchMapping
    public List<Long> patchMany(@RequestParam List<Long> ids, @RequestBody JsonNode patchNode) throws IOException {
        Collection<Product> products = productService.findAllById(ids);

        int updatePathIndex = 0;
        for (Product product : products) {
            objectMapper.readerForUpdating(product).readValue(patchNode.get(updatePathIndex));
            updatePathIndex++;
        }

        List<Product> resultProducts = productService.saveAll(products);
        List<Long> list = resultProducts.stream()
                .map(Product::getId)
                .toList();
        return list;
    }


    @DeleteMapping("/{id}")
    public Product delete(@PathVariable Long id) {
        Product product = getProductById(id);
        productService.delete(product);
        return product;
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        productService.deleteAllById(ids);
    }


    private Product getProductById(Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id));
        }
        return product;
    }
}
