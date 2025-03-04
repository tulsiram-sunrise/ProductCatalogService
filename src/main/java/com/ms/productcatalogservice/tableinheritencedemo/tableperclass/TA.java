package com.ms.productcatalogservice.tableinheritencedemo.tableperclass;

import jakarta.persistence.Entity;

@Entity(name = "tpc_ta")
public class TA extends User {
    private Double ratings;
}
