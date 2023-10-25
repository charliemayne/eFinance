package com.atzfinance.efinance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {

    // landing page and home site routes
    @GetMapping("/")
    public String landingPage() {
        return "landing";
    }

    @GetMapping("/contact")
    public String contactPage() { return "contact"; }


    // eFinance routes. Maybe make a different controller for these? prefix with /efinance or something
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard";
    }

    @PostMapping("/dashboard")
    public String postDashboardPage() {
        return "dashboard";
    }
}
