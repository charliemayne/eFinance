package com.atzfinance.efinance.repository;

import com.atzfinance.efinance.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    Optional<LoanApplication> findByApplicationNumber(Long id);
    Optional<LoanApplication> findByRequestedAmount(double amount);
    Optional<LoanApplication> findByStatus(boolean status);


}

