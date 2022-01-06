package com.codegym.productmanagamentangular.repository;

import com.codegym.productmanagamentangular.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category,Long> {

}
