package com.codegym.productmanagamentangular.service;

import com.codegym.productmanagamentangular.model.Role;
import com.codegym.productmanagamentangular.model.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName name);
}
