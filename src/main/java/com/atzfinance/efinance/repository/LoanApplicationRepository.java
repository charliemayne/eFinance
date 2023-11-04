package com.atzfinance.efinance.repository;

import com.atzfinance.efinance.model.LoanAccount;
import com.atzfinance.efinance.model.LoanApplication;
import com.atzfinance.efinance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    Optional<LoanAccount> findByApplicationNumber(Long id);
    Optional<LoanAccount> findByAmount(double amount);
    Optional<LoanAccount> findByStatus(boolean status);


}

