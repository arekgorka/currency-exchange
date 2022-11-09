package com.kantor.service;

import com.kantor.domain.CurrenciesEnum;
import com.kantor.domain.currency.Bitcoin;
import com.kantor.domain.dto.CryptoDto;
import com.kantor.mapper.CryptoMapper;
import com.kantor.repository.BitcoinRepository;
import com.kantor.webclient.crypto.CryptoWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CryptoService {

    private final CryptoWebClient cryptoWebClient;
    private final BitcoinRepository bitcoinRepository;
    private final CryptoMapper cryptoMapper;

    public CryptoDto getCryptoRates(String name) {

        return cryptoWebClient.getCryptoRates(name);
    }

    public void saveBitcoin() {
        CryptoDto cryptoDto = getCryptoRates(CurrenciesEnum.BTC.name());
        Bitcoin bitcoin = cryptoMapper.mapToBitcoin(cryptoDto);
        bitcoinRepository.save(bitcoin);
    }
}
