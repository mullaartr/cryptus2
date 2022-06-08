package com.example.cryptus.model;


import com.example.cryptus.dto.PortefeuilleDTO;

import java.io.Serializable;
import java.util.*;

public class Portefeuille implements Serializable {

    private int portefeuilleId;
    private List<Asset> assetLijst;
    private Customer owner;



    public Portefeuille(int portefeuilleId, Customer owner, List<Asset> assetLijst) {
        this.portefeuilleId = portefeuilleId;
        this.owner = owner;
        this.assetLijst = assetLijst;
    }

    public Portefeuille(Customer owner) {
        this(0,  owner, new ArrayList<>());
    }

    public Portefeuille() {
        this(new Customer());
        this.assetLijst = new ArrayList<>();
    }

    public Portefeuille(PortefeuilleDTO portefeuilleDTO) {

        this(portefeuilleDTO.getPortefeuilleId(), new Customer(), portefeuilleDTO.getAssets());
    }

    public double berekenWaarde(){
        return 0;
    }

    public double berekenAantal(){
        return 0;
    }


    public void wijzigCurrency(){

    }



    public boolean hasEnoughAssets(String assetNaam, double assetAmount){
        for(Asset asset: this.getAssetLijst()){
            if(asset.getAssetNaam().equals(assetNaam)){
                if(asset.getSaldo() >= assetAmount){
                    return true;
                }
            }
        }
        return false;
    }


    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setPortefeuilleId(int portefeuilleId) {
        this.portefeuilleId = portefeuilleId;
    }

    public int getPortefeuilleId() {
        return portefeuilleId;
    }

    public List<Asset> getAssetLijst() {
        return assetLijst;
    }

    public void setAssetLijst(List<Asset> assetLijst) {
        this.assetLijst = assetLijst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Portefeuille that = (Portefeuille) o;
        return portefeuilleId == that.portefeuilleId && Objects.equals(assetLijst, that.assetLijst);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portefeuilleId, assetLijst);
    }

    @Override
    public String toString() {
        return "Portefeuille{" +
                "portefeuilleId=" + portefeuilleId +
                ", assetLijst=" + assetLijst +
                '}';
    }
}
