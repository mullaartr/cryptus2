package com.example.cryptus.repository;

import com.example.cryptus.dao.*;
import com.example.cryptus.dto.TransactionDTO;
import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionRepository {

    private VolledigeTransactionDaoJdbc transactionDao;
    private final TransactionDao transactionDaoJdbc;
    private final KoopTransactieDaoJDBC koopTransactieDaoJDBC;
    private final PortefeuilleDAO portefeuilleDAO;

    private final CustomerDaoJdbc customerDaoJdbc;
    private final AssetDaoJdbc assetDaoJdbc;

    private final BankAccountDao bankAccountDao;


    private final VerkoopTransactieDaoJdbc verkoopTransactieDaoJdbc;

    @Autowired
    public TransactionRepository(VolledigeTransactionDaoJdbc transactionDaoJdbc,
                                 CustomerDaoJdbc customerDaoJdbc,
                                 AssetDaoJdbc assetDaoJdbc, KoopTransactieDaoJDBC koopTransactieDaoJDBC, PortefeuilleDAO portefeuilleDAO, VerkoopTransactieDaoJdbc verkoopTransactieDaoJdbc, BankAccountDao bankAccountDao) {
        this.transactionDaoJdbc = transactionDaoJdbc;
        this.portefeuilleDAO = portefeuilleDAO;
        this.koopTransactieDaoJDBC = koopTransactieDaoJDBC;
        this.customerDaoJdbc = customerDaoJdbc;
        this.assetDaoJdbc = assetDaoJdbc;
        this.verkoopTransactieDaoJdbc = verkoopTransactieDaoJdbc;
        this.bankAccountDao = bankAccountDao;
    }

    public List<Transaction> findVerKoopTransactions(){
        List<Transaction> transactions = verkoopTransactieDaoJdbc.findTransactions();
        for (Transaction transaction: transactions) {
            Customer seller = customerDaoJdbc.findSellerByTransactionId(transaction.getTransactionId()).orElse(null);
            seller.setPortefeuille(portefeuilleDAO.findPortefeuilleById(seller.getUserId()).orElse(null));
            seller.setBankAccount(bankAccountDao.findBankAccountByUserId(seller.getUserId()).orElse(null));
            Asset asset = portefeuilleDAO.findAssetOfVerkoopTransactie(transaction.getTransactionId()).orElse(null);
            transaction.setBuyer(seller);
            transaction.setAsset(asset);
        }
        return transactions;
    }

    public List<Transaction> findKoopTransactions(){
        List<Transaction> transactions = koopTransactieDaoJDBC.findTransactions();
        for (Transaction transaction: transactions) {
            Customer buyer = customerDaoJdbc.findBuyerByTransactionId(transaction.getTransactionId()).orElse(null);
            buyer.setPortefeuille(portefeuilleDAO.findPortefeuilleOf(buyer.getUserId()).get());
            Asset asset = portefeuilleDAO.findAssetOfKoopTransactie(transaction.getTransactionId()).orElse(null);
            transaction.setBuyer(buyer);
            transaction.setAsset(asset);
        }
        return transactions;
    }

    public void createKoopTransactie(Transaction transaction){
        koopTransactieDaoJDBC.createTransaction(transaction);
    }

    public void createVerkoopTransactie(Transaction transaction){
        verkoopTransactieDaoJdbc.createTransaction(transaction);
    }

    public void updateKoopTransactie(Transaction transaction){
        koopTransactieDaoJDBC.update(transaction);
    }

    public void updateVerkoopTransactie(Transaction transaction){
        verkoopTransactieDaoJdbc.update(transaction);
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
    }
}
