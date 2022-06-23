package com.example.cryptus.dao;

import com.example.cryptus.model.BankConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class BankConfigDaoJDBC implements BankConfigDao {
    private final JdbcTemplate jdbcTemplate;
    private final Logger logger = LogManager.getLogger(PortefeuilleDAOJdbc.class);
    @Autowired
    public BankConfigDaoJDBC(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("new PortefeuilleDAOJdbc");
    }
    RowMapper<BankConfig> rowMapper = (rs, rowNum) -> {
        BankConfig bankConfig = new BankConfig();
        bankConfig.setPercentage(rs.getDouble("value"));
        return bankConfig;
    };
    private PreparedStatement insertConfigurationStatement(BankConfig bankConfig,
                                                           Connection connection) throws SQLException {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO " +
                            "bankinstelling (`value`) VALUES (?) ",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, bankConfig.getPercentage());
            return ps;
        } catch (SQLException e) {
            logger.error("SQL fout tegengekomen");
            e.printStackTrace();
            System.exit(-99);
        }
        return null;
    }
    @Override
    public void updatePercentage(double percentage) {
        BankConfig bankConfig = new BankConfig(percentage);
        String sql =
                "UPDATE bankinstelling SET value =" + percentage + " WHERE " +
                "adminAttribute ='percentage'";
        jdbcTemplate.update(sql);
    }
    @Override
    public double getPercentage() {

        return jdbcTemplate.queryForObject("SELECT value FROM " +
                "bankinstelling WHERE " +
                "adminAttribute ='percentage'", Double.class);

    }
}
