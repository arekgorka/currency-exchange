package com.kantor.scheduler;

import com.kantor.service.CryptoService;
import com.kantor.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class SaveCurrenciesScheduler {

    private final CurrencyService currencyService;
    private final CryptoService cryptoService;

    @Scheduled(cron = "0 * * * * *")
    public void saveCurrenciesAndCrypto() {
        currencyService.saveUSDollar();
        currencyService.saveEuro();
        currencyService.saveSwissFranc();
        cryptoService.saveBitcoin();
    }
}
