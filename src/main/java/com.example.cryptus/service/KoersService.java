package com.example.cryptus.service;

import com.example.cryptus.dto.KoersDto;
import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Koers;
import com.example.cryptus.repository.AssetRepository;
import com.example.cryptus.repository.KoersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSOutput;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class KoersService {

    private final Logger logger = LoggerFactory.getLogger(KoersService.class);
    private KoersRepository koersRepository;
    private AssetRepository assetRepository;

    @Autowired
    public KoersService(KoersRepository koersRepository, AssetRepository assetRepository) {
        this.koersRepository = koersRepository;
        this.assetRepository = assetRepository;
        logger.info("New KoersService");
    }

    //todo coingecko binnenhalen met KoersDto
    //todo op basis van assetNaam de hele Asset ophalen uit de database en toevoegen aan de Koers
    public Koers storeKoersWithAsset(KoersDto koersDto) {
        Koers koers = new Koers(koersDto.getKoersInEuro(), koersDto.getKoersInDollars(), koersDto.getKoersDatum(),
                //hoor ik hier de AssetService of AssetRepository aan te roepen?
                assetRepository.findAssetByAssetNaam(koersDto.getAssetNaam()).orElse(null));
        storeKoers(koers);
        return koers;
    }

    public Optional<Koers> findKoersByAssetNaam(String assetNaam) {
        return koersRepository.findKoersByAssetNaam(assetNaam);
    }

    public List<Koers> findAllKoersenByAssetNaam(String assetNaam) {
        return koersRepository.findAllKoersenByAssetNaam(assetNaam);
    }

//    //todo er zijn twee manieren om van elke asset de meest recente koers te krijgen:
//    // 1) de methode koersDaoJdbc.findMostRecentKoersen() (wellicht buggy):
//    public List<Koers> findMostRecentKoersen() {
//        return koersRepository.findMostRecentKoersen();
//    }
//    //todo 2) door alle assets loopen en voor elke asset de meest recente koers ophalen:
//    // (hoe doe ik dat met een stream?
//    public List<Koers> alleRecenteKoersen() {
//        //List<Asset> assets = assetRepository.findAll();
//        List<Koers> koersen = new ArrayList<>();
//        for (Asset asset: assets) {
//            koersen.add(asset.getKoers());
//        }
//        return koersen;
//    }

    public List<Koers> findMostRecentKoersen(){
        return koersRepository.findMostRecentKoersen();
    }

    public List<Koers> getAllKoersen() {
        return koersRepository.findAllKoersen();
    }

    public void storeKoers(Koers koers){
        //if(koers.getAsset() != null){
            koersRepository.storeKoers(koers);
        //} else System.out.println("Voeg een Asset toe aan de Koers om deze op te slaan");
    }

    public void updateKoers(Koers koers){
        koersRepository.updateKoers(koers);
    }

    public void deleteKoers(String naam, LocalDateTime datumTijd) {
        koersRepository.deleteKoers(naam, datumTijd);
    }
}

