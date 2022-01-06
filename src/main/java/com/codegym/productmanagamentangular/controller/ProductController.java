package com.codegym.productmanagamentangular.controller;

import com.codegym.productmanagamentangular.model.Product;
import com.codegym.productmanagamentangular.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return new ResponseEntity<>((List<Product>) productService.findAllByUserIsNull(), HttpStatus.OK);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Product>> findAllByUserName(@PathVariable Long id) {
        return new ResponseEntity<>(
                productService.findAllByUser_Id(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(productOptional.get(),HttpStatus.OK);
        }
    }


    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Product> update(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> remove(@PathVariable Long id) {
        productService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
