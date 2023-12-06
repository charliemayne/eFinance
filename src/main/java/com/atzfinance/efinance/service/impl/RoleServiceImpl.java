package com.atzfinance.efinance.service.impl;

import com.atzfinance.efinance.model.Role;
import com.atzfinance.efinance.repository.RoleRepository;
import com.atzfinance.efinance.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name);
    }
}
