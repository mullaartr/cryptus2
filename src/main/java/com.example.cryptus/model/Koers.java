//package com.example.cryptus.model;
//
//import com.example.cryptus.dto.KoersDto;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class Koers {
//
//    private final Logger logger = LoggerFactory.getLogger(Koers.class);
//
//    double koersInEuro;
//  double koersInDollars;
//    Asset asset;
//    //later Date toevoegen?
//
//    public Koers(double koersInEuro, double koersInDollars, Asset asset) {
//        this.koersInEuro = koersInEuro;
//        this.koersInDollars = koersInDollars;
//        this.asset = asset;
//    }
//
//    public Koers() {
//        logger.info("Koers created with no-args constructor");
//    }
//
////    //Constructor for KoersDto
////    public Koers(KoersDto koersDto) {
////        this.koersInEuro = koersInEuro;
////        this.koersInDollars = koersInDollars;
////        this.asset = asset; //hier een Asset van maken?
////    }
//
//
//    public double getKoersInEuro() {
//        return koersInEuro;
//    }
//
//    public void setKoersInEuro(double koersInEuro) {
//        this.koersInEuro = koersInEuro;
//    }
//
//    public double getKoersInDollars() {
//        return koersInDollars;
//    }
//
//    public void setKoersInDollars(double koersInDollars) {
//        this.koersInDollars = koersInDollars;
//    }
//
//    public Asset getAsset() {
//        return asset;
//    }
//
//    public void setAsset(Asset asset) {
//        this.asset = asset;
//    }
//}
