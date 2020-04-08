package com.acme.customer.controller;

import com.acme.customer.model.CustomerModel;
import com.acme.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("${acme.api.v1}/customer/customer")
public class CustomerController {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/all")
    public Iterable<CustomerModel> all() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public CustomerModel customerById(@PathVariable String id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public CustomerModel save(@RequestBody CustomerModel customer) {
        return customerRepository.save(customer);
    }

}
