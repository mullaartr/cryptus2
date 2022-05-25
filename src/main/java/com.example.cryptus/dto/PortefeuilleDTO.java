package com.example.cryptus.dto;

import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;

import java.util.List;

public class PortefeuilleDTO {

    private List<Asset> assets;
    private CustomerDTO owner;




    public PortefeuilleDTO(Portefeuille portefeuille) {
        this.assets = portefeuille.getAssets();
        //this.owner = new CustomerDTO(portefeuille.getOwner());
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

}
