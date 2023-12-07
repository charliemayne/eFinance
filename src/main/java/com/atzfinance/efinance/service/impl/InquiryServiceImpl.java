package com.atzfinance.efinance.service.impl;

import com.atzfinance.efinance.dto.InquiryDto;
import com.atzfinance.efinance.model.Inquiry;
import com.atzfinance.efinance.model.User;
import com.atzfinance.efinance.repository.InquiryRepository;
import com.atzfinance.efinance.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InquiryServiceImpl implements InquiryService {

    @Autowired
    private InquiryRepository inquiryRepository;

    @Override
    public void saveInquiry(InquiryDto inquiryDto, User applicantUser) {
        Inquiry inquiry = new Inquiry();
        inquiry.setTitle(inquiryDto.getTitle());
        inquiry.setEmail(inquiryDto.getEmail());
        inquiry.setMessage(inquiryDto.getMessage());

        inquiry.setApplicantName(applicantUser);

        inquiry.setActive(true);
        inquiry.setDate(new Date());
        inquiryRepository.save(inquiry);
    }

    @Override
    public void save(Inquiry inquiry){inquiryRepository.save(inquiry);}

    @Override
    public Optional<Inquiry> getByInquiryid(long inquiryId) {
        return inquiryRepository.findByInquiryid(inquiryId);
    }

//    @Override
//    public Optional<Inquiry> getByInquiryNumber(Long inquiryNumber){
//        return inquiryRepository.findByInquiryNumber(inquiryNumber);
//    }

    @Override
    public List<Inquiry>getAllPendingInquiry(){
        return inquiryRepository.findByActiveTrue();
    }

    @Override
    public boolean respondToInquiry(long inquiryid, String response, User employee) {
        Optional<Inquiry> inquiry = getByInquiryid(inquiryid);
        if (inquiry.isPresent()) {
            inquiry.get().setEmployeeResponse(response);
            inquiry.get().setSigningEmployee(employee);
            inquiry.get().setDidEmployeeRespond(true);
            inquiry.get().setEmployeeResponseDate(new Date());
            inquiry.get().setActive(false);
            inquiryRepository.save(inquiry.get());
            return true;
        }
        return false;
    }

    @Override
    public long getCountOfActiveInquiries() {
        return inquiryRepository.countByActiveTrue();
    }

    @Override
    public List<Inquiry> getCustomersInquiriesByUsername(String username) {
        return inquiryRepository.findByApplicantName_Username(username);
    }


}
