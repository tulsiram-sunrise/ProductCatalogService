package com.ms.productcatalogservice.services.impl;

import com.ms.productcatalogservice.dtos.FakeStoreProductDto;
import com.ms.productcatalogservice.models.Category;
import com.ms.productcatalogservice.models.Product;
import com.ms.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService implements IProductService {
    private final RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public ProductService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public List<Product> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDto[].class).getBody();
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
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponse = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, id);

        if (fakeStoreProductDtoResponse.getStatusCode().equals(HttpStatus.OK) && fakeStoreProductDtoResponse.getBody() != null) {
            return from(fakeStoreProductDtoResponse.getBody());
        }

        return null;
    }

    @Override
    public Product createProduct(Product product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setCategory(product.getCategory().getName());

        FakeStoreProductDto fakeStoreProductDtoResponse = restTemplate.postForEntity("https://fakestoreapi.com/products", fakeStoreProductDto, FakeStoreProductDto.class).getBody();
        if (fakeStoreProductDtoResponse == null) {
            return null;
        }

        return from(fakeStoreProductDtoResponse);
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
}
