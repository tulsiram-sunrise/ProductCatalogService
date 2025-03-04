package com.ms.productcatalogservice.tableinheritencedemo.singletable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "st_instructor")
@DiscriminatorValue(value = "1")
public class Instructor extends User {
    private String company;
}
