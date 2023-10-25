package com.atzfinance.efinance.service;

import com.atzfinance.efinance.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(long id);
    Optional<User> getUserByUsername(String username);
}
