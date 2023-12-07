package com.atzfinance.efinance.service.impl;

import com.atzfinance.efinance.dto.PaymentDto;
import com.atzfinance.efinance.model.LoanAccount;
import com.atzfinance.efinance.model.LoanApplication;
import com.atzfinance.efinance.model.Payment;
import com.atzfinance.efinance.repository.LoanAccountRepository;
import com.atzfinance.efinance.repository.PaymentRepository;
import com.atzfinance.efinance.service.LoanAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoanAccountServiceImpl implements LoanAccountService {
    @Autowired
    private LoanAccountRepository loanAccountRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void saveLoanAccount(LoanApplication loanApp) {
        // create loanAccount object
        LoanAccount loanAccount = new LoanAccount();

        // copy over values from loanApp
        loanAccount.setAmount(loanApp.getAmount());
        loanAccount.setCurrentBalance(loanApp.getAmount());
        loanAccount.setPurpose(loanApp.getPurpose());
        loanAccount.setCustomer(loanApp.getApplicantUser());
        //FIXME: need to refactor loan approve/deny methods
        // loanAccount.setApprovingEmployee();

        // set loanAccount-specific values
        loanAccount.setCreationDate(new Date());
        // save with repo method
        loanAccountRepository.save(loanAccount);
    }
    @Override
    public boolean submitPayment(PaymentDto paymentDto, LoanAccount loanAccount, Long Id) {
        if (paymentDto.getBankName().equals("No saved banking info")) return false;

        Payment payment = new Payment();
        payment.setBankName(paymentDto.getBankName());
        payment.setAmount(paymentDto.getAmount());
        payment.setSubmissionDate(new Date());

        // set the loan account associated
        Optional<LoanAccount> LoanAccountFind  = loanAccountRepository.findById(Id);

        if (LoanAccountFind.isPresent()) {
            payment.setLoanAccount(loanAccount);
            if (LoanAccountFind.get().getCurrentBalance() - paymentDto.getAmount() < 0) return false;
            LoanAccountFind.get().setCurrentBalance(LoanAccountFind.get().getCurrentBalance() - paymentDto.getAmount());
            paymentRepository.save(payment);
            loanAccountRepository.save(LoanAccountFind.get());
            return true;
        }
        return false;
    }

    @Override
    public List<LoanAccount> getCustomersLoans(String username) {
        return loanAccountRepository.findByCustomer_Username(username);
    }
    @Override
    public Optional<LoanAccount> getByID(Long id) {
        return loanAccountRepository.findById(id);
    }
    @Override
    public List<Payment> getAllPaymentInvoices() {
        return paymentRepository.findAll();
    }
}
