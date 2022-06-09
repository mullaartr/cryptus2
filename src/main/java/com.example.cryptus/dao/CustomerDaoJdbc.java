package com.example.cryptus.dao;

import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.Optional;


@Component
public class CustomerDaoJdbc implements CustomerDao {

    private JdbcTemplate jdbcTemplate;
    private final Logger logger = LogManager.getLogger(CustomerDaoJdbc.class);

    @Autowired
    public CustomerDaoJdbc(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New CustomerDaoJdbc");
    }

    RowMapper<Customer> rowMapper = (rs, rowNum) -> {
        Customer customer = new Customer(0,"","","","","",new Date(0),
                "",new Address(),"","");
        Address address = customer.getAddress();
        //System.out.println("hello");
        customer.setUserId(rs.getInt("userId"));
        customer.setFirstName(rs.getString("voornaam"));
        customer.setPreposition(rs.getString("tussenvoegsel"));
        customer.setLastName(rs.getString("achternaam"));
        customer.setUserName(rs.getString("gebruikersnaam"));
        customer.setPassword(rs.getString("wachtwoord"));
        customer.setBirthDate(rs.getDate("geboortedatum"));

        address.setStreet(rs.getString("straat"));
        address.setHouseNumber(rs.getInt("huisnummer"));
        address.setPostcode(rs.getString("postcode"));
        address.setCity(rs.getString("woonplaats"));
        customer.setBSN(rs.getString("BSN"));
        customer.setEmail(rs.getString("emailadres"));
        customer.setPhone(rs.getString("telefoon"));


            return customer;
    };








    RowMapper<Customer> userRowMapper = (rs, rowNum) -> {
        Customer user = new Customer();

        user.setUserId(rs.getInt("userId"));
        user.setFirstName(rs.getString("voornaam"));
        user.setPreposition(rs.getString("tussenvoegsel"));
        user.setLastName(rs.getString("achternaam"));
        user.setUserName(rs.getString("gebruikersnaam"));
        user.setPassword(rs.getString("wachtwoord"));
        return user;
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

    public Optional<Customer> findBuyerByTransactionId( int transactionId) {
        String sql ="select * from klant JOIN user u JOIN bankrekening b JOIN" +
                " transactie t on b.iban = t.debitiban and b.userId = u.userId and "+
                " u.userId = klant.userId where t.transactieId = ?";
        Customer customer = null;
        try{
            customer = jdbcTemplate.queryForObject(sql,rowMapper,transactionId);
        }catch (DataAccessException exception){
            logger.info("Customer was not found");
        }

        return Optional.ofNullable(customer);
    }

    public Optional<Customer> findSellerByTransactionId( int transactionId) {
        String sql ="select * from klant JOIN user u JOIN bankrekening b JOIN" +
                " transactie t on b.iban = t.creditiban and b.userId = u" +
                ".userId and "+
                " u.userId = klant.userId where t.transactieId = ?";
        Customer customer = null;
        try{
            customer = jdbcTemplate.queryForObject(sql,rowMapper,transactionId);
        }catch (DataAccessException exception){
            logger.info("Customer was not found");
        }

        return Optional.ofNullable(customer);
    }

    @Override
    public Optional<Customer> findCustomerByIban(String iban) {
        String sql ="select * from klant JOIN user JOIN bankrekening on " +
                "user.userId = klant.userId AND " +
                "user.userid = bankrekening.userId where " +
                "bankrekening.iban = ?";
        Customer customer = null;
        try{
            customer = jdbcTemplate.queryForObject(sql,rowMapper,iban);
        }catch (DataAccessException exception){
            logger.info("Customer was not found");
        }

        return Optional.ofNullable(customer);

    }

    private PreparedStatement insertUserStatement(Customer customer, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT into user (voornaam, tussenvoegsel, achternaam, gebruikersnaam, wachtwoord) values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, customer.getFirstName());
        ps.setString(2, customer.getPreposition());
        ps.setString(3, customer.getLastName());
        ps.setString(4, customer.getUserName());
        ps.setString(5, customer.getPassword());
       return ps;
    }




