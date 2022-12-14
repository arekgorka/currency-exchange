package com.kantor.service;

import com.kantor.domain.Currencies;
import com.kantor.domain.CurrenciesEnum;
import com.kantor.domain.currency.Bitcoin;
import com.kantor.domain.dto.CryptoDto;
import com.kantor.exception.CurrencyNotFoundException;
import com.kantor.mapper.CryptoMapper;
import com.kantor.repository.BitcoinRepository;
import com.kantor.webclient.crypto.CryptoWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CryptoService {

    private final CryptoWebClient cryptoWebClient;
    private final BitcoinRepository bitcoinRepository;
    private final CryptoMapper cryptoMapper;

    public CryptoDto getCryptoRates(String name) {
        return cryptoWebClient.getCryptoRates(name);
    }

    public double getCryptoBuyFromRepository(String name) throws CurrencyNotFoundException {
        if (name.equals(Currencies.BTC)) {
            Optional<Bitcoin> bitcoin = bitcoinRepository.findAll().stream()
                    .max(Comparator.comparing(Bitcoin::getId));
            return bitcoin.get().getBuy();
        } else {
            throw new CurrencyNotFoundException();
        }
    }

    public double getCryptoSellFromRepository(String name) throws CurrencyNotFoundException {
        if (name.equals(Currencies.BTC)) {
            Optional<Bitcoin> bitcoin = bitcoinRepository.findAll().stream()
                    .max(Comparator.comparing(Bitcoin::getId));
            return bitcoin.get().getSell();
        } else {
            throw new CurrencyNotFoundException();
        }
    }

    public void saveBitcoin() {
        CryptoDto cryptoDto = getCryptoRates(CurrenciesEnum.BTC.name());
        Bitcoin bitcoin = cryptoMapper.mapToBitcoin(cryptoDto);
        bitcoinRepository.save(bitcoin);
    }
}
