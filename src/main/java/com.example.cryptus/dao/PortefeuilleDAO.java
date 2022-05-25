package com.example.cryptus.dao;

import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Portefeuille;

import java.util.Optional;

public interface PortefeuilleDAO {

    Optional<Portefeuille> findPortefeuilleById(int id);

    void store(Portefeuille portefeuille);


    void update(Portefeuille portefeuille, String asset);

    void delete(int id);
}
