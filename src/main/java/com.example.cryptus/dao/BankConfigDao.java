package com.example.cryptus.dao;

import com.example.cryptus.model.BankConfig;

import java.util.List;

public interface BankConfigDao {

    void updatePercentage(double percentage);

    public double getPercentage();
}
