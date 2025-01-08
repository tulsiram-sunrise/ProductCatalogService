package com.ms.productcatalogservice.controllers;

import com.ms.productcatalogservice.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/products")
    public List<Product> getProducts() {
        return null;
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") Long productId) {
        Product product = new Product();
        product.setId(productId);
        product.setName("Product Name");
        product.setDescription("Product Description");
        product.setPrice(100);
        product.setImageUrl("Product Image");
        return product;
    }

//    @GetMapping("/products/{id}")
//    public Product getProductById(@PathVariable int id) {
//        return null;
//    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product  product) {
        return null;
    }
}
