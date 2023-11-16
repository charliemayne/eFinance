package com.atzfinance.efinance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Payment {


    @GetMapping("/pay")
    public String getPaymentForm() {
        return "payment";
    }

    @PostMapping("/pay")
    public String processPayment(@RequestParam("amount") double amount) {
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String getSuccessPage() {
        return "success";
    }
}
