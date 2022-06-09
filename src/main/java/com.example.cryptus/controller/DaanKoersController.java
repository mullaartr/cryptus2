package com.example.cryptus.controller;

import com.example.cryptus.dto.KoersDto;
import com.example.cryptus.service.KoersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "koers")
public class DaanKoersController {

    private KoersService koersService;

    @Autowired
    public DaanKoersController(KoersService koersService) {
        this.koersService = koersService;
    }

//     @PostMapping
//    public ResponseEntity<?> registration(@RequestBody RegisterDto registerDto) {
//        Customer customer = new Customer(registerDto);
//        customer.setPassword(hashpw(customer.getPassword(), gensalt(12) + forUser.getPEPPER()));
//        registrationService.checkRegistration(customer);
//        return ResponseEntity.ok().body(registerDto);
//    }
//
    @PostMapping
    public ResponseEntity<?> koersUpdate(@RequestBody KoersDto koersDto) {
        //Koers koers = new Koers(koersDto);
        koersService.storeKoersWithAsset(koersDto);
     return null;
    }
}
