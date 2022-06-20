package com.example.cryptus.repository;

import com.example.cryptus.dao.*;
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
    private final TransactionDao transactionDaoJdbc;
    private final KoopTransactieDaoJDBC koopTransactieDaoJDBC;
    private final PortefeuilleDAO portefeuilleDAO;

    private final CustomerDaoJdbc customerDaoJdbc;
    private final AssetDaoJdbc assetDaoJdbc;
    private final BankConfigRepository bankConfigRepository;

    @Autowired
    public TransactionRepository(VolledigeTransactionDaoJdbc transactionDaoJdbc,
                                 CustomerDaoJdbc customerDaoJdbc,
                                 AssetDaoJdbc assetDaoJdbc, KoopTransactieDaoJDBC koopTransactieDaoJDBC, PortefeuilleDAO portefeuilleDAO, BankConfigRepository bankConfigRepository) {
        this.transactionDaoJdbc = transactionDaoJdbc;
        this.portefeuilleDAO = portefeuilleDAO;
        this.koopTransactieDaoJDBC = koopTransactieDaoJDBC;
        this.customerDaoJdbc = customerDaoJdbc;
        this.assetDaoJdbc = assetDaoJdbc;
        this.bankConfigRepository = bankConfigRepository;
    }

    public List<Transaction> findKoopTransactions(){
        List<Transaction> transactions = koopTransactieDaoJDBC.findTransactions();
        for (Transaction transaction: transactions) {
            Customer buyer = customerDaoJdbc.findBuyerByTransactionId(transaction.getTransactionId()).orElse(null);
            Asset asset = portefeuilleDAO.findAssetOfTransactie(transaction.getTransactionId()).orElse(null);
            transaction.setBuyer(buyer);
            transaction.setAsset(asset);
            transaction.setFeePercentage(bankConfigRepository.getPercentage());
        }
        return transactions;
    }

    public void createKoopTransactie(Transaction transaction){
        koopTransactieDaoJDBC.createTransaction(transaction);
    }

    public void updateKoopTransactie(Transaction transaction){
        koopTransactieDaoJDBC.update(transaction);
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
    public void updateTransaction(Transaction transaction) {
        transactionDao.update(transaction);
    }
    public void deleteTransaction(int id) {
    }
    public Optional<Transaction> findTransactionById(int transactionId) {
        return transactionDaoJdbc.findTransactionById(transactionId);
        // eerste transactieDao

    }
}
