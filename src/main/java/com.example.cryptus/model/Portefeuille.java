package com.example.cryptus.model;


import com.example.cryptus.dto.PortefeuilleDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Portefeuille implements Serializable {

    private int portefeuilleId;
    private List<Asset> assets;
    private Customer owner;



    public Portefeuille(int portefeuilleId, Customer owner, List<Asset> assets) {
        this.portefeuilleId = portefeuilleId;
        this.owner = owner;
        this.assets = assets;
    }

    public Portefeuille(Customer owner) {
        this(0,  owner, new ArrayList<Asset>());
    }

    public Portefeuille() {
        this(new Customer());
        this.assets = new ArrayList<>();
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

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Portefeuille that = (Portefeuille) o;
        return portefeuilleId == that.portefeuilleId && Objects.equals(assets, that.assets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portefeuilleId, assets);
    }

    @Override
    public String toString() {
        return "Portefeuille{" +
                "portefeuilleId=" + portefeuilleId +
                ", assets=" + assets +
                ", owner=" + owner +
                '}';
    }
}
