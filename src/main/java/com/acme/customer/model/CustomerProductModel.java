package com.acme.customer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class CustomerProductModel {

    @Id
    @GeneratedValue
    private Long id;
    private String customerId;
    private LocalDate deliveryDate;
    private String productId;
    private BigDecimal declaredValue;
    private BigDecimal calculatedFee;

}
