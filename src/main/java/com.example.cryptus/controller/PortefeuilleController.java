package com.example.cryptus.controller;

import com.example.cryptus.dao.PortefeuilleDAO;
import com.example.cryptus.dto.PortefeuilleDTO;
import com.example.cryptus.repository.PortefeuilleRepository;

import org.springframework.stereotype.Controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class PortefeuilleController {

    private PortefeuilleDTO portefeuilleDTO;
    private PortefeuilleDAO portefeuilleDAO;
    private PortefeuilleRepository portefeuilleRepository;
    private Logger logger = LogManager.getLogger(PortefeuilleController.class);
}
