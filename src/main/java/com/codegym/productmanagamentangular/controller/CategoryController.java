package com.codegym.productmanagamentangular.controller;

import com.codegym.productmanagamentangular.model.Category;
import com.codegym.productmanagamentangular.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;


    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return new ResponseEntity<>((List<Category>) categoryService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (!categoryOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(categoryOptional.get(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> save(@RequestBody Category category){
        return new ResponseEntity<>(categoryService.save(category),HttpStatus.CREATED);
    }
}
