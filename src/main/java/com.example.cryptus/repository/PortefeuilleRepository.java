package com.example.cryptus.repository;

import com.example.cryptus.dao.CustomerDao;
import com.example.cryptus.dao.PortefeuilleDAO;
import com.example.cryptus.model.Asset;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PortefeuilleRepository {

    private final PortefeuilleDAO portefeuilleDAO;
    private final CustomerDao customerDao;
    private final Logger logger = LogManager.getLogger();

    public PortefeuilleRepository(PortefeuilleDAO portefeuilleDAO, CustomerDao customerDao) {
        this.portefeuilleDAO = portefeuilleDAO;
        this.customerDao = customerDao;
        logger.info("new PortefeuilleRepository created");
    }

    public Optional<Portefeuille> findPortefeuilleById(int id){
        return  portefeuilleDAO.findPortefeuilleById(id);
    }

    public void storePortefeuille(Portefeuille portefeuille){
        portefeuilleDAO.store(portefeuille);
    }

    public List<Portefeuille> findAllPortefeuilles(){
        List<Portefeuille>  portefeuilles = portefeuilleDAO.findPortefeuilles();

        if(portefeuilles.isEmpty()){
            return new ArrayList<>();
        }

        List<Portefeuille> portefeuilles1 = portefeuilles;
        for (int i = 0; i < portefeuilles1.size(); i++) {
            Optional<Customer> customerOptional = customerDao.findCustomerByPortefeuilleId(portefeuilles1.get(i).getPortefeuilleId());
            if (customerOptional.isEmpty()) {
                return  new ArrayList<>();
            }
            Customer customer = customerOptional.get();
            portefeuilles1.get(i).setOwner(customer);
            //customer.setPortefeuille(portefeuilles1.get(i));
        }
        return portefeuilles1;
    }

    public Optional<Portefeuille> findPortefeuilleWithCustomerById(int id){
        Optional<Portefeuille> portefeuilleOptional = portefeuilleDAO.findPortefeuilleById(id);

        if(portefeuilleOptional.isEmpty()){
            return Optional.empty();
        }

        Portefeuille portefeuille = portefeuilleOptional.get();

        Optional<Customer> customerOptional = customerDao.findCustomerByPortefeuilleId(id);
        if (customerOptional.isEmpty()) {
            return Optional.empty();
        }
        Customer customer = customerOptional.get();
        portefeuille.setOwner(customer);
        //customer.setPortefeuille(portefeuille);

        return Optional.of(portefeuille);
    }

    public void updatePortefeuille(Portefeuille portefeuille, Asset asset){
        portefeuilleDAO.update(portefeuille, asset);
    }

    public void deletePortefeuille(int id){
        portefeuilleDAO.delete(id);
    }


    public void storePortefeuilleRegel(Portefeuille portefeuille, Asset asset){
        portefeuilleDAO.storePortefeuilleRegel(portefeuille, asset);
    }
}
