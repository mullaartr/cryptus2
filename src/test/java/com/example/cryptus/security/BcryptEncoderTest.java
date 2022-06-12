package com.example.cryptus.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.regex.Pattern;

import org.apache.logging.log4j.message.ReusableSimpleMessage;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;

class BcryptEncoderTest {
    @Test
    void testConstructor() {
        BcryptEncoder actualBcryptEncoder = new BcryptEncoder();
        Pattern compileResult = Pattern.compile(".*\\.txt");
        actualBcryptEncoder.setBCRYPT_PATTERN(compileResult);
        assertSame(compileResult, actualBcryptEncoder.getBCRYPT_PATTERN());
    }

    @Test
    void testConstructor2() {
        BcryptEncoder actualBcryptEncoder = new BcryptEncoder();
        assertNull(actualBcryptEncoder.getBCRYPT_PATTERN());
        assertEquals(10, actualBcryptEncoder.getStrength());
    }

    @Test
    void testConstructor3() {
        Pattern compileResult = Pattern.compile(".*\\.txt");
        assertSame(compileResult, (new BcryptEncoder(compileResult)).getBCRYPT_PATTERN());
    }

    @Test
    void testEncode() {
        assertThrows(IllegalArgumentException.class, () -> (new BcryptEncoder()).encode(null));
    }

    @Test
    void testGetStrength() {
        assertEquals(10, (new BcryptEncoder()).getStrength());
    }

    @Test
    void testUpgradeEncoding2() {
        assertFalse((new BcryptEncoder()).upgradeEncoding(null));
    }

    @Test
    void testUpgradeEncoding3() {
        assertThrows(IllegalArgumentException.class,
                () -> (new BcryptEncoder(Pattern.compile(".*\\.txt"))).upgradeEncoding("secret"));
    }

    @Test
    void testUpgradeEncoding5() {
        assertFalse((new BcryptEncoder(Pattern.compile(".*\\.txt"))).upgradeEncoding(""));
    }
}
