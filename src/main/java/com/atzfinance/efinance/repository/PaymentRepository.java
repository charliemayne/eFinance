package com.atzfinance.efinance.repository;

import com.atzfinance.efinance.model.Payment;
import com.atzfinance.efinance.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * The Payment Repository
 * Date: 11/19/23
 * @authors roselam
 */
@Repository
public interface PaymentRepository {
    Optional<Payment> findbyInvoice(Long invoice);
    Optional<Payment> findbyBankName(String bankName);
    Optional<Payment> findByAmountPay(double amount);
}
