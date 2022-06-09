package com.example.cryptus.service;

import com.example.cryptus.model.Asset;
import com.example.cryptus.repository.AssetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

    private final Logger logger = LoggerFactory.getLogger(AssetService.class);
    private AssetRepository assetRepository;

    @Autowired
    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
        logger.info("New AssetService");
    }

    //deze methode staat nu (ook) in de KoersDao + Jdbc
    public Optional<Asset> findAssetByAssetNaam(String naam) {
        return assetRepository.findAssetByAssetNaam(naam);
    }

    public List<Asset> findAllAssets() {
        return  assetRepository.findAllAssets();
    }

    public void storeAsset(Asset asset) {
         assetRepository.storeAsset(asset);
    }



    public void deleteAsset(String naam) {
         assetRepository.deleteAsset(naam);
    }
}
