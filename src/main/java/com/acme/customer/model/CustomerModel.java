package com.acme.customer.model;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CustomerModel {

    @Id
    private String customerId;
    private String customerName;

}
