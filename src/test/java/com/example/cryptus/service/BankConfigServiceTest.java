package com.example.cryptus.service;

import com.example.cryptus.model.BankConfig;
import com.example.cryptus.repository.BankConfigRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BankConfigServiceTest {

    private BankConfig bankConfig;

    @MockBean
    private static BankConfigRepository mockRepo =
            Mockito.mock(BankConfigRepository.class);

    private BankConfigService bankConfigService =
            new BankConfigService(mockRepo);

    @BeforeAll
    public static void testSetup(){
        Mockito.when(mockRepo.getPercentage()).thenReturn(new BankConfig(15.00).getPercentage());
    }

    @Test
    void updatePercentage() {
        bankConfig = new BankConfig();
        bankConfig.setPercentage(16);
        bankConfig.getPercentage();
        assertEquals(16,bankConfig.getPercentage());
    }

    @Test
    void getPercentage() {
        Mockito.when(mockRepo.getPercentage()).thenReturn(15.00);
        BankConfigService serviceUnderTest = new BankConfigService(mockRepo);
        double actual = serviceUnderTest.getPercentage();
        double expected = 15;
        assertThat(actual).isNotNull().isEqualTo(expected);
    }
}