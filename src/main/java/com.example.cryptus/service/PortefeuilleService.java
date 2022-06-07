package com.example.cryptus.service;

import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Portefeuille;
import com.example.cryptus.repository.CustomerRepository;
import com.example.cryptus.repository.PortefeuilleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortefeuilleService {

    private final Logger logger = LogManager.getLogger(BsnService.class);
    private PortefeuilleRepository portefeuilleRepository;

    public PortefeuilleService(PortefeuilleRepository portefeuilleRepository) {
        this.portefeuilleRepository = portefeuilleRepository;
        logger.info("new PortefeuilleService");
    }

    public Optional<List<Portefeuille>> findAll(){
        return portefeuilleRepository.findAllPortefeuilles();
    }


    public Optional<Portefeuille> findPortefeuilleById(int id){
        return  portefeuilleRepository.findPortefeuilleWithCustomerById(id);
    }

    public void storePortefeuille(Portefeuille portefeuille){
        portefeuilleRepository.storePortefeuille(portefeuille);
    }


    public void updatePortefeuille(Portefeuille portefeuille, double saldo, Asset asset){
        portefeuilleRepository.updatePortefeuille(portefeuille, saldo, asset);
    }

    public void deletePortefeuille(int id){
        portefeuilleRepository.deletePortefeuille(id);
    }
}
