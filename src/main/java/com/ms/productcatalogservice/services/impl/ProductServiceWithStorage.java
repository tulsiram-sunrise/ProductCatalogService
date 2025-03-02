package com.ms.productcatalogservice.services.impl;

import com.ms.productcatalogservice.models.Product;
import com.ms.productcatalogservice.repositories.ProductRepository;
import com.ms.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class ProductServiceWithStorage implements IProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceWithStorage(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return productRepository.save(product);
    }
}
