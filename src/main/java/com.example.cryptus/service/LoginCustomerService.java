package com.example.cryptus.service;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.model.Account;
import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.User;
import com.example.cryptus.repository.AssetRepository;
import com.example.cryptus.repository.CustomerRepository;
import com.example.cryptus.repository.PortefeuilleRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LoginCustomerService {

    Account forUser = new Account("password");

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Customer> optionalCustomer = customerDaoJdbc.findCustomerByUsernamePassword(username);
//        Customer customer = optionalCustomer.get();
//        if(customer == null){
//            log.error("User not found in the database");
//            throw new UsernameNotFoundException("Customer not found in the database");
//        } else {
//            log.info("Customer found in the database: {}", username);
//        }
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        List<Customer> customerList = customerRepository.list();
//        customerList.forEach(element -> {authorities
//                .add(new SimpleGrantedAuthority(customer.getUserName()));
//        });
//        return new org.springframework.security.core.userdetails.User(customer.getUserName(), customer.getPassword(),
//                authorities);
//    }

//    Customer adam = new Customer(
//            1,
//            "Adam",
//            "von",
//            "Hilversum",
//            forUser.hashSaltNPepper("password"),
//            "alsomail@email.com",
//            Date.valueOf("1982-12-12"),
//            "12345678",
//            new Address(11, "Suchlaan", "1234BC", "Hilversum"),
//            "adam@hilversum.von",
//            "067373837463");
//
//    public Customer loginSalutation() {
//        return adam;
//    }
}
