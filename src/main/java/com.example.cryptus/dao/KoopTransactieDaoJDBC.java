package com.example.cryptus.dao;

import com.example.cryptus.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class KoopTransactieDaoJDBC implements TransactionDao{

    private final Logger logger =
            LoggerFactory.getLogger(VolledigeTransactionDaoJdbc.class);
    private final JdbcTemplate jdbcTemplate;
    private final CustomerDao customerDao;

    @Autowired
    public KoopTransactieDaoJDBC(JdbcTemplate jdbcTemplate,
                                       CustomerDaoJdbc customerDao ) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.customerDao = customerDao;
        logger.info("Nieuwe KooptransactionDaoJdbc");
    }

    private PreparedStatement insertTransactionStatement(Transaction transaction, Connection connection) throws SQLException {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO " +
                            "transactie (datumtijd, kosten, " +
                            "debitiban,euroammount, debitPortefeuilleID," +
                            "debitassetId, " +
                            "assetammount) " +
                            "VALUES (?,?,?,?,?,?,?) ",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, transaction.getTimestamp().toString());
            ps.setDouble(2, transaction.calcCommission());
            ps.setString(3, transaction.getBuyer().getBankAccount().getIban());
            ps.setDouble(4, transaction.getEuroamount());
            ps.setInt(5, transaction.getBuyer().getPortefeuille().getPortefeuilleId());
            ps.setInt(6, transaction.getAsset().getAssetId());
            ps.setDouble(7, transaction.getAssetamount());
            return ps;
        } catch (SQLException e) {
            logger.error("SQL fout tegengekomen");
            e.printStackTrace();
            System.exit(-99);
        }
        return null;
    }
    RowMapper<Transaction> rowMapper = (rs, rownum) -> {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(rs.getInt("transactieId"));
        transaction.setBuyer(null);
        transaction.setSeller(null);
        transaction.setAsset(null);
        transaction.setTimestamp(rs.getObject("datumtijd", LocalDateTime.class));
        transaction.setEuroamount(rs.getDouble("euroammount"));
        transaction.setAssetamount(rs.getDouble("assetammount"));
        return transaction;
    };

    ResultSetExtractor<List<Transaction>> transactionResultExtractor = rs -> {
        List<Transaction> transactions = new ArrayList<>();
        while (rs.next()) {
            Transaction transaction = new Transaction();
            transaction.setBuyer(null);
            transaction.setAsset(null);
            transaction.setTimestamp(rs.getTimestamp(2).toLocalDateTime());
            transaction.setTransactionId(rs.getInt("transactieId"));
            transaction.setEuroamount(rs.getDouble("euroammount"));
            transaction.setAssetamount(rs.getDouble("assetammount"));
            transactions.add(transaction);
        }
        return transactions;
    };


    @Override
    public List<Transaction> findTransactions() {
        String sql = "SELECT * FROM transactie where creditIban is null and creditportefeuilleID is null and creditassetId is null; ";
        List<Transaction> transactions = new ArrayList<>();
        try {
            transactions = jdbcTemplate.query(sql, transactionResultExtractor);
        } catch (DataAccessException exception) {
            logger.info("no assets where found found");
        }
        return transactions;
    }


    @Override
    public List<Transaction> findBuyTransactionsByUser(int userId) {
        return null;
    }

    @Override
    public List<Transaction> findSellTransactionsByUser(int userId) {
        return null;
    }

    @Override
    public int createTransaction(Transaction transaction) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertTransactionStatement(transaction, connection), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void update(Transaction transaction) {
        String sql = "Update transactie SET creditportefeuilleID = ?, creditassetId = ?, creditIban = ?, euroammount = ?, kosten = ? where transactieId = ?";
        int update = jdbcTemplate.update(sql, transaction.getBuyer().getPortefeuille().getPortefeuilleId(), transaction.getAsset().getAssetId(),
                transaction.getBuyer().getBankAccount().getIban(), transaction.getEuroamount(), transaction.getFeePercentage(), transaction.getTransactionId());
        if (update == 1) {
            logger.info("Transactie updated");
        }
    }

    @Override
    public void deleteTransaction(int transactionId) {
        jdbcTemplate.update("DELETE FROM transactie WHERE transactieId = ?", transactionId);
    }

    @Override
    public Optional<Transaction> findTransactionById(int transactionId) {
        String sql = "SELECT * FROM transactie where transactieId = ? and creditassetId is null and creditportefeuilleID is null and creditassetId is null; ";
        Transaction transactions = null;
        try {
            transactions = jdbcTemplate.queryForObject(sql, rowMapper, transactionId);
        } catch (DataAccessException exception) {
            logger.info("no assets where found found");
        }
        return Optional.of(transactions);
    }

}
