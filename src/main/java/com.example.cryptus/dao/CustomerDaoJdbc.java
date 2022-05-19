package com.example.cryptus.dao;

import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;
import com.example.cryptus.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.Optional;


@Component
public class CustomerDaoJdbc implements CustomerDao {

    private JdbcTemplate jdbcTemplate;
    private final Logger logger = LogManager.getLogger(CustomerDaoJdbc.class);

    public CustomerDaoJdbc(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New CustomerDaoJdbc");
    }

    RowMapper<Customer> rowMapper = (rs, rowNum) -> {
        Customer customer = new Customer(0, "", "", "", Date.valueOf(""), "", "",0,"","","",0,"","","");
        customer.setUserId(rs.getInt("userId"));
        customer.setFirstName(rs.getString("voornaam"));
        customer.setPreposition(rs.getString("tussenvoegsel"));
        customer.setLastName(rs.getString("achternaam"));
        customer.setUserName(rs.getString("gebruikersnaam"));
        customer.setPassword(rs.getString("wachtwoord"));
        customer.setSalt(rs.getString("salt"));
        customer.setBirthDate(rs.getDate("geboortedatum"));

        customer.setStreet(rs.getString("straat"));
        customer.setHouseNumber(rs.getInt("huisnummer"));
        customer.setPostcode(rs.getString("postcode"));
        customer.setCity(rs.getString("woonplaats"));
        customer.setBSN(rs.getString("BSN"));
        customer.setEmail(rs.getString("emailadres"));
        customer.setPhone(rs.getInt("telefoon"));

        return customer;
    };

    @Override
    public Optional<Customer> findCustomerById(int id) {
        String sql ="select * from klant JOIN user u on u.userId = klant.userId where userId = ?";
        Customer customer = null;
        try{
            customer = jdbcTemplate.queryForObject(sql,new Object[]{id},rowMapper);
        }catch (DataAccessException exception){
            logger.info("Customer was not found");
        }
        return Optional.ofNullable(customer);
    }



    public void storeCustomer(Customer customer) {
        String sql2 = "INSERT into user (voornaam, tussenvoegsel, achternaam, gebruikersnaam, wachtwoord, salt) values (?,?,?,?,?,?)";
        String sql = "INSERT into klant( geboortedatum, straat, huisnummer, postcode, woonplaats, bsn, emailadres, telefoon, geboorteDatum, BSN) values (?,?,?,?,?,?,?,?,?,?)";
        int insert2 = jdbcTemplate.update(sql2,customer.getUserName(),customer.getPreposition(),customer.getLastName(),customer.getPassword(),customer.getSalt());
        int insert = jdbcTemplate.update(sql,customer.getBirthDate(),customer.getStreet(),customer.getHouseNumber(), customer.getPostcode(),customer.getCity(),customer.getBSN(),customer.getEmail(),customer.getPhone(),customer.getBirthDate(),customer.getBSN());
        if(insert == 1 && insert2 == 1){
            logger.info("New customer created" + customer.getLastName());

        }

    }

    @Override
    public List<Customer> list() {
        String sql = "SELECT * from klant JOIN user u on u.userId = klant.userId  ";
        return jdbcTemplate.query(sql, rowMapper);

    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE user SET voornaam = ?, tussenvoegsel = ?, achternaam = ?, gebruikersnaam = ?, wachtwoord = ?, salt = ?  WHERE userId = ?" ;
        int update2 = jdbcTemplate.update(sql,customer.getFirstName(), customer.getPreposition(), customer.getLastName(), customer.getUserName(), customer.getPassword(),customer.getSalt());
        String sql1 =  "UPDATE klant SET geboortedatum = ?, straat = ?, huisnummer = ?, postcode = ?, woonplaats = ?, bsn = ?, emailadres = ?, telefoon = ?, geboorteDatum = ?, BSN =?  WHERE userId = ?" ;
        int update = jdbcTemplate.update(sql1,customer.getBirthDate(),customer.getStreet(),customer.getHouseNumber(), customer.getPostcode(),customer.getCity(),customer.getBSN(),customer.getEmail(),customer.getPhone(),customer.getBirthDate(),customer.getBSN());
        if(update ==1 && update2 == 1){
            logger.info("Customer updated" + customer.getUserId());

        }
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM user WHERE userId= ?");


    }

    @Override
    public Optional<Customer> findCustomerByName(String name) {
        String sql ="select * from user JOIN klant k on user.userId = k.userId where achterNaam = ?";
        Customer customer = null;
        try{
            customer = jdbcTemplate.queryForObject(sql,new Object[]{name},rowMapper);
        }catch (DataAccessException exception){
            logger.info("Customer was not found");
        }
        return Optional.ofNullable(customer);
    }

    @Override
    public Optional<Portefeuille> findCustomerByPortefeuilleId(int portefeuilleId) {
        String sql ="select * from user JOIN portefeuille p on user.userId = p.userId where p.portefeuilleID = ?";
        Portefeuille portefeuille = null;
        PortefeuilleDAOJdbc portefeuilleDAOJdbc = null;
        try{
           // portefeuille = jdbcTemplate.queryForObject(sql,new Object[]{portefeuilleId}, portefeuilleDAOJdbc.rowMapper);
        }catch (DataAccessException exception){
            logger.info("Customer was not found");
        }
        return Optional.ofNullable(portefeuille);
    }
}