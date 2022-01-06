package com.codegym.productmanagamentangular.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    private String description;

    private Long quantity;

    @Lob
    private String avatar;

    @ManyToOne
    private Category category;

    @ManyToOne(targetEntity = User.class)
    private User user;

    public Product() {
    }

    public Product(String name, Double price, String description,String avatar, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = 0L;
        this.avatar  = avatar;
        this.category = category;
    }

    public Product(Long id, String name, Double price, String description,String avatar, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.avatar = avatar;
        this.category = category;
    }
}
