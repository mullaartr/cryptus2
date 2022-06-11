package com.example.cryptus.service;

import com.example.cryptus.model.BankAccount;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;
import com.example.cryptus.model.User;
import com.example.cryptus.service.Exceptions.BelowEighteenException;
import com.example.cryptus.service.Exceptions.UserAlreadyExistsException;
import com.example.cryptus.service.Exceptions.WrongBsnException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
public class RegistrationService {

    private final Logger logger = LoggerFactory.getLogger(RegistrationService.class);
    private CustomerService customerService;
    private BsnService bsnService;
    private IbanService ibanService;
    private PortefeuilleService portefeuilleService;
    private BankAccountService bankAccountService;

    @Autowired
    public RegistrationService(CustomerService customerService, BsnService bsnService, IbanService ibanService,
                               PortefeuilleService portefeuilleService, BankAccountService bankAccountService) {
        this.customerService = customerService;
        this.bsnService = bsnService;
        this.ibanService = ibanService;
        this.portefeuilleService = portefeuilleService;
        this.bankAccountService = bankAccountService;
        logger.info("New RegistrationService");
    }

    public boolean isUniek(Customer customer) throws UserAlreadyExistsException {
        List<Customer> customers = customerService.customerByEmail(customer.getEmail());
        boolean checkUniek = customers.stream().anyMatch(c -> c.getEmail().equals(customer.getEmail()));
        if (checkUniek){
            System.out.println("Gebruiker bestaat al");
            throw new UserAlreadyExistsException();
        } else return true;
    }

    public boolean checkAge(Customer customer) throws BelowEighteenException {
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

    public boolean checkBSN(Customer customer) throws WrongBsnException {
        if(!bsnService.isBsn(customer.getBSN())) {
            System.out.println("Dit is geen bsn");
            throw new WrongBsnException();
        } else return true;
    }

    //todo deze methode uit elkaar trekken?
    public void checkRegistration(Customer customer) {
        if (isUniek(customer) && checkAge(customer) && checkBSN(customer)) {
            customer.setBankAccount(new BankAccount(customer, ibanService.ibanGenerator(), 1000000.00));
            int key = customerService.storeCustomer(customer);
            customer.setUserId(key);
            portefeuilleService.storePortefeuille(new Portefeuille(customer));
            bankAccountService.store(new BankAccount(customer));
        }
    }
}
