package com.example.cryptus.controller;

import com.example.cryptus.dao.PortefeuilleDAO;
import com.example.cryptus.dto.PortefeuilleDTO;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;
import com.example.cryptus.repository.PortefeuilleRepository;

import com.example.cryptus.service.PortefeuilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

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
    private Logger logger = LogManager.getLogger(PortefeuilleController.class);

    @Autowired
    public PortefeuilleController(PortefeuilleService portefeuilleService) {
        this.portefeuilleService = portefeuilleService;
        logger.info("new PortefeuilleController");
    }


    @GetMapping(value = "/portefeuilleLijst")
    @ResponseBody public ResponseEntity<List<PortefeuilleDTO>> portefeuilles(){
        List<Portefeuille> portefeuilles = portefeuilleService.findAll().orElse(null);
        List<PortefeuilleDTO> portefeuilleDTO = new ArrayList<>();
        portefeuilles.stream().forEach(portefeuille -> portefeuilleDTO.add(new PortefeuilleDTO(portefeuille)));
        return ResponseEntity.ok().body(portefeuilleDTO);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody public ResponseEntity<PortefeuilleDTO> portefeuille(@PathVariable("id") int id){
        return ResponseEntity.ok().body(new PortefeuilleDTO(portefeuilleService.findPortefeuilleById(id).orElse(null)));
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Portefeuille> store(@RequestBody PortefeuilleDTO portefeuilleDTO){
        Portefeuille portefeuille = new Portefeuille(portefeuilleDTO);
        portefeuilleService.storePortefeuille(portefeuille);
        return ResponseEntity.ok().body(portefeuille);
    }

    @PostMapping(value = "/update/{assetNaam}")
    public ResponseEntity<Portefeuille> updateSaldo(@PathVariable("assetNaam") String asset, @RequestBody PortefeuilleDTO portefeuilleDTO){
        Portefeuille portefeuille = new Portefeuille(portefeuilleDTO);
        portefeuilleService.updatePortefeuille(portefeuille, asset);
        return ResponseEntity.ok().body(portefeuille);
    }
    //werkt niet: foreign key constraint
    @DeleteMapping(value = "/delete/{id}")
    @ResponseBody public void deletePortefeuille(@PathVariable("id")int id){
        portefeuilleService.deletePortefeuille(id);
    }


}
