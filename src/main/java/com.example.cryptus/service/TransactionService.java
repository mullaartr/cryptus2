package com.example.cryptus.service;

import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Transaction;
import com.example.cryptus.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final Logger logger = LogManager.getLogger(CustomerService.class);
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final PortefeuilleRepository portefeuilleRepository;
    private final BankConfigRepository bankConfigRepository;
    private final BankAccountService bankAccountService;
    private final AssetRepository assetRepository;
    private final KoersRepository koersRepository;
    private static final int BANK = 1;
    private static final double PERCENTAGE = 100.00;


    public TransactionService(TransactionRepository transactionRepository,
                              CustomerRepository customerRepository,
                              PortefeuilleRepository portefeuilleRepository,
                              BankConfigRepository bankConfigRepository,
                              BankAccountService bankAccountService,
                              AssetRepository assetRepository,
                              KoersRepository koersRepository) {
        super();
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
        this.portefeuilleRepository = portefeuilleRepository;
        this.bankConfigRepository = bankConfigRepository;
        this.bankAccountService = bankAccountService;
        this.assetRepository = assetRepository;
        this.koersRepository = koersRepository;

        logger.info("Nieuwe transactieservice");
    }
    public List<Transaction> getBuyTransactionsFromUser(int userId) {
        return transactionRepository.getBuyTransactionsFromUser(userId);
    }
    public List<Transaction> getSellTransactionsFromUser(int userId) {
        return transactionRepository.getSellTransactionsFromUser(userId);
    }
    public Optional<Transaction> buyFromBank(Customer buyer, String assetNaam, double assetAmount) {

        Optional<Customer> seller =
                customerRepository.findCustomerById(BANK);//mock
        Optional<Asset> assetBought =
                assetRepository.findAssetByAssetNaam(assetNaam);//mock
        double valueTransaction = calcValueTransactionInEuro(assetNaam,
                assetAmount);
        double valueFee = calcFee(valueTransaction);
        double totalValue = calTotal(valueTransaction, valueFee);
        double percentage = bankConfigRepository.getPercentage();//mock
        if (seller.get().getPortefeuille().hasEnoughAssets(assetNaam, assetAmount)) {
            addAndWithdrawAssets(buyer, assetNaam, assetAmount, seller);
        }
        if (buyer.getBankAccount().hasSufficientFunds(totalValue)) {
            addAndWithdrawEuros(buyer, seller, totalValue);
        }
        Transaction transaction = createNewTransaction(buyer, assetAmount, seller, assetBought, totalValue, percentage);
        return Optional.of(transaction);
    }
    private Transaction createNewTransaction(Customer buyer, double assetAmount, Optional<Customer> seller, Optional<Asset> assetBought, double totalValue, double percentage) {
        Transaction transaction = new Transaction(buyer, seller.get(),
                assetBought.get(),
                assetAmount, totalValue, percentage);
        transactionRepository.createTransaction(transaction);
        return transaction;
    }
    private void addAndWithdrawEuros(Customer buyer, Optional<Customer> seller, double totalValue) {
        bankAccountService.withdrawFunds(totalValue, buyer.getUserId());
        bankAccountService.addFunds(totalValue, seller.get().getUserId());
    }
    private void addAndWithdrawAssets(Customer buyer, String assetNaam, double assetAmount, Optional<Customer> seller) {
        Asset assetSeller =
                seller.get().getPortefeuille().getAssetLijst().stream().filter(asset1 -> asset1.getAssetNaam().equals(assetNaam)).findAny().orElse(null);
        assert assetSeller != null;
        assetSeller.setSaldo(assetSeller.getSaldo() - assetAmount);
        if(assetSeller ==  null){
            portefeuilleRepository.storePortefeuilleRegel(seller.get().getPortefeuille(), assetSeller);//mock
        } else {
            portefeuilleRepository.updatePortefeuille(seller.get().getPortefeuille(),
                    assetSeller);
        }
        Asset assetBuyer =
                buyer.getPortefeuille().getAssetLijst().stream().filter(asset1 -> asset1.getAssetNaam().equals(assetNaam)).findAny().orElse(null);
        if(assetBuyer == null){
            addNewPortefeuilleRow(buyer, assetNaam, assetAmount);
        }else{
            assetBuyer.setSaldo(assetBuyer.getSaldo() + assetAmount);
            portefeuilleRepository.updatePortefeuille(buyer.getPortefeuille(),
                    assetBuyer);
        }
    }
    private void addNewPortefeuilleRow(Customer buyer, String assetNaam, double assetAmount) {
        Asset nieuweAsset = assetRepository.findAssetByAssetNaam(assetNaam).get();
        nieuweAsset.setSaldo(assetAmount);
        buyer.getPortefeuille().getAssetLijst().add(nieuweAsset);
        portefeuilleRepository.storePortefeuilleRegel(buyer.getPortefeuille(), nieuweAsset);
    }
    public double calcValueTransactionInEuro(String assetNaam,double assetAmount) {
        double koersAsset = koersRepository.findKoersByAssetNaam(assetNaam).get().getKoersInEuro();
        System.out.println(koersAsset);
        return koersAsset*assetAmount;
    }
    public double calcFee(double valueInEuro) {
        double percentage = bankConfigRepository.getPercentage();
        return valueInEuro * (percentage / PERCENTAGE);
    }
    public double calTotal(double valueInEuro, double feeInEuro) {
        return valueInEuro + feeInEuro;
    }
    public Optional<Transaction> updateTransaction(Transaction transaction, int transactionId) {
        transactionRepository.updateTransaction(transaction, transactionId);
        return Optional.empty();
    }
    public void deleteTransaction(int transactionId) {
        transactionRepository.deleteTransaction(transactionId);
    }
    public Optional<Transaction> findTransactionById(int transactionId) {
        return transactionRepository.findTransactionById(transactionId);
    }

}
