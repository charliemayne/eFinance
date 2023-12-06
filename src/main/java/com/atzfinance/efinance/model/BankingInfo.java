package com.atzfinance.efinance.model;

import jakarta.persistence.*;

@Entity(name = "banking_info")
public class BankingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User customer;
    private String bankName;
}
