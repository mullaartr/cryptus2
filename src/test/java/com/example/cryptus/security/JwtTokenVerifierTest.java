package com.example.cryptus.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.jsonwebtoken.JwtException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.security.auth.kerberos.EncryptionKey;
import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class JwtTokenVerifierTest {
    @Test
    void testConstructor() throws UnsupportedEncodingException {
        EncryptionKey encryptionKey = new EncryptionKey("AAAAAAAA".getBytes("UTF-8"), 1);

        JwtConfig jwtConfig = new JwtConfig();
        JwtTokenVerifier actualJwtTokenVerifier = new JwtTokenVerifier(encryptionKey, jwtConfig);

        assertTrue(actualJwtTokenVerifier
                .getEnvironment() instanceof org.springframework.web.context.support.StandardServletEnvironment);
        assertNull(actualJwtTokenVerifier.getFilterConfig());
        assertEquals("des-cbc-crc", encryptionKey.getAlgorithm());
        assertFalse(encryptionKey.isDestroyed());
        assertEquals(1, encryptionKey.getKeyType());
        assertEquals("RAW", encryptionKey.getFormat());
        assertEquals(8, encryptionKey.getEncoded().length);
        assertEquals("Authorization", jwtConfig.getAuthorizationHeader());
        assertNull(jwtConfig.getTokenPrefix());
        assertNull(jwtConfig.getTokenExpirationAfterDays());
        assertNull(jwtConfig.getSecretKey());
    }

    @Test
    void testDoFilterInternal() throws IOException, ServletException {
        EncryptionKey secretKey = new EncryptionKey("AAAAAAAA".getBytes("UTF-8"), 1);

        JwtTokenVerifier jwtTokenVerifier = new JwtTokenVerifier(secretKey, new JwtConfig());
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter((javax.servlet.ServletRequest) any(), (javax.servlet.ServletResponse) any());
        jwtTokenVerifier.doFilterInternal(mockHttpServletRequest, response, filterChain);
        verify(filterChain).doFilter((javax.servlet.ServletRequest) any(), (javax.servlet.ServletResponse) any());
        assertFalse(mockHttpServletRequest.isRequestedSessionIdFromURL());
        assertTrue(mockHttpServletRequest.isRequestedSessionIdFromCookie());
        assertFalse(mockHttpServletRequest.isAsyncSupported());
        assertFalse(mockHttpServletRequest.isAsyncStarted());
        assertTrue(mockHttpServletRequest.isActive());
        assertTrue(mockHttpServletRequest.getSession() instanceof org.springframework.mock.web.MockHttpSession);
        assertEquals("", mockHttpServletRequest.getServletPath());
        assertEquals(80, mockHttpServletRequest.getServerPort());
        assertEquals("localhost", mockHttpServletRequest.getServerName());
        assertEquals("http", mockHttpServletRequest.getScheme());
        assertEquals("", mockHttpServletRequest.getRequestURI());
        assertEquals(80, mockHttpServletRequest.getRemotePort());
        assertEquals("localhost", mockHttpServletRequest.getRemoteHost());
        assertEquals("HTTP/1.1", mockHttpServletRequest.getProtocol());
        assertEquals("", mockHttpServletRequest.getMethod());
        assertEquals(80, mockHttpServletRequest.getLocalPort());
        assertEquals("localhost", mockHttpServletRequest.getLocalName());
        assertTrue(
                mockHttpServletRequest.getInputStream() instanceof org.springframework.mock.web.DelegatingServletInputStream);
        assertEquals(DispatcherType.REQUEST, mockHttpServletRequest.getDispatcherType());
        assertEquals("", mockHttpServletRequest.getContextPath());
        assertEquals(-1L, mockHttpServletRequest.getContentLengthLong());
    }

    @Test
    void testDoFilterInternal4() throws IOException, ServletException {
        EncryptionKey secretKey = new EncryptionKey("AAAAAAAA".getBytes("UTF-8"), 1);

        JwtTokenVerifier jwtTokenVerifier = new JwtTokenVerifier(secretKey, new JwtConfig());
        MockHttpServletRequest request = new MockHttpServletRequest();
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doThrow(new JwtException("An error occurred")).when(filterChain)
                .doFilter((javax.servlet.ServletRequest) any(), (javax.servlet.ServletResponse) any());
        assertThrows(JwtException.class, () -> jwtTokenVerifier.doFilterInternal(request, response, filterChain));
        verify(filterChain).doFilter((javax.servlet.ServletRequest) any(), (javax.servlet.ServletResponse) any());
    }
}

