package com.atzfinance.efinance.service;

import com.atzfinance.efinance.model.LoanApplication;

import java.util.List;

public interface LoanApplicationService {
    void saveLoanApplication(LoanApplication loanApplication);
    List<LoanApplication> getAllPendingLoanApplications();
}
