package com.atzfinance.efinance.repository;

import com.atzfinance.efinance.model.BankingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankingInfoRepository extends JpaRepository<BankingInfo, Long> {
    List<BankingInfo> findByCustomer_Username(String username);
}
