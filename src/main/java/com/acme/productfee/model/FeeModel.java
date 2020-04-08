package com.acme.productfee.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class FeeModel {
    @Id
    @GeneratedValue
    private Long id;
    private String categoryName;
    private Long limiLow;
    private Long limitTop;
    private BigDecimal fee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getLimiLow() {
        return limiLow;
    }

    public void setLimiLow(Long limiLow) {
        this.limiLow = limiLow;
    }

    public Long getLimitTop() {
        return limitTop;
    }

    public void setLimitTop(Long limitTop) {
        this.limitTop = limitTop;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }





}
