package com.atzfinance.efinance.service.impl;

import com.atzfinance.efinance.model.LoanAccount;
import com.atzfinance.efinance.model.LoanApplication;
import com.atzfinance.efinance.model.Payment;
import com.atzfinance.efinance.repository.LoanAccountRepository;
import com.atzfinance.efinance.service.LoanAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoanAccountServiceImpl implements LoanAccountService {
    @Autowired
    private LoanAccountRepository loanAccountRepo;

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
        loanAccountRepo.save(loanAccount);
    }
    @Override
    public void submitPayment(Long id, Payment paymentApp) {
        Optional<LoanAccount> loanAccount = loanAccountRepo.findById(id);

        if (loanAccount.isPresent()) {
            // create payment object
            Payment payment = new Payment();

        // copy over values from payment
        payment.setAmount(paymentApp.getAmount());
        }
    }

    @Override
    public List<LoanAccount> getCustomersLoans(String username) {
        return loanAccountRepo.findByCustomer_Username(username);
    }
    @Override
    public Optional<LoanAccount> getByID(Long id) {
        return loanAccountRepo.findById(id);
    }
}
