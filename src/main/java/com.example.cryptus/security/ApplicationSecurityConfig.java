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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.example.cryptus.security.ApplicationUserPermission.*;
import static com.example.cryptus.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final CustomerDaoJdbc customerDaoJdbc;
    private final String PEPPER = "iliaWavWavaZisSublisZarRvi";

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, CustomerDaoJdbc customerDaoJdbc) {
        this.passwordEncoder = passwordEncoder;
        this.customerDaoJdbc = customerDaoJdbc;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        Whitelisting URLs
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
//                .csrf().disable()
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
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/assets", true)
                .passwordParameter("password")
                .usernameParameter("username")
                .and()
                .rememberMe()
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1))
                .key("cryptusSecureKey")
                .rememberMeParameter("remember-me")
                .and()
                .logout()

                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .clearAuthentication((true))
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login");




    }
  @Override
    @Bean
    protected UserDetailsService userDetailsService() {
//        This method should eventually retrieve users from the database
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

//        UserDetails alainBadioUser = User.builder()
//                .username(customerDaoJdbc.findCustomerByUsernamePassword("alain@badiou.nl").get().getUserName())
//                .password(passwordEncoder.encode("alainbadiou"))
////                .roles(CUSTOMER.name())
//                .authorities(CUSTOMER.getGrantedAuthorities())
//                .build();

        UserDetails peterPanUser = User.builder()
                .username("peter@pan.nl")
                .password(passwordEncoder.encode("peterpan"))
//                .roles(ADMINTRAINEE.name()) // ROLE_ADMINTRAINEE
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                baruchSpinozaUser,
                napoleonUser,
//                alainBadioUser,
                peterPanUser,
                adamHilversumUser
        );
    }

}
