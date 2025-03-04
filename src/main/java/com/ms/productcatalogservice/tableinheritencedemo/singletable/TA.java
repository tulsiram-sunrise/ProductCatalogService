package com.ms.productcatalogservice.tableinheritencedemo.singletable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "st_ta")
@DiscriminatorValue(value = "3")
public class TA extends User {
    private Double ratings;
}
