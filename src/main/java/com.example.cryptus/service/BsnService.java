package com.example.cryptus.service;

import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
@Service
public class BsnService {
    private static final int[] BSN_ONDNR_MULTIPLIERS = {9, 8, 7, 6, 5, 4, 3, 2, -1};
    private final int uitkomst = 0;
    private final Logger logger = LogManager.getLogger(BsnService.class);



    public BsnService() {
        logger.info("new BsnService created");
    }

    public boolean isBsn(String digits){
        if(digits.length() == 9){
            return isElfProef(getDigits(digits), BSN_ONDNR_MULTIPLIERS);
        } else {
            return false;
        }

    }

    private boolean isElfProef(int[] digits, int[] multipliers)
    {
        int sum = 0;
        for (int i = 0; i < multipliers.length; i++) {
            sum += digits[i] * multipliers[i];
        }
        return sum % 11 == uitkomst;
    }

    private int[] getDigits(String nummer)
    {
        int[] digits = new int[9];
        for (int i = 0; i < nummer.length() ; i++) {
            digits[i] = Character.getNumericValue(nummer.charAt(i));
        }

        return digits;
    }
}
