package com.example.cryptus.controller;

import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerManagementControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        final List<Customer> CUSTOMERS = Arrays.asList(
                new Customer(
                        1,
                        "Adam",
                        null,
                        "Hilversum",
                        "adamhilversum",
                        "adam@hilversum.nl",
                        Date.valueOf("1982-12-12"),
                        "12345678",
                        new Address(11, "Suchlaan", "1234BC", "Hilversum"),
                        "adam@hilversum.von",
                        "067373837463"),
                new Customer(
                        2,
                        "Baruch",
                        null,
                        "Spinoza",
                        "baruchspinoza",
                        "baruch@spinoza.nl",
                        Date.valueOf("1982-12-12"),
                        "12345678",
                        new Address(11, "Suchlaan", "1234BC", "Hilversum"),
                        "adam@hilversum.von",
                        "067373837463"),
                new Customer(
                        3,
                        "Alain",
                        null,
                        "Badiou",
                        "alainbadiou",
                        "alain@badiou.nl",
                        Date.valueOf("1982-12-12"),
                        "12345678",
                        new Address(11, "Suchlaan", "1234BC", "Hilversum"),
                        "adam@hilversum.von",
                        "067373837463"),
                new Customer(
                        4,
                        "James",
                        null,
                        "Bond",
                        "jamesbond",
                        "james@bond.nl",
                        Date.valueOf("1982-12-12"),
                        "12345678",
                        new Address(11, "Suchlaan", "1234BC", "Hilversum"),
                        "adam@hilversum.von",
                        "067373837463"),
                new Customer(
                        5,
                        "Tom",
                        "and",
                        "Jerry",
                        "tomjerry",
                        "tom@jerry.nl",
                        Date.valueOf("1982-12-12"),
                        "12345678",
                        new Address(11, "Suchlaan", "1234BC", "Hilversum"),
                        "adam@hilversum.von",
                        "067373837463")
        );
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllCustomers() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/allcustomers");
        try {

            ResultActions response = mockMvc.perform(request);
            MvcResult result =
                    response.andDo(MockMvcResultHandlers.print())
                            .andExpect(MockMvcResultMatchers.status().isOk())
                            .andReturn();

            assertThat(result.getResponse().getContentType()).contains("json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void registerNewCustomer() {
    }

    @Test
    void deleteCustomer() {
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void lockAccount() {
    }

    @Test
    void unlockAccount() {
    }
}