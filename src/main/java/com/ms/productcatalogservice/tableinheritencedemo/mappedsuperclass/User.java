package com.ms.productcatalogservice.tableinheritencedemo.mappedsuperclass;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class User {
    @Id
    private Long id;
    private String name;
    private String email;
}
