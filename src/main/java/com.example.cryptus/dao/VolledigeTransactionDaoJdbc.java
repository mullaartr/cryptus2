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
public class VolledigeTransactionDaoJdbc implements TransactionDao {
    private final Logger logger =
            LoggerFactory.getLogger(VolledigeTransactionDaoJdbc.class);
    private final JdbcTemplate jdbcTemplate;
    private final CustomerDao customerDao;
    @Autowired
    public VolledigeTransactionDaoJdbc(JdbcTemplate jdbcTemplate,
                                       CustomerDaoJdbc customerDao ) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.customerDao = customerDao;
        logger.info("Nieuwe transactionDaoJdbc");
    }
    private PreparedStatement insertTransactionStatement(Transaction transaction, Connection connection) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO " +
                            "transactie (datumtijd, kosten, creditiban, " +
                            "debitiban,euroammount, debitPortefeuilleID," +
                            "debitassetId,creditportefeuilleID,creditassetId, " +
                            "assetammount) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?) ",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, transaction.getTimestamp().toString());
            ps.setDouble(2, transaction.calcCommission());
            ps.setString(3, transaction.getSeller().getBankAccount().getIban());
            ps.setString(4, transaction.getBuyer().getBankAccount().getIban());
            ps.setDouble(5, transaction.getEuroamount());
            ps.setInt(6, transaction.getBuyer().getPortefeuille().getPortefeuilleId());
            ps.setInt(7, transaction.getAsset().getAssetId());
            ps.setInt(8, transaction.getSeller().getPortefeuille().getPortefeuilleId());
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
        transaction.setTransactionId(rs.getInt("transactieId"));
        transaction.setBuyer(null);
        transaction.setSeller(null);
        transaction.setAsset(null);
        transaction.setTimestamp(rs.getObject("datumtijd", LocalDateTime.class));
        //transaction.setFeePercentage(rs.getInt("percentage"));
        transaction.setEuroamount(rs.getDouble("euroammount"));
        transaction.setAssetamount(rs.getDouble("assetammount"));
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
        while ( rs.next() ) {
            customer.setUserId(rs.getInt("userId"));
        }
        return customer;
    };
    ResultSetExtractor<Asset> assetResultExtractor = rs -> {
        Asset asset = new Asset();
        while ( rs.next() ) {
            asset.setAssetId(rs.getInt("assetId"));
            asset.setAssetNaam(rs.getString("naam"));
            asset.setAssetAfkorting(rs.getString("afkorting"));
        }
        return asset;
    };
    private Optional<Customer> getCustomer(int transactionId, String sql) {
        //15-6-2022: Customer customer = null aangepast in;
        Customer customer = new Customer();
        try {
            customer = jdbcTemplate.query(sql, customerResultSetExtractor, transactionId);
        } catch (DataAccessException exception) {
            logger.info("No customer was found found");
        }
        assert customer != null;
        return Optional.of(customer);
    }
    private Optional<Customer> findBuyerByTransaction(int transactionId) {
        String sql = "SELECT userid FROM " +
                "transactie JOIN bankrekening on " +
                "bankrekening.iban = transactie.debitiban WHERE" +
                " transactieId = ?";
        Optional<Customer> customer =  getCustomer(transactionId, sql);
        if (customer.isPresent()) {
             return customerDao.findCustomerById(customer.get().getUserId());
        }
        else return customer;
    }
    private Optional<Customer> findSellerByTransaction(int transactionId) {
        String sql = "SELECT userid FROM transactie JOIN bankrekening on bankrekening.iban = transactie.creditiban WHERE transactieId = ?";
        Optional<Customer> customer =  getCustomer(transactionId, sql);
        if (customer.isPresent()) {
            return customerDao.findCustomerById(customer.get().getUserId());
        }
        else return customer;
    }
    private Optional<Asset> findAssetByTransaction(int transactionId) {
        String sql = "SELECT assetId, naam, afkorting  FROM transactie JOIN " +
                "bankrekening join asset on " +
                "bankrekening.iban = transactie.creditiban OR bankrekening" +
                ".iban = transactie.debitiban WHERE assetId = " +
                "debitAssetId and " +
                " transactieId = ?";
        Asset asset = new Asset();
        try {
            asset = jdbcTemplate.query(sql, assetResultExtractor, transactionId);
        } catch (DataAccessException exception) {
            logger.info("no assets where found found");
        }
        assert asset != null;
        return Optional.of(asset);
    }
    @Override
    public List<Transaction> findTransactions() {
        String sql = "SELECT * FROM transactie";
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
            assert transaction != null;
            transaction.setBuyer(findBuyerByTransaction(transactionId).orElse(null));
            transaction.setSeller(findSellerByTransaction(transactionId).orElse(null));
            transaction.setAsset(findAssetByTransaction(transactionId).orElse(null));
        } catch (DataAccessException exception) {
            logger.info("Transaction was not found");
        }
        return Optional.of(transaction);
    }
    @Override
    public int createTransaction(Transaction transaction) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertTransactionStatement(transaction, connection), keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
    @Override
    public void update(Transaction transaction) {
    }
    @Override
    public void deleteTransaction(int transactionId) {
        jdbcTemplate.update("DELETE FROM transactie WHERE transactieId = ?",transactionId);
    }
}
