package com.acme.productfee.controller;

import com.acme.productfee.model.CategoryModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void saveAndGet() {

        String categoryName1 = "AAA";
        CategoryModel category = new CategoryModel();
        category.setCategoryName(categoryName1);
        CategoryModel saved = restTemplate.postForObject("/api/v1/productfee/category/save", category, CategoryModel.class);

        assertEquals(categoryName1, saved.getCategoryName());

        CategoryModel foundCategory = restTemplate.getForObject("/api/v1/productfee/category/" + categoryName1, CategoryModel.class);
        assertEquals(categoryName1, foundCategory.getCategoryName());

        String categoryName2 = "BBB";
        CategoryModel category2 = new CategoryModel();
        category2.setCategoryName(categoryName2);
        CategoryModel saved2 = restTemplate.postForObject("/api/v1/productfee/category/save", category2, CategoryModel.class);
        assertEquals(categoryName2, saved2.getCategoryName());


        CategoryModel[] result2 = restTemplate.getForObject("/api/v1/productfee/category/all", CategoryModel[].class);
        assertEquals(2, result2.length);
        assertEquals("AAA", result2[0].getCategoryName());
        assertEquals("BBB", result2[1].getCategoryName());

    }

}
