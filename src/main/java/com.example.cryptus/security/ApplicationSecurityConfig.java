package com.example.cryptus.security;

import com.example.cryptus.dao.CustomerDaoJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.cryptus.security.ApplicationUserPermission.*;
import static com.example.cryptus.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final CustomerDaoJdbc customerDaoJdbc;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, CustomerDaoJdbc customerDaoJdbc) {
        this.passwordEncoder = passwordEncoder;
        this.customerDaoJdbc = customerDaoJdbc;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        Whitelisting URLs
        http
                .csrf().disable() // COMING UP!
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/customer/**").hasRole(CUSTOMER.name())
                .antMatchers(HttpMethod.DELETE,"/manage/**")
                .hasAuthority(PORTEFEUILLE_WRITE.getPermission())
                .antMatchers(HttpMethod.POST,"/manage/**")
                .hasAuthority(PORTEFEUILLE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/manage/**")
                .hasAuthority(PORTEFEUILLE_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/manage/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails baruchSpinozaUser = User.builder()
                .username("baruch@spinoza.nl")
                .password(passwordEncoder.encode("baruchspinoza"))
//                .roles(CUSTOMER.name()) // ROLE_CUSTOMER
                .authorities(CUSTOMER.getGrantedAuthorities())
                .build();

        UserDetails napoleonUser = User.builder()
                .username("napo@leon.nl")
                .password(passwordEncoder.encode("napoleon"))
//                .roles(ADMIN.name()) // ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails adamHilversumUser = User.builder()
                .username("adam@hilversum.nl")
                .password(passwordEncoder.encode("adamhilversum"))
//                .roles(CUSTOMER.name())
                .authorities(CUSTOMER.getGrantedAuthorities())
                .build();

        UserDetails alainBadioUser = User.builder()
                .username(customerDaoJdbc.findCustomerByUsernamePassword("alain@badiou.nl").get().getUserName())
                .password(passwordEncoder.encode("alainbadiou"))
//                .roles(CUSTOMER.name())
                .authorities(CUSTOMER.getGrantedAuthorities())
                .build();

        UserDetails peterPanUser = User.builder()
                .username("peter@pan.nl")
                .password(passwordEncoder.encode("peterpan"))
//                .roles(ADMINTRAINEE.name()) // ROLE_ADMINTRAINEE
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                baruchSpinozaUser,
                napoleonUser,
                adamHilversumUser,
                alainBadioUser,
                peterPanUser
        );
    }
}
