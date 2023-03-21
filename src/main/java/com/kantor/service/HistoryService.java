package com.kantor.service;

import com.kantor.domain.Currencies;
import com.kantor.domain.CurrenciesEnum;
import com.kantor.domain.Currency;
import com.kantor.domain.currency.Bitcoin;
import com.kantor.domain.currency.Euro;
import com.kantor.domain.currency.SwissFranc;
import com.kantor.domain.currency.USDollar;
import com.kantor.domain.currency.dto.BitcoinDto;
import com.kantor.domain.currency.dto.EuroDto;
import com.kantor.domain.currency.dto.SwissFrancDto;
import com.kantor.domain.currency.dto.USDollarDto;
import com.kantor.domain.dto.CurrencyDto;
import com.kantor.exception.CurrencyNotFoundException;
import com.kantor.mapper.CryptoMapper;
import com.kantor.mapper.CurrencyMapper;
import com.kantor.repository.BitcoinRepository;
import com.kantor.repository.EuroRepository;
import com.kantor.repository.SwissFrancRepository;
import com.kantor.repository.USDollarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final USDollarRepository usDollarRepository;
    private final EuroRepository euroRepository;
    private final SwissFrancRepository swissFrancRepository;
    private final BitcoinRepository bitcoinRepository;
    private final CurrencyMapper currencyMapper;
    private final CryptoMapper cryptoMapper;

    public List<USDollarDto> getUSDollarHistory() {
        List<USDollar> usDollarList = usDollarRepository.findAll();
        List<USDollar> usDollars = usDollarList.stream()
                .filter(s -> s.getDateTime().isAfter(LocalDateTime.now().minusDays(3)))
                .collect(Collectors.toList());
        return currencyMapper.mapToListUSDollarDto(usDollars);
    }

    public List<EuroDto> getEuroHistory() {
        List<Euro> euroList = euroRepository.findAll();
        List<Euro> euros = euroList.stream()
                .filter(s -> s.getDateTime().isAfter(LocalDateTime.now().minusDays(3)))
                .collect(Collectors.toList());
        return currencyMapper.mapToListEuroDto(euros);
    }

    public List<SwissFrancDto> getSwissFrancHistory() {
        List<SwissFranc> swissFrancList = swissFrancRepository.findAll();
        List<SwissFranc> swissFrancs = swissFrancList.stream()
                .filter(s -> s.getDateTime().isAfter(LocalDateTime.now().minusDays(3)))
                .collect(Collectors.toList());
        return currencyMapper.mapToListSwissFrancDto(swissFrancs);
    }

    public List<BitcoinDto> getBitcoinHistory() {
        List<Bitcoin> bitcoinList = bitcoinRepository.findAll();
        List<Bitcoin> bitcoins = bitcoinList.stream()
                .filter(s -> s.getDateTime().isAfter(LocalDateTime.now().minusDays(3)))
                .collect(Collectors.toList());
        return cryptoMapper.mapToListBitcoinDto(bitcoins);
    }

    public List<CurrencyDto> getCurrencyHistory(String currencyName) throws CurrencyNotFoundException {
        return new ArrayList<>(); //tymczasowa zaślepka
    }
}
