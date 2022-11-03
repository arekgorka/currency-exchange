package com.kantor.service;

import com.kantor.domain.Currencies;
import com.kantor.domain.dto.CurrencyDto;
import com.kantor.webclient.currency.CurrencyWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyWebClient currencyWebClient;

    public CurrencyDto getCurrencyBidAndAsk(String code) {
        return currencyWebClient.getAskAndBitForCurrency(code);
    }
}
