package com.kantor.service;

import com.kantor.domain.dto.CryptoDto;
import com.kantor.webclient.crypto.CryptoWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CryptoService {

    private final CryptoWebClient cryptoWebClient;

    public CryptoDto getCryptoRates(String name) {
        return cryptoWebClient.getCryptoRates(name);
    }
}
