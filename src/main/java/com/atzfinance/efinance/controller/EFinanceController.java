package com.atzfinance.efinance.controller;

import com.atzfinance.efinance.dto.RegistrationDto;
import com.atzfinance.efinance.model.User;
import com.atzfinance.efinance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/efinance")
public class EFinanceController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String checkAuthentication(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "login";
        } else {
            return "dashbord";
        }
    }
}
