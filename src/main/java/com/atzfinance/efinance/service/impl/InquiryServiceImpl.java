package com.atzfinance.efinance.service.impl;

import com.atzfinance.efinance.dto.InquiryDto;
import com.atzfinance.efinance.model.Inquiry;
import com.atzfinance.efinance.model.User;
import com.atzfinance.efinance.repository.InquiryRepository;
import com.atzfinance.efinance.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class InquiryServiceImpl implements InquiryService {

    @Autowired
    private InquiryRepository inquiryRepository;

    @Override
    public void saveInquiry(InquiryDto inquiryDto, User applicantUser) {
        Inquiry inquiry = new Inquiry();
        inquiry.setInquiryName(inquiryDto.getFullName());
        inquiry.setEmail(inquiryDto.getEmail());
        inquiry.setMessage(inquiryDto.getMessage());

        // inquiry.setApplicantUser(applicantUser);
        // I keep getting an error on this one have a look

        inquiry.setActive(true);
        inquiry.setDate(new Date());
        inquiryRepository.save(inquiry);
    }

    @Override
    public void save(Inquiry inquiry){inquiryRepository.save(inquiry);}

    @Override
    public Optional<Inquiry> getByInquiryNumber(long inquiryNumber) {
        return inquiryRepository.findByInquiryNumber(inquiryNumber);
    }

//    @Override
//    public Optional<Inquiry> getByInquiryNumber(Long inquiryNumber){
//        return inquiryRepository.findByInquiryNumber(inquiryNumber);
//    }


    @Override
    public long getCountOfActiveInquiries() {
        return inquiryRepository.countByActiveTrue();
    }
}
