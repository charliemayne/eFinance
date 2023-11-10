package com.atzfinance.efinance.service;

import com.atzfinance.efinance.dto.LoanApplicationDto;
import com.atzfinance.efinance.model.LoanApplication;
import com.atzfinance.efinance.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LoanApplicationService {
    void saveLoanApplication(LoanApplicationDto loanApplicationDto, User applicantUser);
    List<LoanApplication> getAllPendingLoanApplications();
    List<LoanApplication> getCustomersLoans(String username);
    Optional<LoanApplication> getByApplicationNumber(Long applicationNumber);
    boolean approveLoan(Long applicationNumber);
    boolean denyLoan(Long applicationNumber);
    void acknowledgeDeniedLoan(Long applicationNumber);
}
