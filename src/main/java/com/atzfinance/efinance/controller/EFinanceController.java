package com.atzfinance.efinance.controller;

import com.atzfinance.efinance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/efinance/")
public class EFinanceController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String dashboardPage(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        return "dashboard";
    }
    @GetMapping("/myloans")
    public String myloansPage() { return "myloans"; }
    @GetMapping("/dashboard")
    public String dashboardPage() { return "dashboard"; }
    @GetMapping("/applyforloan")
    public String applyforloanPage() {
        return "loan_application_form";
    }


}
