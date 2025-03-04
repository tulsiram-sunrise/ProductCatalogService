package com.ms.productcatalogservice.tableinheritencedemo.tableperclass;

import jakarta.persistence.Entity;

@Entity(name = "tpc_instructor")
public class Instructor extends User {
    private String company;
}
