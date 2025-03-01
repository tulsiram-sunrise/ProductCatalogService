package com.ms.productcatalogservice.controllers;

import com.ms.productcatalogservice.dtos.CategoryDto;
import com.ms.productcatalogservice.dtos.ProductDto;
import com.ms.productcatalogservice.models.Category;
import com.ms.productcatalogservice.models.Product;
import com.ms.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<ProductDto> getProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return null;
        }

        return products.stream().map(this::convertToDto).toList();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        try {
            if (productId < 1) {
                throw new RuntimeException("Invalid productId");
            }

            Product product = productService.getProductById(productId);
            if (product == null) {
                return null;
            }
            return new ResponseEntity<>(convertToDto(product), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto  productDto) {
        if (productDto == null) {
            throw new RuntimeException("Invalid productDto");
        }
        if (productDto.getName().isEmpty()) {
            throw new RuntimeException("Product name cannot be empty");
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setIsPrimeSpecific(true);

        if (productDto.getCategory() != null) {
            Category category = new Category();
            category.setId(productDto.getCategory().getId());
            category.setName(productDto.getCategory().getName());
            category.setDescription(productDto.getCategory().getDescription());
            product.setCategory(category);
        }

        return convertToDto(productService.createProduct(product));
    }


    private ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        if (product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setDescription(product.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }

        return productDto;
    }
}
