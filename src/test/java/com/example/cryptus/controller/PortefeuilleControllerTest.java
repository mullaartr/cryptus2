package com.example.cryptus.controller;

import com.example.cryptus.dao.CustomerDaoJdbc;
import com.example.cryptus.dto.PortefeuilleDTO;
import com.example.cryptus.model.Address;
import com.example.cryptus.model.Customer;
import com.example.cryptus.model.Portefeuille;
import com.example.cryptus.security.ApplicationSecurityConfig;
import com.example.cryptus.service.CustomerService;
import com.example.cryptus.service.PortefeuilleService;
import com.google.gson.*;
import com.mysql.cj.xdevapi.JsonString;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.Type;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

//@WebMvcTest(PortefeuilleController.class)

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PortefeuilleControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private PortefeuilleService portefeuilleService;

    @MockBean
    private CustomerService customerService;

    private Customer mullaart;

    private Portefeuille portefeuille1;

    private PortefeuilleDTO portefeuilleDTO;



    @Autowired
    public PortefeuilleControllerTest(MockMvc mockMvc){
        super();
        this.mockMvc = mockMvc;


    }

    @BeforeEach
    void setUp(){
        mullaart = new Customer(1,"Rogier",null,"Mullaart","12345","12345", Date.valueOf("1969-08-13"),"163647861",
                new Address(6,"Justine de Gouwerhof","2011GP","Haarlem")
                ,"rogier.mullaart@gmail.com","0647185165");
        portefeuille1 = new Portefeuille(3, mullaart, new HashMap<>());
        portefeuilleDTO = new PortefeuilleDTO(portefeuille1);
    }

    @Test
    void portefeuilles() {

        when(portefeuilleService.findAll()).thenReturn(Optional.of(new ArrayList<Portefeuille>()));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/portefeuille");
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
        when(portefeuilleService.findPortefeuilleById(1)).thenReturn(Optional.of(portefeuille1));
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
    void store() throws Exception {
// test werk maar de controller zal de owner moeten ophalen om deze in de DTO te kunnen zetten
 /*       Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime()).create();
        String jsonString = gson.toJson(portefeuilleDTO);
        Mockito.doNothing().when(portefeuilleService).storePortefeuille(new Portefeuille(portefeuilleDTO));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/portefeuille/save")
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions response = mockMvc.perform(requestBuilder);

        response.andExpect(MockMvcResultMatchers.status().isOk());
        */

    }

    @Test
    void updateSaldo() {

    }

    @Test
    void deletePortefeuille() {
    }
}