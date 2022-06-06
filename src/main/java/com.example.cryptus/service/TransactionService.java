package com.example.cryptus.service;

import com.example.cryptus.model.Asset;
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

    private PortefeuilleRepository portefeuilleRepository;


    public TransactionService(TransactionRepository transactionRepository,
                              CustomerRepository customerRepository,
                              PortefeuilleRepository portefeuilleRepository) {
        super();
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
        this.portefeuilleRepository = portefeuilleRepository;

        logger.info("Nieuwe TransactieService");
    }

    public List<Transaction> getBuyTransactionsFromUser(int userId) {
        List<Transaction> transactions =
                transactionRepository.getBuyTransactionsFromUser(userId);
        return transactions;
    }
//misschien naam

    public Optional<Transaction> buyFromBank(int userIdKoper,
                                             int assetId,
                                             int assetAmount,
                                             int portefeuilleIdseller) {

        //check of de verkoper (= bank = user 1) voldoende assets heeft
        //Geef me het saldo van de asset van de bank

        if (hasEnoughAssets(assetId,portefeuilleIdseller,assetAmount)) {


            //blokkeren amount en euro's

            //Ophalen koers van asset op basis van assetid

            // Waarde van de aankoop in euro's. Zie hieronder voor een eerste
            // opzet van een methode: calcValueTransactionInEuro
            double valueTransaction = calcValueTransactionInEuro(assetId, assetAmount);

            // Waarde van de commissie. Zie ook methode onder
            double valueFee = calcFee(valueTransaction);

            // Totaal kosten. Optellen kosten aankoop + kosten commissie

            double totalValue = calTotal(valueTransaction, valueFee);

            //check of de customer voldoende euro's heeft


            //Haal de gegevens op van de koper

            //Haal de gegevens op van de verkoper en dat is altijd de bank en
            // dus userId 1

            //create transaction

            //save transaction

            //return transaction

        }return null;
    }
    private boolean hasEnoughAssets(int assetId, int portfeuilleId,
                                    double assetAmount){
        Optional<Portefeuille> portefeuille =
                portefeuilleRepository.findPortefeuilleById(portfeuilleId);
        for(Map.Entry<Asset, Double> entry: portefeuille.orElse(null).getAssetLijst().entrySet()){
            if(entry.getKey().getAssetId() == assetId){
                if(entry.getValue() >= assetAmount){
                    return true;
                }
            }
        }
        return false;
    }
    public double calcValueTransactionInEuro(int assetId, double assetAmount) {

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
