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

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
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
        switch (code) {
            case Currencies.PLN:
                return 1;
            case Currencies.USD:
                Optional<USDollar> usDollar = usDollarRepository.findAll().stream()
                        .max(Comparator.comparing(USDollar::getId));
                return usDollar.orElseThrow().getBuy();
            case Currencies.EUR:
                Optional<Euro> euro = euroRepository.findAll().stream()
                        .max(Comparator.comparing(Euro::getId));
                return euro.orElseThrow().getBuy();
            case Currencies.CHF:
                Optional<SwissFranc> swissFranc = swissFrancRepository.findAll().stream()
                        .max(Comparator.comparing(SwissFranc::getId));
                return swissFranc.orElseThrow().getBuy();
            default:
                throw new CurrencyNotFoundException();
        }
    }

    public double getCurrencySellFromRepository(String code) throws CurrencyNotFoundException {
        switch (code) {
            case Currencies.PLN:
                return 1;
            case Currencies.USD:
                Optional<USDollar> usDollar = usDollarRepository.findAll().stream()
                        .max(Comparator.comparing(USDollar::getId));
                return usDollar.orElseThrow().getSell();
            case Currencies.EUR:
                Optional<Euro> euro = euroRepository.findAll().stream()
                        .max(Comparator.comparing(Euro::getId));
                return euro.orElseThrow().getSell();
            case Currencies.CHF:
                Optional<SwissFranc> swissFranc = swissFrancRepository.findAll().stream()
                        .max(Comparator.comparing(SwissFranc::getId));
                return swissFranc.orElseThrow().getSell();
            default:
                throw new CurrencyNotFoundException();
        }
    }

    public void saveUSDollar() {
        CurrencyDto currencyDto = getCurrencyBidAndAsk(CurrenciesEnum.USD.name());
        USDollar usDollar = currencyMapper.mapToUSDDollar(currencyDto);
        double buyDollar = roundTo2(usDollar.getBuy());
        double sellDollar = roundTo2(usDollar.getSell());
        USDollar roundedUSDollar = new USDollar(buyDollar,sellDollar, usDollar.getName());
        usDollarRepository.save(roundedUSDollar);
    }

    public void saveEuro() {
        CurrencyDto currencyDto = getCurrencyBidAndAsk(CurrenciesEnum.EUR.name());
        Euro euro = currencyMapper.mapToEuro(currencyDto);
        double buyEuro = roundTo2(euro.getBuy());
        double sellEuro = roundTo2(euro.getSell());
        Euro roundedEuro = new Euro(buyEuro,sellEuro, euro.getName());
        euroRepository.save(roundedEuro);
    }

    public void saveSwissFranc() {
        CurrencyDto currencyDto = getCurrencyBidAndAsk(CurrenciesEnum.CHF.name());
        SwissFranc swissFranc = currencyMapper.mapToSwissFranc(currencyDto);
        double buyFranc = roundTo2(swissFranc.getBuy());
        double sellFranc = roundTo2(swissFranc.getSell());
        SwissFranc roundedSwissFranc = new SwissFranc(buyFranc,sellFranc, swissFranc.getName());
        swissFrancRepository.save(roundedSwissFranc);
    }

    public static double roundTo2(double value) {
        int precision = 2;
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(precision, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
