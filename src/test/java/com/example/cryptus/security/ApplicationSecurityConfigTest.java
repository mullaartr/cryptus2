package com.example.cryptus.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.service.ApplicationUserService;

import java.io.UnsupportedEncodingException;

import java.util.HashMap;

import javax.crypto.SecretKey;
import javax.security.auth.kerberos.EncryptionKey;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.accept.ContentNegotiationStrategy;

@ContextConfiguration(classes = {ApplicationSecurityConfig.class, JwtConfig.class, AuthenticationConfiguration.class})
@ExtendWith(SpringExtension.class)
@PropertySource("classpath:application-test.properties")
@EnableConfigurationProperties
class ApplicationSecurityConfigTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ApplicationSecurityConfig applicationSecurityConfig;

    @MockBean
    private ApplicationUserService applicationUserService;

    @MockBean
    private AuthenticationTrustResolver authenticationTrustResolver;

    @MockBean
    private ContentNegotiationStrategy contentNegotiationStrategy;

    @MockBean
    private CustomerDaoJdbc customerDaoJdbc;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private ObjectPostProcessor<Object> objectPostProcessor;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private SecretKey secretKey;


    @Test
    void testConfigure() throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(
                this.objectPostProcessor);
        this.applicationSecurityConfig.configure(authenticationManagerBuilder);
        assertTrue(authenticationManagerBuilder.isConfigured());
    }

    @Test
    void testDaoAuthenticationProvider() throws UnsupportedEncodingException {

        BcryptEncoder passwordEncoder = new BcryptEncoder();
        CustomerDaoJdbc customerDaoJdbc = new CustomerDaoJdbc(mock(JdbcTemplate.class));
        ApplicationUserService applicationUserService = new ApplicationUserService(
                new CustomerDaoJdbc(mock(JdbcTemplate.class)));
        EncryptionKey secretKey = new EncryptionKey("AAAAAAAA".getBytes("UTF-8"), 1);

        DaoAuthenticationProvider actualDaoAuthenticationProviderResult = (new ApplicationSecurityConfig(passwordEncoder,
                customerDaoJdbc, applicationUserService, secretKey, new JwtConfig())).daoAuthenticationProvider();
        assertTrue(actualDaoAuthenticationProviderResult
                .getUserCache() instanceof org.springframework.security.core.userdetails.cache.NullUserCache);
        assertTrue(actualDaoAuthenticationProviderResult.isHideUserNotFoundExceptions());
        assertFalse(actualDaoAuthenticationProviderResult.isForcePrincipalAsString());
    }

}

