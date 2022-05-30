package com.example.cryptus.dao;

import com.example.cryptus.dto.TransactionDTO;
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
            ps.setString(4, transaction.getSeller().getBankAccount().getIban());
            ps.setString(5, transaction.getBuyer().getBankAccount().getIban());
            ps.setDouble(6, transaction.getEuroamount());
            ps.setInt(7,
                    transaction.getBuyer().getPortefeuille().getPortefeuilleId());
            ps.setInt(8,
                    transaction.getSeller().getPortefeuille().getPortefeuilleId());
            ps.setInt(9, transaction.getAsset().getAssetId());
            ps.setDouble(10, transaction.getAssetamount());
            return ps;
        } catch (SQLException e) {
            logger.error("SQL fout tegengekomen");
            e.printStackTrace();
            System.exit(-99);
        }
        return null;
    }

    private static class TransactionDTORowMapper implements RowMapper<TransactionDTO> {
        @Override
        public TransactionDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {

            int transactieId = resultSet.getInt("transactieid");
            LocalDateTime timestamp = resultSet.getObject("datumtijd", LocalDateTime.class);
            int percentage = resultSet.getInt("percentage");
            String creditIban = resultSet.getString("creditiban");
            String debitIban = resultSet.getString("debitiban");
            double euroammount = resultSet.getDouble("euroammount");
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
// we moeten nog nadenken over wat er precies moet gebeuren bij een update
// van de transactie. Is dat alleen het aanpassen van de hoeveelheid crypto's?
    @Override
    public void update(Transaction transaction, int transactionId) {
//        jdbcTemplate.update("UPDATE transactie SET  = ? WHERE " +
//                "transactieId = ?",  transaction, transactionId);

    }

    @Override
    public void deleteTransaction(int transactionId) {
        jdbcTemplate.update("DELETE FROM transactie WHERE transactieId = ?",transactionId);
    }

    // ik krijg onderstaande methode niet aan de praat. Hier moet ik hulp
    // vragen van de docent.
    @Override
    public List<TransactionDTO> findTransactionsByUser(int userId) {
        List<TransactionDTO> transactions = jdbcTemplate.query("SELECT * FROM " +
                        "transactie JOIN bankrekening on " +
                        "bankrekening.iban = transactie.debitiban WHERE userId = ?",
                new TransactionDTORowMapper(), userId);
        return transactions;
    }

    @Override
    public List<TransactionDTO> findTransactionsByUser(Customer user) {
        return jdbcTemplate.query("SELECT * FROM " +
                        "transactie JOIN bankrekening on " +
                        "bankrekening.iban = transactie.debitiban OR " +
                        "bankrekening.iban = transactie.creditiban WHERE" +
                        " userId = ?",
                new TransactionDTORowMapper(), user.getUserId());
    }
    // ik krijg onderstaande methode niet aan de praat. Hier moet ik hulp
    // vragen van de docent.
    @Override
    public Optional<TransactionDTO> findTransactionById(int transactionId) {
//        TransactionDTO transaction =
//                jdbcTemplate.query("select * from transactie where " +
//                                "transactieId = ?",
//                        new TransactionDTORowMapper(),transactionId);
//        if (transaction.getTransactionId()) {
//            return Optional.of(transaction.);
//        } else {
//            return Optional.empty();
//        }
        return null;
    }
}
