package com.example.cryptus.security;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.repository.JwtFakeRepo;
import com.example.cryptus.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

import static com.example.cryptus.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final CustomerDaoJdbc customerDaoJdbc;
    private final ApplicationUserService applicationUserService;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private JwtFakeRepo jwtFakeRepo;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder,
                                     CustomerDaoJdbc customerDaoJdbc,
                                     ApplicationUserService applicationUserService,
                                     SecretKey secretKey,
                                     JwtConfig jwtConfig, JwtFakeRepo jwtFakeRepo) {
        this.passwordEncoder = passwordEncoder;
        this.customerDaoJdbc = customerDaoJdbc;
        this.applicationUserService = applicationUserService;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
        this.jwtFakeRepo = jwtFakeRepo;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        Whitelisting URLs
        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey, jwtFakeRepo))
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig, jwtFakeRepo), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()

                .antMatchers("/","/*.html", "index.html", "/css/*", "/js/*","/images/*").permitAll()

                .antMatchers("/", "/*.html", "/Assets/*", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/customer/**").hasRole(CUSTOMER.name())
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/portefeuille").permitAll()
                .antMatchers("/customer/**").permitAll()
                .antMatchers("/users/register").permitAll()
//                .hasRole(CUSTOMER.name())
//                .antMatchers(HttpMethod.DELETE,"/manage/**")
//                    .hasAuthority(PORTEFEUILLE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST,"/manage/**")
//                    .hasAuthority(PORTEFEUILLE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT,"/manage/**")
//                    .hasAuthority(PORTEFEUILLE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET, "/manage/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                .anyRequest()
                .authenticated();
//                .and()
//                .logout(logout -> logout.clearAuthentication(true)
//                );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}
