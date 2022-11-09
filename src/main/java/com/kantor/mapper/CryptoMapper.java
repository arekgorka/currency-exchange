package com.kantor.mapper;

import com.kantor.domain.currency.Bitcoin;
import com.kantor.domain.dto.CryptoDto;
import com.kantor.domain.dto.CurrencyDto;
import org.springframework.stereotype.Service;

@Service
public class CryptoMapper {

    public Bitcoin mapToBitcoin(CryptoDto cryptoDto) {
        return new Bitcoin(
                cryptoDto.getBuy(),
                cryptoDto.getSell(),
                cryptoDto.getSymbol()
        );
    }
}
