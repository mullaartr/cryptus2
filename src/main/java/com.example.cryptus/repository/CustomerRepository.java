package com.example.cryptus.repository;
import com.example.cryptus.dao.CustomerDao;
import com.example.cryptus.dao.PortefeuilleDAO;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;
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
    private PortefeuilleDAO portefeuilleDAO;
    @Autowired
    public CustomerRepository(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public CustomerRepository(CustomerDao customerDao,
                              PortefeuilleDAO portefeuilleDAO)
        {
            this.customerDao = customerDao;
            this.portefeuilleDAO = portefeuilleDAO;
            Logger logger = LogManager.getLogger(CustomerRepository.class);
            logger.info("New CustomerRepository");
        }


        public Optional<Customer> findCustomerById ( int id){
            Optional<Customer> customerOptional = customerDao.findCustomerById(id);
            if (customerOptional.isEmpty()) {
                return Optional.empty();
            } else {
                Portefeuille portefeuille1 = portefeuilleDAO.findPortefeuilleOf(id).orElse(null);
                Customer customer1 = customerOptional.orElse(null);
                customer1.setPortefeuille(portefeuille1);
                return Optional.of(customer1);

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
            customerDao.storeCustomer(customer);

            return customer.getUserId();
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
                return customerDao.findCustomerByName(name);

            }

        }


        public Optional<Customer> findCustomerByUsernamePassword (String
        username){
            Optional<Customer> customerOptional = customerDao.findCustomerByUsernamePassword(username);
            if (customerOptional.isEmpty()) {
                return Optional.empty();

            } else {
                return customerDao.findCustomerByUsernamePassword(username);

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

        public Optional<Customer> findCustomerByEmail (String email){
            Optional<Customer> customerOptional = customerDao.findCustomerByEmail(email);
            if (customerOptional.isEmpty()) {
                return Optional.empty();

            } else {
                return customerDao.findCustomerByEmail(email);

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

    }

