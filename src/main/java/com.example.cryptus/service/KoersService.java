package com.example.cryptus.service;
import com.example.cryptus.model.Koers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class KoersService <T>{

    List<Koers> koersen;
    private final Logger logger = LogManager.getLogger(KoersService.class);


    public KoersService() {
        logger.info("new koersService created");
    }

    @Scheduled(fixedRate = 400000)
    public void coinAPI() throws IOException, JSONException {
     URL url = new URL("https://api.coingecko.com/api/v3/coins/markets?vs_currency=eur&ids=bitcoin%2Cethereum%2Ctether%2Cusd-coin%2" +
             "Cbinancecoin%2Cripple%2Ccardano%2Cbinance-usd%2Csolana%2Cdogecoin%2Cpolkadot%2Cwrapped-bitcoin%2Ctron%2Clido" +
             "-staked-ether-wormhole%2Cdai%2Cavalanche-2%2Cshiba-inu%2Cleo%2Ccronos-wormhole%2Clitecoin&order=market_cap_desc&per" +
             "_page=100&page=1&sparkline=false");
     HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
     httpURLConnection.setRequestMethod("GET");

     httpURLConnection.setRequestProperty("Content-Type", "application/json");
     String contentType = httpURLConnection.getHeaderField("Content-Type");

     httpURLConnection.setConnectTimeout(10000);
     httpURLConnection.setReadTimeout(10000);

     httpURLConnection.setInstanceFollowRedirects(false);
     HttpURLConnection.setFollowRedirects(false);
     ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
     koersen = mapper.readValue(httpURLConnection.getInputStream(),  new TypeReference<List<Koers>>(){});




     httpURLConnection.disconnect();


     }



}
