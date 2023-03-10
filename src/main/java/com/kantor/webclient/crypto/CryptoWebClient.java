package com.kantor.webclient.crypto;

import com.kantor.domain.dto.CryptoDto;
import com.kantor.domain.dto.CryptoMainDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CryptoWebClient {

    private static final String CRYPTO_URL = "https://api.coinstats.app/public/v1/coins/";
    private static final String CURRENCY_DETAILS = "?currency=PLN";
    private RestTemplate restTemplate = new RestTemplate();

    public CryptoDto getCryptoRates(String name) {
        CryptoMainDto cryptoMainDto = callGetMethod("{symbol}" + CURRENCY_DETAILS ,CryptoMainDto.class, name);
        return CryptoDto.builder()
                .symbol(cryptoMainDto.getCoin().getSymbol())
                .buy(cryptoMainDto.getCoin().getPrice())
                .sell(cryptoMainDto.getCoin().getPrice())
                .build();
    }

    private <T> T callGetMethod(String url, Class<T> responseType, Object... objects) {
        return restTemplate.getForObject(CRYPTO_URL + url, responseType, objects);
    }
}
