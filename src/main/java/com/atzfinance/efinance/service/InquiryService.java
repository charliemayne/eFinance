package com.atzfinance.efinance.service;

import com.atzfinance.efinance.dto.InquiryDto;
import com.atzfinance.efinance.model.Inquiry;
import com.atzfinance.efinance.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface InquiryService {
    void saveInquiry(InquiryDto inquiryDto, User inquiryUser);
    void save(Inquiry inquiry);
    Optional<Inquiry> getByInquiryid(long inquiryid);
    long getCountOfActiveInquiries();
    List<Inquiry> getCustomersInquiriesByUsername(String username);
}

