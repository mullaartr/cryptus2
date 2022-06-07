package com.example.cryptus.repository;
import com.example.cryptus.dao.BankConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class BankConfigRepository {
    private final BankConfigDao bankConfigDao;

    @Autowired
    public BankConfigRepository(BankConfigDao bankConfigDao) {
        this.bankConfigDao = bankConfigDao;
    }
    public void updatePercentage(double percentage) {
        bankConfigDao.updatePercentage(percentage);
    }
    public double getPercentage() {
        return bankConfigDao.getPercentage();

    }

}
