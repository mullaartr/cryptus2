package com.example.cryptus.service;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.model.Customer;
import com.example.cryptus.repository.CustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class KlantenMakerService {

    private CustomerDaoJdbc customerDaoJdbc = new CustomerDaoJdbc(new JdbcTemplate());
    private CustomerRepository customerRepository = new CustomerRepository(customerDaoJdbc);
    private ArrayList<String> namenLijst;
    private ArrayList<String> plaatsenLijst;

    private ArrayList<Customer> klantenlijst = new ArrayList<>();
    private List<String> BSNNummers;

    private HashService hashService = new HashService();


    public KlantenMakerService() {
        this.namenLijst = new ArrayList<>();
        this.plaatsenLijst = new ArrayList<>();
        this.BSNNummers = new ArrayList<>();
    }

    public void maakPlaatsenLijst() {
        File invoerBestand = new File("resources/9.3 Plaatsnamenlijst.txt");
        try {
            Scanner nieuweScanner = new Scanner(invoerBestand);
            while (nieuweScanner.hasNextLine()) {
                plaatsenLijst.add(nieuweScanner.nextLine());
            }
        } catch (FileNotFoundException bestandNietGevonden) {
            System.out.println(bestandNietGevonden.getMessage());
        }
    }

    public void maakNamenlijst(){
        File invoer = new File ("resources/9.3 NamenLijstGroot.csv");
        try{
            Scanner nieuweScanner = new Scanner(invoer);
            while (nieuweScanner.hasNextLine()){
                this.namenLijst.add(String.valueOf(nieuweScanner.nextLine().split(",")));
            }
        } catch(FileNotFoundException bestandNietGevonden) {
            System.out.println(bestandNietGevonden.getMessage());
        }
    }

    public String kiesRandomplaats(){
        int random = (int) (Math.random() * plaatsenLijst.size()) + 1;
        return plaatsenLijst.get(random);
    }

    public String randomnaam(){
        int random = (int) (Math.random() * namenLijst.size()) + 1;
        return namenLijst.get(random);
    }

    public LocalDate maakDatum(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    public String randomWachtwoord(int lengte){
        byte[] array = new byte[lengte];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }

    public String maakUsername(String voornaam, String achternaam){
        return voornaam + "." + achternaam + "@crypt.nl";
    }

    public void bsnGenerator(){
        File invoer = new File ("resources\\BSNNummers.txt");
        try{
            Scanner nieuweScanner = new Scanner(invoer);
            while (nieuweScanner.hasNextLine()){
                this.BSNNummers.add(String.valueOf(nieuweScanner.nextLine()));
            }
        } catch(FileNotFoundException bestandNietGevonden) {
            System.out.println(bestandNietGevonden.getMessage());
        }
    }

    public String genereerPostcode() {
        String postcode = "";
        for (int i = 0; i < 4; i++) {
            int cijfer = (int) (Math.random() * 10 + 1);
            postcode = postcode + String.valueOf(cijfer);
        }
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXY";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(2);
        for (int i = 0; i < 2; i++){
            sb.append(chars.charAt(rnd.nextInt(chars.length())));

        }
        return postcode + sb.toString();
    }




    private void maakKlantenlijst(int lijstGrootte) throws NoSuchAlgorithmException {
        for (int teller = 0; teller < lijstGrootte; teller++) {
            int id = 0;
            id++;
            String voornaam = customerRepository.nextFirstName();
            String[] naam = randomnaam().split(",");
            String tussenvoegsel = naam[0];
            String achternaam = naam[1];
            LocalDate vroegsteDatum = LocalDate.of(1920, 1, 1);
            LocalDate laatsteDatum = LocalDate.now();
            String randomWW = randomWachtwoord(8);
            String hash = hashService.Hash(randomWW);
            String userName = maakUsername(voornaam, achternaam);
            bsnGenerator();
            String BSN = BSNNummers.get(teller);
            String plaatsnaam = kiesRandomplaats();
            int huisnummer = (int) (Math.random() * 40 + 1);
            String postcode = genereerPostcode();
            //Customer klant = new Customer(id, voornaam, tussenvoegsel, achternaam, maakDatum(vroegsteDatum, laatsteDatum),  );


        }
    }


}
