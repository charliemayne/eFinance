package com.atzfinance.efinance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "loan_application")
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long applicationNumber;
    private double amount;
    private boolean status;
    private String applicantName;
    private String purpose;

    public void setApplicationNumber(long applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
