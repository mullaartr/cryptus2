package com.example.cryptus.controller;

import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;
import com.example.cryptus.service.CustomerService;
import com.example.cryptus.service.PortefeuilleService;
import org.junit.jupiter.api.Test;
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

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(PortefeuilleController.class)
/*@AutoConfigureMockMvc
@SpringBootTest*/
class PortefeuilleControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private PortefeuilleService portefeuilleService;

    @MockBean
    private CustomerService customerService;

    @Autowired
    public PortefeuilleControllerTest(MockMvc mockMvc){
        super();
        this.mockMvc = mockMvc;
    }

    @Test
    void portefeuilles() {
        Mockito.when(portefeuilleService.findAll()).thenReturn(Optional.of(new ArrayList<Portefeuille>()));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/portefeuille/portefeuilleLijst");
        try {
            ResultActions response = mockMvc.perform(request);
            MvcResult result = response.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            assertThat(result.getResponse().getContentType()).contains("json");

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Test
    void portefeuille() {
        Mockito.when(portefeuilleService.findPortefeuilleById(1)).thenReturn(Optional.of(new Portefeuille(0,null, new ArrayList<>())));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/portefeuille/1");
        try {
            ResultActions response = mockMvc.perform(request);
            MvcResult result = response.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            assertThat(result.getResponse().getContentType()).contains("json");

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void store() {

    }

    @Test
    void updateSaldo() {
    }

    @Test
    void deletePortefeuille() {
    }
}