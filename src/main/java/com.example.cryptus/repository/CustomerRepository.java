package com.example.cryptus.repository;
import com.example.cryptus.dao.CustomerDao;
import com.example.cryptus.model.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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


    private final CustomerDao customerDao;

    public CustomerRepository(CustomerDao customerDao) {
        this.customerDao = customerDao;
        Logger logger = LogManager.getLogger(CustomerRepository.class);
        logger.info("New CustomerRepository");
    }


    public Optional<Customer> findCustomerById(int id) {
        Optional<Customer> customerOptional =customerDao.findCustomerById(id);
        if(customerOptional.isEmpty()){
            return Optional.empty();

        }else{
            return customerDao.findCustomerById(id);

        }

    }


    public void storeCustomer(Customer customer) {
        customerDao.storeCustomer(customer);

    }


    public void update(Customer customer) {
        customerDao.update(customer);

    }


    public void delete(int id) {
        customerDao.delete(id);

    }


    public Optional<Customer> findCustomerByName(String name) {
        Optional<Customer> customerOptional =customerDao.findCustomerByName(name);
        if(customerOptional.isEmpty()){
            return Optional.empty();

        }else{
            return customerDao.findCustomerByName(name);

        }

    }

    public List<Customer> list(){
        return customerDao.list();
    }

    private static Random randomizer = new Random();

    public String nextFirstName() {
        return firstNames[randomizer.nextInt(firstNames.length)];
    }




}
