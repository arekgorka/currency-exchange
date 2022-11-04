package com.kantor.controller;

import com.kantor.domain.dto.CurrencyDto;
import com.kantor.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/currency_exchange/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping(value = "rates/{code}")
    public ResponseEntity<CurrencyDto> getCurrencyBidAndAsk(@PathVariable String code) {
        return ResponseEntity.ok(currencyService.getCurrencyBidAndAsk(code));
    }
}
