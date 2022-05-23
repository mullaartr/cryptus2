package com.example.cryptus.dao;

import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;


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
        Customer customer = new Customer(0,"","","","","",new Date(0),
                "",new Address(0,"","",""),"","","");
        System.out.println("hello");
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
        customer.setPhone(rs.getString("telefoon"));


            return customer;
    };

    @Override
    public Optional<Customer> findCustomerById(int id) {
        String sql ="select * from klant JOIN user u on u.userId = klant.userId where u.userId = ?";
        Customer customer = null;
        try{
            customer = jdbcTemplate.queryForObject(sql,rowMapper,id);
        }catch (DataAccessException exception){
            logger.info("Customer was not found");
        }

        return Optional.ofNullable(customer);

    }


    private PreparedStatement insertUserStatement(Customer customer, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT into user (voornaam, tussenvoegsel, achternaam, gebruikersnaam, wachtwoord, salt) values (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, customer.getFirstName());
        ps.setString(2, customer.getPreposition());
        ps.setString(3, customer.getLastName());
        ps.setString(4, customer.getUserName());
        ps.setString(5, customer.getPassword());
        ps.setString(6, customer.getSalt());
       return ps;
    }



    public void storeCustomer(Customer customer){
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> insertUserStatement(customer, connection), keyHolder);
            int newKey = keyHolder.getKey().intValue();
            String sql = "INSERT into klant(userId, geboortedatum, straat, huisnummer, postcode, woonplaats, bsn, emailadres, telefoon) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            int insert = jdbcTemplate.update(sql,newKey, customer.getBirthDate(), customer.getStreet(), customer.getHouseNumber(), customer.getPostcode(),
                    customer.getCity(), customer.getBSN(), customer.getEmail(), customer.getPhone());

            if (insert == 1) {
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
        String sql = "UPDATE user " +
                "SET voornaam = ?, tussenvoegsel = ?, achternaam = ?, gebruikersnaam = ?, wachtwoord = ?, salt = ?  WHERE userId = ?" ;
        int update2 = jdbcTemplate.update(sql,customer.getFirstName(), customer.getPreposition(), customer.getLastName(), customer.getUserName(),
                customer.getPassword(),customer.getSalt());
        String sql1 =  "UPDATE klant " +
                "SET geboortedatum = ?, straat = ?, huisnummer = ?, postcode = ?, woonplaats = ?, bsn = ?, emailadres = ?," +
                " telefoon = ?, geboorteDatum = ?, BSN =?  WHERE userId = ?" ;
        int update = jdbcTemplate.update(sql1,customer.getBirthDate(),customer.getStreet(),customer.getHouseNumber(), customer.getPostcode(),customer.getCity(),
                customer.getBSN(),customer.getEmail(),customer.getPhone(),customer.getBirthDate(),customer.getBSN());
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
        String sql ="select * from user JOIN klant k on user.userId = k.userId where user.achternaam = ?";
        Customer customer = null;
        try{
            customer = jdbcTemplate.queryForObject(sql,rowMapper,name);
        }catch (DataAccessException exception){
            logger.info("Customer was not found");
        }
        return Optional.ofNullable(customer);
    }




    @Override
    public Optional<Customer> findCustomerByUsernamePassword(String username) {
        String sql ="select * from user where gebruikersnaam = ?";
        Customer customer = null;
        try{
            customer = jdbcTemplate.queryForObject(sql,rowMapper,username);
        }catch (DataAccessException exception){
            logger.info("Customer was not found");
        }
        return Optional.ofNullable(customer);
    }

    //SELECT * FROM cryptus.user WHERE gebruikersnaam = 'username' AND wachtwoord = 'password'"

    @Override
    public Optional<Portefeuille> findCustomerByPortefeuilleId(int portefeuilleId) {
        String sql ="select * from user JOIN portefeuille p on user.userId = p.userId where p.portefeuilleID = ?";
        Portefeuille portefeuille = null;
        ResultSetExtractor resultSetExtractor = null;
        try{

            portefeuille = (Portefeuille) jdbcTemplate.query(sql, resultSetExtractor, portefeuilleId);

           // portefeuille = jdbcTemplate.queryForObject(sql,new Object[]{portefeuilleId}, portefeuilleDAOJdbc.rowMapper);


        }catch (DataAccessException exception){
            logger.info("Customer was not found");
        }
        return Optional.ofNullable(portefeuille);
    }




}