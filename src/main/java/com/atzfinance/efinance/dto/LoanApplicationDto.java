package com.atzfinance.efinance.dto;

import lombok.Data;
/**
 * LoanApplicationDto
 * Date: 11/19/23
 * @authors charlimayene
 */

@Data
public class LoanApplicationDto {
    private double loanAmount;
    private String loanPurpose;
    private String fullName;
}
