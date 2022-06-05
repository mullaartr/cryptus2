package com.example.cryptus.model;


import com.example.cryptus.dto.PortefeuilleDTO;

import java.io.Serializable;
import java.util.*;

public class Portefeuille implements Serializable {

    private int portefeuilleId;
    private Map<Asset, Double> assetLijst;
    private Customer owner;



    public Portefeuille(int portefeuilleId, Customer owner, Map<Asset, Double> assetLijst) {
        this.portefeuilleId = portefeuilleId;
        this.owner = owner;
        this.assetLijst = assetLijst;
    }

    public Portefeuille(Customer owner) {
        this(0,  owner, new HashMap<>());
    }

    public Portefeuille() {
        this(new Customer());
        this.assetLijst = new HashMap<>();
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

    public boolean heeftVoldoendeAssets(){
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

    public Map<Asset, Double> getAssetLijst() {
        return assetLijst;
    }

    public void setAssetLijst(Map<Asset, Double> assetLijst) {
        this.assetLijst = assetLijst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Portefeuille that = (Portefeuille) o;
        return portefeuilleId == that.portefeuilleId && Objects.equals(assetLijst, that.assetLijst) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portefeuilleId, assetLijst);
    }

    @Override
    public String toString() {
        return "Portefeuille{" +
                "portefeuilleId=" + portefeuilleId +
                ", assets=" + assetLijst +
                ", owner=" + owner +
                '}';
    }
}
