package com.atzfinance.efinance.service.impl;

import com.atzfinance.efinance.model.Inquiry;
import com.atzfinance.efinance.repository.InquiryRepository;
import com.atzfinance.efinance.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InquiryServiceImpl implements InquiryService {

    @Autowired
    private InquiryRepository inquiryRepository;
    private final List<Inquiry> inquiries = new ArrayList<>();

    @Override
    public void saveInquiry(Inquiry inquiry) {
        inquiry.setTimestamp(LocalDateTime.now());
        inquiryRepository.save(inquiry);
    }

    @Override
    public List<Inquiry> getAllInquiries() {
        return inquiryRepository.findAll();
    }
}
