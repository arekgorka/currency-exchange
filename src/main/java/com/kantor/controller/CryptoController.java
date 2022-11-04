package com.kantor.controller;

import com.kantor.domain.dto.CryptoDto;
import com.kantor.service.CryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/currency_exchange/crypto")
@RequiredArgsConstructor
public class CryptoController {

    private final CryptoService cryptoService;

    @GetMapping(value = "rates/{name}")
    public ResponseEntity<CryptoDto> getCryptoRates(@PathVariable String name) {
        return ResponseEntity.ok(cryptoService.getCryptoRates(name));
    }

}
