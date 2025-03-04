package com.ms.productcatalogservice.tableinheritencedemo.tableperclass;

import jakarta.persistence.Entity;

@Entity(name = "tpc_mentor")
public class Mentor extends User {
    private Long hours;
}
