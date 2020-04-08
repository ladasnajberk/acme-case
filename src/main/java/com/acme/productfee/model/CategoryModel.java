package com.acme.productfee.model;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CategoryModel {

    @Id
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
