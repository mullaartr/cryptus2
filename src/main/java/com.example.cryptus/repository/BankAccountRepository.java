package com.example.cryptus.repository;
import com.example.cryptus.dao.BankAccountDao;
import com.example.cryptus.model.BankAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class BankAccountRepository {

    BankAccountDao bankAccountDao;
    Logger logger = LogManager.getLogger(BankAccountRepository.class);

    public BankAccountRepository(BankAccountDao bankAccountDao) {
        this.bankAccountDao = bankAccountDao;
        logger.info("New BankAccountRepository");
    }


    public Optional<BankAccount> findBankaccountByUserId(int id) {
        Optional<BankAccount> bankAccountOptional =bankAccountDao.findBankAccountByUserId(id);
        if(bankAccountOptional.isEmpty()){
            return Optional.empty();

        }else{
            return bankAccountDao.findBankAccountByUserId(id);

        }

    }
    public void store(BankAccount bankAccount){
        bankAccountDao.store(bankAccount);
    }

    public void update(BankAccount bankAccount){
        bankAccountDao.update(bankAccount);

    }

    public List<BankAccount> list(){
        return bankAccountDao.list();
    }

    public void delete(int userId){
        bankAccountDao.delete(userId);
    }

    public void addFunds(double amount, int id){


        bankAccountDao.addFunds(amount, id);

    }
    public void withdrawFunds(double amount, int id){
        bankAccountDao.withdrawFunds(amount, id);

    }



}
