package com.atzfinance.efinance.controller;

import com.atzfinance.efinance.dto.LoanApplicationDto;
import com.atzfinance.efinance.model.LoanApplication;
import com.atzfinance.efinance.model.User;
import com.atzfinance.efinance.security.SecurityUtil;
import com.atzfinance.efinance.service.LoanApplicationService;
import com.atzfinance.efinance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
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

    // customer URLs (should update to be loanApplications before we start working with loan accounts and things get confusing
    @GetMapping("/myLoans")
    public String customerLoansPage(Model model) {
        Optional<User> user = userService.getUserByUsername(SecurityUtil.getSesstionUser());
        List<LoanApplication> customersLoanApplications = null;
        if (user.isPresent()) {
            customersLoanApplications = loanApplicationService.getCustomersLoans(user.get().getUsername());
        }
        model.addAttribute("loanApplications", customersLoanApplications);
        return "customer_loans";
    }

    @GetMapping("/myLoans/{loanId}")
    public String customerLoanPage(@PathVariable("loanId") Long loanId, Model model) {
        Optional<LoanApplication> loanApplication = loanApplicationService.getByApplicationNumber(loanId);
        if (loanApplication.isPresent()) {
            model.addAttribute("loanApplication", loanApplication.get());
        }
        return "customer_review_loan_app";
    }

    @PostMapping("/acknowledgeDeniedLoan")
    public String acknowledgeDeniedLoan(@RequestParam("loanId") Long loanId, Model model) {
        loanApplicationService.acknowledgeDeniedLoan(loanId);
        List<LoanApplication> loanApplications = loanApplicationService.getAllPendingLoanApplications();
        model.addAttribute("loanApplications", loanApplications);
        return "redirect:/efinance/myLoans";
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

    // employee URLs
    @GetMapping("/reviewLoans")
    public String reviewLoanApplicationsPage(Model model) {
        List<LoanApplication> loanApplications = loanApplicationService.getAllPendingLoanApplications();
        model.addAttribute("loanApplications", loanApplications);
        return "review_loan_applications";
    }

    @GetMapping("/reviewLoans/{loanId}")
    public String reviewLoan(@PathVariable("loanId") Long loanId, Model model) {
        Optional<LoanApplication> loanApplication = loanApplicationService.getByApplicationNumber(loanId);
        if (loanApplication.isPresent()) {
            model.addAttribute("loanApplication", loanApplication.get());
        }
        return "review_single_loan_app";
    }

    @PostMapping("/approveLoan")
    public String approveLoan(@RequestParam("loanId") Long loanId, Model model) {
        // mark loan as ready for customer
        if (loanApplicationService.approveLoan(loanId)) {
            // get updated list of loans to review
            List<LoanApplication> loanApplications = loanApplicationService.getAllPendingLoanApplications();
            model.addAttribute("loanApplications", loanApplications);
            // redirect to loan review with success message
            return "redirect:/efinance/reviewLoans?approved=true";
        }
        return "redirect:/efinance/reviewLoans?error=true";
    }

    @PostMapping("/denyLoan")
    public String denyLoan(@RequestParam("loanId") Long loanId, Model model) {
        // mark loan as ready for customer
        if (loanApplicationService.denyLoan(loanId)) {
            //get updated list of loans to review
            List<LoanApplication> loanApplications = loanApplicationService.getAllPendingLoanApplications();
            model.addAttribute("loanApplications", loanApplications);
            // redirect to loan review with successfully denied message
            return "redirect:/efinance/reviewLoans?denied=true";
        }
        return "redirect:/efinance/reviewLoans?error=true";
    }
}
