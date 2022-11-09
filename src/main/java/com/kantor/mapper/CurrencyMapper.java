package com.kantor.mapper;

import com.kantor.domain.currency.Euro;
import com.kantor.domain.currency.SwissFranc;
import com.kantor.domain.currency.USDollar;
import com.kantor.domain.dto.CurrencyDto;
import org.springframework.stereotype.Service;

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
}
