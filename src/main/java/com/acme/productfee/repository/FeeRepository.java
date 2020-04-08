package com.acme.productfee.repository;

import com.acme.productfee.model.FeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeRepository extends JpaRepository<FeeModel, Long> {
}
