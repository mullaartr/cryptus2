package com.example.cryptus.controller;

import com.example.cryptus.dto.KoersDto;
import com.example.cryptus.dto.PortefeuilleDTO;
import com.example.cryptus.model.Koers;
import com.example.cryptus.model.Portefeuille;
import com.example.cryptus.service.KoersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    @GetMapping(value = "meest-recent")
    @ResponseBody public ResponseEntity<List<KoersDto>> recentsteKoersen(){
    List<Koers> koersen = koersService.findMostRecentKoersen();
    List<KoersDto> koersDtos = new ArrayList<>();
    koersen.stream().forEach(koers -> koersDtos.add(new KoersDto(koers)));
    return ResponseEntity.ok().body(koersDtos);
}



    @PostMapping
    public ResponseEntity<?> koersUpdate(@RequestBody KoersDto koersDto) {
        //Koers koers = new Koers(koersDto);
        koersService.storeKoersWithAsset(koersDto);
     return null;
    }
}
