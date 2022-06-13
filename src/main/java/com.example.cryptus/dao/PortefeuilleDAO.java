package com.example.cryptus.dao;

import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Portefeuille;

import java.util.List;
import java.util.Optional;

public interface PortefeuilleDAO {

    Optional<Portefeuille> findPortefeuilleById(int id);
    Optional<Portefeuille> findPortefeuilleOf(int id);
    Optional<Portefeuille> findPortefeuilleOfAsset(int id, int id2);
    Optional<List<Portefeuille>> findPortefeuilles();
    void store(Portefeuille portefeuille);
    void update(Portefeuille portefeuille, Asset asset);
    void delete(int id);

}
