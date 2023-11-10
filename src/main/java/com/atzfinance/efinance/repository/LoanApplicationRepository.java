package com.atzfinance.efinance.repository;

import com.atzfinance.efinance.model.LoanApplication;
import com.atzfinance.efinance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    Optional<LoanApplication> findByApplicationNumber(Long id);
    Optional<LoanApplication> findByAmount(double amount);
    Optional<LoanApplication> findByActive(boolean status);
    List<LoanApplication> findByActiveTrue();
    List<LoanApplication> findByActiveTrueAndReadyForCustomerFalse();
    List<LoanApplication> findByApplicantUser_UsernameAndActiveTrue(String username);
}

