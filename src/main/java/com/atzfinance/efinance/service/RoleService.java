package com.atzfinance.efinance.service;

import com.atzfinance.efinance.model.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role getByName(String name);
}
