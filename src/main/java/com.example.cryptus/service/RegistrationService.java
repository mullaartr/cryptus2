package com.example.cryptus.service;

import com.example.cryptus.dao.transfer.RegisterDto;
import com.example.cryptus.model.Customer;
import com.example.cryptus.service.Exceptions.BelowEighteenException;
import com.example.cryptus.service.Exceptions.RegistrationFailedException;
import com.example.cryptus.service.Exceptions.WrongBsnException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class RegistrationService {

    private final Logger logger = LoggerFactory.getLogger(RegistrationService.class);
    private CustomerService customerService;
    private BsnService bsnService;

    @Autowired
    public RegistrationService(CustomerService customerService) {
        this.customerService = customerService;
        this.bsnService = bsnService;
        logger.info("New RegistrationService");
    }

    //todo overal booleans van maken en toch exceptions throwen? als allemaal gecheckt continue naar store

    public void checkCustomer(Customer customer) {
        checkDouble(customer);
        checkBSN(customer);
        checkAge(customer);
    }

    public void checkDouble(Customer customer) {
        Optional<Customer> existing = customerService.findCustomerByEmail(customer.getEmail());//vind customer niet op mail
        if (existing.isPresent()) {
            throw new RegistrationFailedException();
        }else return;
        //customerService.storeCustomer(customer);//first check bsn and age, then store
    }

    public void checkBSN(Customer customer) {
        if(!bsnService.isBsn(customer.getBSN())) {
            throw new WrongBsnException();
        } else return;
    }

    public void checkAge(Customer customer) {
        Date dob = customer.getBirthDate();
        Calendar today = Calendar.getInstance();
        today.clear(Calendar.HOUR); today.clear(Calendar.MINUTE); today.clear(Calendar.SECOND);
        Date todayDate = today.getTime(); // is dit wel nodig?
        OffsetDateTime dobOdt = dob.toInstant().atOffset(ZoneOffset.UTC);
        OffsetDateTime todayOdt = todayDate.toInstant().atOffset(ZoneOffset.UTC); //heb al Calender today?
        int ageToCheck = Period.between(dobOdt.toLocalDate(), todayOdt.toLocalDate()).getYears();
        if (ageToCheck < 18) {
            throw new BelowEighteenException();
        }
    }
}
