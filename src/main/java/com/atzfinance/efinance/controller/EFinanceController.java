package com.atzfinance.efinance.controller;

import com.atzfinance.efinance.dto.LoanApplicationDto;
import com.atzfinance.efinance.model.User;
import com.atzfinance.efinance.security.SecurityUtil;
import com.atzfinance.efinance.service.LoanApplicationService;
import com.atzfinance.efinance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/efinance")
public class EFinanceController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoanApplicationService loanApplicationService;

    @GetMapping
    public String dashboardPage(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        return "dashboard";
    }

    @GetMapping("/applyForLoan")
    public String loanApplicationPage(Model model) {
        LoanApplicationDto loanApplicationDto = new LoanApplicationDto();
        model.addAttribute("loanApplication", loanApplicationDto);
        return "loan_application_form";
    }

    @PostMapping("/applyForLoan")
    public String applyForLoan(@ModelAttribute("loanApplication") LoanApplicationDto loanApplication,
                               BindingResult result, Model model) {
        Optional<User> user = userService.getUserByUsername(SecurityUtil.getSesstionUser());
        if (user.isPresent() && loanApplication != null) {
            loanApplicationService.saveLoanApplication(loanApplication, user.get());
            return "redirect:/efinance/applyForLoan?success=true";
        } else {
            return "redirect:/efinance/applyForLoan?error=true";
        }
    }
}
