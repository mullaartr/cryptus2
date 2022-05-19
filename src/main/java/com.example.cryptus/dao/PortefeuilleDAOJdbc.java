package com.example.cryptus.dao;

import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class PortefeuilleDAOJdbc  implements PortefeuilleDAO{

    private JdbcTemplate jdbcTemplate;

    private Logger logger =  LogManager.getLogger(PortefeuilleDAOJdbc.class);



    @Autowired
    public PortefeuilleDAOJdbc(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("new PortefeuilleDAOJdbc");
    }

    RowMapper<Portefeuille> rowMapper = ((rs, rowNum) -> {
        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setPortefeuilleId(rs.getInt("PortefeuilleId"));
        portefeuille.setSaldo(rs.getDouble("saldo"));
        portefeuille.setOwner(null);
        return portefeuille;
    });

    @Override
    public Optional<Portefeuille> findPortefeuilleById(int id) {
        String Sql = "select * from portefeuille where Id = ?";
        Portefeuille portefeuille = null;
        try{
            portefeuille = jdbcTemplate.queryForObject(Sql,new Object[]{id},rowMapper);
        }catch (DataAccessException exception){
            logger.info("Customer was not found");
        }
        return Optional.ofNullable(portefeuille);
    }


    private PreparedStatement insertPortefeuilleStatement(Portefeuille portefeuille, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "insert into Cryptus.portefeuille (saldo, userId) values (?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, portefeuille.getSaldo());
        ps.setInt(2, portefeuille.getOwner().getUserId());
        return ps;
    }
    @Override
    public void store(Portefeuille portefeuille) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertPortefeuilleStatement(portefeuille, connection), keyHolder);
        int newKey = keyHolder.getKey().intValue();
        portefeuille.setPortefeuilleId(newKey);
    }

    public Optional<Portefeuille> findPortefeuilleOf(int userId) {
        int portefeuilleId = jdbcTemplate.queryForObject("select " +
                "portefeuilleId from Cryptus.portefeuille where userId = ?", Integer.class, userId);
        return findPortefeuilleById(portefeuilleId);
    }

    @Override
    public void update(Portefeuille portefeuille) {
        String sql = "Update Cryptus.portefeuille SET saldo = ?, userId = ? where portefeuilleId ";
        int update = jdbcTemplate.update(sql, portefeuille.getSaldo(), portefeuille.getOwner().getUserId());
        if (update == 1) {
            logger.info("portefeuille updated" + portefeuille.getPortefeuilleId());
        }
    }


        @Override
        public void delete ( int id){
            jdbcTemplate.update("DELETE FROM portefeuille WHERE portefeuilleId = ?, id");
        }
    }

