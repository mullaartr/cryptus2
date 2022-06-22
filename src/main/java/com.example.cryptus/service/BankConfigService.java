package com.example.cryptus.service;

import com.example.cryptus.repository.BankConfigRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankConfigService {

    private BankConfigRepository bankConfigRepository;
    private final Logger logger =
            LogManager.getLogger(BankConfigService.class);

    @Autowired
    public BankConfigService(BankConfigRepository bankConfigRepository) {
        super();
        this.bankConfigRepository = bankConfigRepository;
        logger.info("nieuwe ConfigurationService created");
    }

    public void updatePercentage(double percentage) {
        bankConfigRepository.updatePercentage(percentage);

    }
    public double getPercentage(){
        return bankConfigRepository.getPercentage();
    }

}
