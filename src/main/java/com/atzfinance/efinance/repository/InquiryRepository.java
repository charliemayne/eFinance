package com.atzfinance.efinance.repository;

import com.atzfinance.efinance.model.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    // Query method to find inquiries by email
    List<Inquiry> findByEmail(String email);

    // Query method to find inquiries by name
    List<Inquiry> findByName(String name);

    // Custom query method to find inquiries by name and email
    List<Inquiry> findByNameAndEmail(String name, String email);

    // Custom query method to find inquiries containing a certain keyword in the message
    List<Inquiry> findByMessageContaining(String keyword);

    // custom query method to find count of active inquiries (for employee dashboard)
    long countByActiveTrue();
}
