package com.kantor.controller;

import com.kantor.domain.currency.dto.BitcoinDto;
import com.kantor.domain.currency.dto.EuroDto;
import com.kantor.domain.currency.dto.SwissFrancDto;
import com.kantor.domain.currency.dto.USDollarDto;
import com.kantor.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/currency_exchange/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping(value = "{usd}")
    public ResponseEntity<List<USDollarDto>> getUSDollarHistory() {
        return ResponseEntity.ok(historyService.getUSDollarHistory());
    }

    @GetMapping(value = "eur")
    public ResponseEntity<List<EuroDto>> getEuroHistory() {
        return ResponseEntity.ok(historyService.getEuroHistory());
    }

    @GetMapping(value = "chf")
    public ResponseEntity<List<SwissFrancDto>> getSwissFrancHistory() {
        return ResponseEntity.ok(historyService.getSwissFrancHistory());
    }

    @GetMapping(value = "btc")
    public ResponseEntity<List<BitcoinDto>> getBitcoinHistory() {
        return ResponseEntity.ok(historyService.getBitcoinHistory());
    }

    @GetMapping(value = "{currencyName}")
    public ResponseEntity<List<Object>> getCurrencyHistory(@PathVariable String currencyName) {
        return ResponseEntity.ok(historyService.getCurrencyHistory(currencyName));
    }
}
