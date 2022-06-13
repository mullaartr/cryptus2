package com.example.cryptus.repository;

import com.example.cryptus.dao.AssetDao;
import com.example.cryptus.dao.AssetDaoJdbc;
import com.example.cryptus.dao.KoersDao;
import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Koers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class KoersRepository {

    private final Logger logger = LoggerFactory.getLogger(KoersRepository.class);
    private KoersDao koersDao;

    private AssetDao assetDao;

    @Autowired
    public KoersRepository(KoersDao koersDao, AssetDao assetDao) {
        this.koersDao = koersDao;
        this.assetDao = assetDao;
        logger.info("New KoersRepository");
    }

    //return Optional.of(koersDao.findMostRecentKoersByAssetNaam(assetNaam));
    public Optional<Koers> findKoersByAssetNaam(String assetNaam) {
        return koersDao.findMostRecentKoersByAssetNaam(assetNaam);
    }

    public List<Koers> findAllKoersenByAssetNaam(String assetNaam) {
        return koersDao.findAllKoersenByAssetNaam(assetNaam);
    }

    public List<Koers> findMostRecentKoersen() {
        List<Koers> koersen = koersDao.findMostRecentKoersen();
        koersen.stream().forEach(koers -> koers.setAsset(assetDao.findAssetByAssetId(koers.getId()).orElse(null)));
        return koersen;
    }

    public List<Koers> findAllKoersen(){
        return koersDao.findAllKoersen();
    }

    public void storeKoers(Koers koers) {
        koersDao.store(koers);
    }

    public void updateKoers(Koers koers) {
        koersDao.update(koers);
    }

    public void deleteKoers(String naam, LocalDateTime datumTijd) {
        koersDao.delete(naam, datumTijd);
    }

}
