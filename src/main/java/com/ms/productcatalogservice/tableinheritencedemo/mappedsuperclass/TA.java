package com.ms.productcatalogservice.tableinheritencedemo.mappedsuperclass;

import jakarta.persistence.Entity;

@Entity(name = "msc_ta")
public class TA extends User {
    private Double ratings;
}
