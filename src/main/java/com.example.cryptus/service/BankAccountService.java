package com.example.cryptus.service;

import com.example.cryptus.model.BankAccount;
import com.example.cryptus.repository.BankAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.cryptus.model.BankAccount.MINIMUM_BALANCE;

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

    public void delete(String iban){
        bankAccountRepository.delete(iban);
    }

    public void addFunds(double amount, int id){
        if(amount <= MINIMUM_BALANCE){
            System.out.println("Amount must be greater than Zero");
        }else{
            bankAccountRepository.addFunds(amount, id);

        }

    }
    public void withdrawFunds(double amount, int id){


        bankAccountRepository.withdrawFunds(amount, id);





    }
}
