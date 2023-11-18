package com.atzfinance.efinance.service;

import com.atzfinance.efinance.model.LoanAccount;
import com.atzfinance.efinance.model.LoanApplication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoanAccountService {
    void saveLoanAccount(LoanApplication loanApp);
    List<LoanAccount> getCustomersLoans(String username);
}
