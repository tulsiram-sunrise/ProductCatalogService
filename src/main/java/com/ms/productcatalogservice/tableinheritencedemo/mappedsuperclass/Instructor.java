package com.ms.productcatalogservice.tableinheritencedemo.mappedsuperclass;

import jakarta.persistence.Entity;

@Entity(name = "msc_instructor")
public class Instructor extends User {
    private String company;
}