    public int storeCustomer(Customer customer){

        Address address = customer.getAddress();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertUserStatement(customer, connection), keyHolder);
        int newKey = keyHolder.getKey().intValue();
        String sql = "INSERT into klant(userId, geboortedatum, straat, huisnummer, postcode, woonplaats, bsn, emailadres, telefoon) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int insert = jdbcTemplate.update(sql,newKey, customer.getBirthDate(), address.getStreet(), address.getHouseNumber(), address.getPostcode(),
                address.getCity(), customer.getBSN(), customer.getEmail(), customer.getPhone());

        if (insert == 1) {
            logger.info("New customer created" + customer.getLastName());

            }


        return customer.getUserId();
    }

    @Override
    public List<Customer> list() {
        String sql = "SELECT * from klant JOIN user u on u.userId = klant.userId  ";
        return jdbcTemplate.query(sql, rowMapper);

    }

    @Override
    public void update(Customer customer) {
        Address address = new Address();
        String sql = "UPDATE user " +
                "SET voornaam = ?, tussenvoegsel = ?, achternaam = ?, gebruikersnaam = ?, wachtwoord = ?  WHERE userId = ?" ;
        int update2 = jdbcTemplate.update(sql,customer.getFirstName(), customer.getPreposition(), customer.getLastName(), customer.getUserName(),
                customer.getPassword());
        String sql1 =  "UPDATE klant " +
                "SET geboortedatum = ?, straat = ?, huisnummer = ?, postcode = ?, woonplaats = ?, bsn = ?, emailadres = ?," +
                " telefoon = ?, geboorteDatum = ?, BSN =?  WHERE userId = ?" ;
        int update = jdbcTemplate.update(sql1,customer.getBirthDate(),address.getStreet(),address.getHouseNumber(), address.getPostcode(),address.getCity(),
                customer.getBSN(),customer.getEmail(),customer.getPhone(),customer.getBirthDate(),customer.getBSN());
        if(update ==1 && update2 == 1){
            logger.info("Customer updated" + customer.getUserId());

        }
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM user WHERE userId= ?",id);


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
        String sql ="select * from user JOIN klant k on user.userId = k.userId where user.gebruikersnaam = ?";
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
    public Optional<Customer> findCustomerByPortefeuilleId(int portefeuilleId) {
        String sql ="select * from user JOIN portefeuille p on user.userId = p.userId join klant k on k.userId = user.userId  where p.portefeuilleID = ?";
        Customer customer = null;

        try{

            customer =  jdbcTemplate.queryForObject(sql, rowMapper, portefeuilleId);

           // portefeuille = jdbcTemplate.queryForObject(sql,new Object[]{portefeuilleId}, portefeuilleDAOJdbc.rowMapper);


        }catch (DataAccessException exception){
            logger.info("Customer was not found");
        }
        return Optional.ofNullable(customer);
    }

    @Override
    public Optional<Customer> findCustomerByEmail(String email) {
        String sql ="select * from klant where emailadres = ?";
        Customer customer = null;
        try{

            customer = jdbcTemplate.queryForObject(sql,userRowMapper,email);

        }catch (DataAccessException exception){
            logger.info("A new customer");
        }
        return Optional.ofNullable(customer);

    }

    //Daan: I added this method to check if an email is already in use
   @Override
    public List<Customer> customerByEmail(String email) {
        String sql="select * from klant JOIN user u on klant.userId = u.userId where klant.emailadres = ?";
        //Customer customer = null;
       List<Customer> customers = null;
        try{

            customers = jdbcTemplate.query(sql,rowMapper,email);

        }catch (DataAccessException exception){
            logger.info("A new customer");
        }
        return customers;

    }


}