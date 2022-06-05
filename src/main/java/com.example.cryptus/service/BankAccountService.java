package com.example.cryptus.service;

import com.example.cryptus.model.BankAccount;
import com.example.cryptus.repository.BankAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    private final Logger logger =  LogManager.getLogger(BankAccountService.class);
    private BankAccountRepository  bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
        logger.info("New BankAccountService");
    }

    public Optional<BankAccount> findBankaccountByUserId(int id) {
        Optional<BankAccount> bankAccountOptional =bankAccountRepository.findBankaccountByUserId(id);
        if(bankAccountOptional.isEmpty()){
            return Optional.empty();

        }else{
            return bankAccountRepository.findBankaccountByUserId(id);

        }

    }
    public void store(BankAccount bankAccount){
        bankAccountRepository.store(bankAccount);
    }

    public void update(BankAccount bankAccount){
        bankAccountRepository.update(bankAccount);

    }

    public List<BankAccount> list(){
        return bankAccountRepository.list();
    }

    public void delete(int userId){
        bankAccountRepository.delete(userId);
    }

    Optional<BankAccount> addFunds(double amount, int id){
        return  bankAccountRepository.addFunds(amount, id);

    }
    Optional<BankAccount> withdrawFunds(double amount, int id){
        return bankAccountRepository.withdrawFunds(amount, id);

    }
}
