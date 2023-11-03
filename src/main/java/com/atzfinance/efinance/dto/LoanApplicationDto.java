package com.atzfinance.efinance.dto;

import lombok.Data;

@Data
public class LoanApplicationDto {
    private double loanAmount;
    private String loanPurpose;
    private String fullName;
}
