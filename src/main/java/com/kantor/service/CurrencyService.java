package com.kantor.service;

import com.kantor.domain.Currencies;
import com.kantor.domain.CurrenciesEnum;
import com.kantor.domain.currency.Euro;
import com.kantor.domain.currency.SwissFranc;
import com.kantor.domain.currency.USDollar;
import com.kantor.domain.dto.CurrencyDto;
import com.kantor.exception.CurrencyNotFoundException;
import com.kantor.mapper.CurrencyMapper;
import com.kantor.repository.EuroRepository;
import com.kantor.repository.SwissFrancRepository;
import com.kantor.repository.USDollarRepository;
import com.kantor.webclient.currency.CurrencyWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyWebClient currencyWebClient;
    private final USDollarRepository usDollarRepository;
    private final EuroRepository euroRepository;
    private final SwissFrancRepository swissFrancRepository;
    private final CurrencyMapper currencyMapper;

    public CurrencyDto getCurrencyBidAndAsk(String code) {
        return currencyWebClient.getAskAndBidForCurrency(code);
    }

    public double getCurrencyBuyFromRepository(String code) throws CurrencyNotFoundException {
        if (code.equals(Currencies.PLN)) {
            return 1;
        } else if (code.equals(Currencies.USD)) {
            Optional<USDollar> usDollar = usDollarRepository.findAll().stream()
                    .max(Comparator.comparing(USDollar::getId));
            return usDollar.get().getBuy();
        } else if (code.equals(Currencies.EUR)) {
            Optional<Euro> euro = euroRepository.findAll().stream()
                    .max(Comparator.comparing(Euro::getId));
            return euro.get().getBuy();
        } else if (code.equals(Currencies.CHF)) {
            Optional<SwissFranc> swissFranc = swissFrancRepository.findAll().stream()
                    .max(Comparator.comparing(SwissFranc::getId));
            return swissFranc.get().getBuy();
        } else {
            throw new CurrencyNotFoundException();
        }
    }

    public double getCurrencySellFromRepository(String code) throws CurrencyNotFoundException {
        if (code.equals(Currencies.PLN)) {
            return 1;
        } else if (code.equals(Currencies.USD)) {
            Optional<USDollar> usDollar = usDollarRepository.findAll().stream()
                    .max(Comparator.comparing(USDollar::getId));
            return usDollar.get().getSell();
        } else if (code.equals(Currencies.EUR)) {
            Optional<Euro> euro = euroRepository.findAll().stream()
                    .max(Comparator.comparing(Euro::getId));
            return euro.get().getSell();
        } else if (code.equals(Currencies.CHF)) {
            Optional<SwissFranc> swissFranc = swissFrancRepository.findAll().stream()
                    .max(Comparator.comparing(SwissFranc::getId));
            return swissFranc.get().getSell();
        } else {
            throw new CurrencyNotFoundException();
        }
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
