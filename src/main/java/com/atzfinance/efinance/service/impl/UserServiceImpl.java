package com.atzfinance.efinance.service.impl;

import com.atzfinance.efinance.dto.RegistrationDto;
import com.atzfinance.efinance.model.Role;
import com.atzfinance.efinance.model.User;
import com.atzfinance.efinance.repository.RoleRepository;
import com.atzfinance.efinance.repository.UserRepository;
import com.atzfinance.efinance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(RegistrationDto registration) {
        User newUser = new User();
        newUser.setUsername(registration.getUsername());
        newUser.setEmail(registration.getEmail());
        newUser.setPassword(passwordEncoder.encode(registration.getPassword()));
        Role role = roleRepository.findByName("CUSTOMER");
        newUser.setRoles(Arrays.asList(role));
        userRepository.save(newUser);
    }

    @Override
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserByEmail(String email) { return userRepository.findByEmail(email); }


}
