package com.atzfinance.efinance.repository;

import com.atzfinance.efinance.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The Role Repository
 * Date: 11/19/23
 * @authors charlimayene
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
