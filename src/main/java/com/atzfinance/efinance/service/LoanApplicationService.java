package com.atzfinance.efinance.service;

import com.atzfinance.efinance.dto.LoanApplicationDto;
import com.atzfinance.efinance.model.LoanApplication;
import com.atzfinance.efinance.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoanApplicationService {
    void saveLoanApplication(LoanApplicationDto loanApplicationDto, User applicantUser);
    List<LoanApplication> getAllPendingLoanApplications();
    List<LoanApplication> getCustomersLoans(String username);
}
