package com.atzfinance.efinance.service.impl;

import com.atzfinance.efinance.dto.LoanApplicationDto;
import com.atzfinance.efinance.model.LoanApplication;
import com.atzfinance.efinance.model.User;
import com.atzfinance.efinance.repository.LoanApplicationRepository;
import com.atzfinance.efinance.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Override
    public void saveLoanApplication(LoanApplicationDto loanApplicationDto, User applicantUser) {
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setApplicantName(loanApplicationDto.getFullName());
        loanApplication.setPurpose(loanApplicationDto.getLoanPurpose());
        loanApplication.setAmount(loanApplicationDto.getLoanAmount());
        loanApplication.setApplicantUser(applicantUser);
        loanApplication.setActive(true);
        loanApplication.setApplicationDate(new Date());
        loanApplicationRepository.save(loanApplication);
    }

    @Override
    public List<LoanApplication> getAllPendingLoanApplications() {
        return loanApplicationRepository.findByActiveTrue();
    }
}
