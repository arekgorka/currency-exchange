package com.kantor.mapper;

import com.kantor.domain.currency.Bitcoin;
import com.kantor.domain.currency.dto.BitcoinDto;
import com.kantor.domain.dto.CryptoDto;
import com.kantor.domain.dto.CurrencyDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CryptoMapper {

    public Bitcoin mapToBitcoin(final CryptoDto cryptoDto) {
        return new Bitcoin(
                cryptoDto.getBuy(),
                cryptoDto.getSell(),
                cryptoDto.getSymbol()
        );
    }

    public BitcoinDto mapToBitcoinDto(final Bitcoin bitcoin) {
        return new BitcoinDto(
                bitcoin.getId(),
                bitcoin.getDateTime(),
                bitcoin.getBuy(),
                bitcoin.getSell(),
                bitcoin.getName()
        );
    }

    public List<BitcoinDto> mapToListBitcoinDto(final List<Bitcoin> bitcoinList) {
        return bitcoinList.stream()
                .map(this::mapToBitcoinDto)
                .collect(Collectors.toList());
    }
}
