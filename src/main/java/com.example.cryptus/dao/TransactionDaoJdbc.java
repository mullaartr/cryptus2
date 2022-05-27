package com.example.cryptus.dao;

import com.example.cryptus.dto.TransactionDTO;
import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class TransactionDaoJdbc implements TransactionDao {

    private final Logger logger =
            LoggerFactory.getLogger(TransactionDaoJdbc.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionDaoJdbc(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("Nieuwe TransactionDaoJdbc");
    }

    private PreparedStatement insertTransactionStatement(Transaction transaction, Connection connection) throws SQLException {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO " +
                            "transactie (datumtijd, kosten, percentage, creditiban, debitiban,euroammount, debitPortefeuilleID,creditportefeuilleID1,AssetId, assetammount) VALUES (?,?,?,?,?,?,?,?,?,?) ",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, transaction.getTimestamp().toString());
            ps.setDouble(2, transaction.calcCommission());
            ps.setInt(3, transaction.getCommisionPercentage());
            ps.setString(4, transaction.getVerkoper().getBankAccount().getIban());
            ps.setString(5, transaction.getKoper().getBankAccount().getIban());
            ps.setDouble(6, transaction.getEuroammount());
            ps.setInt(7,
                    transaction.getKoper().getPortefeuille().getPortefeuilleId());
            ps.setInt(8,
                    transaction.getVerkoper().getPortefeuille().getPortefeuilleId());
            ps.setInt(9, transaction.getAsset().getAssetId());
            ps.setDouble(10, transaction.getAssetammount());
            return ps;
        } catch (SQLException e) {
            logger.error("SQL fout tegengekomen");
            e.printStackTrace();
            System.exit(-99);
        }
        return null;
    }

    private static class TransactionDTORowMapper implements RowMapper<TransactionDTO> {

        private final Customer user;

        public TransactionDTORowMapper(Customer user) {
            this.user = user;
        }

        @Override
        public TransactionDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {

            int transactieId = resultSet.getInt("transactieid");
            LocalDateTime timestamp = resultSet.getObject("datumtijd", LocalDateTime.class);
            double kosten = resultSet.getDouble("kosten");
            int percentage = resultSet.getInt("percentage");
            String creditIban = resultSet.getString("creditiban");
            String debitIban = resultSet.getString("debitiban");
            double euroammount = resultSet.getDouble("euroammount");
            String debitportefeuilleId = resultSet.getString("debitportefeuilleid");
            String creditportefeuilleId = resultSet.getString("creditportefeuilleid1");
            int assetId = resultSet.getInt("assetid");
            double assetammount = resultSet.getDouble("assetammount");

            return new TransactionDTO(transactieId, creditIban,
                    debitIban, assetId, assetammount, euroammount, percentage,
                    timestamp);
        }
    }

    @Override
    public void createTransaction(Transaction transaction) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertTransactionStatement(transaction, connection), keyHolder);
        int newKey = Objects.requireNonNull(keyHolder.getKey()).intValue();
        transaction.setTransactionId(newKey);
    }

    @Override
    public void update(int transactionId, int assetAmount) {
        jdbcTemplate.update("UPDATE transactie SET assetammount = ? WHERE " +
                "transactieId = ?", assetAmount, transactionId);

    }

    @Override
    public void deleteTransaction(int transactionId) {
        jdbcTemplate.update("DELETE FROM transactie WHERE transactieId = ?",transactionId);
    }


//    @Override
//    public List<TransactionDTO> findTransactionsByUser(int userId) {
//        List<TransactionDTO> transactions = jdbcTemplate.query("SELECT * FROM " +
//                        "transactie JOIN bankrekening on " +
//                        "bankrekening.iban = transactie.debitiban WHERE userId = ?",
//                new TransactionDTORowMapper(), userId);
//        return transactions;
//    }

    @Override
    public List<TransactionDTO> findTransactionsByUser(Customer user) {
        return jdbcTemplate.query("SELECT * FROM " +
                        "transactie JOIN bankrekening on " +
                        "bankrekening.iban = transactie.debitiban OR " +
                        "bankrekening.iban = transactie.creditiban WHERE" +
                        " userId = ?",
                new TransactionDTORowMapper(user), user.getUserId());
    }

    @Override
    public Optional<Transaction> findTransactionById(int transactionId) {
//        List<TransactionDTO> transactions =
//                jdbcTemplate.query ("select * from transactie where " +
//                                "transactieId = ?",new TransactionDTORowMapper(),
//                        transactionId);
//        if (transactions.size() != 1) {
//            return Optional.empty();
//        } else {
//            return Optional.of(transactions.get(0));
//        }
        return Optional.empty();
    }
}
