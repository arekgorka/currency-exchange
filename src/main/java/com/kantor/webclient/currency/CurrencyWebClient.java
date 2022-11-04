package com.kantor.webclient.currency;

import com.kantor.domain.dto.CurrencyDto;
import com.kantor.domain.dto.CurrencyMainDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CurrencyWebClient {

    private static final String CURRENCY_URL = "http://api.nbp.pl/api/exchangerates/rates/c/";
    private RestTemplate restTemplate = new RestTemplate();

    public CurrencyDto getAskAndBitForCurrency(String code) {
        CurrencyMainDto currencyMainDto = callGetMethod("{code}", CurrencyMainDto.class, code);
        return CurrencyDto.builder()
                .code(currencyMainDto.getCode())
                .buy(currencyMainDto.getRates().get(0).getBid())
                .sell(currencyMainDto.getRates().get(0).getAsk())
                .build();
    }

    private <T> T callGetMethod(String url, Class<T> responseType, Object... objects) {
        return restTemplate.getForObject(CURRENCY_URL + url, responseType, objects);
    }
}
