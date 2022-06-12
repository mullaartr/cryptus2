package com.example.cryptus.dao;

import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Koers;
import com.example.cryptus.model.Portefeuille;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AssetDao {

    //deze methode staat nu (ook) in de KoersDao + Jdbc
    Optional<Asset> findAssetByAssetNaam(String naam);

    Optional<Asset> findAssetByAssetId(int id);

    List<Asset> findAllAssets();
    void store (Asset asset);
    void delete (String naam);

}
