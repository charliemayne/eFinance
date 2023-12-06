package com.atzfinance.efinance.service;

import com.atzfinance.efinance.dto.PaymentDto;
import com.atzfinance.efinance.model.LoanAccount;
import com.atzfinance.efinance.model.LoanApplication;
import com.atzfinance.efinance.model.Payment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LoanAccountService {
    void saveLoanAccount(LoanApplication loanApp);
    List<LoanAccount> getCustomersLoans(String username);
    Optional<LoanAccount> getByID(Long id);
    List<Payment> getAllPaymentInvoices();
    boolean submitPayment(PaymentDto paymentDto, LoanAccount loanAccount, Long id);
}
