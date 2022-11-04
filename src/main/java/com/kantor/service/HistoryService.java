package com.kantor.service;

import com.kantor.domain.currency.dto.BitcoinDto;
import com.kantor.domain.currency.dto.EuroDto;
import com.kantor.domain.currency.dto.SwissFrancDto;
import com.kantor.domain.currency.dto.USDollarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    public List<USDollarDto> getUSDollarHistory() {
        return new ArrayList<>();
    }

    public List<EuroDto> getEuroHistory() {
        return new ArrayList<>();
    }

    public List<SwissFrancDto> getSwissFrancHistory() {
        return new ArrayList<>();
    }

    public List<BitcoinDto> getBitcoinHistory() {
        return new ArrayList<>();
    }

    public List<Object> getCurrencyHistory(String currencyName) {
        return new ArrayList<>();
    }
}
