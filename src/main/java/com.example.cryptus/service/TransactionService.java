package com.example.cryptus.service;

import com.example.cryptus.dto.TransactionDTO;
import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Transaction;
import com.example.cryptus.model.User;
import com.example.cryptus.repository.*;
import com.example.cryptus.service.Exceptions.NotEnoughAssetsAcception;
import com.example.cryptus.service.Exceptions.NotEnoughSaldoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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


    public Transaction buyFromBank(Customer buyer, String assetNaam, double assetAmount) throws NotEnoughSaldoException, NotEnoughAssetsAcception{
        Customer seller =
                customerRepository.findCustomerById(BANK).get();//mock
        Optional<Asset> assetBought =
                assetRepository.findAssetByAssetNaam(assetNaam);//mock
        double valueTransaction = calcValueTransactionInEuro(assetNaam,
                assetAmount);
        double valueFee = calcFee(valueTransaction);
        double totalValue = calTotal(valueTransaction, valueFee);
        double percentage = bankConfigRepository.getPercentage();//mock
        checkSaldoEnDoeTransactie(seller, buyer, assetNaam, assetAmount, totalValue);
        return createNewTransaction(buyer, assetAmount, seller, assetBought, totalValue, percentage);
    }

    public Transaction doSell(TransactionDTO transactionDTO, Customer huidigeGebruiker) throws NotEnoughSaldoException, NotEnoughAssetsAcception{
        Transaction transaction = transactionRepository.findTransactionById(transactionDTO.getTransactieId()).orElse(null);
        Customer buyer = customerRepository.getCustomerById(transaction.getSeller().getUserId()).orElse(null);
        transaction.setBuyer(buyer);
        return doeVerkoopTransactie(transactionDTO, transaction, huidigeGebruiker);
    }

    public Transaction doBuy(TransactionDTO transactionDTO, Customer huidigeGebruiker) throws NotEnoughSaldoException, NotEnoughAssetsAcception{
        Transaction transaction = transactionRepository.findTransactionById(transactionDTO.getTransactieId()).orElse(null);
        Customer seller = customerRepository.getCustomerById(transaction.getSeller().getUserId()).orElse(null);
        transaction.setSeller(seller);
        return doeKoopTransactie(transactionDTO, transaction, huidigeGebruiker);
    }

    public Transaction maakVerkoopTransactie(TransactionDTO transactionDTO, Customer huidigeGebruiker) throws NotEnoughSaldoException, NotEnoughAssetsAcception{
        List<Transaction> transactions = transactionRepository.findKoopTransactions();
        double euroAmount = transactionDTO.getEuroammount();
        for (Transaction transaction: transactions) {
            if(transaction.getEuroamount() <= euroAmount && transaction.getAssetamount() == transactionDTO.getAssetammount()){
                doeVerkoopTransactie(transactionDTO, transaction, huidigeGebruiker);
                return transaction;
            }
        }
        Transaction transaction = createKoopVerkoopTransactie(transactionDTO, huidigeGebruiker);
        transactionRepository.createVerkoopTransactie(transaction);
        return transaction;
    }

    public Transaction maakKoopTransactie(TransactionDTO transactionDTO, Customer huidigeGebruiker) throws NotEnoughSaldoException, NotEnoughAssetsAcception{
        List<Transaction> transactions = transactionRepository.findVerKoopTransactions();
        double euroAmount = transactionDTO.getEuroammount();
        for (Transaction transaction: transactions) {
            if(transaction.getEuroamount() <= euroAmount && transaction.getAssetamount() == transactionDTO.getAssetammount()){
                return doeKoopTransactie(transactionDTO, transaction, huidigeGebruiker);
            }
        }
        Transaction transaction = createKoopVerkoopTransactie(transactionDTO, huidigeGebruiker);
        transactionRepository.createKoopTransactie(transaction);
        return transaction;
    }
    private Transaction createNewTransaction(Customer buyer, double assetAmount, Customer seller, Optional<Asset> assetBought, double totalValue, double percentage) {
        Transaction transaction = new Transaction(buyer, seller,
                assetBought.get(),
                assetAmount, totalValue, percentage);
        transactionRepository.createTransaction(transaction);
        return transaction;
    }

    private void addAndWithdrawEuros(Customer buyer, Customer seller, double totalValue) {
        bankAccountService.withdrawFunds(totalValue, buyer.getUserId());
        bankAccountService.addFunds(totalValue, seller.getUserId());
    }

    private void addAndWithdrawAssets(Customer buyer, String assetNaam, double assetAmount, Customer seller) {
        Asset assetSeller =
                seller.getPortefeuille().getAssetLijst().stream().filter(asset1 -> asset1.getAssetNaam().equals(assetNaam)).findAny().orElse(null);
        assert assetSeller != null;
        assetSeller.setSaldo(assetSeller.getSaldo() - assetAmount);
        if(assetSeller ==  null){
            portefeuilleRepository.storeAssets(seller.getPortefeuille(), assetSeller);//mock
        } else {
            portefeuilleRepository.updatePortefeuille(seller.getPortefeuille(),
                    assetSeller);
        }
        Asset assetBuyer =
                buyer.getPortefeuille().getAssetLijst().stream()
                        .filter(asset1 -> asset1
                        .getAssetNaam()
                        .equals(assetNaam))
                        .findAny().orElse(null);
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
        portefeuilleRepository.storeAssets(buyer.getPortefeuille(), nieuweAsset);
    }
    public double calcValueTransactionInEuro(String assetNaam,double assetAmount) {
        double koersAsset = koersRepository.findKoersByAssetNaam(assetNaam).get().getKoersInEuro();
        return koersAsset*assetAmount;
    }
    public double calcFee(double valueInEuro) {
        double percentage = bankConfigRepository.getPercentage();
        return valueInEuro * (percentage / PERCENTAGE);
    }
    public double calTotal(double valueInEuro, double feeInEuro) {
        return valueInEuro + feeInEuro;
    }
    public Optional<Transaction> updateTransaction(Transaction transaction) {
        transactionRepository.updateTransaction(transaction);
        return Optional.empty();
    }
    public void deleteTransaction(int transactionId) {
        transactionRepository.deleteTransaction(transactionId);
    }
    public Optional<Transaction> findTransactionById(int transactionId) {
        return transactionRepository.findTransactionById(transactionId);
    }


    private double [] berekenRate(double gewenstbedrag, double gebodenBedrag){
        double aankoopbedrag = (gewenstbedrag + gebodenBedrag) / 2;
        double fee = calcFee(aankoopbedrag);
        double [] bedragEnFee = {calTotal(aankoopbedrag, fee), fee};
        return bedragEnFee;
    }

    private Transaction createKoopVerkoopTransactie(TransactionDTO transactionDTO, Customer huidigeGebruiker){
        Transaction transaction = new Transaction();
        transaction.setBuyer(huidigeGebruiker);
        Asset asset = assetRepository.findAssetByAssetNaam(transactionDTO.getAsset().getAssetNaam()).get();
        transaction.setAsset(asset);
        transaction.setAssetamount(transactionDTO.getAssetammount());
        transaction.setEuroamount(transactionDTO.getEuroammount());
        return transaction;
    }

    public List<Transaction> toonAanbod(){
        return transactionRepository.findVerKoopTransactions();
    }

    public List<Transaction> toonOpbod(){
        return transactionRepository.findKoopTransactions();
    }

    private void checkSaldoEnDoeTransactie(Customer seller, Customer buyer, String assetNaam, double assetAmount, double totalValue){
        if (seller.getPortefeuille().hasEnoughAssets(assetNaam, assetAmount)) {
            addAndWithdrawAssets(buyer, assetNaam, assetAmount, seller);
        } else{
            throw new NotEnoughAssetsAcception();
        }
        if (buyer.getBankAccount().hasSufficientFunds(totalValue)) {
            addAndWithdrawEuros(buyer, seller, totalValue);
        } else {
            throw new NotEnoughSaldoException();
        }
    }

    private Transaction doeKoopTransactie(TransactionDTO transactionDTO, Transaction transaction, Customer huidigeGebruiker) throws NotEnoughSaldoException, NotEnoughAssetsAcception{
        Customer seller = transaction.getBuyer();
        double euroAmount = transactionDTO.getEuroammount();
        double [] bedragEnFee = berekenRate(transactionDTO.getEuroammount(), transaction.getEuroamount());
        euroAmount =  bedragEnFee[0];
        transaction.setFeePercentage(bedragEnFee[1]);
        checkSaldoEnDoeTransactie(seller, huidigeGebruiker, transaction.getAsset().getAssetNaam(), transactionDTO.getAssetammount(), euroAmount);
        transaction.setBuyer(huidigeGebruiker);
        transaction.setSeller(seller);
        transaction.setEuroamount(euroAmount);
        transactionRepository.updateVerkoopTransactie(transaction);
        return transaction;
    }

    private Transaction doeVerkoopTransactie(TransactionDTO transactionDTO, Transaction transaction, Customer huidigeGebruiker){
        double euroAmount = transactionDTO.getEuroammount();
        Customer buyer = transaction.getBuyer();
        double [] bedragEnFee = berekenRate(transactionDTO.getEuroammount(), transaction.getEuroamount());
        euroAmount =  bedragEnFee[0];
        transaction.setFeePercentage(bedragEnFee[1]);
        checkSaldoEnDoeTransactie(huidigeGebruiker, buyer, transaction.getAsset().getAssetNaam(), transactionDTO.getAssetammount(), euroAmount);
        transaction.setSeller(huidigeGebruiker);
        transaction.setEuroamount(euroAmount);
        transactionRepository.updateVerkoopTransactie(transaction);
        return transaction;
    }
}
