package com.codegym.productmanagamentangular.service.product;

import com.codegym.productmanagamentangular.model.Product;
import com.codegym.productmanagamentangular.service.IGeneralService;

import java.util.List;

public interface IProductService extends IGeneralService<Product> {
    List<Product> findAllByUser_Id(Long id);
    List<Product> findAllByUserIsNull();
}
