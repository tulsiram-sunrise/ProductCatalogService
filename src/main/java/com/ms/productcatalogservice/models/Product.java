package com.ms.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product extends BaseModel {
    private String name;
    private String description;
    private String imageUrl;
    private int price;
    private Category category;
    private Boolean isPrimeSpecific;
}
