package com.example.cryptus.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.security.auth.kerberos.EncryptionKey;

import com.example.cryptus.repository.JwtFakeRepo;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.AuthenticationException;

class JwtUsernameAndPasswordAuthenticationFilterTest {

    @Test
    void testAttemptAuthentication() throws UnsupportedEncodingException, AuthenticationException {
        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
        JwtConfig jwtConfig = new JwtConfig();
        JwtFakeRepo jwtFakeRepo = new JwtFakeRepo();
        JwtUsernameAndPasswordAuthenticationFilter jwtUsernameAndPasswordAuthenticationFilter = new JwtUsernameAndPasswordAuthenticationFilter(
                authenticationManager, jwtConfig, new EncryptionKey("AAAAAAAA".getBytes("UTF-8"), 1), jwtFakeRepo);
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertThrows(RuntimeException.class,
                () -> jwtUsernameAndPasswordAuthenticationFilter.attemptAuthentication(request, new Response()));
    }

    @Test
    void testAttemptAuthentication2() throws UnsupportedEncodingException, AuthenticationException {
        JwtConfig jwtConfig = new JwtConfig();
        JwtFakeRepo jwtFakeRepo = new JwtFakeRepo();
        JwtUsernameAndPasswordAuthenticationFilter jwtUsernameAndPasswordAuthenticationFilter = new JwtUsernameAndPasswordAuthenticationFilter(
                null, jwtConfig, new EncryptionKey("AAAAAAAA".getBytes("UTF-8"), 1), jwtFakeRepo);
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertThrows(RuntimeException.class,
                () -> jwtUsernameAndPasswordAuthenticationFilter.attemptAuthentication(request, new Response()));
    }

    @Test
    void testConstructor() throws UnsupportedEncodingException {
        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
        JwtConfig jwtConfig = new JwtConfig();
        JwtFakeRepo jwtFakeRepo = new JwtFakeRepo();
        JwtUsernameAndPasswordAuthenticationFilter actualJwtUsernameAndPasswordAuthenticationFilter = new JwtUsernameAndPasswordAuthenticationFilter(
                authenticationManager, jwtConfig, new EncryptionKey("AAAAAAAA".getBytes("UTF-8"), 1), jwtFakeRepo);

        assertEquals("username", actualJwtUsernameAndPasswordAuthenticationFilter.getUsernameParameter());
        assertTrue(actualJwtUsernameAndPasswordAuthenticationFilter
                .getRememberMeServices() instanceof org.springframework.security.web.authentication.NullRememberMeServices);
        assertEquals("password", actualJwtUsernameAndPasswordAuthenticationFilter.getPasswordParameter());
    }


}

