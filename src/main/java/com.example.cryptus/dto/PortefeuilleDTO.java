package com.example.cryptus.dto;

import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PortefeuilleDTO  {
    private int portefeuilleId;
    private List<AssetDTO> assets;
    private Customer owner;// moet eigenlijk customerDTo worden



    public PortefeuilleDTO() {

    }

    public PortefeuilleDTO(Portefeuille portefeuille) {
        this.portefeuilleId = portefeuille.getPortefeuilleId();
        this.assets = portefeuille.getAssetLijst().stream().map(AssetDTO::new).collect(Collectors.toList());
        portefeuille.getOwner().setPortefeuille(null);
    }

    public List<AssetDTO> getAssets() {
        return assets;
    }

    public void setAssets(List<AssetDTO> assets) {
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
