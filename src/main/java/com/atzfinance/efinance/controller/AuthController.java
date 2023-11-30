package com.atzfinance.efinance.controller;

import com.atzfinance.efinance.dto.RegistrationDto;
import com.atzfinance.efinance.model.User;
import com.atzfinance.efinance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

/**
 * The AuthControl routes the logging in and registration of the app
 * Date: 11/19/23
 * @authors charlimayene
 */
@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user")RegistrationDto user,
                           BindingResult result, Model model) {
        Optional<User> existingEmail = userService.getUserByEmail(user.getEmail());
        Optional<User> existingUsername = userService.getUserByUsername(user.getUsername());
        if (existingEmail.isPresent() || existingUsername.isPresent()) {
            result.rejectValue("email", "There is already a user with that email or username");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/login?success";
    }
}
