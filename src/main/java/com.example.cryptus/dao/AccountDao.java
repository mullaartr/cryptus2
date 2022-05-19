package com.example.cryptus.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao {

    void storeGebruikersNaam();

    void findGebruikersnaam();

    void updateGebruikersnaam();

    void storeWachtwoord();

//    void authenticateWachtwoord();

    void updateWachtwoord();
}
