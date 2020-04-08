package com.acme.productfee.model;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductModel {

    @Id
    private String productName;
    private String categoryName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
