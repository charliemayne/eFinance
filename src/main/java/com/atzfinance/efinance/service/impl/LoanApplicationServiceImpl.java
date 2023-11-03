package com.atzfinance.efinance.service.impl;

import com.atzfinance.efinance.model.LoanApplication;
import com.atzfinance.efinance.repository.LoanApplicationRepository;
import com.atzfinance.efinance.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LoanApplicationServiceImpl implements LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;


    @Override
    public void saveLoanApplication(LoanApplication loanApplication) {
        loanApplicationRepository.save(loanApplication);
    }

    @Override
    public List<LoanApplication> getAllPendingLoanApplications() {
        return loanApplicationRepository.findByActiveTrue();
    }
}
