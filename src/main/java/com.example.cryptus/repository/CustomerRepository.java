package com.example.cryptus.repository;
import com.example.cryptus.dao.BankAccountDao;
import com.example.cryptus.dao.CustomerDao;
import com.example.cryptus.dao.PortefeuilleDAO;
import com.example.cryptus.model.BankAccount;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;
import com.example.cryptus.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class CustomerRepository  {

    private static String[] firstNames = {
            "James", "Mary",
            "John", "Patricia",
            "Robert", "Jennifer",
            "Michael", "Linda",
            "William", "Elizabeth",
            "David", "Barbara",
            "Richard", "Susan",
            "Joseph", "Jessica",
            "Thomas", "Sarah",
            "Charles", "Karen",
            "Christopher", "Nancy",
            "Daniel", "Margaret",
            "Matthew", "Lisa",
            "Anthony", "Betty",
            "Donald", "Dorothy",
            "Mark", "Sandra",
            "Paul", "Ashley",
            "Steven", "Kimberly",
            "Andrew", "Donna",
            "Kenneth", "Emily",
            "Joshua", "Michelle",
            "George", "Carol",
            "Kevin", "Amanda",
            "Brian", "Melissa",
            "Edward", "Deborah",
            "Ronald", "Stephanie",
            "Timothy", "Rebecca",
            "Jason", "Laura",
            "Jeffrey", "Sharon",
            "Ryan", "Cynthia",
            "Jacob", "Kathleen",
            "Gary", "Helen",
            "Nicholas", "Amy",
            "Eric", "Shirley",
            "Stephen", "Angela",
            "Jonathan", "Anna",
            "Larry", "Brenda",
            "Justin", "Pamela",
            "Scott", "Nicole",
            "Brandon", "Ruth",
            "Frank", "Katherine",
            "Benjamin", "Samantha",
            "Gregory", "Christine",
            "Samuel", "Emma",
            "Raymond", "Catherine",
            "Patrick", "Debra",
            "Alexander", "Virginia",
            "Jack", "Rachel",
            "Dennis", "Carolyn",
            "Jerry", "Janet",
            "Tyler", "Maria",
            "Aaron", "Heather",
            "Jose", "Diane",
            "Henry", "Julie",
            "Douglas", "Joyce",
            "Adam", "Victoria",
            "Peter", "Kelly",
            "Nathan", "Christina",
            "Zachary", "Joan",
            "Walter", "Evelyn",
            "Kyle", "Lauren",
            "Harold", "Judith",
            "Carl", "Olivia",
            "Jeremy", "Frances",
            "Keith", "Martha",
            "Roger", "Cheryl",
            "Gerald", "Megan",
            "Ethan", "Andrea",
            "Arthur", "Hannah",
            "Terry", "Jacqueline",
            "Christian", "Ann",
            "Sean", "Jean",
            "Lawrence", "Alice",
            "Austin", "Kathryn",
            "Joe", "Gloria",
            "Noah", "Teresa",
            "Jesse", "Doris",
            "Albert", "Sara",
            "Bryan", "Janice",
            "Billy", "Julia",
            "Bruce", "Marie",
            "Willie", "Madison",
            "Jordan", "Grace",
            "Dylan", "Judy",
            "Alan", "Theresa",
            "Ralph", "Beverly",
            "Gabriel", "Denise",
            "Roy", "Marilyn",
            "Juan", "Amber",
            "Wayne", "Danielle",
            "Eugene", "Abigail",
            "Logan", "Brittany",
            "Randy", "Rose",
            "Louis", "Diana",
            "Russell", "Natalie",
            "Vincent", "Sophia",
            "Philip", "Alexis",
            "Bobby", "Lori",
            "Johnny", "Kayla",
            "Bradley", "Jane",
    };

    private CustomerDao customerDao;
    private PortefeuilleRepository portefeuilleRepository;
    private BankAccountDao bankaccountDAO;
    private TransactionRepository transactionRepository;

    public CustomerRepository(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Autowired
    public CustomerRepository(CustomerDao customerDao, PortefeuilleRepository portefeuilleRepository,
                              BankAccountDao bankaccountDAO,
                              TransactionRepository transactionRepository) {
        this.customerDao = customerDao;
        this.portefeuilleRepository = portefeuilleRepository;
        this.bankaccountDAO = bankaccountDAO;
        this.transactionRepository= transactionRepository;
        Logger logger = LogManager.getLogger(CustomerRepository.class);
        logger.info("New CustomerRepository");
    }

    public Optional<Customer> findCustomerById (int id){
            Optional<Customer> customerOptional = customerDao.findCustomerById(id);
            if (customerOptional.isEmpty()) {
                return Optional.empty();
            } else {
                return volledigeCustomer(customerOptional);
            }
        }


        public Optional<Customer> getCustomerById(int id){
        Optional<Customer> customerOptional = customerDao.getCustomerById(id);
        if (customerOptional.isEmpty()) {
            return Optional.empty();
        } else {

            return customerDao.getCustomerById(id);
        }

    }


        public int storeCustomer (Customer customer){
            int key = customerDao.storeCustomer(customer);
            return key;
        }


        public void update (Customer customer){
            customerDao.update(customer);

        }


        public void delete ( int id){
            customerDao.delete(id);

        }


        public Optional<Customer> findCustomerByName (String name){
            Optional<Customer> customerOptional = customerDao.findCustomerByName(name);
            if (customerOptional.isEmpty()) {
                return Optional.empty();

            } else {
                return volledigeCustomer(customerOptional);
            }

        }


        public Optional<Customer> findCustomerByUsernamePassword (String
        username){
            Optional<Customer> customerOptional = customerDao.findCustomerByUsernamePassword(username);
            if (customerOptional.isEmpty()) {
                return Optional.empty();

            } else {

                return volledigeCustomer(customerOptional);

            }


        }

        public Optional<Customer> findCustomerByPortefeuilleId (
        int portefeuilleId){

            Optional<Customer> customerOptional = customerDao.findCustomerByPortefeuilleId(portefeuilleId);
            if (customerOptional.isEmpty()) {
                return Optional.empty();

            } else {
                return customerDao.findCustomerByPortefeuilleId(portefeuilleId);

            }

        }



        //Daan: I added this method to check if an email is already in use
        //todo werkt nog niet
        public List<Customer> customerByEmail (String email){
            List<Customer> customers = customerDao.customerByEmail(email);
//        if(customers.isEmpty()){
//            return null;// customers;//return niets
//        }
//        else{
            return customers; //customerDao.customerByEmail(email);//return customers, ga ze niet nog een keer ophalen
            //}
        }

        public List<Customer> list () {
            return customerDao.list();
        }

        private static Random randomizer = new Random();

        public String nextFirstName () {
            return firstNames[randomizer.nextInt(firstNames.length)];
        }


        private Optional<Customer> volledigeCustomer(Optional<Customer> customerOptional){
            Customer customer = customerOptional.orElse(null);

            Portefeuille portefeuille = portefeuilleVanKlant(customer);
            customer.setPortefeuille(portefeuille);

            BankAccount account = bankAccountVanKlant(customer);
            customer.setBankAccount( account );

            customer.setTransactionList(transactieLijst(customer.getUserId()));
            return Optional.of(customer);
        }

    private List<Transaction> transactieLijst(int id){
        List<Transaction> list  =
                transactionRepository.getBuyTransactionsFromUser( id );
        List<Transaction> sellList  =
                transactionRepository.getSellTransactionsFromUser( id );
        list.addAll( sellList );
        return list;
    }

    private Portefeuille portefeuilleVanKlant(Customer customer){
        Portefeuille portefeuille = portefeuilleRepository.findPortefeuilleOfCustomer(customer.getUserId()).orElse(null);
        assert portefeuille != null;
        portefeuille.setOwner(customer);
        return portefeuille;
    }

    private BankAccount bankAccountVanKlant(Customer customer){
        BankAccount account = bankaccountDAO.findBankAccountByUserId(customer.getUserId()).orElse( null );
        assert account != null;
        account.setAccountHolder(customer);
        return account;
    }
}



