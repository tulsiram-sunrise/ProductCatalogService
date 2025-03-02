package com.ms.productcatalogservice.services;

import com.ms.productcatalogservice.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IProductService {
    public List<Product> getAllProducts();
    public Product getProductById(Long id);
    public Product createProduct(Product product);
    public Product replaceProduct(Long id, Product product);
}
