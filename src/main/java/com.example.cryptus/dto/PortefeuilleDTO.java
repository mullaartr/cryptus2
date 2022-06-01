package com.example.cryptus.dto;

import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;


import java.util.ArrayList;
import java.util.List;

public class PortefeuilleDTO  {
    private int portefeuilleId;
    private List<Asset> assets;
    private Customer owner; // moet eigenlijk customerDTo worden


    public PortefeuilleDTO() {

    }

    public PortefeuilleDTO(Portefeuille portefeuille) {
        this.portefeuilleId = portefeuille.getPortefeuilleId();
        this.assets = portefeuille.getAssets();
        portefeuille.getOwner().setPortefeuille(null);
        this.owner = portefeuille.getOwner();
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    public int getPortefeuilleId() {
        return portefeuilleId;
    }

    public void setPortefeuilleId(int portefeuilleId) {
        this.portefeuilleId = portefeuilleId;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }
}
