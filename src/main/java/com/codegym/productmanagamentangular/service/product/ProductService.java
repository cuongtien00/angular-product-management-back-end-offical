package com.codegym.productmanagamentangular.service.product;

import com.codegym.productmanagamentangular.model.Product;
import com.codegym.productmanagamentangular.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductService implements IProductService{

    @Autowired
    private IProductRepository productRepository;

    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void remove(Long id) {
productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAllByUser_Id(Long id) {
        return productRepository.findAllByUser_Id(id);
    }

    @Override
    public List<Product> findAllByUserIsNull() {
        return productRepository.findAllByUserIsNull();
    }
}
