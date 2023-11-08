package com.atzfinance.efinance.service;

import com.atzfinance.efinance.dto.RegistrationDto;
import com.atzfinance.efinance.model.User;

import java.util.Optional;

public interface UserService {
    void saveUser(RegistrationDto registration);
    void saveUser(String username, String password, String email, String roleName);
    Optional<User> getUserById(long id);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
}
