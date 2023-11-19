package com.atzfinance.efinance.repository;

import com.atzfinance.efinance.model.LoanAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanAccountRepository extends JpaRepository<LoanAccount, Long> {
    Optional<LoanAccount> findByName(String name);
    Optional<LoanAccount> findByAmount(double amount);
    Optional<LoanAccount> findById(Long id);
    List<LoanAccount> findByCustomer_Username(String username);
}
