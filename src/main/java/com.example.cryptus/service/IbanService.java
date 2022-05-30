package com.example.cryptus.service;


import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;

@Service
public class IbanService {
    private final String bankCode = "CRYP";


    public String ibanGenerator(){
        return new Iban.Builder().
                countryCode(CountryCode.NL).
                bankCode(bankCode).
                buildRandom().
                toString();
    }

}
