package com.codegym.productmanagamentangular.repository;

import com.codegym.productmanagamentangular.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByUser_Id(Long id);
    List<Product> findAllByUserIsNull();
}
