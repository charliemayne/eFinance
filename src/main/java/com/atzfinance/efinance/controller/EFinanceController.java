package com.atzfinance.efinance.controller;

import com.atzfinance.efinance.dto.BankingInfoDto;
import com.atzfinance.efinance.dto.InquiryDto;
import com.atzfinance.efinance.dto.LoanApplicationDto;
import com.atzfinance.efinance.dto.PaymentDto;
import com.atzfinance.efinance.model.*;
import com.atzfinance.efinance.repository.PaymentRepository;
import com.atzfinance.efinance.security.CustomUserDetailsService;
import com.atzfinance.efinance.security.SecurityUtil;
import com.atzfinance.efinance.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Mapping for EFinance Application User and Customer Dashboard
 * Date: 11/19/23
 * @authors charlie,roselam
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
    @Autowired
    private InquiryService inquiryService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BankingInfoService bankingInfoService;

    @GetMapping
    public String dashboardPage(Model model) throws JsonProcessingException {
        Optional<User> user = userService.getUserByUsername(SecurityUtil.getSesstionUser());
        model.addAttribute("username", user.get().getUsername());
        if (user.get().getRoles().contains(roleService.getByName("CUSTOMER"))) {
            // get loan account total balance
            // get all loan accounts maybe? Could do a chart
            List<LoanAccount> loanAccounts = loanAccountService.getCustomersLoans(user.get().getUsername());
            model.addAttribute("loanAccounts", loanAccounts);
            double totalBalance = 0;
            HashMap<String, Double> loanBarData = new HashMap<>();
            for (LoanAccount loan : loanAccounts) {
                totalBalance += loan.getCurrentBalance();
                loanBarData.put(loan.getPurpose() + " Loan #" + loan.getId(), loan.getCurrentBalance());
            }
            model.addAttribute("totalBalance", totalBalance);
            model.addAttribute("loanBarData", loanBarData);
        } else if (user.get().getRoles().contains(roleService.getByName("EMPLOYEE"))) {
            // get counts of open loan applications and inquiries
            long loanAppCount = loanApplicationService.getCountOfPendingLoanApplications();
            model.addAttribute("loanAppCount", loanAppCount);
            long activeInquiryCount = inquiryService.getCountOfActiveInquiries();
            model.addAttribute("activeInquiryCount", activeInquiryCount);
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
        // TODO: get currently signed in user (who is the signing employee)

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

    @PostMapping("/payment/{loanId}")
    public String payment() {
        return "null";
    }

    @GetMapping("/inquiry")
    public String inquiryFormPage(Model model) {

        InquiryDto inquiryDto = new InquiryDto();
        model.addAttribute("inquiry", inquiryDto);

        return "inquiry_form";
    }
    @PostMapping("/inquiry")
    public String submitInquiry(@ModelAttribute("inquiry") InquiryDto inquiryDto,
                                BindingResult result, Model model){
        Optional<User> user = userService.getUserByUsername(SecurityUtil.getSesstionUser());
        if(user.isPresent() && inquiryDto != null){
            inquiryService.saveInquiry(inquiryDto, user.get());
            return "redirect:/efinance/myInquiries?success=true";
        }else{
            return "redirect:/efinance/myInquiries?error=true";
        }
    }

    @GetMapping("/myInquiries")
    public String customersInquiries(Model model) {
        Optional<User> user = userService.getUserByUsername(SecurityUtil.getSesstionUser());
        List<Inquiry> inquiries = inquiryService.getCustomersInquiriesByUsername(user.get().getUsername());
        model.addAttribute("inquiries", inquiries);
        return "customer_inquiries";
    }

    @GetMapping("/myLoans/payment/{loanId}")
    public String paymentID(@PathVariable("loanId") Long id, Model model) {
        Optional<LoanAccount> loanAccount = loanAccountService.getByID(id);
        if (loanAccount.isPresent()) {
            model.addAttribute("loanAccount", loanAccount.get());
        }
        return "payment";
    }
    @PostMapping("/myLoans/pay")
    public String pay(@RequestParam("loanId") Long id, PaymentDto paymentDto, Model model) {
        Optional<LoanAccount> loanAccount = loanAccountService.getByID(id);

        if (loanAccountService.submitPayment(paymentDto,loanAccount.get() ,id)) {
            List<Payment> payments = loanAccountService.getAllPaymentInvoices();
            model.addAttribute("payment", payments);

            // redirect to myloan with success message
            return String.format("redirect:/efinance/myLoans?success=true", id);
        }

        return String.format("redirect:/efinance/myLoans?error=true", id);
    }

    @GetMapping("/bankingInfo")
    public String getCustomersBankingInfo(Model model) {
        Optional<User> user = userService.getUserByUsername(SecurityUtil.getSesstionUser());
        List<BankingInfo> bankingInfos = bankingInfoService.getCustomersBankingInfo(user.get().getUsername());
        model.addAttribute("bankingInfos", bankingInfos);
        return "customer_banking_infos";
    }

    @GetMapping("/newBankingInfo")
    public String getBankingInfoForm(Model model) {
        BankingInfoDto bankingInfoDto = new BankingInfoDto();
        model.addAttribute("bankingInfo", bankingInfoDto);
        return "banking_info_form";
    }

    @PostMapping("/saveBankingInfo")
    public String saveBankingInfo(@ModelAttribute("bankingInfo") BankingInfoDto bankingInfoDto,
                                  BindingResult result, Model model) {
        Optional<User> user = userService.getUserByUsername(SecurityUtil.getSesstionUser());
        if (user.isPresent() && bankingInfoDto != null) {
            bankingInfoService.saveBankingInfo(bankingInfoDto, user.get());
            return "redirect:/efinance/bankingInfo?success=true";
        } else {
            return "redirect:/efinance/newBankingInfo?error=true";
        }
    }

}
