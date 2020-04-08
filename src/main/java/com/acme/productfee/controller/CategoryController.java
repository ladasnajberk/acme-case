package com.acme.productfee.controller;

import com.acme.productfee.model.CategoryModel;
import com.acme.productfee.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RestController
@RequestMapping("${acme.api.v1}/productfee/category")
public class CategoryController {

    private CategoryRepository categoryRespository;

    @Autowired
    public CategoryController(CategoryRepository categoryRespository) {
        this.categoryRespository = categoryRespository;
    }

    @GetMapping("/all")
    public Iterable<CategoryModel> all() {
        return categoryRespository.findAll();
    }

    @GetMapping("/{id}")
    public CategoryModel categoryById(@PathVariable String id) {
        return categoryRespository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        Optional<CategoryModel> categoryModel = categoryRespository.findById(id);
        if (categoryModel.isPresent() && categoryModel.get().getCategoryName().equals(id)) {
            categoryRespository.delete(categoryModel.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save")
    public CategoryModel save(@RequestBody CategoryModel category) {
        return categoryRespository.save(category);
    }

}
