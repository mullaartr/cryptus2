package com.example.cryptus.service;
import com.example.cryptus.model.Koers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;



@Service
public class CoinGeckoAPIService{

    private final Logger logger = LogManager.getLogger(CoinGeckoAPIService.class);

    private Map<String, Map<String, Double>> koersenPrimitief;

    private Set<Koers> koersen;

    public CoinGeckoAPIService() {
        logger.info("new koersService created");
    }

    @Scheduled(fixedRate = 400000)
    public void coinAPI() throws IOException {
        URL url = new URL("https://api.coingecko.com/api/v3/simple/price?ids=Bitcoin%2CEthereum%2Ctether%2Cusd-coin" +
                "%2Cbinancecoin%2Cripple%2Ccardano%2Cbinance-usd%2Csolana%2Cdogecoin%2Cpolkadot%2Cwrapped-bitcoin%2Ctron%2Clido-staked-ether-wormhole%2C" +
                "dai%2Cavalanche-2%2Cshiba-inu%2Cleo%2Ccronos-wormhole%2Clitecoin&vs_currencies=eur%2Cusd");
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                logger.info(httpURLConnection.getResponseMessage());
            } else {
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(10000);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = bufferedReader.readLine()) != null) {
                    content.append(inputLine);
                }
                ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                koersenPrimitief = mapper.readValue(content.toString(), new TypeReference<Map<String, Map<String, Double>>>() {});
                if(!koersenPrimitief.isEmpty()){
                    koersen = koersOmzetter(koersenPrimitief);
                    //hier moet de koersen nog naar de database geschreven worden
                    logger.info("Koersen zijn aangepast");
                }
            }
            httpURLConnection.disconnect();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }


    private Set<Koers> koersOmzetter(Map<String, Map<String, Double>> koersen){
        Set<Koers> koersenSet = new HashSet<>();
        for (Map.Entry<String, Map<String, Double>> entry: koersen.entrySet()) {
            Koers koers = new Koers();
            koers.setName(entry.getKey());
            for (Map.Entry <String, Double> entry2: entry.getValue().entrySet()) {
                if(entry2.getKey().equals("eur")){
                    koers.setKoersInEuro(entry2.getValue());
                } else {
                    koers.setKoersInDollar(entry2.getValue());
                }
            }
            koersenSet.add(koers);
        }
        return koersenSet;
    }












}
