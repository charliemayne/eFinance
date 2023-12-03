package com.atzfinance.efinance.repository;

import com.atzfinance.efinance.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * The Loan Application Repository
 * Date: 11/19/23
 * @authors charlimayene
 */
@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    Optional<LoanApplication> findByApplicationNumber(Long id);
    Optional<LoanApplication> findByAmount(double amount);
    Optional<LoanApplication> findByActive(boolean status);
    List<LoanApplication> findByActiveTrue();
    List<LoanApplication> findByActiveTrueAndReadyForCustomerFalse();
    List<LoanApplication> findByApplicantUser_UsernameAndActiveTrue(String username);
    long countByActiveTrueAndReadyForCustomerFalse();
}

