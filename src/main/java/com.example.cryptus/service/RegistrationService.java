package com.example.cryptus.service;

import com.example.cryptus.model.Customer;
import com.example.cryptus.model.User;
import com.example.cryptus.service.Exceptions.BelowEighteenException;
import com.example.cryptus.service.Exceptions.UserAlreadyExistsException;
import com.example.cryptus.service.Exceptions.WrongBsnException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class RegistrationService {

    private final Logger logger = LoggerFactory.getLogger(RegistrationService.class);
    private CustomerService customerService;
    private BsnService bsnService;

    @Autowired
    public RegistrationService(CustomerService customerService, BsnService bsnService, IbanService ibanService) {
        this.customerService = customerService;
        this.bsnService = bsnService;
        logger.info("New RegistrationService");
    }

//    //todo vind customer niet op mail
//    public boolean isUniek(Customer customer) {
//        Optional<Customer> existing = customerService.findCustomerByEmail(customer.getEmail());
//        if (existing.isPresent()) {
//            System.out.println("Gebruiker bestaat al");
//            throw new UserAlreadyExistsException();
//        }else  return true;
//    }


    public boolean checkAge(Customer customer) {
        Instant dateOfBirth = Instant.ofEpochMilli(customer.getBirthDate().getTime());
        Calendar today = Calendar.getInstance();
        today.clear(Calendar.HOUR); today.clear(Calendar.MINUTE); today.clear(Calendar.SECOND);
        Date todayDate = today.getTime();
        OffsetDateTime dobOdt = dateOfBirth.atOffset(ZoneOffset.UTC);
        OffsetDateTime todayOdt = todayDate.toInstant().atOffset(ZoneOffset.UTC);
        int ageToCheck = Period.between(dobOdt.toLocalDate(), todayOdt.toLocalDate()).getYears();
        if (ageToCheck < 18) {
            System.out.println("Te jong voor bankrekening");
            throw new BelowEighteenException();
        } else return true;
    }

    public boolean checkBSN(Customer customer) {
        if(!bsnService.isBsn(customer.getBSN())) {
            System.out.println("Dit is geen bsn");
            throw new WrongBsnException();
        } else return true;
    }

    public void checkRegistration(Customer customer) {
       //if(isUniek(customer) && checkAge(customer) && checkBSN(customer)){ //uitgecomment tot isUniek()werkt;
        if(checkAge(customer) && checkBSN(customer)){
           //ken iban toe aan customer; hiervoor is of een methode nodig
           //Optional<User> findUserById(customer.getUserId)
           //of een methode in BankAccountService
           //setUserId(customer.getUserId), waarin een iban wordt aangemaakt en een startsaldo wordt gegeven
            customerService.storeCustomer(customer);

        }
    }


}
