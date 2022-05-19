package com.example.cryptus.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashService {

    private PepperService pepperService = new PepperService();

    private SaltMaker saltMaker = new SaltMaker();
    private final int saltLength = 8;
    private final int rounds = 10;


    public String Hash(String password) throws NoSuchAlgorithmException {
        String gepeperdWW = password + pepperService.getPepper();
        MessageDigest messageDigest = MessageDigest.getInstance(HashService.AlgoritmeType.SHA256.algoritme);
        messageDigest.update(gepeperdWW.getBytes(StandardCharsets.UTF_8));
        for (int i = 0; i < rounds; i++) {
            byte[] digest1 = messageDigest.digest();
            String tijdelijk = encodeHexString(digest1);
            messageDigest.update(tijdelijk.getBytes(StandardCharsets.UTF_8));
        }
        byte[] digest = messageDigest.digest();
        return encodeHexString(digest);
    }

    public enum AlgoritmeType{
        SHA256("sha-256"),
        SHA384("sha-384");

        public final String algoritme;

        AlgoritmeType(String algoritme) {
            this.algoritme = algoritme;
        }
    }


    public static String encodeHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (byte b : byteArray) {
            hexStringBuffer.append(byteToHex(b));
        }
        return hexStringBuffer.toString();
    }

    private static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }
}
