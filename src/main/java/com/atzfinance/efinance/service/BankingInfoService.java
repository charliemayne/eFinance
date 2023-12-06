package com.atzfinance.efinance.service;

import com.atzfinance.efinance.dto.BankingInfoDto;
import com.atzfinance.efinance.model.BankingInfo;
import com.atzfinance.efinance.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BankingInfoService {
    List<BankingInfo> getCustomersBankingInfo(String username);
    void saveBankingInfo(BankingInfoDto dto, User user);
}
