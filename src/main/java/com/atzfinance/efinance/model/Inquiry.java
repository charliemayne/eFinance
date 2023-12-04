package com.atzfinance.efinance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "inquiry")
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inquiryNumber;
    private String name;
    private String email;
    private String message;
    private boolean active;
    private Date inquiryDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User applicantName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signingEmployee")
    private User signingEmployee;

    // getters and setters

    public void setInquiryID(long inquiryID){this.inquiryNumber = inquiryID;}
    public void setInquiryName(String inquiryName){this.name = inquiryName;}
    public void  setEmail(String email){this.email = email;}
    public void setMessage(String message){this.message = message;}
    public void setDate(Date date){this.inquiryDate = date;}
    public void setActive(boolean active) {this.active = active;}
}

