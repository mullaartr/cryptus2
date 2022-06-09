package com.example.cryptus.service;

import com.example.cryptus.dao.CustomerDao;
import com.example.cryptus.model.ApplicationUser;
import com.example.cryptus.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.cryptus.security.ApplicationUserRole.ADMIN;

@Service
public class ApplicationUserService implements UserDetailsService {

//    private final ApplicationUserDao applicationUserDao;
    private final CustomerDao customerDao;

    @Autowired
    public ApplicationUserService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

//    @Autowired
//    public ApplicationUserService(@Qualifier("fake") ApplicationUserDao applicationUserDao) {
//        this.applicationUserDao = applicationUserDao;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = customerDao.findCustomerByUsernamePassword(username)
                .orElseThrow(() -> new UsernameNotFoundException(String
                        .format("Username %s not found", username)));
        ApplicationUser applicationUser = new ApplicationUser (ADMIN.getGrantedAuthorities(),
        user.getPassword(),
                user.getUserName(),
                true,
                true,
                true,
                true);
        return applicationUser;
    }
}
