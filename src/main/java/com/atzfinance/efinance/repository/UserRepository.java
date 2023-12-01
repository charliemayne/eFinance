package com.atzfinance.efinance.repository;

import com.atzfinance.efinance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * User Repository
 * Date: 11/19/23
 * @authors charlimayene
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findFirstByUsername(String username);
}
