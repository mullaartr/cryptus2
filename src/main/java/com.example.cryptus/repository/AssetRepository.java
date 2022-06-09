package com.example.cryptus.repository;

import com.example.cryptus.dao.AssetDao;
import com.example.cryptus.model.Asset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AssetRepository {

    private final Logger logger = LoggerFactory.getLogger(AssetRepository.class);
    private AssetDao assetDao;

    @Autowired
    public AssetRepository(AssetDao assetDao) {
        this.assetDao = assetDao;
        logger.info("New AssetRepository");
    }

    //deze methode staat nu (ook) in de KoersDao + Jdbc
    public Optional<Asset> findAssetByAssetNaam(String naam) {
        return assetDao.findAssetByAssetNaam(naam);
    }

    public List<Asset> findAllAssets() {
        return assetDao.findAllAssets();
    }

    public void storeAsset(Asset asset) {
        assetDao.store(asset);
    }

    public void deleteAsset(String naam) {
        assetDao.delete(naam);
    }
}
