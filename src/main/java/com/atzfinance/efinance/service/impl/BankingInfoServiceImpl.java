package com.atzfinance.efinance.service.impl;

import com.atzfinance.efinance.model.BankingInfo;
import com.atzfinance.efinance.repository.BankingInfoRepository;
import com.atzfinance.efinance.service.BankingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankingInfoServiceImpl implements BankingInfoService {
    @Autowired
    private BankingInfoRepository bankingInfoRepository;
    @Override
    public List<BankingInfo> getCustomersBankingInfo(String username) {
        return bankingInfoRepository.findByCustomer_Username(username);
    }
}
