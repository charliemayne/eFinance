package com.atzfinance.efinance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    // shows if loan application is currently open
    private boolean active;
    // shows if loan has been approved by employee. Default should be false
    private boolean approved;
    // shows if loan application is ready for customer approval
    private boolean readyForCustomer;
    private String applicantName;
    private String purpose;
    private Date applicationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User applicantUser;

    public void setApplicationNumber(long applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
