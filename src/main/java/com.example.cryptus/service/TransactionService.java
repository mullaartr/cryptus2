package com.example.cryptus.service;

import com.example.cryptus.model.Portefeuille;
import com.example.cryptus.model.Transaction;
import com.example.cryptus.repository.CustomerRepository;
import com.example.cryptus.repository.PortefeuilleRepository;
import com.example.cryptus.repository.TransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class TransactionService {

    private final Logger logger = LogManager.getLogger(CustomerService.class);
    private TransactionRepository transactionRepository;
    private CustomerRepository customerRepository;

    private Portefeuille portefeuille;


    public TransactionService(TransactionRepository transactionRepository,
                              CustomerRepository customerRepository) {
        super();
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;

        logger.info("Nieuwe TransactieService");
    }

    public List<Transaction> getBuyTransactionsFromUser(int userId) {
        List<Transaction> transactions =
                transactionRepository.getBuyTransactionsFromUser(userId);
        return transactions;
    }


    public Optional<Transaction> buyFromBank(int userIdKoper,
                                             String assetNaam,
                                             double assetAmount) {

        //1.check of de verkoper (= bank = user 1) voldoende assets heeft en of
        // hij voldoende saldo heeft.
        // Methode heb ik geschreven en staat nu in de model klasse Portefeuille
        // Wat ik me afvraag is hoe ik nu de portefeuille van persoon x hier
        // ophaal met het bijbehorende saldo en de methode hasenoughassets
        // toepas.

        if (portefeuille.hasEnoughAssets(assetNaam,assetAmount)) {

            // 1.Schrijf het amount van assets af van de portefeuille van de
            // seller -> sebastiaan
            // 2. Schrijf de amount van assets bij bij de portefeuille van de
            // koper -> sebastiaan

            ///1. Ophalen koers van asset op basis van assetid; Daan
            // schrijft deze methode

            // Waarde van de aankoop in euro's. Zie hieronder voor een eerste
            // opzet van een methode: calcValueTransactionInEuro
            double valueTransaction = calcValueTransactionInEuro(assetNaam,
                    assetAmount);

            // Waarde van de commissie. Zie ook methode onder
            double valueFee = calcFee(valueTransaction);

            // Totaal kosten. Optellen kosten aankoop + kosten commissie

            double totalValue = calTotal(valueTransaction, valueFee);

            //check of de customer voldoende euro's heeft


            //Haal de gegevens op van de koper -> via token user er uit halen
            // Sebastiaan maakt hier een methode van die aan te roepen is

            //Haal de gegevens op van de verkoper en dat is altijd de bank en
            // dus userId 1

            // schrijf hoeveelheid assets af + bij -> Sebastiaan
            // schrijf euro's af en bij de bank - > Mekky

            //create transaction
            //save transaction

            //return transaction
        }
    return null;
}


    public double calcValueTransactionInEuro(String assetNaam,double assetAmount) {

        //return koers * assetAmount
        return 0;
    }

    public double calcFee(double valueInEuro) {
        //double percentage = configurationrepository.getCommisionpercentage();
        //return valueInEuro*(percentage/100);
        return 0;
    }

    public double calTotal(double valueInEuro, double feeInEuro) {
        return valueInEuro * feeInEuro;
    }

    public Optional<Transaction> updateTransaction(Transaction transaction, int transactionId) {
        transactionRepository.updateTransaction(transaction, transactionId);
        return null;
    }

    public void deleteTransaction(int transactionId) {
        transactionRepository.deleteTransaction(transactionId);
    }

    public Optional<Transaction> findTransactionById(int transactionId) {
        return transactionRepository.findTransactionById(transactionId);
    }


}
