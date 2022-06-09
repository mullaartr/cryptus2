//package com.example.cryptus.service;
//
//import com.example.cryptus.dao.ApplicationUserDao;
//import com.example.cryptus.model.ApplicationUser;
//import com.google.common.collect.Lists;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//import static com.example.cryptus.security.ApplicationUserRole.*;
//
//@Repository("fake")
//public class FakeApplicationUserDaoService implements ApplicationUserDao {
//
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
//        return getApplicationUsers()
//                .stream()
//                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
//                .findFirst();
//    }
//
//    public List<ApplicationUser> getApplicationUsers(){
//        List<ApplicationUser> applicationUsers = Lists.newArrayList(
//                new ApplicationUser(
//                        CUSTOMER.getGrantedAuthorities(),
//                        passwordEncoder.encode("baruchspinoza"),
//                        "baruch@spinoza.nl",
//                        true,
//                        true,
//                        true,
//                        true
//                ),
//                new ApplicationUser(
//                        ADMIN.getGrantedAuthorities(),
//                        passwordEncoder.encode("napoleon"),
//                        "napo@leon.nl",
//                        true,
//                        true,
//                        true,
//                        true
//                ),
//                new ApplicationUser(
//                        ADMINTRAINEE.getGrantedAuthorities(),
//                        passwordEncoder.encode("peterpan"),
//                        "peter@pan.nl",
//                        true,
//                        true,
//                        true,
//                        true
//                )
//        );
//
//        return applicationUsers;
//    }
//}
