package com.example.cryptus.controller;

import com.example.cryptus.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;


@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    private MockMvc mockMvc;


    @MockBean
    private CustomerService customerService;


    @Autowired
    public CustomerControllerTest( MockMvc mockMvc) {
        super();
        this.mockMvc = mockMvc;
    }

    @Test
    void getCustomers() {
        Mockito.when(customerService.list()).thenReturn(new ArrayList<>());
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

    @Test
    void findCustomerById() {
    }

    @Test
    void findCustomerByName() {
    }
}