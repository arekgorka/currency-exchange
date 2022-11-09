package com.kantor.service;

import com.kantor.domain.Currencies;
import com.kantor.domain.CurrenciesEnum;
import com.kantor.domain.currency.Euro;
import com.kantor.domain.currency.SwissFranc;
import com.kantor.domain.currency.USDollar;
import com.kantor.domain.dto.CurrencyDto;
import com.kantor.mapper.CurrencyMapper;
import com.kantor.repository.EuroRepository;
import com.kantor.repository.SwissFrancRepository;
import com.kantor.repository.USDollarRepository;
import com.kantor.webclient.currency.CurrencyWebClient;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyWebClient currencyWebClient;
    private final USDollarRepository usDollarRepository;
    private final EuroRepository euroRepository;
    private final SwissFrancRepository swissFrancRepository;
    private final CurrencyMapper currencyMapper;

    public CurrencyDto getCurrencyBidAndAsk(String code) {
        return currencyWebClient.getAskAndBitForCurrency(code);
    }

    public void saveUSDollar() {
        CurrencyDto currencyDto = getCurrencyBidAndAsk(CurrenciesEnum.USD.name());
        USDollar usDollar = currencyMapper.mapToUSDDollar(currencyDto);
        usDollarRepository.save(usDollar);
    }

    public void saveEuro() {
        CurrencyDto currencyDto = getCurrencyBidAndAsk(CurrenciesEnum.EUR.name());
        Euro euro = currencyMapper.mapToEuro(currencyDto);
        euroRepository.save(euro);
    }

    public void saveSwissFranc() {
        CurrencyDto currencyDto = getCurrencyBidAndAsk(CurrenciesEnum.CHF.name());
        SwissFranc swissFranc = currencyMapper.mapToSwissFranc(currencyDto);
        swissFrancRepository.save(swissFranc);
    }
}
