package com.example.cryptus.dao;

import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Customer;
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
import java.util.Objects;
import java.util.Optional;

@Component
public class TransactionDaoJdbc implements TransactionDao {

    private final Logger logger =
            LoggerFactory.getLogger(TransactionDaoJdbc.class);
    private final JdbcTemplate jdbcTemplate;
    private final CustomerDao customerDao;

    @Autowired
    public TransactionDaoJdbc(JdbcTemplate jdbcTemplate,
                              CustomerDaoJdbc customerDao ) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.customerDao = customerDao;
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

    RowMapper<Transaction> rowMapper = (rs, rownum) -> {
        Transaction transaction = new Transaction();
        transaction.setBuyer(null);
        transaction.setSeller(null);
        transaction.setAsset(null);
        transaction.setTimestamp(rs.getObject("datumtijd", LocalDateTime.class));
        transaction.setCommisionPercentage(rs.getInt("percentage"));
        transaction.setEuroamount(rs.getDouble("euroammount"));
        transaction.setAssetamount(rs.getDouble("assetammount"));
        transaction.setTransactionId(rs.getInt("transactieId"));
        return transaction;
    };

    ResultSetExtractor<List<Transaction>> transactionResultExtractor = rs -> {
        List<Transaction> transactions = new ArrayList<>();
        while (rs.next()) {
            Transaction transaction = new Transaction();
            transaction.setBuyer(null);
            transaction.setSeller(null);
            transaction.setAsset(null);
            transaction.setTimestamp(null);
            transaction.setTransactionId(rs.getInt("transactieId"));
            transactions.add(transaction);
        }
        return transactions;
    };

    ResultSetExtractor<Customer> customerResultSetExtractor = rs -> {
        Customer customer = new Customer();
        customer.setUserId(rs.getInt("userId"));
        return customer;
    };

    ResultSetExtractor<Asset> assetResultExtractor = rs -> {
        Asset asset = new Asset();
        asset.setAssetId(rs.getInt("assetId"));
        asset.setAssetNaam(rs.getString("naam"));
        asset.setAssetAfkorting(rs.getString("afkorting"));
        asset.setSaldo(rs.getDouble("saldo"));

        return asset;
    };

    private Optional<Customer> getCustomer(int transactionId, String sql) {
        Customer customer = null;
        try {
            customer = jdbcTemplate.query(sql, customerResultSetExtractor, transactionId);
        } catch (DataAccessException exception) {
            logger.info("No customer was found found");
        }
        return Optional.of(customer);
    }

    private Optional<Customer> findBuyerByTransaction(int transactionId) {
        String sql = "SELECT userid FROM " +
                "transactie JOIN bankrekening on " +
                "bankrekening.iban = transactie.debitiban WHERE" +
                " transactieId = ?";
        Optional<Customer> customer =  getCustomer(transactionId, sql);
        if (customer.isPresent()) {
             return customerDao.findCustomerById( customer.get().getUserId());
        }
        else return customer;
    }

    private Optional<Customer> findSellerByTransaction(int transactionId) {
        String sql = "SELECT userid FROM transactie JOIN bankrekening on bankrekening.iban = transactie.creditiban WHERE transactieId = ?";
        Optional<Customer> customer =  getCustomer(transactionId, sql);
        if (customer.isPresent()) {
            return customerDao.findCustomerById( customer.get().getUserId());
        }
        else return customer;
    }
    private Optional<Asset> findAssetByTransaction(int transactionId) {
        String sql = "SELECT assetId FROM transactie JOIN bankrekening on " +
                "bankrekening.iban = transactie.creditiban OR bankrekening" +
                ".iban = transactie.debitiban WHERE" +
                " transactieId = ?";
        Asset asset = new Asset();
        try {
            asset = jdbcTemplate.query(sql, assetResultExtractor, transactionId);
        } catch (DataAccessException exception) {
            logger.info("no assets where found found");
        }
        return Optional.of(asset);
    }

    @Override
    public List<Transaction> findBuyTransactionsByUser(int userId) {
        return jdbcTemplate.query
                ("SELECT * FROM " +
                                "transactie JOIN bankrekening on " +
                                "bankrekening.iban = transactie.debitiban WHERE userId = ?",
                        rowMapper, userId);
    }

    @Override
    public List<Transaction> findSellTransactionsByUser(int userId) {
        return jdbcTemplate.query
                ("SELECT * FROM " +
                                "transactie JOIN bankrekening on " +
                                "bankrekening.iban = transactie.creditiban " +
                                "WHERE userId = ?",
                        rowMapper, userId);
    }

    @Override
    public Optional<Transaction> findTransactionById(int transactionId) {
        String Sql = "select * from transactie t where t.transactieId = ?";
        Transaction transaction = new Transaction();
        try {
            transaction = jdbcTemplate.queryForObject(Sql, rowMapper, transactionId);
            transaction.setBuyer(findBuyerByTransaction(transactionId).orElse(null));
            transaction.setSeller(findSellerByTransaction(transactionId).orElse(null));
            transaction.setAsset(findAssetByTransaction(transactionId).orElse(null));
        } catch (DataAccessException exception) {
            logger.info("Transaction was not found");
        }
        return Optional.of(transaction);
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
}
