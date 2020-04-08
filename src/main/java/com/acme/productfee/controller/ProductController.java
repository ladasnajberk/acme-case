package com.acme.productfee.controller;

import com.acme.productfee.model.ProductModel;
import com.acme.productfee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("${acme.api.v1}/productfee/product")
public class ProductController {

    private ProductRepository productRespository;

    @Autowired
    public ProductController(ProductRepository productRespository) {
        this.productRespository = productRespository;
    }

    @GetMapping("/all")
    public Iterable<ProductModel> all() {
        return productRespository.findAll();
    }

    @GetMapping("/{id}")
    public ProductModel productById(@PathVariable String id) {
        return productRespository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ProductModel save(@RequestBody ProductModel user) {
        return productRespository.save(user);
    }

    @PostMapping("/upload")
    public String upload(@RequestBody MultipartFile file) {
        //TODO implement
        return "Not implemented yet";
    }

}
