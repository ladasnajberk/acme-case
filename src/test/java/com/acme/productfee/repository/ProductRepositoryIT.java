package com.acme.productfee.repository;

import com.acme.productfee.model.ProductModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ProductRepositoryIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void saveAndGet() {
        ProductModel product = new ProductModel();
        product.setProductName("product1");
        product.setCategoryName("category1");

        entityManager.persist(product);
        entityManager.flush();

        Optional<ProductModel> found = productRepository.findById(product.getProductName());
        assertTrue(found.isPresent());
        assertEquals(product.getProductName(), found.get().getProductName());
    }
}