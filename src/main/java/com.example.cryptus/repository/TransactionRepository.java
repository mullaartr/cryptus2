package com.example.cryptus.repository;

import com.example.cryptus.dao.AssetDaoJdbc;
import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dao.TransactionDao;
import com.example.cryptus.dao.TransactionDaoJdbc;
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
    private final TransactionDaoJdbc transactionDaoJdbc;
    private final CustomerDaoJdbc customerDaoJdbc;
    private final AssetDaoJdbc assetDaoJdbc;

    @Autowired
    public TransactionRepository(TransactionDaoJdbc transactionDaoJdbc,
                                 CustomerDaoJdbc customerDaoJdbc,
                                 AssetDaoJdbc assetDaoJdbc) {
        this.transactionDaoJdbc = transactionDaoJdbc;
        this.customerDaoJdbc = customerDaoJdbc;
        this.assetDaoJdbc = assetDaoJdbc;
    }
    public List<Transaction> getBuyTransactionsFromUser(int userId) {
        List<Transaction> result = new ArrayList<>();
        List<Transaction> transactions =
                transactionDaoJdbc.findBuyTransactionsByUser(userId);
        fillInTransactionDetails(result, transactions);
        return result;
    }
    public List<Transaction> getSellTransactionsFromUser(int userId) {

        List<Transaction> result = new ArrayList<>();
        List<Transaction> transactions =
                transactionDaoJdbc.findSellTransactionsByUser(userId);

        fillInTransactionDetails(result, transactions);
        return result;
    }

    private void fillInTransactionDetails(List<Transaction> result, List<Transaction> transactions) {
        for (Transaction t : transactions) {
            Optional<Customer> buyer =
                    customerDaoJdbc.findBuyerByTransactionId(t.getTransactionId() );
            Optional<Customer> seller =
                    customerDaoJdbc.findSellerByTransactionId(t.getTransactionId() );
            Optional<Asset> asset =
                    assetDaoJdbc.findAssetByTransactionId(t.getTransactionId());
            if (buyer.isEmpty() || seller.isEmpty() || asset.isEmpty()) {
                continue;
            }
            Transaction transaction = new Transaction(
                    t.getTransactionId(), buyer.orElse(null),
                    seller.orElse(null),
                    asset.orElse(null),
                    t.getAssetamount(), t.getEuroamount(),
                    t.getFeePercentage(),
                    t.getTimestamp());
            result.add(transaction);
        }
    }



    public void createTransaction(Transaction transaction) {
        transactionDaoJdbc.createTransaction(transaction);
    }
    public void updateTransaction(Transaction transaction,
                                  int transactionId) {
        transactionDao.update(transaction,transactionId);
    }
    public void deleteTransaction(int id) {
    }
    public Optional<Transaction> findTransactionById(int transactionId) {
        Optional<Transaction> opt =
                transactionDao.findTransactionById(transactionId);
        if(opt.isEmpty()){
            return Optional.empty();

        }else{
            return transactionDao.findTransactionById(transactionId);
        }
    }
}
