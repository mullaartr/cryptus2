package com.example.cryptus.controller;

import com.example.cryptus.model.BankConfig;
import com.example.cryptus.service.BankConfigService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdminControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private BankConfigService bankConfigService;
    private final Logger logger = LoggerFactory.getLogger(AdminControllerTest.class);

    public AdminControllerTest(MockMvc mockMvc) {
        super();
        this.mockMvc = mockMvc;
        logger.info("Nieuwe instantie van admincontroller test aangemaakt.");
    }

    @Test
    void getCommisionPercentage() {

        when(bankConfigService.getPercentage()).thenReturn(new BankConfig().getPercentage());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(
                "/admin/get_percentage");
        try {
            ResultActions response = mockMvc.perform(request);
            MvcResult result = response.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            AssertionsForClassTypes.assertThat(result.getResponse().getContentType()).contains("15");

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

//    @Test
//    void set_percentage() {
//        Response response = given().post("/admin" +
//                "/get_percentage/15");
//        System.out.println(response.asString());
//        assertThat(response.asString()).isNotNull().contains("15");
//    }


}