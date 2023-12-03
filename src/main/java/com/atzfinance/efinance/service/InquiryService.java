package com.atzfinance.efinance.service;

import com.atzfinance.efinance.model.Inquiry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InquiryService {
    void saveInquiry(Inquiry inquiry);
    List<Inquiry> getAllInquiries();
    long getCountOfActiveInquiries();
}

