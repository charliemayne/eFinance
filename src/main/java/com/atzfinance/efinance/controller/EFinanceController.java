package com.atzfinance.efinance.controller;

import com.atzfinance.efinance.dto.LoanApplicationDto;
import com.atzfinance.efinance.model.LoanAccount;
import com.atzfinance.efinance.model.LoanApplication;
import com.atzfinance.efinance.model.User;
import com.atzfinance.efinance.security.SecurityUtil;
import com.atzfinance.efinance.service.LoanAccountService;
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

/**
 * Mapping for EFinance Application User and Customer Dashboard
 * Date: 11/19/23
 * @authors charlimayene,roselam
 */

@Controller
@RequestMapping("/efinance")
public class EFinanceController {
    // instance variables
    @Autowired
    private UserService userService;
    @Autowired
    private LoanApplicationService loanApplicationService;
    @Autowired
    private LoanAccountService loanAccountService;

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
        List<LoanAccount> customersLoanAccounts = null;
        if (user.isPresent()) {
            // get loanApps
            customersLoanApplications = loanApplicationService.getCustomersLoans(user.get().getUsername());
            // get loanAccounts
            customersLoanAccounts = loanAccountService.getCustomersLoans(user.get().getUsername());
        }
        model.addAttribute("loanApplications", customersLoanApplications);
        model.addAttribute("loanAccounts", customersLoanAccounts);
        return "customer_loans";
    }

    // o
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

    @PostMapping("/acceptLoan")
    public String customerAcceptLoan(@RequestParam("loanId") Long loanId) {
        Optional<LoanApplication> loanApp = loanApplicationService.getByApplicationNumber(loanId);
        if (loanApp.isPresent()) {
            // create loan account
            loanAccountService.saveLoanAccount(loanApp.get());

            // close loanApp
            loanApp.get().setActive(false);
            loanApplicationService.save(loanApp.get());
            return "redirect:/efinance/myLoans?accepted=true";
        } else {
            return "redirect:/efinance/myLoans?error=true";
        }
    }

    @PostMapping("/declineLoan")
    public String customerDeclineLoan(@RequestParam("loanId") Long loanId) {
        Optional<LoanApplication> loanApp = loanApplicationService.getByApplicationNumber(loanId);
        if (loanApp.isPresent()) {
            // close loanApp
            loanApp.get().setActive(false);
            loanApplicationService.save(loanApp.get());
            return "redirect:/efinance/myLoans?declined=true";
        } else {
            return "redirect:/efinance/myLoans?error=true";
        }
    }

//    @PostMapping("/acknowledgeDeniedLoan")
//    public String customerAcknowledgeDeniedLoan(@RequestParam("loanId") Long loanId) {
//        Optional<LoanApplication> loanApp = loanApplicationService.getByApplicationNumber(loanId);
//        if (loanApp.isPresent()) {
//            // close loanApp
//            loanApp.get().setActive(false);
//            loanApplicationService.save(loanApp.get());
//            return "redirect:/efinance/myLoans";
//        } else {
//            return "redirect:/efinance/myLoans?error=true";
//        }
//    }

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
        // get currently signed in user (who is the signing employee)

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

    @GetMapping("/myLoans/payment/{loanId}")
    public String paymentID(@PathVariable("loanId") Long id, Model model) {
        Optional<LoanAccount> loanAccount = loanAccountService.getByID(id);
        if (loanAccount.isPresent()) {
            model.addAttribute("loanAccount", loanAccount.get());
        }
        return "payment";
    }
    /*
    @PostMapping("/payment/{loanId}")
    public String payment(@RequestParam("loanId") Long loanId) {
        Optional<LoanAccount> loanAccount = loanAccountService.getByID(loanId);
        if (loanAccount.isPresent()) {
            // create loan account
            loanAccountService.submitPayment(loanAccount.get());


            return "redirect:/efinance/myLoans";
        } else {
            return "redirect:/efinance/myLoans";
        }
    }

     */

}
