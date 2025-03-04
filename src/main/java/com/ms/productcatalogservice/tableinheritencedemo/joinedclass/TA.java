package com.ms.productcatalogservice.tableinheritencedemo.joinedclass;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name = "jc_ta")
@PrimaryKeyJoinColumn(name = "user_id")
public class TA extends User {
    private Double ratings;
}
