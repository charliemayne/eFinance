package com.atzfinance.efinance.service;

import com.atzfinance.efinance.model.Inquiry;

import java.util.List;

public interface InquiryService {
    void saveInquiry(Inquiry inquiry);
    List<Inquiry> getAllInquiries();
}

