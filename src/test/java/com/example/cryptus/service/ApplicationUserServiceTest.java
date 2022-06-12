package com.example.cryptus.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.cryptus.dao.CustomerDao;
import com.example.cryptus.model.Customer;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ApplicationUserService.class})
@ExtendWith(SpringExtension.class)
class ApplicationUserServiceTest {
    @Autowired
    private ApplicationUserService applicationUserService;

    @MockBean
    private CustomerDao customerDao;

    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        when(this.customerDao.findCustomerByUsernamePassword((String) any())).thenReturn(Optional.of(new Customer(123)));
        UserDetails actualLoadUserByUsernameResult = this.applicationUserService.loadUserByUsername("cryptus");
        assertEquals(7, actualLoadUserByUsernameResult.getAuthorities().size());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertNull(actualLoadUserByUsernameResult.getUsername());
        assertNull(actualLoadUserByUsernameResult.getPassword());
        verify(this.customerDao).findCustomerByUsernamePassword((String) any());
    }

    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {
        when(this.customerDao.findCustomerByUsernamePassword((String) any())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> this.applicationUserService.loadUserByUsername("cryptus"));
        verify(this.customerDao).findCustomerByUsernamePassword((String) any());
    }

    @Test
    void testLoadUserByUsername3() throws UsernameNotFoundException {
        when(this.customerDao.findCustomerByUsernamePassword((String) any()))
                .thenThrow(new UsernameNotFoundException("Username %s not found"));
        assertThrows(UsernameNotFoundException.class, () -> this.applicationUserService.loadUserByUsername("cryptus"));
        verify(this.customerDao).findCustomerByUsernamePassword((String) any());
    }
}

