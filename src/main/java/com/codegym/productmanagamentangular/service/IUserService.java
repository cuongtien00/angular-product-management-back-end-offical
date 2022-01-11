package com.codegym.productmanagamentangular.service;

import com.codegym.productmanagamentangular.model.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String name);
    Optional<User> findById(Long id);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User save(User user);
}
