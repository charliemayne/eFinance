package com.atzfinance.efinance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The AppController routes the home page of the application
 * Date: 11/19/23
 * @authors charlimayene
 */
@Controller
public class AppController {

    // landing page and home site routes
    @GetMapping("/")
    public String landingPage() {
        return "landing";
    }

    @GetMapping("/personal")
    public String personalLoansPage() {
        return "personal";
    }

    @GetMapping("/auto")
    public String autoLoansPage() {
        return "auto";
    }

    @GetMapping("/renovation")
    public String renovationLoansPage() {
        return "renovation";
    }

    @GetMapping("/business")
    public String businessLoansPage() {
        return "business";
    }

    @GetMapping("/contact")
    public String contactPage() { return "contact"; }
}
