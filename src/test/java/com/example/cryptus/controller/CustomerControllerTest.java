package com.example.cryptus.controller;

import com.example.cryptus.dto.PortefeuilleDTO;
import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;
import com.example.cryptus.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;


@AutoConfigureMockMvc
@SpringBootTest
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    private MockMvc mockMvc;


    @MockBean
    private CustomerService mockService;

    private Customer customer;
    private Customer customer1;

    private Portefeuille portefeuille1;
    private PortefeuilleDTO portefeuilleDTO;
    private CustomerService customerService = Mockito.mock(CustomerService.class);





    @Autowired
    public CustomerControllerTest(MockMvc mockMvc) {
        super();
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    void setUp(){
        customer1 = new Customer(12,"John","gg","mekky","'","",
                Date.valueOf("2015-03-31"),"",
                new Address(0,"","",""),"122");
        portefeuille1 = new Portefeuille(3, customer1, new ArrayList<>());
        portefeuilleDTO = new PortefeuilleDTO(portefeuille1);
    }


    @Test
    void getCustomers() {
        Mockito.when(customerService.list()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/klant/list");
        try {
            ResultActions response = mockMvc.perform(request);
            // check of de response aan de verwachtingen voldoet
            MvcResult result = response
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            assertThat(result.getResponse().getContentType()).contains("json");
        } catch (Exception e) {
            e.printStackTrace();
        }





    }

    @Test
    void getCustomerById() {
        when(customerService.getCustomerById(12)).thenReturn(Optional.of(customer1));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/klant/12");
        try {
            ResultActions response = mockMvc.perform(request);
            MvcResult result =
                    response.andDo(MockMvcResultHandlers.print())
                            .andExpect(MockMvcResultMatchers.status().isOk())
                            //.andExpect(MockMvcResultMatchers.content().json(""))
                            .andReturn();

            assertThat(result.getResponse().getContentType()).contains("json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void findCustomerByName() {
        when(customerService.findCustomerByName("John")).thenReturn(Optional.of(customer1));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/klant");
        try {
            ResultActions response = mockMvc.perform(request);
            MvcResult result =
                    response.andDo(MockMvcResultHandlers.print())
                            .andExpect(MockMvcResultMatchers.status().isOk())
                            //.andExpect(MockMvcResultMatchers.content().json(""))
                            .andReturn();

            assertThat(result.getResponse().getContentType()).contains("json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}