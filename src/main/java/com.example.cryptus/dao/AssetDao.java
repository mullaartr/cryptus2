package com.example.cryptus.dao;

import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Portefeuille;

import java.util.Optional;

public interface AssetDao {

    void store (Asset asset);

    Optional<Asset> findAssetById(int id);

    Optional<Asset> findAssetByPortefeuille(Portefeuille portefeuille);

    void update (Asset asset, int id);

    void delete (int id);

}
