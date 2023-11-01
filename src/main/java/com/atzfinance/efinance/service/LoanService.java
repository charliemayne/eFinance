package com.atzfinance.efinance.service;

import com.atzfinance.efinance.model.Loan;

import java.math.BigDecimal;
import java.util.List;

public interface LoanService {
    // Save a new loan application
    void applyForLoan(Loan loan);

    // Retrieve a loan by its unique identifier
    Loan getLoanById(Long loanId);

    // Retrieve all loans for a specific customer
    List<Loan> getLoansByCustomer(String customerName);

    // Retrieve approved loans
    List<Loan> getApprovedLoans();

    // Retrieve loans by loan type
    List<Loan> getLoansByType(String loanType);

    // Update loan information (e.g., approval status, loan amount)
    void updateLoan(Loan loan);

    // Delete a loan
    void deleteLoan(Long loanId);

    // Calculate the total loan amount for a specific type of loan
    BigDecimal calculateTotalLoanAmountByType(String loanType);
}
