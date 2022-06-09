package com.example.cryptus.dao;

import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Koers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface KoersDao {

    Optional<Koers> findMostRecentKoersByAssetNaam(String naam);
    Optional<Asset> findAssetByAssetNaam(String naam);
    List<Koers> findAllKoersenByAssetNaam(String naam);
    List<Koers> findMostRecentKoersen();
    List<Koers> findAllKoersen();
    void store (Koers koers);
    void update (Koers koers); //moet hier nog iets met Asset gebeuren?
    void delete(String naam, LocalDateTime dateTime);
}
