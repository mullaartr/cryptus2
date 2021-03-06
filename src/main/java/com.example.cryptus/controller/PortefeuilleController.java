package com.example.cryptus.controller;

import com.example.cryptus.dao.PortefeuilleDAO;
import com.example.cryptus.dto.PortefeuilleDTO;
import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;

//import com.example.cryptus.security.ApplicationSecurityConfig;
import com.example.cryptus.service.AssetService;
import com.example.cryptus.service.CustomerService;
import com.example.cryptus.service.PortefeuilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "portefeuille")
public class PortefeuilleController {

    private PortefeuilleDTO portefeuilleDTO;
    private PortefeuilleDAO portefeuilleDAO;
    private PortefeuilleService portefeuilleService;
    private AssetService assetService;
    private CustomerService customerService;
    private Logger logger = LogManager.getLogger(PortefeuilleController.class);

    @Autowired
    public PortefeuilleController(PortefeuilleService portefeuilleService, CustomerService customerService, AssetService assetService) {
        this.portefeuilleService = portefeuilleService;
        this.customerService = customerService;
        this.assetService = assetService;
        logger.info("new PortefeuilleController");
    }


    @GetMapping
    @ResponseBody public ResponseEntity<List<PortefeuilleDTO>> portefeuilles(){
        List<Portefeuille> portefeuilles = portefeuilleService.findAll();
        List<PortefeuilleDTO> portefeuilleDTO = new ArrayList<>();
        portefeuilles.stream().forEach(portefeuille -> portefeuilleDTO.add(new PortefeuilleDTO(portefeuille)));
        return ResponseEntity.ok().body(portefeuilleDTO);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody public ResponseEntity<?> portefeuille(@PathVariable("id") int id){
        Optional<Portefeuille> portefeuille = portefeuilleService.findPortefeuilleById(id);
        if(portefeuille.isPresent()){
            return ResponseEntity.ok().body(new PortefeuilleDTO(portefeuille.orElse(null)));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

        @PostMapping(value = "/save")
    public ResponseEntity<?> store(@RequestBody PortefeuilleDTO portefeuilleDTO) throws Exception {
        Portefeuille portefeuille = new Portefeuille(portefeuilleDTO, customerService.getCurrentUser());
        portefeuilleService.storePortefeuille(portefeuille);
        return ResponseEntity.ok().build();

    }

    @PatchMapping (value = "/update/{assetNaam}")
    public ResponseEntity<Portefeuille> updateSaldo(@PathVariable("assetNaam") String asset, @RequestBody PortefeuilleDTO portefeuilleDTO){
        Portefeuille portefeuille = new Portefeuille(portefeuilleDTO, customerService.getCurrentUser());
        Asset asset1 = portefeuille.getAssetLijst().
                stream().filter(asset2 -> asset2.getAssetNaam().equals(asset)).
                findAny().orElse(null);
        portefeuilleService.updatePortefeuille(portefeuille, asset1);
        return ResponseEntity.ok().body(portefeuille);
    }


    //werkt niet: foreign key constraint
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity <?>deletePortefeuille(@PathVariable("id")int id){
        Optional<Portefeuille> portefeuille = portefeuilleService.findPortefeuilleById(id);
        if(portefeuille.isPresent()){
            portefeuilleService.deletePortefeuille(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }


}
