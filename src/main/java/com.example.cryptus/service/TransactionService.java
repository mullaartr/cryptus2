package com.example.cryptus.service;

import com.example.cryptus.model.*;
import com.example.cryptus.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final Logger logger = LogManager.getLogger(CustomerService.class);
    private TransactionRepository transactionRepository;
    private CustomerRepository customerRepository;
    private PortefeuilleRepository portefeuilleRepository;
    private BankConfigRepository bankConfigRepository;
    private BankAccountRepository bankAccountRepository;
    private AssetRepository assetRepository;


    public TransactionService(TransactionRepository transactionRepository,
                              CustomerRepository customerRepository,
                              PortefeuilleRepository portefeuilleRepository,
                              BankConfigRepository bankConfigRepository,
                              BankAccountRepository bankAccountRepository,
                              AssetRepository assetRepository) {
        super();
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
        this.portefeuilleRepository = portefeuilleRepository;
        this.bankConfigRepository = bankConfigRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.assetRepository = assetRepository;

        logger.info("Nieuwe transactieservice");
    }

    public List<Transaction> getBuyTransactionsFromUser(int userId) {
        return transactionRepository.getBuyTransactionsFromUser(userId);
    }

    public List<Transaction> getSellTransactionsFromUser(int userId) {
        return transactionRepository.getSellTransactionsFromUser(userId);
    }

    public Optional<Transaction> buyFromBank(Customer buyer, String assetNaam, double assetAmount) {

        //A. ASSETGEDEELTE

        //1. de koper is bekend en krijg ik binnen via de controller

        //2. seller = bank = userId 1

        Optional<Customer> seller = customerRepository.findCustomerById(1);

        //3. Ophalen portefeuille van de buyer en seller:

        Portefeuille portefeuilleBuyer =
                portefeuilleRepository.findPortefeuilleWithCustomerById(buyer.getUserId()).get();

        Optional<Portefeuille> portefeuilleSeller =
                portefeuilleRepository.findPortefeuilleWithCustomerById(seller.get().getUserId());

        //4.check of de bank de asset überhaupt heeft en of er voldoende
        // saldo is. Zo ja (true) ga dan door.

        if (portefeuilleSeller.get().hasEnoughAssets(assetNaam,
                assetAmount)) {

            // 5.Schrijf het amount van assets af van de portefeuille van de
            // seller

            Asset assetSeller =
                    portefeuilleSeller.get().getAssetLijst().stream().filter(asset1 -> asset1.getAssetNaam().equals(assetNaam)).findAny().orElse(null);
            assetSeller.setSaldo(assetSeller.getSaldo() - assetAmount);

            portefeuilleRepository.updatePortefeuille(portefeuilleSeller.get(), assetSeller);

            // 6. Schrijf de amount van assets bij bij de portefeuille van de
            // koper

            Asset assetBuyer =
                    portefeuilleBuyer.getAssetLijst().stream().filter(asset1 -> asset1.getAssetNaam().equals(assetNaam)).findAny().orElse(null);
            assetBuyer.setSaldo(assetBuyer.getSaldo() - assetAmount);

            portefeuilleRepository.updatePortefeuille(portefeuilleBuyer,
                    assetBuyer);
        }

        //B. BEPALEN WAARDE EN COMMISSIE = TOTALE WAARDE

        //7. Ophalen koers van asset op basis van assetid; Daan
        // schrijft deze methode

        // 8. Waarde van de aankoop in euro's. Zie hieronder voor een eerste
        // opzet van een methode: calcValueTransactionInEuro
        double valueTransaction = calcValueTransactionInEuro(assetNaam,
                assetAmount);

        // 9. Waarde van de commissie. Zie ook methode onder
        double valueFee = calcFee(valueTransaction);

        // 10. Totale kosten. Optellen kosten aankoop + kosten commissie

        double totalValue = calTotal(valueTransaction, valueFee);

        //C. AFSCHRIJVEN EN BIJSCHRIJVEN EURO'S OP BANKREKENING ETC.

        //11. Ophalen bankaccount van de buyer:

        Optional<BankAccount> bankAccountBuyer =
                bankAccountRepository.findBankaccountByUserId(buyer.getUserId());

        //12. Ophalen bankaccount van de seller (= bank = userId1)

        Optional<BankAccount> bankAccountSeller =
                bankAccountRepository.findBankaccountByUserId(1);

        //13. check of de buyer voldoende euro's heeft
        if (bankAccountBuyer.get().hasEnoughBalance(totalValue)) {

            //14. Schrijf het aantal euro's af van de bankrekening van de buyer

            bankAccountBuyer.get().withdrawFunds(totalValue);

            //15. schrijf euro's af en bij de bank

            bankAccountSeller.get().addFunds(totalValue);
        }
        //D. Transactie gedeelte

        //16. Haal de Asset op doormiddel van de assetnaam -> hoe moet dit?
        // -> Volgens mij bestaat deze methode niet in de repository en ook
        // niet op naam. Wel op basis van ID.

        Optional<Asset> assetBought = null;

        //17. Haal het huidige percentage uit de DB:

        double percentage = bankConfigRepository.getPercentage();

        //.18.Maak een transactie met alle ingredienten (Asset mis nog!)

        Transaction transaction = new Transaction(buyer, seller.get(),
                assetBought.get(),
                assetAmount, totalValue, percentage);

        //19. Maak een transactie aan in de database

        transactionRepository.createTransaction(transaction);

        return Optional.of(transaction);
    }

    public double calcValueTransactionInEuro(String assetNaam,double assetAmount) {

        //return koers * assetAmount -> deze methode schrijft Daan
        return 0;
    }

    public double calcFee(double valueInEuro) {
        double percentage = bankConfigRepository.getPercentage();
        return valueInEuro * (percentage / 100);
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
