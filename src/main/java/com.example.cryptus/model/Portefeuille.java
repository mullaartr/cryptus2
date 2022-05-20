package com.example.cryptus.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Portefeuille {

    int portefeuilleId;
    private List<Asset> assets;
    private double saldo;
    private Customer owner;


    public Portefeuille(int portefeuilleId, double saldo, Customer owner, List<Asset> assets) {
        this.portefeuilleId = portefeuilleId;
        this.saldo = saldo;
        this.owner = owner;
        this.assets = assets;
    }

    public Portefeuille(double saldo, Customer owner) {
        this(0, saldo, owner, new ArrayList<Asset>());
    }

    public Portefeuille() {
        this(0, null);
        this.assets = new ArrayList<>();
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


    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
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
        return portefeuilleId == that.portefeuilleId && Double.compare(that.saldo, saldo) == 0 && Objects.equals(assets, that.assets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portefeuilleId, assets, saldo);
    }
}
