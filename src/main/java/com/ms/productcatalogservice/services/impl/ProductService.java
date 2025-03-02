package com.ms.productcatalogservice.services.impl;

import com.ms.productcatalogservice.client.FakeStoreApiClient;
import com.ms.productcatalogservice.dtos.FakeStoreProductDto;
import com.ms.productcatalogservice.models.Category;
import com.ms.productcatalogservice.models.Product;
import com.ms.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService implements IProductService {
    private final FakeStoreApiClient fakeStoreApiClient;

    @Autowired
    public ProductService(FakeStoreApiClient fakeStoreApiClient) {
        this.fakeStoreApiClient = fakeStoreApiClient;
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDtos = fakeStoreApiClient.getAllProducts();
        if (fakeStoreProductDtos == null) {
            return new ArrayList<>();
        }

        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            products.add(from(fakeStoreProductDto));
        }
        return products;
    }

    @Override
    public Product getProductById(Long id) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.getProductById(id);

        if (fakeStoreProductDto != null) {
            return from(fakeStoreProductDto);
        }

        return null;
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDto = from(product);

        fakeStoreProductDto = fakeStoreApiClient.createProduct(fakeStoreProductDto);
        if (fakeStoreProductDto == null) {
            return null;
        }

        return from(fakeStoreProductDto);
    }

    public Product replaceProduct(Long id, Product product) {
        FakeStoreProductDto fakeStoreProductDto = from(product);
        fakeStoreProductDto = fakeStoreApiClient.replaceProduct(id, fakeStoreProductDto);
        if (fakeStoreProductDto == null) {
            return null;
        }

        return from(fakeStoreProductDto);
    }

    private Product from(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());

        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        category.setDescription(("Description of " + fakeStoreProductDto.getCategory()));

        product.setCategory(category);

        return product;
    }

    private FakeStoreProductDto from(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setCategory(product.getCategory().getName());

        return fakeStoreProductDto;
    }

}
