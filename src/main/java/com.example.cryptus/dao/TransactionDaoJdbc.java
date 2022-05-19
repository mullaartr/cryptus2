package com.example.cryptus.dao;

import com.example.cryptus.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public class TransactionDaoJdbc implements TransactionDao {

    private final Logger logger = LoggerFactory.getLogger(TransactionDaoJdbc.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionDaoJdbc(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("Nieuwe TransactionDaoJdbc");
    }

    private PreparedStatement insertTransactionStatement(Transaction transaction, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO transactie (datumtijd, kosten, creditiban, debitiban, euroammount, debitPortefeuilleID, creditportefeuilleID1, AssetId, assetammount) VALUES (?,?,?,?,?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, transaction.getTimestamp().toString());
        ps.setDouble(2, transaction.calcCommission());
        // ps.setInt(3,transaction.getVerkoper().);hier komt de getter getCreditIban.
        //ps.setInt(4,transaction.getKoper().);  hier komt de getter getDebitIban.
        ps.setDouble(5, transaction.getEuroammount());
        //ps.setInt(6, transaction.getKoper()); hier komt de getter getDebitPortefeuilleID.
        //ps.setInt(7, transaction.getVerkoper()); hier komt de getter getCreditPortefeuilleID.
        ps.setInt(8, transaction.getAsset().getAssetId());
        ps.setDouble(9, transaction.getAssetammount());
        return ps;
    }

    private static class TransactionRowMapper implements RowMapper<Transaction> {
        @Override
        public Transaction mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int transactieId = resultSet.getInt("transactieid");
            LocalDateTime timestamp= resultSet.getObject("datumtijd", LocalDateTime.class);
            double kosten = resultSet.getDouble("kosten");
            String creditIban = resultSet.getString("creditiban");
            String debitIban = resultSet.getString("debitiban");
            double euroammount = resultSet.getDouble("euroammount");
              int percentage = resultSet.getInt("percentage");
            String debitportefeuilleId = resultSet.getString("debitportefeuilleid");
            String creditportefeuilleId = resultSet.getString("creditportefeuilleid");
            int assetId = resultSet.getInt("assetid");
            double assetammount = resultSet.getDouble("assetammount");

            Transaction transaction = new Transaction(transactieId, null, null,null,assetammount,euroammount,0,timestamp);
            return transaction;
        }
    }

    @Override
    public void storeTransaction(Transaction transaction) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertTransactionStatement(transaction, connection), keyHolder);
        int newKey = keyHolder.getKey().intValue();
        transaction.setTransactionId(newKey);
    }

    @Override
    public void update(Transaction transaction) {

    }

    @Override
    public void deleteTransaction(int transactionId) {
        jdbcTemplate.update("DELETE FROM transactie WHERE transactieId = ?", transactionId);
    }

    @Override
    public List<Transaction> findTransactionsByUser(int userId) {
        List<Transaction> transactions = jdbcTemplate.queryForList("SELECT * FROM transactie JOIN bankrekening WHERE userId = ?", Transaction.class, userId);
        return transactions;
    }

    @Override
    public Optional<Transaction> findTransactionById(int transactionId) {
        List<Transaction> transactions =
                jdbcTemplate.query("select * from transactie where transactieId = ?",new TransactionRowMapper(),
                        transactionId);
        if (transactions.size() != 1) {
            return Optional.empty();
        } else {
            return Optional.of(transactions.get(0));
        }
    }
}
