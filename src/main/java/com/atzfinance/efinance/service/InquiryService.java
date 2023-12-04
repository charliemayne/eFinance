package com.atzfinance.efinance.service;

import com.atzfinance.efinance.dto.InquiryDto;
import com.atzfinance.efinance.model.Inquiry;
import com.atzfinance.efinance.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface InquiryService {
    void saveInquiry(InquiryDto inquiryDto, User inquiryUser);
    void save(Inquiry inquiry);
    Optional<Inquiry> getByInquiryNumber(long inquiryNumber);
    long getCountOfActiveInquiries();
}

