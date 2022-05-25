package com.example.cryptus.repository;

import com.example.cryptus.dao.AssetDaoJdbc;
import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.TransactionDao;
import com.example.cryptus.dao.TransactionDaoJdbc;
import com.example.cryptus.dto.TransactionDTO;
import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionRepository {

    private TransactionDao transactionDao;
    private TransactionDaoJdbc transactionDaoJdbc;
    private CustomerDaoJdbc customerDaoJdbc;
    private AssetDaoJdbc assetDaoJdbc;

    @Autowired
    public TransactionRepository(TransactionDaoJdbc transactionDaoJdbc,
                                 CustomerDaoJdbc customerDaoJdbc,
                                 AssetDaoJdbc assetDaoJdbc) {
        this.transactionDaoJdbc = transactionDaoJdbc;
        this.customerDaoJdbc = customerDaoJdbc;
        this.assetDaoJdbc = assetDaoJdbc;
    }

    public List<Transaction> findTransactionsByUser(Customer customer) {

        List<Transaction> result = new ArrayList<>();
        List<TransactionDTO> transactions =
                transactionDaoJdbc.findTransactionsByUser(customer);

        for (TransactionDTO t : transactions) {
            // vind transactie met dit id EN en id verkoper
            Optional<Customer> buyer =
                    customerDaoJdbc.findCustomerByIban(t.getKoperIban());
            Optional<Customer> seller =
                    customerDaoJdbc.findCustomerByIban(t.getVerkoperIban());
            Asset asset = assetDaoJdbc.findAssetById(t.getAsset()).get();
            Transaction transaction = new Transaction(
                    t.getTransactionId(), buyer.get(), seller.get(),//wat als
                    // buyer of seller er niet is in de DB
                    asset, t.getAssetammount(), t.getEuroammount(), t.getCommisionPercentage(),
                    t.getTimestamp()
            );
            result.add( transaction);
        }
        return result;
    }

    public void createTransaction(Transaction transaction) {
        transactionDao.createTransaction(transaction);

    }
}
