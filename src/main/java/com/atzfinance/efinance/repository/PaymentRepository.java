package com.atzfinance.efinance.repository;

import com.atzfinance.efinance.model.LoanApplication;
import com.atzfinance.efinance.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * The Payment Repository
 * Date: 11/19/23
 * @authors roselam
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByInvoice(long invoice);
    Optional<Payment> findByBankName(String bankName);
    Optional<Payment> findByAmount(double amount);
}
