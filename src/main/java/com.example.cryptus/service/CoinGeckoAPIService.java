package com.example.cryptus.service;
import com.example.cryptus.model.Koers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Request;
import okio.Timeout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;



@Service
public class CoinGeckoAPIService{



    private final Logger logger = LogManager.getLogger(CoinGeckoAPIService.class);

    private Map<String, Map<String, Double>> koersen;
    public CoinGeckoAPIService() {
        logger.info("new koersService created");
    }

    @Scheduled(fixedRate = 400000)
    public void coinAPI() throws IOException, JSONException {
        URL url = new URL("https://api.coingecko.com/api/v3/simple/price?ids=Bitcoin%2CEthereum%2Ctether%2Cusd-coin" +
                "%2Cbinancecoin%2Cripple%2Ccardano%2Cbinance-usd%2Csolana%2Cdogecoin%2Cpolkadot%2Cwrapped-bitcoin%2Ctron%2Clido-staked-ether-wormhole%2C" +
                "dai%2Cavalanche-2%2Cshiba-inu%2Cleo%2Ccronos-wormhole%2Clitecoin&vs_currencies=eur%2Cusd");

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");

        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        String contentType = httpURLConnection.getHeaderField("Content-Type");

        httpURLConnection.setConnectTimeout(10000);
        httpURLConnection.setReadTimeout(10000);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = bufferedReader.readLine()) != null) {
            content.append(inputLine);
        }

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        koersen = mapper.readValue(content.toString(), new TypeReference<Map<String, Map<String, Double>>>(){});


        httpURLConnection.disconnect();
    }






}
