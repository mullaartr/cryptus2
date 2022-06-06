//package com.example.cryptus.controller;
//
//import com.example.cryptus.model.Address;
//import com.example.cryptus.model.Customer;
//import com.example.cryptus.model.Portefeuille;
//import com.example.cryptus.service.CustomerService;
//import com.example.cryptus.service.PortefeuilleService;
//import com.google.gson.*;
//import com.mysql.cj.xdevapi.JsonString;
//import org.json.JSONObject;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.json.GsonJsonParser;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.json.GsonTester;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.lang.reflect.Type;
//import java.sql.Date;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.util.ArrayList;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//@WebMvcTest(PortefeuilleController.class)
//@AutoConfigureMockMvc(addFilters = false)
///*@AutoConfigureMockMvc
//@SpringBootTest*/
//class PortefeuilleControllerTest {
//
//    private MockMvc mockMvc;
//
//
//    @MockBean
//    private PortefeuilleService portefeuilleService;
//
//    @MockBean
//    private CustomerService customerService;
//
//    @Autowired
//    public PortefeuilleControllerTest(MockMvc mockMvc){
//        super();
//        this.mockMvc = mockMvc;
//    }
//
//    @Test
//    void portefeuilles() {
//        when(portefeuilleService.findAll()).thenReturn(Optional.of(new ArrayList<Portefeuille>()));
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/portefeuille");
//        try {
//            ResultActions response = mockMvc.perform(request);
//            MvcResult result = response.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//            assertThat(result.getResponse().getContentType()).contains("json");
//
//        }catch (Exception e){
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    @Test
//    void portefeuille() {
//        when(portefeuilleService.findPortefeuilleById(1)).thenReturn(Optional.of(new Portefeuille(0,null, new ArrayList<>())));
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/portefeuille/1");
//        try {
//            ResultActions response = mockMvc.perform(request);
//            MvcResult result = response.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//            assertThat(result.getResponse().getContentType()).contains("json");
//
//        }catch (Exception e){
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void store() throws Exception {
//    /*    Customer mullaart = new Customer(1,"Rogier",null,"Mullaart","12345","12345", Date.valueOf("1969-08-13"),"163647861",
//                new Address(6,"Justine de Gouwerhof","2011GP","Haarlem")
//                ,"rogier.mullaart@gmail.com","0647185165");
//        Portefeuille portefeuille1 = new Portefeuille(3, null, new ArrayList<>());
//        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
//                ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime()).create();
//        String jsonString = gson.toJson(portefeuille1);
//        Mockito.doNothing().when(portefeuilleService).storePortefeuille(portefeuille1);
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/save")
//                .accept(MediaType.APPLICATION_JSON)
//                .content(jsonString)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
//
//        assertEquals("http://localhost:8080/portefeuille/save",response.getHeader(HttpHeaders.LOCATION));*/
//
//    }
//
//    @Test
//    void updateSaldo() {
//    }
//
//    @Test
//    void deletePortefeuille() {
//    }
//}