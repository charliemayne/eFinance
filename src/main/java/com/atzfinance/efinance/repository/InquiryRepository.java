package com.atzfinance.efinance.repository;

import com.atzfinance.efinance.model.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    // Method to find inquiries by ID
    Optional<Inquiry> findByInquiryid(Long id);

    // custom query method to find count of active inquiries (for employee dashboard)
    long countByActiveTrue();
    long countByDidEmployeeRespondFalse();

    List<Inquiry> findByApplicantName_Username(String username);
    List<Inquiry> findByActiveTrue();
    List<Inquiry> findByDidEmployeeRespondFalse();

    Optional<Inquiry>findByActive(boolean status);
}
