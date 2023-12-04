package com.atzfinance.efinance.repository;

import com.atzfinance.efinance.model.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    // Method to find inquiries by ID
    Optional<Inquiry> findByInquiryNumber(Long id);

    // custom query method to find count of active inquiries (for employee dashboard)
    long countByActiveTrue();
}
