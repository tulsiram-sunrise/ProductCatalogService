package com.ms.productcatalogservice.tableinheritencedemo.mappedsuperclass;

import jakarta.persistence.Entity;

@Entity(name = "msc_mentor")
public class Mentor extends User {
    private Long hours;
}
