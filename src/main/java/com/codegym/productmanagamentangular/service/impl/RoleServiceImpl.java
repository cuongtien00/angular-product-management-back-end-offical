package com.codegym.productmanagamentangular.service.impl;

import com.codegym.productmanagamentangular.model.Role;
import com.codegym.productmanagamentangular.model.RoleName;
import com.codegym.productmanagamentangular.repository.IRoleRepository;
import com.codegym.productmanagamentangular.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}