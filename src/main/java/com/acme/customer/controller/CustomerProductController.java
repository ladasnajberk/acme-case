package com.acme.customer.controller;

import com.acme.customer.model.CustomerProductModel;
import com.acme.customer.repository.CustomerProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("${acme.api.v1}/customer/product")
public class CustomerProductController {

    private CustomerProductRepository customerProductRepository;

    @Autowired
    public CustomerProductController(CustomerProductRepository customerProductRepository) {
        this.customerProductRepository = customerProductRepository;
    }

    @GetMapping("/all")
    public Iterable<CustomerProductModel> all() {
        return customerProductRepository.findAll();
    }

    @GetMapping("/{id}")
    public CustomerProductModel customerProductById(@PathVariable Long id) {
        return customerProductRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public CustomerProductModel save(@RequestBody CustomerProductModel customer) {
        return customerProductRepository.save(customer);
    }

    @PostMapping("/upload")
    public String upload(@RequestBody MultipartFile file) {
        //TODO implement
        return "Not implemented yet";
    }

}
