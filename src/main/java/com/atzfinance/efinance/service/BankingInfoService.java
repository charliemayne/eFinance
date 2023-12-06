package com.atzfinance.efinance.service;

import com.atzfinance.efinance.model.BankingInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BankingInfoService {
    List<BankingInfo> getCustomersBankingInfo(String username);
}
