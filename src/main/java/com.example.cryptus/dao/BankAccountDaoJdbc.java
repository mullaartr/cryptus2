package com.example.cryptus.dao;


import com.example.cryptus.model.BankAccount;
import com.example.cryptus.model.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Component
public class BankAccountDaoJdbc implements BankAccountDao {

    private JdbcTemplate jdbcTemplate;

    private final Logger logger = LogManager.getLogger(BankAccountDaoJdbc.class);


    public BankAccountDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New BankAccountJdbc");
    }


    RowMapper<BankAccount> rowMapper = (rs, rowNum) -> {
        BankAccount bankAccount = new BankAccount("", 0, 0);
        bankAccount.setIban(rs.getString("iban"));
        bankAccount.setBalance(rs.getDouble("saldo"));
        bankAccount.setUserId(rs.getInt("userId"));

        return bankAccount;
    };

    private PreparedStatement insertUserStatement(BankAccount bankAccount, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT into bankrekening (iban, saldo,userId) values (?, ?, ?)");
        ps.setString(1, bankAccount.getIban());
        ps.setDouble(2, bankAccount.getBalance());
        ps.setInt(3, bankAccount.getUserId());

        return ps;
    }

    @Override
    public Optional<BankAccount> findBankAccountByUserId(int id) {

        String sql = "select * from bankrekening  where userId = ?";
        BankAccount bankAccount = null;
        try {
            bankAccount = jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (DataAccessException exception) {
            logger.info("BankAccount was not found");
        }

        return Optional.ofNullable(bankAccount);

    }

    @Override
    public void addFunds(double amount, int id) {
        String sql = "UPDATE bankrekening set saldo = saldo + ? Where userId = ?";


        try {

            int update = jdbcTemplate.update(sql, amount, id);
            if (update == 1) {
                logger.info("Funds added" );

            }
        } catch (DataAccessException exception) {

            logger.info("BankAccount was not found");

        }

    }



    @Override
    public void withdrawFunds(double amount, int id) {
        String sql = "update bankrekening set saldo = saldo - ? Where userId = ?";

        try {
            int update = jdbcTemplate.update(sql, amount, id);
            if (update == 1) {
                logger.info("Withdraw was successful" );

            }
        } catch (DataAccessException exception) {
            logger.info("BankAccount was not found");
        }



    }

    @Override
    public void store(BankAccount bankAccount) {
        // KeyHolder keyHolder = new GeneratedKeyHolder();
        //jdbcTemplate.update(connection -> insertUserStatement(bankAccount, connection));
        //int newKey = keyHolder.getKey().intValue();
        String sql = "INSERT into bankrekening(iban, saldo, userId) " +
                "values (?, ?, ?)";
        int insert = jdbcTemplate.update(sql, bankAccount.getIban(), bankAccount.getBalance(), bankAccount.getUserId());


        if (insert == 1) {
            logger.info("New customer created" + bankAccount.getUserId());

        }

    }

    @Override
    public List<BankAccount> list() {
        String sql = "SELECT * from bankrekening ";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void update(BankAccount bankAccount) {

        String sql = "UPDATE bankrekening SET  saldo =? WHERE userId = ?";
        int update = jdbcTemplate.update(sql, bankAccount.getBalance(),bankAccount.getUserId());

        if (update == 1) {
            logger.info("BankAccount updated" );

        }
    }

    @Override
    public void delete(int userId) {
        jdbcTemplate.update("DELETE FROM bankrekening WHERE userId= ?", userId);
        logger.info("BankAccount deleted");

    }
}


/*

 @Override
    public void store(BankAccount bankAccount) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertUserStatement(bankAccount, connection), keyHolder);
        int newKey = keyHolder.getKey().intValue();
        String sql = "INSERT into bankrekening(iban, saldo, userId) " +
                "values (?, ?, ?)";
        int insert = jdbcTemplate.update(sql, bankAccount.getIban(), bankAccount.getBalance(), newKey);


        if (insert == 1) {
            logger.info("New customer created" + bankAccount.getUserId());

        }

    }
*/



/*private PreparedStatement insertUserStatement(BankAccount bankAccount, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT into bankrekening (iban, saldo) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, bankAccount.getIban());
        ps.setDouble(2, bankAccount.getBalance());

        return ps;*/


