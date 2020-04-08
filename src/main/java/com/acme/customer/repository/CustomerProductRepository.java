package com.acme.customer.repository;

import com.acme.customer.model.CustomerProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerProductRepository extends JpaRepository<CustomerProductModel, Long> {
}
