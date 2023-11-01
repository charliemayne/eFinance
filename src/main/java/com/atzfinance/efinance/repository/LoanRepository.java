package com.atzfinance.efinance.repository;

import com.atzfinance.efinance.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    //Find loans of a specific type (e.g., personal, business, car)
    List<Loan> findByLoanType(String loanType);

    //Find loans associated with a specific customer.
    List<Loan> findByCustomerName(String customerName);

    //Find loans associated with a specific customer ID.
    List<Loan> findByCustomerID(int customerID);

    //Find loans by Loan ID Number.
    List<Loan> findByLoanIn(int loanID);
}
