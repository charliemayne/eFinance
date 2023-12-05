package com.atzfinance.efinance.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentDto {
    private String bankName;
    private Date submissionDate;
    private double amount;
}