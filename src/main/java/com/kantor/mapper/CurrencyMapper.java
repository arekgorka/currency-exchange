package com.kantor.mapper;

import com.kantor.domain.currency.Euro;
import com.kantor.domain.currency.SwissFranc;
import com.kantor.domain.currency.USDollar;
import com.kantor.domain.currency.dto.EuroDto;
import com.kantor.domain.currency.dto.SwissFrancDto;
import com.kantor.domain.currency.dto.USDollarDto;
import com.kantor.domain.dto.CurrencyDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyMapper {

    public USDollar mapToUSDDollar(final CurrencyDto currencyDto) {
        return new USDollar(
                currencyDto.getBuy(),
                currencyDto.getSell(),
                currencyDto.getCode()
        );
    }

    public Euro mapToEuro(final CurrencyDto currencyDto) {
        return new Euro(
                currencyDto.getBuy(),
                currencyDto.getSell(),
                currencyDto.getCode()
        );
    }

    public SwissFranc mapToSwissFranc(final CurrencyDto currencyDto) {
        return new SwissFranc(
                currencyDto.getBuy(),
                currencyDto.getSell(),
                currencyDto.getCode()
        );
    }

    public USDollarDto mapToUSDollarDto(final USDollar usDollar) {
        return new USDollarDto(
                usDollar.getId(),
                usDollar.getDateTime(),
                usDollar.getBuy(),
                usDollar.getSell(),
                usDollar.getName()
        );
    }

    public EuroDto mapToEuroDto(final Euro euro) {
        return new EuroDto(
                euro.getId(),
                euro.getDateTime(),
                euro.getBuy(),
                euro.getSell(),
                euro.getName()
        );
    }

    public SwissFrancDto mapToSwissFrancDto(final SwissFranc swissFranc) {
        return new SwissFrancDto(
                swissFranc.getId(),
                swissFranc.getDateTime(),
                swissFranc.getBuy(),
                swissFranc.getSell(),
                swissFranc.getName()
        );
    }

    public List<USDollarDto> mapToListUSDollarDto(final List<USDollar> usDollarList) {
        return usDollarList.stream()
                .map(this::mapToUSDollarDto)
                .collect(Collectors.toList());
    }

    public List<EuroDto> mapToListEuroDto(final List<Euro> euroList) {
        return euroList.stream()
                .map(this::mapToEuroDto)
                .collect(Collectors.toList());
    }

    public List<SwissFrancDto> mapToListSwissFrancDto(final List<SwissFranc> swissFrancList) {
        return swissFrancList.stream()
                .map(this::mapToSwissFrancDto)
                .collect(Collectors.toList());
    }
}
